package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;

public class DashboarduserId implements Serializable {

    private Long dashboardId;
    private Long userId;

    public DashboarduserId() {
    }
    
    public DashboarduserId(Long dashboardId, Long userId) {
		super();
		this.dashboardId = dashboardId;
		this.userId = userId;
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
    
}
