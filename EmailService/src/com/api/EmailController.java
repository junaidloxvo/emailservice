package com.api;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Attachment;
import com.model.Email;
import com.mysql.fabric.xmlrpc.base.Array;
import com.service.EmailService;
import com.utils.EmailThread;


@RestController
public class EmailController {



	@Autowired
	EmailService emailService ;
	
	@RequestMapping(value="/api/createEmail" , method=RequestMethod.POST)
	public ResponseEntity<String>  sendMail(@RequestBody Email email) {


 

		return new ResponseEntity<String>("Email Sent" , HttpStatus.OK) ;

	}
	
	@RequestMapping(value="/api/email/{id}" , method=RequestMethod.GET)
	public ResponseEntity<Email>  sendMail(@PathVariable Long id) {


		Email email = emailService.getEmailByid(id);
		
		if(email == null){
			return new ResponseEntity<Email>(email , HttpStatus.BAD_REQUEST) ;
		}

		return new ResponseEntity<Email>(email , HttpStatus.OK) ;

	}
	
	@RequestMapping(value="/api/unsended" , method=RequestMethod.GET)
	public ResponseEntity<List<Email>>  unsended() {


		List<Email> emails = emailService.getPendingEmails();
		


		return new ResponseEntity<List<Email>>(emails , HttpStatus.OK) ;

	}
	
	 @RequestMapping(value="/api/sendEmail", method=RequestMethod.POST)
	    public @ResponseBody String sendEmail(@RequestParam("email") String emailJson,
	            @RequestParam("file") MultipartFile[] files) throws JsonParseException, JsonMappingException, IOException{
		 
		  ObjectMapper mapper = new ObjectMapper();
		  Email email = mapper.readValue(emailJson, Email.class);
		  File[] tempfiles = new File[files.length] ;
		  List<Attachment> attachments = new ArrayList<Attachment>();
		 if (files != null && files.length >0) {
		 		try {
		 			
		 			for(int i =0 ;i< files.length; i++){
		 				byte[] bytes = files[i].getBytes();
		 				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(files[i].getOriginalFilename())));
		 				stream.write(bytes);
		 				stream.close();
		 				File convFile = new File( files[i].getOriginalFilename());
		 				files[i].transferTo(convFile);
		 				tempfiles[i] =convFile ;
		 				
		 				Attachment att = new Attachment();
		 				att.setFileName(files[i].getOriginalFilename());
		 				att.setContent(bytes);
		 				att.setExt(this.getFileExtension(files[i].getOriginalFilename()));
		 				attachments.add(att);
		 				email.setAttachments(attachments);
		 			}
	                
	               
	               
	                
	   			
	                
	              
	                
	          
	            	EmailThread mailThread = new EmailThread(email,emailService,tempfiles);
	        		Thread t = new Thread(mailThread);
	        		 t.start();
	                
	                return "Email Sent";
	            } catch (Exception e) {
	             
	            }
	        } else {
	            
	        }
	        return "";
	    }
	 
	 private String getFileExtension(String path){
		 
		 String extension = "";

			 int i = path.lastIndexOf('.');
			 if (i > 0) {
				 extension = path.substring(i+1);
			 }
			 
			 return extension;
		 
	 }
}
