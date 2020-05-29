package com.nfinity.reporting.reportingapp1.application.dashboardversion.dto;

public class CreateDashboardversionInput {

	private String description;
	private String title;
	private Long userId;
	private Long dashboardId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId){
		this.userId = userId;
	}
	
	public Long getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(Long dashboardId) {
		this.dashboardId = dashboardId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title){
		this.title = title;
	}


}
