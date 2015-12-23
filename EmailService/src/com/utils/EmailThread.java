package com.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Email;
import com.service.EmailService;

public class EmailThread implements Runnable {


	
	EmailService service ;
	
	
	private Email email ;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.sendMail();
	}
	public EmailThread(Email email,EmailService service ){
		this.email = email ;
		this.service = service;
	}



	private Session getSession() {

		final String username = "testput007@gmail.com";
		final String password = "testingapp";

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getInstance(props, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		return session;



	}

	private void sendMail() {

		try {
			Session session = getSession();
			Message mailMessage = new MimeMessage(session);
			MimeMessage simpleMessage = new MimeMessage(session);
			setRecipients(simpleMessage, email.getRecipients(), email.getReceipents_cc(), "");
			simpleMessage.setText(email.getContent_html(), "utf-8", "html");
			simpleMessage.setSubject(email.getTopic());
			mailMessage.saveChanges();

			Transport.send(simpleMessage);
			
			
			email.setSended(true);
			if(email.getId() == null){
				service.saveEmail(email);
			}else{
				service.updateEmail(email);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			email.setSended(false);
			email.setLast_error(e.getMessage());
			service.saveEmail(email);
		}

	}

	private void setRecipients(Message mailMessage, String to, String cc,
			String bcc) throws Exception {

		if (mailMessage != null) {
			try {
				if (to != null) {
					List toList = new ArrayList();
					StringTokenizer tokenizer = new StringTokenizer(to, ",");
					while (tokenizer.hasMoreTokens()) {
						toList.add(new InternetAddress(tokenizer.nextToken()));
					}
					mailMessage.setRecipients(Message.RecipientType.TO,
							(InternetAddress[]) toList
							.toArray(new InternetAddress[0]));
				}
				// set CC recipients
				if (cc != null) {
					List ccList = new ArrayList();
					StringTokenizer tokenizer = new StringTokenizer(cc, ",");
					while (tokenizer.hasMoreTokens()) {
						ccList.add(new InternetAddress(tokenizer.nextToken()));
					}
					mailMessage.setRecipients(Message.RecipientType.CC,
							(InternetAddress[]) ccList
							.toArray(new InternetAddress[0]));
				}

				// set BCC recipients
				if (bcc != null) {
					List bccList = new ArrayList();
					StringTokenizer tokenizer = new StringTokenizer(bcc, ",");
					while (tokenizer.hasMoreTokens()) {
						bccList.add(new InternetAddress(tokenizer.nextToken()));
					}
					mailMessage.setRecipients(Message.RecipientType.BCC,
							(InternetAddress[]) bccList
							.toArray(new InternetAddress[0]));
				}
			} catch (MessagingException e) {
			} catch (Exception e) {
			}
		}
	}

}
