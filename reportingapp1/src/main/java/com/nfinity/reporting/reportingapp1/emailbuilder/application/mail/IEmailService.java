package com.nfinity.reporting.reportingapp1.emailbuilder.application.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public interface IEmailService {

	
	public void sendEmail(SimpleMailMessage email);
	public SimpleMailMessage buildEmail(String email, String appUrl, String resetCode);
}
