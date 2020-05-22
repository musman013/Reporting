package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;

public class ReportroleId implements Serializable {

    private Long reportId;
    private Long roleId;

    public ReportroleId(){
    }
    
    public ReportroleId(Long reportId, Long roleId) {
		super();
		this.reportId = reportId;
		this.roleId = roleId;
	}
    
	public Long getReportId() {
        return reportId;
    }
	
    public void setReportId(Long reportId){
        this.reportId = reportId;
    }
    
    public Long getRoleId() {
        return roleId;
    }
    
    public void setRoleId(Long roleId){
        this.roleId = roleId;
    }
}
