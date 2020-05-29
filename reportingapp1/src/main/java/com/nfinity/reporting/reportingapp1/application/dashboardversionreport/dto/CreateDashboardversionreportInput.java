package com.nfinity.reporting.reportingapp1.application.dashboardversionreport.dto;

import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class CreateDashboardversionreportInput {

	@NotNull(message = "dashboardId Should not be null")
	private Long dashboardId;
	private Long userId;
	private String version;

	@NotNull(message = "reportId Should not be null")
	private Long reportId;


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
