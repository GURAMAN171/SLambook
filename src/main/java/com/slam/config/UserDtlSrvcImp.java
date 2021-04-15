package com.slam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.slam.dao.UserRepository;
import com.slam.model.User;

public class UserDtlSrvcImp implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// Fetching data of user from database
		User user = userRepository.getUserByUserEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("COULD NOT FOUND USER");
		}
		OurUserDetails ourUserDetails = new OurUserDetails(user);
		return ourUserDetails;
	}

}
