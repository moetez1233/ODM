package com.app.FirstApp.domain.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.app.FirstApp.domain.Entity.Role;
import com.app.FirstApp.domain.Entity.User;
import com.app.FirstApp.domain.Entity.UserResp;
import com.app.FirstApp.domain.Repository.RoleRepo;
import com.app.FirstApp.domain.Repository.UserRepo;
import com.app.FirstApp.domain.SaveMethodes.CryptID;
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Service
public class UserServiceImpl implements UserService {
	private static final long serialVersionUID = -833637346442153773L;//auto generated 

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RoleRepo roleRopo;;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	CryptID cryptId;

	@Override
	public UserResp saveUser(User user) {
		if (userRepo.findByEmail(user.getEmail()) != null)
			throw new RuntimeException("email already exist ");
	

	

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		String generate_User=cryptId.generateUserid(30);
		user.setUserId(generate_User);
		UserResp returnVal = new UserResp();
		User userSAved = userRepo.save(user);
		BeanUtils.copyProperties(userSAved, returnVal);
		return returnVal;
	}

	@Override
	public Role saveRole(Role role) {
		// TODO Auto-generated method stub
		return roleRopo.save(role);
	}

	@Override
	public User getUser(String email) {
		// TODO Auto-generated method stub
		return userRepo.findByEmail(email);
	}

	@Override
	public List<UserResp> getUsers() {
		// TODO Auto-generated method stub
		List<User> ListUser = userRepo.findAll();
		List<UserResp> ListReturnVal = new ArrayList<>();
		for (int i = 0; i < ListUser.size(); i++) {
			UserResp returnVal = new UserResp();
			BeanUtils.copyProperties(ListUser.get(i), returnVal);
			ListReturnVal.add(returnVal);
		}
		return ListReturnVal;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(email);
		if (user == null)
			throw new UsernameNotFoundException(email);
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		// System.out.println("user_implementation : "+user.getRoles());
		Collection<Role> roles = user.getRoles();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		// System.out.println("authorities "+authorities);

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

	@Override
	public UserResp updateUser(User user) {
		User userUp = userRepo.findByEmail(user.getEmail());
		if (userUp == null)
			throw new RuntimeException("User not exist ");
		userUp.setUserId(user.getUserId());
		userUp.setName(user.getName());
		userUp.setLastName(user.getLastName());
		userUp.setAdress(user.getAdress());
		userUp.setCin(user.getCin());
		userUp.setPhone_number(user.getPhone_number());
		userUp.setEmail(user.getEmail());
		userUp.setRoles(user.getRoles());
		User newUser = userRepo.save(userUp);
		UserResp returnVal = new UserResp();
		BeanUtils.copyProperties(newUser, returnVal);
		return returnVal;
	}

	@Override
	public void deletUser(String email) {
		User userUp = userRepo.findByEmail(email);
		if (userUp == null)
			throw new RuntimeException("User not exist ");
		userRepo.delete(userUp);
	}
	
	

}
