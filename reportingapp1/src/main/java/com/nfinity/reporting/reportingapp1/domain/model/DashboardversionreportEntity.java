package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dashboardversionreport", schema = "reporting")
@IdClass(DashboardversionreportId.class)
public class DashboardversionreportEntity implements Serializable {

	private Long dashboardId;
	private String dashboardVersion;
	private Long reportId;
 
  	public DashboardversionreportEntity() {
  	}
  	
  	@Id
  	@Column(name = "dashboardId", nullable = false)
  	public Long getDashboardId() {
  		return dashboardId;
  	}

  	public void setDashboardId(Long dashboardversionId) {
  		this.dashboardId = dashboardversionId;
  	}
  	
  	@Id
  	@Column(name = "dashboardVersion", nullable = false)
  	public String getDashboardVersion() {
  		return dashboardVersion;
  	}

  	public void setDashboardVersion(String dashboardVersion) {
  		this.dashboardVersion = dashboardVersion;
  	}
  	
  	@Id
  	@Column(name = "reportId", nullable = false)
  	public Long getReportId() {
  		return reportId;
  	}

  	public void setReportId(Long reportId) {
  		this.reportId = reportId;
  	}
  

  	@ManyToOne
  	@JoinColumns({
  	@JoinColumn(name = "dashboardId", referencedColumnName = "id", insertable=false, updatable=false),
  	@JoinColumn(name = "dashboardVersion",referencedColumnName = "version", insertable=false, updatable=false)
  	})
  	public DashboardversionEntity getDashboardversion() {
    	return dashboardversion;
  	}
  	public void setDashboardversion(DashboardversionEntity dashboardversion) {
    	this.dashboardversion = dashboardversion;
  	}
  
  	private DashboardversionEntity dashboardversion;
 
  	@ManyToOne
  	@JoinColumn(name = "reportId", insertable=false, updatable=false)
  	public ReportEntity getReport() {
    	return report;
  	}
  	public void setReport(ReportEntity report) {
    	this.report = report;
  	}
  
  	private ReportEntity report;
  

}
