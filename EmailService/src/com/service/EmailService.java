package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.model.Attachment;
import com.model.Email;


public interface EmailService {


	public  int saveEmail(Email email);
	
	public  void updateEmail(Email email);
	
	public List<Email> getPendingEmails();
	
	public Email getEmailByid(Long id);

	public void saveAttachment(Attachment attachment);
	
	public List<Attachment> getAttachmentsById(Long id );
}
