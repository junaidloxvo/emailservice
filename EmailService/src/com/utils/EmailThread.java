package com.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.web.multipart.MultipartFile;

import com.model.Email;
import com.service.EmailService;

public class EmailThread implements Runnable {



	EmailService service ;
	File[] file;

	private Email email ;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.sendMail();
	}
	public EmailThread(Email email,EmailService service ,File[] file  ){
		this.email = email ;
		this.service = service;
		this.file = file ;
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

			MimeMessage message = new MimeMessage(session);
			setRecipients(message, email.getRecipients(), email.getReceipents_cc(), "");

			message.setSubject(email.getTopic());

			MimeBodyPart  messageBodyPart = new MimeBodyPart();

			messageBodyPart.setText(email.getContent_html());
			messageBodyPart.setContent(email.getContent_html(), "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			if (file != null && file.length >0) {
				// attach the file to the message

				for(int i =0 ;i< file.length; i++){
					MimeBodyPart  messageBodyAttachment = new MimeBodyPart();
					messageBodyAttachment.attachFile(file[i]);
					multipart.addBodyPart(messageBodyAttachment);
				}

			}


			message.setContent(multipart);


			Transport.send(message);


			email.setSended(true);
			if(email.getId() == null){
				int id = service.saveEmail(email);
				if(file != null){
					for(int i =0 ;i< file.length; i++){
						
						email.getAttachments().get(i).setEmail_id(new Long(id));
						service.saveAttachment(email.getAttachments().get(i));
					}

				}
			}else{
				service.updateEmail(email);
			}

		} catch (Exception e) {
			e.printStackTrace();
			email.setSended(false);
			email.setLast_error(e.getMessage());
			int id =service.saveEmail(email);
			if(file != null){
				email.getAttachments().get(0).setEmail_id(new Long(id));
				service.saveAttachment(email.getAttachments().get(0));
			}
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
