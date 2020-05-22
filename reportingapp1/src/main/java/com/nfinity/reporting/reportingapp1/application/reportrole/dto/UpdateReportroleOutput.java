package com.nfinity.reporting.reportingapp1.application.reportrole.dto;


public class UpdateReportroleOutput {

  private Long roleId;
  private Long reportId;
	private String roleDescriptiveField;
//	private String reportDescriptiveField;

  public String getRoleDescriptiveField() {
  	return roleDescriptiveField;
  }

  public void setRoleDescriptiveField(String roleDescriptiveField){
  	this.roleDescriptiveField = roleDescriptiveField;
  }
 
//  public String getReportDescriptiveField() {
//  	return reportDescriptiveField;
//  }
//
//  public void setReportDescriptiveField(String reportDescriptiveField){
//  	this.reportDescriptiveField = reportDescriptiveField;
//  }
 
  public Long getRoleId() {
  	return roleId;
  }

  public void setRoleId(Long roleId){
  	this.roleId = roleId;
  }
  
  public Long getReportId() {
  	return reportId;
  }

  public void setReportId(Long reportId){
  	this.reportId = reportId;
  }
  

}