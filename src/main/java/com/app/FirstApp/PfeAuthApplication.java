package com.app.FirstApp;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.app.FirstApp.domain.Entity.User;
import com.app.FirstApp.domain.Services.UserService;

@SpringBootApplication
public class PfeAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(PfeAuthApplication.class, args);
	}
@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveUser(new User(null,"moetez", "moetezmaddouri@gmail.com", "12356sdf", "role_User"));
			userService.saveUser(new User(null,"Hadil", "moetezmaddouri@gmail.com", "12356sdf","role_Manager" ));

		};
	}

}
