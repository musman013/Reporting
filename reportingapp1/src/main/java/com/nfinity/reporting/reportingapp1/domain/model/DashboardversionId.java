package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;

public class DashboardversionId implements Serializable {

    private Long id;
    private String version;

    public DashboardversionId(){
    }
    
    public DashboardversionId(Long id, String version) {
		super();
		this.id = id;
		this.version = version;
	}
    
	public Long getId() {
        return id;
    }
	
    public void setId(Long id){
        this.id = id;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version){
        this.version = version;
    }
}
