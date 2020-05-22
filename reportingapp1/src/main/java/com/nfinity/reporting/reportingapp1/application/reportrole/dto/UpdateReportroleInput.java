package com.nfinity.reporting.reportingapp1.application.reportrole.dto;

import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class UpdateReportroleInput {

  @NotNull(message = "roleId Should not be null")
  private Long roleId;
  @NotNull(message = "reportId Should not be null")
  private Long reportId;

 
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
