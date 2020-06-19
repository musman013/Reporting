package com.nfinity.reporting.reportingapp1.application.authorization.user.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class CreateUserOutput {

    private Integer accessFailedCount;
    private String authenticationSource;
    private String emailAddress;
    private String emailConfirmationCode;
    private String firstName;
    private Long id;
    private Boolean isActive;
    private Boolean isEmailConfirmed;
    private Boolean isLockoutEnabled;
    private String isPhoneNumberConfirmed;
    private Date lastLoginTime;
    private String lastName;
    private Date lockoutEndDateUtc;
    private String passwordResetCode;
    private Date passwordTokenExpiration;
    private String phoneNumber;
    private Long profilePictureId;
    private Boolean twoFactorEnabled;
    private String userName;

}
