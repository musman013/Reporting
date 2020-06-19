package com.nfinity.reporting.reportingapp1.domain.model;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;

import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import com.nfinity.reporting.reportingapp1.domain.model.UserpermissionEntity;
import com.nfinity.reporting.reportingapp1.domain.model.UserroleEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "user", schema = "reporting")
public class UserEntity extends AbstractEntity{

	private static final long serialVersionUID = 1L;
  
  	@Basic
  	@Column(name = "accessFailedCount", nullable = true)
  	private Integer accessFailedCount;
  	
  	@Basic
  	@Column(name = "authenticationSource", nullable = true, length =64)
  	private String authenticationSource;
  	
  	@Basic
  	@Column(name = "emailAddress", nullable = false, length =256)
  	private String emailAddress;
  	
  	@Basic
  	@Column(name = "emailConfirmationCode", nullable = true, length =328)
  	private String emailConfirmationCode;
  	
    @Basic
  	@Column(name = "firstName", nullable = false, length =32)
    private String firstName;
  
  	@Id
  	@GeneratedValue(strategy = GenerationType.IDENTITY)
  	@Column(name = "id", nullable = false)
  	private Long id;
  	
  	@Basic
  	@Column(name = "isActive", nullable = true)
  	private Boolean isActive;
  
  	@Basic
  	@Column(name = "isEmailConfirmed", nullable = true)
  	private Boolean isEmailConfirmed;
  
  	@Basic
  	@Column(name = "isLockoutEnabled", nullable = true)
  	private Boolean isLockoutEnabled;
  
  	@Basic
  	@Column(name = "isPhoneNumberConfirmed", nullable = true, length =255)
  	private String isPhoneNumberConfirmed;
  
  	@Basic
  	@Column(name = "lastLoginTime", nullable = true)
  	private Date lastLoginTime;
  
  	@Basic
  	@Column(name = "lastName", nullable = false, length =32)
  	private String lastName;
  	
  	@Basic
  	@Column(name = "lockoutEndDateUtc", nullable = true)
  	private Date lockoutEndDateUtc;
  
  	@Basic
  	@Column(name = "password", nullable = false, length =128)
  	private String password;
  
  	@Basic
  	@Column(name = "passwordResetCode", nullable = true, length =328)
  	private String passwordResetCode;
  
  	@Basic
  	@Column(name = "passwordTokenExpiration", nullable = true)
  	private Date passwordTokenExpiration;
  
  	@Basic
  	@Column(name = "phoneNumber", nullable = true, length =32)
  	private String phoneNumber;
  
  	@Basic
  	@Column(name = "profilePictureId", nullable = true)
  	private Long profilePictureId;
  	
  	@Basic
  	@Column(name = "twoFactorEnabled", nullable = true)
  	private Boolean twoFactorEnabled;
  	
  	@Basic
  	@Column(name = "userName", nullable = false, length =32)
  	private String userName;
  	
  	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL) 
  	private Set<DashboardEntity> dashboardSet = new HashSet<DashboardEntity>(); 
  	
  	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL) 
	private Set<DashboardversionEntity> dashboardversionSet = new HashSet<DashboardversionEntity>(); 
  
  	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL) 
  	private Set<ReportEntity> reportSet = new HashSet<ReportEntity>(); 
  	
  	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL) 
  	private Set<ReportversionEntity> reportversionSet = new HashSet<ReportversionEntity>(); 
  	
  	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL) 
  	private Set<UserpermissionEntity> userpermissionSet = new HashSet<UserpermissionEntity>();

  	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<UserroleEntity> userroleSet = new HashSet<UserroleEntity>(); 
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL) 
  	private Set<ReportuserEntity> reportuserSet = new HashSet<ReportuserEntity>();
  	
  	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL) 
  	private Set<DashboarduserEntity> dashboarduserSet = new HashSet<DashboarduserEntity>(); 
  	
  	public void addUserpermission(UserpermissionEntity userpermission) {
		userpermissionSet.add(userpermission);
		userpermission.setUser(this);
	}

	public void removeUserpermission(UserpermissionEntity userpermission) {
		userpermissionSet.remove(userpermission);
		userpermission.setUser(null);
	}

	public void addUserrole(UserroleEntity userrole) {
		userroleSet.add(userrole);
		userrole.setUser(this);
	}

	public void removeUserrole(UserroleEntity userrole) {
		userroleSet.remove(userrole);
		userrole.setUser(null);
	}
	
	public void addReportversion(ReportversionEntity reportVersion) {
		reportversionSet.add(reportVersion);
		reportVersion.setUser(this);
	}

	public void removeReportversion(ReportversionEntity reportVersion) {
		reportversionSet.remove(reportVersion);
		reportVersion.setUser(null);
	}
	
	public void addReport(ReportEntity report) {
		reportSet.add(report);
		report.setUser(this);
	}

	public void removeReport(ReportEntity report) {
		reportSet.remove(report);
		report.setUser(null);
	}
	
	public void addReportuser(ReportuserEntity reportUser) {
		reportuserSet.add(reportUser);
		reportUser.setUser(this);
	}

	public void removeReportuser(ReportuserEntity reportUser) {
		reportuserSet.remove(reportUser);
		reportUser.setUser(null);
	}
	
	public void addDashboardversion(DashboardversionEntity dashboardVersion) {
		dashboardversionSet.add(dashboardVersion);
		dashboardVersion.setUser(this);
	}

	public void removeDashboardversion(DashboardversionEntity dashboardVersion) {
		dashboardversionSet.remove(dashboardVersion);
		dashboardVersion.setUser(null);
	}
	
	public void addDashboard(DashboardEntity dashboard) {
		dashboardSet.add(dashboard);
		dashboard.setUser(this);
	}

	public void removeDashboard(DashboardEntity dashboard) {
		dashboardSet.remove(dashboard);
		dashboard.setUser(null);
	}
	
	public void addDashboarduser(DashboarduserEntity dashboarduser) {
		dashboarduserSet.add(dashboarduser);
		dashboarduser.setUser(this);
	}

	public void removeDashboarduser(DashboarduserEntity dashboarduser) {
		dashboarduserSet.remove(dashboarduser);
		dashboarduser.setUser(null);
	}
	
}

  
      


