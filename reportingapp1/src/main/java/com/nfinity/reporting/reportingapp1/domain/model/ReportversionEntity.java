package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.json.simple.JSONObject;
import com.nfinity.reporting.reportingapp1.JSONObjectConverter;

@Entity
@Table(name = "reportversion", schema = "reporting")
@IdClass(ReportversionId.class)
public class ReportversionEntity implements Serializable {
	
	private Long userId;
	private Long reportId;
  	private String ctype;
  	private String description;
  	private JSONObject query;
  	private String reportType;
  	private String title;
  	private String reportWidth;
  	private String version;

	public ReportversionEntity() {
  	}
	
	
  //	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
  	@Column(name = "userId", nullable = false)
  	public Long getUserId() {
  		return userId;
  	}

  	public void setUserId(Long userId) {
  		this.userId = userId;
  	}
  	
  	@Id
  	@Column(name = "reportId", nullable = false)
  	public Long getReportId() {
  		return reportId;
  	}

  	public void setReportId(Long reportId) {
  		this.reportId = reportId;
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
  
  	@Basic
  	@Column(name = "reportWidth" , nullable= true, length = 255)
  	public String getReportWidth() {
		return reportWidth;
	}

	public void setReportWidth(String reportWidth) {
		this.reportWidth = reportWidth;
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
 
  	@Basic
  	@Column(name = "title", nullable = true, length =255)
  	public String getTitle() {
  		return title;
  	}

  	public void setTitle(String title) {
  		this.title = title;
  	}
  	
  	@ManyToOne
  	@JoinColumn(name = "reportId", referencedColumnName = "id", insertable = false, updatable = false)
  	public ReportEntity getReport() {
    	return report;
  	}
  	public void setReport(ReportEntity report) {
    	this.report = report;
  	}
  
  	private ReportEntity report;
  	
  	@ManyToOne
  	@JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
  	public UserEntity getUser() {
    	return user;
  	}
  	public void setUser(UserEntity user) {
    	this.user = user;
  	}
  
  	private UserEntity user;

}
