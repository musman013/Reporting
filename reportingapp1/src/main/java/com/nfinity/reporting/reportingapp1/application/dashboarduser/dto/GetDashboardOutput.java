package com.nfinity.reporting.reportingapp1.application.dashboarduser.dto;

public class GetDashboardOutput {
	
	private String description;
	private Long id;
	private String title;

	private Long dashboarduserUserId;

	public Long getDashboarduserUserId() {
		return dashboarduserUserId;
	}

	public void setDashboarduserUserId(Long dashboarduserUserId){
		this.dashboarduserUserId = dashboarduserUserId;
	}
	private Long dashboarduserDashboardId;

	public Long getDashboarduserDashboardId() {
		return dashboarduserDashboardId;
	}

	public void setDashboarduserDashboardId(Long dashboarduserDashboardId){
		this.dashboarduserDashboardId = dashboarduserDashboardId;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description){
		this.description = description;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title){
		this.title = title;
	}

}
