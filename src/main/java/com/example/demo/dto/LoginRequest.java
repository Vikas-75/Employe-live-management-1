package com.example.demo.dto;

import com.example.demo.models.RoleType;

public class LoginRequest {
    private String email;
    private String password;
    private RoleType roleType;
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
	public RoleType getRoleType() {
		return roleType;
	}
	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

    // getters and setters
    
    
}
