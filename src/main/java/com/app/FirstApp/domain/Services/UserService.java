package com.app.FirstApp.domain.Services;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.app.FirstApp.domain.Entity.Role;
import com.app.FirstApp.domain.Entity.User;
import com.app.FirstApp.domain.Entity.UserResp;

public interface UserService extends UserDetailsService  {
	UserResp saveUser(User user);
	Role saveRole(Role role);
//void addRoleToUser(String email);

	User getUser(String email);
	UserResp updateUser(User user);
	void deletUser(String email);
	List<UserResp> getUsers(); 
	

}
