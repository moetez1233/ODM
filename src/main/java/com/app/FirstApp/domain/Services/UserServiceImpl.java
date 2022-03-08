package com.app.FirstApp.domain.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.FirstApp.domain.Entity.User;
import com.app.FirstApp.domain.Repository.UserRepo;



@Service 
public class UserServiceImpl implements UserService {
	@Autowired
	private  UserRepo userRepo;
	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return userRepo.save(user);
	}

	@Override
	public User getUser(String email) {
		// TODO Auto-generated method stub
		return userRepo.findByEmail(email);
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}
	/*@Autowired
	private  UserRepo userRepo;
	@Autowired

	@Override
	public User saveUser(User user) {
		
		return userRepo.save(user);
	} 
	@Override
	public User getUser(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}*/

}
