package com.nfinity.reporting.reportingapp1.emailbuilder.application.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class EmailService implements IEmailService{

	@Autowired
	public JavaMailSender emailSender;

	
	@Async
	public void sendEmail(SimpleMailMessage email) {
		emailSender.send(email);
	}
	
	public SimpleMailMessage buildEmail(String email, String appUrl, String resetCode)
	{
		SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
		passwordResetEmail.setTo(email);
		passwordResetEmail.setSubject("Password Reset Request");
		passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
				+ "/reset?token=" + resetCode);
		 System.out.println("App url " + passwordResetEmail.getText());
		
		return passwordResetEmail;
	}
	
}
