package com.nfinity.reporting.reportingapp1.application.report.dto;

import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class UpdateReportInput {

  private String ctype;
  private String description;
  @NotNull(message = "id Should not be null")
  private Long id;
  private String query;
  @Length(max = 255, message = "reportType must be less than 255 characters")
  private String reportType;
  private String title;
  private Long userId;

  public Long getUserId() {
  	return userId;
  }

  public void setUserId(Long userId){
  	this.userId = userId;
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
