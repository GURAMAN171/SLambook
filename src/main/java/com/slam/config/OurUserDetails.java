package com.slam.config;

import java.util.Collection;
import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.*;

import com.slam.model.User;

public class OurUserDetails implements UserDetails 
{
	private User user;
	
	public OurUserDetails(User user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(user.getRole());
		return List.of(simpleGrantedAuthority);
	}

	@Override
	public String getPassword() {
		return user.getPassword(); //pswrd extrct kra
	}

	@Override /* eml is our usrnm  */
	public String getUsername()  { //email extrct kra
		return user.getEmail();
		
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	

}
