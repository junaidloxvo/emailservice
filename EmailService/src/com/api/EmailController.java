package com.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model.Email;
import com.service.EmailService;
import com.utils.EmailThread;


@RestController
public class EmailController {



	@Autowired
	EmailService emailService ;
	
	@RequestMapping(value="/api/sendEmail" , method=RequestMethod.POST)
	public ResponseEntity<String>  sendMail(@RequestBody Email email) {


		
	
		EmailThread mailThread = new EmailThread(email,emailService);
		Thread t = new Thread(mailThread);
		 t.start(); 

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
}
