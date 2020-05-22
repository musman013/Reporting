package com.nfinity.reporting.reportingapp1.application.dashboardversion.dto;

import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class CreateDashboardversionInput {

  private String description;
  
  private String title;
  
  private Long userId;

  public Long getUserId() {
  	return userId;
  }

  public void setUserId(Long userId){
  	this.userId = userId;
  }
  public String getDescription() {
  return description;
  }

  public void setDescription(String description){
  this.description = description;
  }
  
  public String getTitle() {
  return title;
  }

  public void setTitle(String title){
  this.title = title;
  }
  
 
}
