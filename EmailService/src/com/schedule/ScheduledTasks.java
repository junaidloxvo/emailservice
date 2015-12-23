package com.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.model.Email;
import com.service.EmailService;
import com.utils.EmailThread;

@Component
public class ScheduledTasks {

	@Autowired
	
	EmailService emailService ;
	
	
	

	    @Scheduled(fixedRate = 9000)
	    public void resendPendingEmails() {
	    	List<Email> pendingEmails =  emailService.getPendingEmails();
	    	
	    	if(pendingEmails != null && pendingEmails.size() > 0){
	    		for(Email email : pendingEmails){
	    			EmailThread mailThread = new EmailThread(email,emailService);
	    			Thread t = new Thread(mailThread);
	    			t.start();
	    		}

	    	}
	    	
	        
	    }
	    
	  
}
