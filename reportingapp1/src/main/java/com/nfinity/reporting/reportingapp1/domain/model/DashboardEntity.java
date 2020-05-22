package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;

@Entity
@Table(name = "dashboard", schema = "reporting")
public class DashboardEntity implements Serializable {

	private Long id;
	private Boolean isPublished;
//  	private String title;
//  	private String description;
 
  	public DashboardEntity() {
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
	@Column(name = "isPublished", nullable = false)
  	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}
  	
//  	@Basic
//  	@Column(name = "description", nullable = true, length =255)
//  	public String getDescription() {
//  		return description;
//  	}
//
//  	public void setDescription(String description) {
//  		this.description = description;
//  	}
// 
//	@Basic
//  	@Column(name = "title", nullable = true, length =255)
//  	public String getTitle() {
//  		return title;
//  	}
//
//  	public void setTitle(String title) {
//  		this.title = title;
//  	}
//  
  	@OneToMany(mappedBy = "dashboard", cascade = CascadeType.ALL) 
  	public Set<ReportdashboardEntity> getReportdashboardSet() { 
    	return reportdashboardSet; 
  	} 

	public void setReportdashboardSet(Set<ReportdashboardEntity> reportdashboard) { 
    	this.reportdashboardSet = reportdashboard; 
  	} 
 
  	private Set<ReportdashboardEntity> reportdashboardSet = new HashSet<ReportdashboardEntity>(); 
  
  	@ManyToOne
  	@JoinColumn(name = "ownerId")
  	public UserEntity getUser() {
    	return user;
  	}
  	public void setUser(UserEntity user) {
    	this.user = user;
  	}
  
  	private UserEntity user;
  	
  	@OneToMany(mappedBy = "dashboard", cascade = CascadeType.ALL) 
  	public Set<DashboardversionEntity> getDashboardversionSet() { 
    	return dashboardversionSet; 
  	} 
 
  	public void setDashboardversionSet(Set<DashboardversionEntity> dashboardversion) { 
    	this.dashboardversionSet = dashboardversion; 
  	} 
 
  	private Set<DashboardversionEntity> dashboardversionSet = new HashSet<DashboardversionEntity>(); 
 

 	@OneToMany(mappedBy = "dashboard", cascade = CascadeType.ALL) 
  	public Set<DashboarduserEntity> getDashboarduserSet() { 
    	return dashboarduserSet; 
  	} 
 
  	public void setDashboarduserSet(Set<DashboarduserEntity> dashboarduser) { 
    	this.dashboarduserSet = dashboarduser; 
  	} 
 
  	private Set<DashboarduserEntity> dashboarduserSet = new HashSet<DashboarduserEntity>(); 
  	
  	@OneToMany(mappedBy = "dashboard", cascade = CascadeType.ALL) 
  	public Set<DashboardroleEntity> getDashboardroleSet() { 
    	return dashboardroleSet; 
  	} 
 
  	public void setDashboardroleSet(Set<DashboardroleEntity> dashboardrole) { 
    	this.dashboardroleSet = dashboardrole; 
  	} 
 
  	private Set<DashboardroleEntity> dashboardroleSet = new HashSet<DashboardroleEntity>(); 

}

  
      


