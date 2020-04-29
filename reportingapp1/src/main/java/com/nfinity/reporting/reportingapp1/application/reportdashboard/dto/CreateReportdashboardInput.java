package com.nfinity.reporting.reportingapp1.application.reportdashboard.dto;

import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class CreateReportdashboardInput {

  @NotNull(message = "dashboardId Should not be null")
  private Long dashboardId;
  
  @NotNull(message = "reportId Should not be null")
  private Long reportId;
  

  public Long getDashboardId() {
  return dashboardId;
  }

  public void setDashboardId(Long dashboardId){
  this.dashboardId = dashboardId;
  }
  
  public Long getReportId() {
  return reportId;
  }

  public void setReportId(Long reportId){
  this.reportId = reportId;
  }
  
 
}
