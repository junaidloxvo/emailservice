package com.api;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
	
	@RequestMapping(value="/api/sendEmail" , method=RequestMethod.POST)
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
	
	 @RequestMapping(value="/api/upload", method=RequestMethod.POST)
	    public @ResponseBody String handleFileUpload(@RequestParam("email") String emailJson,
	            @RequestParam("file") MultipartFile file){
	        if (!file.isEmpty()) {
	            try {
	            	ObjectMapper mapper = new ObjectMapper();
	            	
	                byte[] bytes = file.getBytes();
	                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
	                stream.write(bytes);
	                stream.close();
	                File convFile = new File( file.getOriginalFilename());
	   			 	file.transferTo(convFile);
	   			
	                
	                Email email = mapper.readValue(emailJson, Email.class);
	                
	                List<Attachment> attachments = new ArrayList<Attachment>();
	                Attachment att = new Attachment();
	                att.setFileName(file.getOriginalFilename());
	                att.setContent(bytes);
	                att.setExt(this.getFileExtension(file.getOriginalFilename()));
	                attachments.add(att);
	                email.setAttachments(attachments);
	            	EmailThread mailThread = new EmailThread(email,emailService,convFile);
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
