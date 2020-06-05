package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import com.nfinity.reporting.reportingapp1.domain.model.UserpermissionEntity;
import com.nfinity.reporting.reportingapp1.domain.model.UserroleEntity;

@Entity
@Table(name = "user", schema = "reporting")
public class UserEntity implements Serializable {

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
  	private String password;
  	private String passwordResetCode;
  	private Date passwordTokenExpiration;
  	private String phoneNumber;
	private Long profilePictureId;
  	private Boolean twoFactorEnabled;
  	private String userName;
 
  	public UserEntity() {
  	}

  	@Basic
  	@Column(name = "accessFailedCount", nullable = true)
    public Integer getAccessFailedCount() {
  		return accessFailedCount;
  	}

  	public void setAccessFailedCount(Integer accessFailedCount) {
  		this.accessFailedCount = accessFailedCount;
  	} 
  
  	@Basic
  	@Column(name = "authenticationSource", nullable = true, length =64)
  	public String getAuthenticationSource() {
  		return authenticationSource;
  	}

  	public void setAuthenticationSource(String authenticationSource) {
  		this.authenticationSource = authenticationSource;
  	}
  
  	@Basic
  	@Column(name = "emailAddress", nullable = false, length =256)
  	public String getEmailAddress() {
  		return emailAddress;
  	}

  	public void setEmailAddress(String emailAddress) {
  		this.emailAddress = emailAddress;
  	}
  
  	@Basic
  	@Column(name = "emailConfirmationCode", nullable = true, length =328)
  	public String getEmailConfirmationCode() {
  		return emailConfirmationCode;
  	}

  	public void setEmailConfirmationCode(String emailConfirmationCode) {
  		this.emailConfirmationCode = emailConfirmationCode;
  	}
  
  	@Basic
  	@Column(name = "firstName", nullable = false, length =32)
  	public String getFirstName() {
  		return firstName;
  	}

  	public void setFirstName(String firstName) {
  		this.firstName = firstName;
  	}
  
  	@Id
  	@GeneratedValue(strategy = GenerationType.IDENTITY)
  	@Column(name = "id", nullable = false)
  	public Long getId() {
  		return id;
  	}

  	public void setId(Long id) {
  		this.id = id;
  	}
  
  	@Basic
  	@Column(name = "isActive", nullable = true)
  	public Boolean getIsActive() {
  		return isActive;
  	}

	public void setIsActive(Boolean isActive) {
  		this.isActive = isActive;
	}
  
  	@Basic
  	@Column(name = "isEmailConfirmed", nullable = true)
  	public Boolean getIsEmailConfirmed() {
  		return isEmailConfirmed;
  	}

	public void setIsEmailConfirmed(Boolean isEmailConfirmed) {
  		this.isEmailConfirmed = isEmailConfirmed;
	}
  
  	@Basic
  	@Column(name = "isLockoutEnabled", nullable = true)
  	public Boolean getIsLockoutEnabled() {
  		return isLockoutEnabled;
  	}

	public void setIsLockoutEnabled(Boolean isLockoutEnabled) {
  		this.isLockoutEnabled = isLockoutEnabled;
	}
  
  	@Basic
  	@Column(name = "isPhoneNumberConfirmed", nullable = true, length =255)
  	public String getIsPhoneNumberConfirmed() {
  		return isPhoneNumberConfirmed;
  	}

  	public void setIsPhoneNumberConfirmed(String isPhoneNumberConfirmed) {
  		this.isPhoneNumberConfirmed = isPhoneNumberConfirmed;
  	}
  
  	@Basic
  	@Column(name = "lastLoginTime", nullable = true)
  	public Date getLastLoginTime() {
  		return lastLoginTime;
  	}

  	public void setLastLoginTime(Date lastLoginTime) {
  		this.lastLoginTime = lastLoginTime;
  	}
  
  	@Basic
  	@Column(name = "lastName", nullable = false, length =32)
  	public String getLastName() {
  		return lastName;
  	}

  	public void setLastName(String lastName) {
  		this.lastName = lastName;
  	}
  
  	@Basic
  	@Column(name = "lockoutEndDateUtc", nullable = true)
  	public Date getLockoutEndDateUtc() {
  		return lockoutEndDateUtc;
  	}

  	public void setLockoutEndDateUtc(Date lockoutEndDateUtc) {
  		this.lockoutEndDateUtc = lockoutEndDateUtc;
  	}
  
  	@Basic
  	@Column(name = "password", nullable = false, length =128)
  	public String getPassword() {
  		return password;
  	}

  	public void setPassword(String password) {
  		this.password = password;
  	}
  
  	@Basic
  	@Column(name = "passwordResetCode", nullable = true, length =328)
  	public String getPasswordResetCode() {
  		return passwordResetCode;
  	}

  	public void setPasswordResetCode(String passwordResetCode) {
  		this.passwordResetCode = passwordResetCode;
  	}
  
  	@Basic
  	@Column(name = "passwordTokenExpiration", nullable = true)
  	public Date getPasswordTokenExpiration() {
  		return passwordTokenExpiration;
  	}

  	public void setPasswordTokenExpiration(Date passwordTokenExpiration) {
  		this.passwordTokenExpiration = passwordTokenExpiration;
  	}
  
