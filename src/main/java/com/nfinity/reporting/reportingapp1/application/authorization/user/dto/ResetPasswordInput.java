package com.nfinity.reporting.reportingapp1.application.authorization.user.dto;

import javax.validation.constraints.NotNull;

public class ResetPasswordInput {
	
	@NotNull
	String token;
	
	@NotNull
	String password;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
