package com.app.FirstApp.domain.Services;

import java.util.List;

import com.app.FirstApp.domain.Entity.User;

public interface UserService {
	User saveUser(User user);
	
	User getUser(String email);
	List<User> getUsers(); 
	

}
