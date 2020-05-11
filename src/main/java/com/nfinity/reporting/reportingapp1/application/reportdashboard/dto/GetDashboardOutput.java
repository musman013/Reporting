package com.nfinity.reporting.reportingapp1.application.reportdashboard.dto;

import java.util.Date;

public class GetDashboardOutput {
  private String description;
  private Long id;
  private String title;

  private Long reportdashboardDashboardId;
  
  public Long getReportdashboardDashboardId() {
  	return reportdashboardDashboardId;
  }

  public void setReportdashboardDashboardId(Long reportdashboardDashboardId){
  	this.reportdashboardDashboardId = reportdashboardDashboardId;
  }
  private Long reportdashboardReportId;
  
  public Long getReportdashboardReportId() {
  	return reportdashboardReportId;
  }

  public void setReportdashboardReportId(Long reportdashboardReportId){
  	this.reportdashboardReportId = reportdashboardReportId;
  }
  public String getDescription() {
  	return description;
  }

  public void setDescription(String description){
  	this.description = description;
  }
  public Long getId() {
  	return id;
  }

  public void setId(Long id){
  	this.id = id;
  }
  public String getTitle() {
  	return title;
  }

  public void setTitle(String title){
  	this.title = title;
  }

}
