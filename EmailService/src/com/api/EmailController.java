package com.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model.Email;
import com.utils.EmailThread;


@RestController
public class EmailController {


	@RequestMapping(value="/api/email" , method=RequestMethod.POST)
	public ResponseEntity<String>  sendMail(@RequestBody Email email) {

	
		
		EmailThread mailThread = new EmailThread(email);
        Thread t = new Thread(mailThread);
        t.start(); 
		
		return new ResponseEntity<String>("Email Sent" , HttpStatus.OK) ;

	}
}
