package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;

public class ReportuserId implements Serializable {

    private Long reportId;
    private Long userId;

    public ReportuserId() {
    }
    
    public ReportuserId(Long reportId, Long userId) {
		super();
		this.reportId = reportId;
		this.userId = userId;
	}
    
	public Long getReportId() {
        return reportId;
    }
	
    public void setReportId(Long reportId){
        this.reportId = reportId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId){
        this.userId = userId;
    }
}
