package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dashboard", schema = "reporting")
public class DashboardEntity implements Serializable {

	private Long id;
	private Boolean isPublished;
	private Boolean isSharable;

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
	
	@Basic
	@Column(name = "isSharable", nullable = false)
  	public Boolean getIsSharable() {
		return isSharable;
	}

	public void setIsSharable(Boolean isSharable) {
		this.isSharable = isSharable;
	}
  	
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
  	
  	public void removeDashboardVersion(DashboardversionEntity rv) {
        this.dashboardversionSet.remove(rv);
    }
  	
  	public void removeDashboardrole(DashboardroleEntity rv) {
        this.dashboardroleSet.remove(rv);
    }
  	
  	public void removeDashboarduser(DashboarduserEntity rv) {
        this.dashboarduserSet.remove(rv);
    }
  	

}

  
      


