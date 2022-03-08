 package com.app.FirstApp.domain.Entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
@Entity
@Table(name = "Alluser")
public class User  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
	@Column(name = "name")
private String name;
	@Column(name = "email")
private String email;
	@Column(name = "password")
private String password;

private String Roles;

public User() {
	
}



public User(Long id, String name, String email, String password, String roles) {
	super();
	this.id = id;
	this.name = name;
	this.email = email;
	this.password = password;
	Roles = roles;
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

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getRoles() {
	return Roles;
}

public void setRoles(String roles) {
	Roles = roles;
}







}
