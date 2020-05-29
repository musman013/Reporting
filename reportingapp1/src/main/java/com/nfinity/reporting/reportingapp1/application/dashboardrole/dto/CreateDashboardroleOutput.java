package com.nfinity.reporting.reportingapp1.application.dashboardrole.dto;

public class CreateDashboardroleOutput {

    private Long roleId;
    private Long dashboardId;
	private String roleDescriptiveField;
	
  public String getRoleDescriptiveField() {
  	return roleDescriptiveField;
  }

  public void setRoleDescriptiveField(String roleDescriptiveField){
  	this.roleDescriptiveField = roleDescriptiveField;
  }
//  private String dashboardDescriptiveField;
//
//  public String getDashboardDescriptiveField() {
//  	return dashboardDescriptiveField;
//  }
//
//  public void setDashboardDescriptiveField(String dashboardDescriptiveField){
//  	this.dashboardDescriptiveField = dashboardDescriptiveField;
//  }
 
  public Long getRoleId() {
  	return roleId;
  }

  public void setRoleId(Long roleId){
  	this.roleId = roleId;
  }
  
  public Long getDashboardId() {
  	return dashboardId;
  }

  public void setDashboardId(Long dashboardId){
  	this.dashboardId = dashboardId;
  }
  
}
