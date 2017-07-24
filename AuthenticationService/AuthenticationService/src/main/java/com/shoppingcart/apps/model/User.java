package com.shoppingcart.apps.model;

public class User {	
	
	private String userName;
	private String role;
	private boolean active;
	private String password;
	
	
	public User(String userName, String role, boolean active, String password) {
		super();
		this.userName = userName;
		this.role = role;
		this.active = active;
		this.password = password;
	}
	
	public User() {
		super();
	
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	

}
