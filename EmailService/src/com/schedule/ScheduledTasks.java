package com.schedule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
	public void resendPendingEmails() throws IOException {

		List<Email> pendingEmails =  emailService.getPendingEmails();

		if(pendingEmails != null && pendingEmails.size() > 0){
			for(Email email : pendingEmails){
				email.setAttachments( emailService.getAttachmentsById(email.getId()));
				File tempFile = File.createTempFile(email.getAttachments().get(0).getFileName(), email.getAttachments().get(0).getExt(), null);
				FileOutputStream fos = new FileOutputStream(tempFile);
				fos.write(email.getAttachments().get(0).getContent());
				fos.close();
				EmailThread mailThread = new EmailThread(email,emailService,tempFile);
				Thread t = new Thread(mailThread);
				t.start();
			}

		}


	}


}
