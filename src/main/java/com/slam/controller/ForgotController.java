package com.slam.controller;

import java.util.Random;

//import javax.mail.Session;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.slam.dao.UserRepository;
import com.slam.model.User;
import com.slam.service.*;

@Controller
public class ForgotController {
	Random r=new Random(1000); 
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	//email id form opn handler
	@RequestMapping("/forgot")
	public String openEmailForm()
	{
		return "frgtemlfrm";
	}
	
	@PostMapping("/sendotp")
	public String sendOTP(@RequestParam("email") String email,HttpSession session)
	{
		System.out.println("EMAIL "+email);
		
		//genrt otp using 4 so min no is 1000
		int otp=r.nextInt(9999);
		System.out.println("OTP : "+otp);
		
		//code for sending otp to email
		String subject="OTP FROM SLAM BOOK";
		String message=""
				+"<div style='border:1 px solid #e2e2e2; padding 20px'>"
				+"<h1>"
				+"OTP IS : "
				+"<b>"+otp
				+"</n>"
				+"</h1>"
				+"</div>";
				
		String to=email;
		boolean flag = this.emailService.sendEmail(subject,message,to);
		
		if(flag)
		{
			session.setAttribute("myotp", otp);
			session.setAttribute("email", email);
			return "verifyotp";	
		}
		else
		{
			session.setAttribute("message", "CHECK YOUR EMAIL ID!");
			
			return "frgtemlfrm";
		}
		
	}
	@PostMapping("/verify")
	String verifyOtp(@RequestParam("otp") int otp,HttpSession session)
	{
		int myOtp=(int)session.getAttribute("myotp");
		String email=(String)session.getAttribute("email");
		if(myOtp==otp)
		{
			User user=this.userRepository.getUserByUserEmail(email);
			if(user==null)
			{
				//snd error mesg
				session.setAttribute("message", "User does not exist with this email!!");
				return "frgtemlfrm";
			}
			else
			{
				//senf chng pswrd form
				//return "pswrdchngfrm";
				
			}
			return "pswrdchngfrm";
		}
		else
		{
			session.setAttribute("message"," OTP entered is incorrect!");
			return "verifyotp";
		}
	}
	@PostMapping("/chngpswrd")
	public String chngPass(@RequestParam("newpassword")String newpassword,HttpSession session)
	{
		String email=(String)session.getAttribute("email");
		User user=this.userRepository.getUserByUserEmail(email);
		user.setPassword(this.bcrypt.encode(newpassword));
		this.userRepository.save(user);
		
		return "redirect:/loginto?change=Password changed successfully";
		
	}

}
