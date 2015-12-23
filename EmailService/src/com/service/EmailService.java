package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.model.Email;


public interface EmailService {


	public  void saveEmail(Email email);
	
	public  void updateEmail(Email email);
	
	public List<Email> getPendingEmails();
    
    
}
