package com.app.FirstApp;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.FirstApp.domain.Entity.User;
import com.app.FirstApp.domain.Services.UserService;

@SpringBootApplication
public class PfeAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(PfeAuthApplication.class, args);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			//userService.saveUser(new User(null,"moetez", "moetezmaddouri@gmail.com", "12356sdf", "role_User"));
			//userService.saveUser(new User(null,"moetez", "moetezmaddouri1@gmail.com", "12356sdf", "role_Manager"));

		};
	}

}
