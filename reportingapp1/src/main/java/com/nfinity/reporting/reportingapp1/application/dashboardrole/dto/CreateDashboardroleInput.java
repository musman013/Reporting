package com.nfinity.reporting.reportingapp1.application.dashboardrole.dto;

import javax.validation.constraints.NotNull;

public class CreateDashboardroleInput {

	@NotNull(message = "roleId Should not be null")
	private Long roleId;

	@NotNull(message = "dashboardId Should not be null")
	private Long dashboardId;

	@NotNull(message = "editable Should not be null")
	private Boolean editable;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId){
		this.roleId = roleId;
	}

	public Long getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(Long dashboardId){
		this.dashboardId = dashboardId;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}


}
