package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dashboardversion", schema = "reporting")
@IdClass(DashboardversionId.class)
public class DashboardversionEntity implements Serializable {
	
	private Long id;
	private String description;
  	private String title;
  	private String version;
  	
  	@Id
  	@Column(name = "id", nullable = false)
  	public Long getId() {
  		return id;
  	}

  	public void setId(Long id) {
  		this.id = id;
  	}
  	
  	@Id
  	@Column(name = "version", nullable = false, length =255)
  	public String getVersion() {
  		return version;
  	}

  	public void setVersion(String version) {
  		this.version = version;
  	}
	
	@Basic
  	@Column(name = "description", nullable = true, length =255)
  	public String getDescription() {
  		return description;
  	}

  	public void setDescription(String description) {
  		this.description = description;
  	}
 
  	@Basic
  	@Column(name = "title", nullable = true, length =255)
  	public String getTitle() {
  		return title;
  	}

  	public void setTitle(String title) {
  		this.title = title;
  	}
  	
  	@ManyToOne
  	@JoinColumn(name = "userId")
  	public UserEntity getUser() {
    	return user;
  	}
  	public void setUser(UserEntity user) {
    	this.user = user;
  	}
  
  	private UserEntity user;
  	
  	@ManyToOne
  	@JoinColumn(name = "dashboardId")
  	public DashboardEntity getDashboard() {
    	return dashboard;
  	}
  	public void setDashboard(DashboardEntity dashboard) {
    	this.dashboard = dashboard;
  	}
  
  	private DashboardEntity dashboard;

}
