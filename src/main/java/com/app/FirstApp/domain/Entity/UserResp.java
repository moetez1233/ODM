package com.app.FirstApp.domain.Entity;

import java.util.ArrayList;
import java.util.List;

public class UserResp {
	private Long id;
	private String name;
	private String email;
	private List<Role> roles = new ArrayList<>();
	
	
	public UserResp() {
		super();
	}


	public UserResp(Long id, String name, String email, List<Role> roles) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.roles = roles;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public List<Role> getRoles() {
		return roles;
	}


	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}


	@Override
	public String toString() {
		return "UserResp [id=" + id + ", name=" + name + ", email=" + email + ", roles=" + roles + "]";
	}
	
	
}
