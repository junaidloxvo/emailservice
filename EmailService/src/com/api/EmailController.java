package com.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dao.EmailService;
import com.model.Email;
import com.utils.EmailThread;


@RestController
public class EmailController {



	@Autowired
	EmailService emailService ;
	
	@RequestMapping(value="/api/email" , method=RequestMethod.POST)
	public ResponseEntity<String>  sendMail(@RequestBody Email email) {


		
	
		EmailThread mailThread = new EmailThread(email,emailService);
		Thread t = new Thread(mailThread);
		 t.start(); 

		return new ResponseEntity<String>("Email Sent" , HttpStatus.OK) ;

	}
}
