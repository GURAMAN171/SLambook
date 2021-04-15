package com.slam.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.slam.dao.UserRepository;
import com.slam.helper.*;
import com.slam.model.User;

@Controller
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder PasswordEncoder;

	@Autowired
	private UserRepository userRepository;

	/*
	 * for testing
	 * 
	 * @Autowired private UserRepository userRepository;
	 * 
	 * @GetMapping("/test")
	 * 
	 * @ResponseBody public String test() { //creating object for user User user=new
	 * User(); user.setName("AMAN"); user.setEmail("guraman@gmail.com");
	 * userRepository.save(user); //to save single entity return "Working"; }
	 */
	@RequestMapping("/")
	public String home(Model model) // attributes used for rendering views
	{
		model.addAttribute("title", "Home - Slam Book");
		return "home";
	}

	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About - Slam Book");
		return "about";
	}

	// for sign up page
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "SignUp - Slam Book");
		model.addAttribute("user", new User());
		return "signup";
	}

	// handler for Registering user
	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user, // map a form's inputs to a bean & bean used is
																			// validated
			BindingResult result1, @RequestParam("profileimage") MultipartFile file, Model model, HttpSession session)

	{
		try {

			if (result1.hasErrors()) // ServerSide Validation
			{
				System.out.println("ERROR" + result1.toString());
				model.addAttribute("user", user);
				return "signup";
			}
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setPassword(PasswordEncoder.encode(user.getPassword()));

			// ----------------------Processing and uploading file
			if (file.isEmpty()) {
				// if file is empty than give this mesg
				System.out.println("Error in uploading image");

			} else {
				// move file to folder and update name to user table
				user.setImage(file.getOriginalFilename());
				File saveFile = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING); // static method
				System.out.println("Image Uploaded");
			}

			// --------------------
			System.out.println("User:" + user);

			User result = this.userRepository.save(user);
			model.addAttribute("user", new User());
			// message class from helper defining content and type
			session.setAttribute("message", new Message("SUCCESSFULLY REGISTERED", "alert-success"));
			return "signup";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", "user");
			session.setAttribute("message", new Message("Something went wrong" + e.getMessage(), "alert-danger"));
			return "signup";
		}
	}

	/* handler for our user login */
	@GetMapping("/loginto") // url to opn login page
	public String ourLogin(Model model) {
		model.addAttribute("title", "Login - Slam Book");
		return "login";
	}
}
