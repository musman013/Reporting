package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "report", schema = "reporting")
public class ReportEntity implements Serializable {

	private Long id;
	private Boolean isPublished;
  	
	public ReportEntity() {
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
  	
  	@ManyToOne
  	@JoinColumn(name = "ownerId")
  	public UserEntity getUser() {
    	return user;
  	}
  	public void setUser(UserEntity user) {
    	this.user = user;
  	}
  
  	private UserEntity user;
  	
  	@OneToMany(mappedBy = "report", cascade = CascadeType.ALL) 
  	public Set<ReportversionEntity> getReportversionSet() { 
    	return reportversionSet; 
  	} 
 
  	public void setReportversionSet(Set<ReportversionEntity> reportversion) { 
    	this.reportversionSet = reportversion; 
  	}
 
  	private Set<ReportversionEntity> reportversionSet = new HashSet<ReportversionEntity>(); 
  	
  	
  	@OneToMany(mappedBy = "report", cascade = CascadeType.ALL) 
  	public Set<DashboardversionreportEntity> getReportdashboardSet() { 
    	return reportdashboardSet; 
  	} 
 
  	public void setReportdashboardSet(Set<DashboardversionreportEntity> reportdashboard) { 
    	this.reportdashboardSet = reportdashboard; 
  	} 
 
  	private Set<DashboardversionreportEntity> reportdashboardSet = new HashSet<DashboardversionreportEntity>(); 
  	
  	@OneToMany(mappedBy = "report", cascade = CascadeType.ALL) 
  	public Set<ReportuserEntity> getReportuserSet() { 
    	return reportuserSet; 
  	} 
 
  	public void setReportuserSet(Set<ReportuserEntity> reportuser) { 
    	this.reportuserSet = reportuser; 
  	} 
 
  	private Set<ReportuserEntity> reportuserSet = new HashSet<ReportuserEntity>(); 
  	
  	@OneToMany(mappedBy = "report", cascade = CascadeType.ALL) 
  	public Set<ReportroleEntity> getReportroleSet() { 
    	return reportroleSet; 
  	} 
 
  	public void setReportroleSet(Set<ReportroleEntity> reportrole) { 
    	this.reportroleSet = reportrole; 
  	} 
 
  	private Set<ReportroleEntity> reportroleSet = new HashSet<ReportroleEntity>(); 
  	
 
  	public void removeReportUser(ReportuserEntity rv) {
        this.reportuserSet.remove(rv);
    }
  	
  	public void removeReportRole(ReportroleEntity rv) {
        this.reportroleSet.remove(rv);
    }
  	
  	public void removeReportVersion(ReportversionEntity rv) {
        this.reportversionSet.remove(rv);
    }
  	
  	public void removeDashboardversionreport(DashboardversionreportEntity rv) {
        this.reportdashboardSet.remove(rv);
    }

//  @Override
//  public boolean equals(Object o) {
//    if (this == o) return true;
//      if (!(o instanceof ReportEntity)) return false;
//        ReportEntity report = (ReportEntity) o;
//        return id != null && id.equals(report.id);
//  }

}

  
      


