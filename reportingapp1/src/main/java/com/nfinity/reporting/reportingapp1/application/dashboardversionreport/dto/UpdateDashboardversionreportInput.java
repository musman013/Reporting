package com.nfinity.reporting.reportingapp1.application.dashboardversionreport.dto;

import javax.validation.constraints.NotNull;

public class UpdateDashboardversionreportInput {

	@NotNull(message = "dashboardId Should not be null")
	private Long dashboardId;
	@NotNull(message = "reportId Should not be null")
	private Long reportId;
	private Long userId;
	private String version;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Long getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(Long dashboardId){
		this.dashboardId = dashboardId;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId){
		this.reportId = reportId;
	}
}
