package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;

@Entity
@Table(name = "reportdashboard", schema = "reporting")
@IdClass(ReportdashboardId.class)
public class ReportdashboardEntity implements Serializable {

	private Long dashboardId;
	private Long reportId;
 
  	public ReportdashboardEntity() {
  	}

  	@ManyToOne
  	@JoinColumn(name = "dashboardId", insertable=false, updatable=false)
  	public DashboardEntity getDashboard() {
    	return dashboard;
  	}
  	public void setDashboard(DashboardEntity dashboard) {
    	this.dashboard = dashboard;
  	}
  
  	private DashboardEntity dashboard;
 
  	@Id
  	@Column(name = "dashboardId", nullable = false)
  	public Long getDashboardId() {
  		return dashboardId;
  	}

  	public void setDashboardId(Long dashboardId) {
  		this.dashboardId = dashboardId;
  	}
  
  	@ManyToOne
  	@JoinColumn(name = "reportId", insertable=false, updatable=false)
  	public ReportEntity getReport() {
    	return report;
  	}
  	public void setReport(ReportEntity report) {
    	this.report = report;
  	}
  
  	private ReportEntity report;
 
  	@Id
  	@Column(name = "reportId", nullable = false)
  	public Long getReportId() {
  		return reportId;
  	}

  	public void setReportId(Long reportId) {
  		this.reportId = reportId;
  	}
  

//  @Override
//  public boolean equals(Object o) {
//    if (this == o) return true;
//      if (!(o instanceof ReportdashboardEntity)) return false;
//        ReportdashboardEntity reportdashboard = (ReportdashboardEntity) o;
//        return id != null && id.equals(reportdashboard.id);
//  }

}

  
      


