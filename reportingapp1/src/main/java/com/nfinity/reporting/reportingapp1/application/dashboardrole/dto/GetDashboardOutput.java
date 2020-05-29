package com.nfinity.reporting.reportingapp1.application.dashboardrole.dto;

public class GetDashboardOutput {

	private String description;
	private Long id;
	private String title;


	private Long dashboardroleRoleId;

	public Long getDashboardroleRoleId() {
		return dashboardroleRoleId;
	}

	public void setDashboardroleRoleId(Long dashboardroleRoleId){
		this.dashboardroleRoleId = dashboardroleRoleId;
	}
	private Long dashboardroleDashboardId;

	public Long getDashboardroleDashboardId() {
		return dashboardroleDashboardId;
	}

	public void setDashboardroleDashboardId(Long dashboardroleDashboardId){
		this.dashboardroleDashboardId = dashboardroleDashboardId;
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
