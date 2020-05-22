package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;

public class DashboardroleId implements Serializable {

    private Long dashboardId;
    private Long roleId;

    public DashboardroleId(){
    }
    
    public DashboardroleId(Long dashboardId, Long roleId) {
		super();
		this.dashboardId = dashboardId;
		this.roleId = roleId;
	}
	public Long getDashboardId() {
        return dashboardId;
    }
    public void setDashboardId(Long dashboardId){
        this.dashboardId = dashboardId;
    }
    public Long getRoleId() {
        return roleId;
    }
    public void setRoleId(Long roleId){
        this.roleId = roleId;
    }
    
}