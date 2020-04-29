package com.nfinity.reporting.reportingapp1.application.authorization.user.dto;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class UpdatePasswordInput {
	
	@NotNull
	@Length(min = 8, max = 128, message = "password must be between 8 and 128 characters")
	String oldPassword;
	
	@NotNull
	@Length(min = 8, max = 128, message = "password must be between 8 and 128 characters")
	String newPassword;
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	

}
