package com.app.FirstApp.domain.Services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.app.FirstApp.domain.Entity.User;

public interface UserService extends UserDetailsService  {
	User saveUser(User user);
	
	User getUser(String email);
	List<User> getUsers(); 
	

}
