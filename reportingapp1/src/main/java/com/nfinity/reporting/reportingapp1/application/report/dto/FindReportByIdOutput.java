package com.nfinity.reporting.reportingapp1.application.report.dto;

import java.util.Date;
public class FindReportByIdOutput {

  private String ctype;
  private String description;
  private Long id;
  private String query;
  private String reportType;
  private String title;
  private Long userId;
  private String userDescriptiveField;

  public Long getUserId() {
  	return userId;
  }

  public void setUserId(Long userId){
  	this.userId = userId;
  }
  
  public String getUserDescriptiveField() {
  	return userDescriptiveField;
  }

  public void setUserDescriptiveField(String userDescriptiveField){
  	this.userDescriptiveField = userDescriptiveField;
  }
 
  public String getCtype() {
  	return ctype;
  }

  public void setCtype(String ctype){
  	this.ctype = ctype;
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
  
  public String getQuery() {
  	return query;
  }

  public void setQuery(String query){
  	this.query = query;
  }
  
  public String getReportType() {
  	return reportType;
  }

  public void setReportType(String reportType){
  	this.reportType = reportType;
  }
  
  public String getTitle() {
  	return title;
  }

  public void setTitle(String title){
  	this.title = title;
  }
  
 
}
