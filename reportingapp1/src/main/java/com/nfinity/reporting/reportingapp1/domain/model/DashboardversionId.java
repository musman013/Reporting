package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;

public class DashboardversionId implements Serializable {

    private Long dashboardId;
    private Long userId;
    private String version;
   

    public DashboardversionId(){
    }
    
    public DashboardversionId(Long userId,Long dashboardId,  String version) {
		super();
		this.dashboardId = dashboardId;
		this.userId = userId;
		this.version = version;
	}
    
	public Long getDashboardId() {
        return dashboardId;
    }
	
    public void setDashboardId(Long dashboardId){
        this.dashboardId = dashboardId;
    }
    
    public Long getUserId() {
        return userId;
    }
	
    public void setUserId(Long userId){
        this.userId = userId;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version){
        this.version = version;
    }
}
