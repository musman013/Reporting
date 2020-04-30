package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;
import javax.persistence.*;

import org.json.simple.JSONObject;
import com.nfinity.reporting.reportingapp1.JSONObjectConverter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "report", schema = "reporting")
public class ReportEntity implements Serializable {

  	private String ctype;
  	private String description;
	private Long id;
  	private JSONObject query;
  	private String reportType;
  	private String title;
  	private String reportWidth;
 
  	@Basic
  	@Column(name = "reportWidth" , nullable= true, length = 255)
  	public String getReportWidth() {
		return reportWidth;
	}

	public void setReportWidth(String reportWidth) {
		this.reportWidth = reportWidth;
	}

	public ReportEntity() {
  	}

  	@Basic
  	@Column(name = "ctype", nullable = true, length =255)
  	public String getCtype() {
  		return ctype;
  	}

  	public void setCtype(String ctype) {
  		this.ctype = ctype;
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
  
  	@Basic
  	@Column(columnDefinition = "TEXT",name = "query", nullable = true, length =255)
  	@Convert(converter= JSONObjectConverter.class)
  	public JSONObject getQuery() {
  		return query;
  	}

  	public void setQuery(JSONObject query) {
  		this.query = query;
  	}
  
  	@Basic
  	@Column(name = "reportType", nullable = true, length =255)
  	public String getReportType() {
  		return reportType;
  	}

  	public void setReportType(String reportType) {
  		this.reportType = reportType;
  	}
  
  	@OneToMany(mappedBy = "report", cascade = CascadeType.ALL) 
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
//      if (!(o instanceof ReportEntity)) return false;
//        ReportEntity report = (ReportEntity) o;
//        return id != null && id.equals(report.id);
//  }

}

  
      


