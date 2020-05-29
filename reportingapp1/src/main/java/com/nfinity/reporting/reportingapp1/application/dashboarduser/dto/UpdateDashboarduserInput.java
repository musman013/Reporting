package com.nfinity.reporting.reportingapp1.application.dashboarduser.dto;

import javax.validation.constraints.NotNull;

public class UpdateDashboarduserInput {

	@NotNull(message = "userId Should not be null")
	private Long userId;
	@NotNull(message = "dashboardId Should not be null")
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
}
