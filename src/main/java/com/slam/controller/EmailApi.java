package com.slam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.slam.model.EmailRequest;
import com.slam.service.EmailService;

@RestController
public class EmailApi {
	@Autowired
	private EmailService emailService;

	@RequestMapping("/welcome")
	public String welcome() {
		return "hy this is email api";
	}

	// API used to send email
	@RequestMapping(value = "/sendemail", method = RequestMethod.POST)
	public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request) {
		System.out.println(request);
		boolean result = this.emailService.sendEmail(request.getSubject(), request.getMessage(), request.getTo());
		if (result) {
			return ResponseEntity.ok("Email snd succesfully");
		} else {
			return ResponseEntity.ok("some error");
		}
	}

}
