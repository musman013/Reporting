package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

@Entity
@Table(name = "dashboardversion", schema = "reporting")
@IdClass(DashboardversionId.class)
public class DashboardversionEntity implements Serializable {
	
	private Long userId;
	private Long dashboardId;
	private String description;
  	private String title;
  	private String version;
  	
  	@Id
  	@Column(name = "userId", nullable = false)
  	public Long getUserId() {
  		return userId;
  	}

  	public void setUserId(Long userId) {
  		this.userId = userId;
  	}
  	
  	@Id
  	@Column(name = "dashboardId", nullable = false)
  	public Long getDashboardId() {
  		return dashboardId;
  	}

  	public void setDashboardId(Long dashboardId) {
  		this.dashboardId = dashboardId;
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
  	@Column(name = "title", nullable = false, length =255)
  	public String getTitle() {
  		return title;
  	}

  	public void setTitle(String title) {
  		this.title = title;
  	}
  	
  	@ManyToOne
  	@JoinColumn(name = "dashboardId", insertable = false, updatable = false)
  	public DashboardEntity getDashboard() {
    	return dashboard;
  	}
  	public void setDashboard(DashboardEntity dashboard) {
    	this.dashboard = dashboard;
  	}
  
  	private DashboardEntity dashboard;
  	
  	@ManyToOne
  	@JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
  	public UserEntity getUser() {
    	return user;
  	}
  	public void setUser(UserEntity user) {
    	this.user = user;
  	}
  
  	private UserEntity user;
  	
  	@OneToMany(mappedBy = "dashboardversion", cascade = CascadeType.ALL, orphanRemoval = true) 
  	public Set<DashboardversionreportEntity> getDashboardversionreportSet() { 
    	return dashboardversionreportSet; 
  	} 

	public void setDashboardversionreportSet(Set<DashboardversionreportEntity> dashboardversionreport) { 
    	this.dashboardversionreportSet = dashboardversionreport; 
  	} 
 
  	private Set<DashboardversionreportEntity> dashboardversionreportSet = new HashSet<DashboardversionreportEntity>(); 

  	@PreRemove
  	private void dismissParent() {
  	//SYNCHRONIZING THE OTHER SIDE OF RELATIONSHIP
  	if(this.user != null) {
  	this.user.removeDashboardVersion(this);
  	this.user = null;
  	}

  	if(this.dashboard != null) {
  	this.dashboard.removeDashboardVersion(this);
  	this.dashboard = null;
  	}

  	}
  	
  	public void removeDashboardversionreport(DashboardversionreportEntity rv) {
        this.dashboardversionreportSet.remove(rv);
    }
}
