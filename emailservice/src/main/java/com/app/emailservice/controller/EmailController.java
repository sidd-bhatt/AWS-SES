package com.app.emailservice.controller;

import com.amazonaws.services.simpleemail.model.AmazonSimpleEmailServiceException;
import com.app.emailservice.service.EmailService;
import com.app.emailservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping("/send")
	public String sendAWSEmail(@RequestBody User user) throws AmazonSimpleEmailServiceException {
		emailService.sendEmail(user);
		return "Email sent";
	}

}
