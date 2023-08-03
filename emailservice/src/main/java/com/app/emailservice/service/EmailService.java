package com.app.emailservice.service;

import com.amazonaws.services.simpleemail.model.AmazonSimpleEmailServiceException;
import com.app.emailservice.utility.AWSEmailService;
import com.app.emailservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private AWSEmailService awsEmailService;
	public void sendEmail(User user) throws AmazonSimpleEmailServiceException {
		awsEmailService.sendEmail(user);
	}

}

