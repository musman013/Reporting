package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;

@Entity
@Table(name = "dashboard", schema = "reporting")
public class DashboardEntity implements Serializable {

  	private String description;
	private Long id;
  	private String title;
 
  	public DashboardEntity() {
  	}

  	@Basic
  	@Column(name = "description", nullable = true, length =255)
  	public String getDescription() {
  		return description;
  	}

  	public void setDescription(String description) {
  		this.description = description;
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
  
  	@OneToMany(mappedBy = "dashboard", cascade = CascadeType.ALL) 
  	public Set<ReportdashboardEntity> getReportdashboardSet() { 
    	return reportdashboardSet; 
  	} 
 
  	public void setReportdashboardSet(Set<ReportdashboardEntity> reportdashboard) { 
    	this.reportdashboardSet = reportdashboard; 
  	} 
 
  	private Set<ReportdashboardEntity> reportdashboardSet = new HashSet<ReportdashboardEntity>(); 
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
 

//  @Override
//  public boolean equals(Object o) {
//    if (this == o) return true;
//      if (!(o instanceof DashboardEntity)) return false;
//        DashboardEntity dashboard = (DashboardEntity) o;
//        return id != null && id.equals(dashboard.id);
//  }

}

  
      


