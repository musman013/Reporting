package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;

public class DashboardversionreportId implements Serializable {

	    private Long dashboardId;
	    private String dashboardVersion;
	    private Long reportId;

	    public DashboardversionreportId() {
	    }
	    
	    public DashboardversionreportId(Long dashboardId,String dashboardVersion,Long reportId) {
	  		this.dashboardId =dashboardId;
	  		this.dashboardVersion = dashboardVersion;
	  		this.reportId =reportId;
	    }
	    
	    public Long getDashboardId() {
	        return dashboardId;
	    }
	    public void setDashboardId(Long dashboardId){
	        this.dashboardId = dashboardId;
	    }
	    
	    public String getDashboardVersion() {
	        return dashboardVersion;
	    }
	    public void setDashboardVersion(String dashboardVersion){
	        this.dashboardVersion = dashboardVersion;
	    }
	    
	    public Long getReportId() {
	        return reportId;
	    }
	    public void setReportId(Long reportId){
	        this.reportId = reportId;
	    }
}
