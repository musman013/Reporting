package com.nfinity.reporting.reportingapp1.application.dashboard.dto;

public class GetRoleOutput {

	private Long id;
	private String displayName;
	private String name;
	private Long dashboardId;
	private Boolean editable;
	
	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Long getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(Long dashboardId){
		this.dashboardId = dashboardId;
	}
}