  	@Basic
  	@Column(name = "phoneNumber", nullable = true, length =32)
  	public String getPhoneNumber() {
  		return phoneNumber;
  	}

  	public void setPhoneNumber(String phoneNumber) {
  		this.phoneNumber = phoneNumber;
  	}
  
  	@Basic
  	@Column(name = "profilePictureId", nullable = true)
  	public Long getProfilePictureId() {
  		return profilePictureId;
  	}

  	public void setProfilePictureId(Long profilePictureId) {
  		this.profilePictureId = profilePictureId;
  	}
  	
  	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL) 
  	public Set<DashboardEntity> getDashboardSet() { 
    	return dashboardSet; 
  	} 
 
  	public void setDashboardSet(Set<DashboardEntity> dashboard) { 
    	this.dashboardSet = dashboard; 
  	} 
 
  	private Set<DashboardEntity> dashboardSet = new HashSet<DashboardEntity>(); 
  	
  	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL) 
	public Set<DashboardversionEntity> getDashboardversionSet() { 
    	return dashboardversionSet; 
  	} 
 
  	public void setDashboardversionSet(Set<DashboardversionEntity> dashboardversion) { 
    	this.dashboardversionSet = dashboardversion; 
  	} 
 
  	private Set<DashboardversionEntity> dashboardversionSet = new HashSet<DashboardversionEntity>(); 
  	
  
  	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL) 
  	public Set<ReportEntity> getReportSet() { 
    	return reportSet; 
  	} 
 
  	public void setReportSet(Set<ReportEntity> report) { 
    	this.reportSet = report; 
  	} 
 
  	private Set<ReportEntity> reportSet = new HashSet<ReportEntity>(); 
  	
  	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL) 
  	public Set<ReportversionEntity> getReportversionSet() { 
    	return reportversionSet; 
  	} 
 
  	public void setReportversionSet(Set<ReportversionEntity> reportversion) { 
    	this.reportversionSet = reportversion; 
  	}
 
  	private Set<ReportversionEntity> reportversionSet = new HashSet<ReportversionEntity>(); 
  	
  	@Basic
  	@Column(name = "twoFactorEnabled", nullable = true)
  	public Boolean getTwoFactorEnabled() {
  		return twoFactorEnabled;
  	}

	public void setTwoFactorEnabled(Boolean twoFactorEnabled) {
  		this.twoFactorEnabled = twoFactorEnabled;
	}
  
  	@Basic
  	@Column(name = "userName", nullable = false, length =32)
  	public String getUserName() {
  		return userName;
  	}

  	public void setUserName(String userName) {
  		this.userName = userName;
  	}
  

  	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL) 
  	public Set<UserpermissionEntity> getUserpermissionSet() { 
		return userpermissionSet; 
  	} 
 
  	public void setUserpermissionSet(Set<UserpermissionEntity> userpermission) { 
    	this.userpermissionSet = userpermission; 
  	} 
 
  	private Set<UserpermissionEntity> userpermissionSet = new HashSet<UserpermissionEntity>();

  	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
   public Set<UserroleEntity> getUserroleSet() { 
      return userroleSet; 
    } 
 
    public void setUserroleSet(Set<UserroleEntity> userrole) { 
      this.userroleSet = userrole; 
    } 
 
	private Set<UserroleEntity> userroleSet = new HashSet<UserroleEntity>(); 
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL) 
  	public Set<ReportuserEntity> getReportuserSet() { 
    	return reportuserSet; 
  	} 
 
  	public void setReportuserSet(Set<ReportuserEntity> reportuser) { 
    	this.reportuserSet = reportuser; 
  	} 
 
  	private Set<ReportuserEntity> reportuserSet = new HashSet<ReportuserEntity>();
  	
  	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL) 
  	public Set<DashboarduserEntity> getDashboarduserSet() { 
    	return dashboarduserSet; 
  	} 
 
  	public void setDashboarduserSet(Set<DashboarduserEntity> dashboarduser) { 
    	this.dashboarduserSet = dashboarduser; 
  	} 
 
  	private Set<DashboarduserEntity> dashboarduserSet = new HashSet<DashboarduserEntity>(); 
  	
  	public void removeReportVersion(ReportversionEntity rv) {
        this.reportversionSet.remove(rv);
    }
  	
  	public void removeDashboardVersion(DashboardversionEntity dv) {
        this.dashboardversionSet.remove(dv);
    }
  	
  	public void removeDashboard(DashboardEntity dv) {
        this.dashboardSet.remove(dv);
    }
  	
	public void removeReport(ReportEntity dv) {
        this.reportSet.remove(dv);
    }
  	
  	public void removeReportuser(ReportuserEntity rv) {
        this.reportuserSet.remove(rv);
    }
  	
  	public void removeDashboarduser(DashboarduserEntity rv) {
        this.dashboarduserSet.remove(rv);
    }
  	
  	public void removeUserrole(UserroleEntity rv) {
        this.userroleSet.remove(rv);
    }
  	
  	public void removeUserpermission(UserpermissionEntity rv) {
        this.userpermissionSet.remove(rv);
    }

}

  
      


