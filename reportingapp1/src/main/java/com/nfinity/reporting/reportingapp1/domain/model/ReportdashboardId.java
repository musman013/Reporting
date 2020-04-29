package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;

public class ReportdashboardId implements Serializable {

    private Long dashboardId;
    private Long reportId;

    public ReportdashboardId() {
    }
    
    public ReportdashboardId(Long dashboardId,Long reportId) {
  		this.dashboardId =dashboardId;
  		this.reportId =reportId;
    }
    
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