package com.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.Email;

@RestController
public class EmailController {


	@RequestMapping(value="/api/email" , method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Email>  greeting() {

		Email e = new Email();
		e.setComp_id(8868L);
	
		return new ResponseEntity<Email>(e , HttpStatus.BAD_REQUEST) ;

	}
}
