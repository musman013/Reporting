package com.nfinity.reporting.reportingapp1.application.authorization.user.dto;

import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter @Setter
public class CreateUserInput {

	private Integer accessFailedCount; 
	@Length(max = 64, message = "authenticationSource must be less than 64 characters")
	private String authenticationSource;

	@NotNull(message = "emailAddress Should not be null")
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Email Address should be valid")
	@Length(max = 256, message = "emailAddress must be less than 256 characters")
	private String emailAddress;

	@Length(max = 328, message = "emailConfirmationCode must be less than 328 characters")
	private String emailConfirmationCode;

	@NotNull(message = "firstName Should not be null")
	@Length(max = 32, message = "firstName must be less than 32 characters")
	private String firstName;

	private Boolean isActive;

	private Boolean isEmailConfirmed;
	private Boolean isLockoutEnabled;

	@Length(max = 255, message = "isPhoneNumberConfirmed must be less than 255 characters")
	private String isPhoneNumberConfirmed;

	private Date lastLoginTime;

	@NotNull(message = "lastName Should not be null")
	@Length(max = 32, message = "lastName must be less than 32 characters")
	private String lastName;

	private Date lockoutEndDateUtc;

	@NotNull(message = "password Should not be null")
	@Length(max = 128, message = "password must be less than 128 characters")
	private String password;

	@Length(max = 328, message = "passwordResetCode must be less than 328 characters")
	private String passwordResetCode;

	private Date passwordTokenExpiration;

	@Length(max = 32, message = "phoneNumber must be less than 32 characters")
	private String phoneNumber;

	private Long profilePictureId;

	private Boolean twoFactorEnabled;

	@NotNull(message = "userName Should not be null")
	@Length(max = 32, message = "userName must be less than 32 characters")
	private String userName;

}
