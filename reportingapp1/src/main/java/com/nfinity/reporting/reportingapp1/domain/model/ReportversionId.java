package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;

public class ReportversionId implements Serializable {

    private Long userId;
    private Long reportId;
    private String version;

    public ReportversionId(){
    }
    
    public ReportversionId(Long userId, Long reportId, String version) {
		super();
		this.userId = userId;
		this.reportId = reportId;
		this.version = version;
	}
    
//    public ReportversionId(Long userId,  String version) {
//		super();
//		this.userId = userId;
//		this.version = version;
//	}
    
	public Long getUserId() {
        return userId;
    }
	
    public void setUserId(Long userId){
        this.userId = userId;
    }
    
    public Long getReportId() {
  		return reportId;
  	}

  	public void setReportId(Long reportId) {
  		this.reportId = reportId;
  	}
  	
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version){
        this.version = version;
    }
}
