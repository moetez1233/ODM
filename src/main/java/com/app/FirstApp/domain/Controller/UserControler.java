package com.app.FirstApp.domain.Controller;


import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.FirstApp.domain.Entity.User;
import com.app.FirstApp.domain.Services.UserServiceImpl;

@RestController
@RequestMapping("api")
public class UserControler {
	@Autowired
	private UserServiceImpl UserServiceImpl;
	
	@GetMapping("users")
	public ResponseEntity<List<User>> getAllUsers(){
		return ResponseEntity.ok(UserServiceImpl.getUsers());
		
	}
	@PostMapping("users/save")
	public ResponseEntity<User> SaveUsers(@RequestBody User user){
		URI uri =URI.create((ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/save").toString()));
		return ResponseEntity.created(uri).body(UserServiceImpl.saveUser(user));
		
	}
	

	
}

