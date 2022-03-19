package com.app.FirstApp.domain.Entity;

import java.util.ArrayList;
import java.util.List;

public class UserResp {
	private String userId;
	private String name;
	private String LastName;
	private String adress;
	private String cin;
	private String phone_number;
	private String email;
	private List<Role> roles = new ArrayList<>();
	
	
	public UserResp() {
		super();
	}


	public UserResp(String userId, String name,String LastName, String adress, String cin, String phone_number, String email,
			List<Role> roles) {
		super();
		this.userId = userId;
		this.name = name;
		this.LastName=LastName;
		this.adress = adress;
		this.cin = cin;
		this.phone_number = phone_number;
		this.email = email;
		this.roles = roles;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLastName() {
		return LastName;
	}


	public void setLastName(String lastName) {
		LastName = lastName;
	}


	public String getAdress() {
		return adress;
	}


	public void setAdress(String adress) {
		this.adress = adress;
	}


	public String getCin() {
		return cin;
	}


	public void setCin(String cin) {
		this.cin = cin;
	}


	public String getPhone_number() {
		return phone_number;
	}


	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
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
		return "UserResp [userId=" + userId + ", name=" + name + ", adress=" + adress + ", cin=" + cin
				+ ", phone_number=" + phone_number + ", email=" + email + ", roles=" + roles + "]";
	}


	
	
	
}
