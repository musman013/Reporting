package com.nfinity.reporting.reportingapp1.application.reportuser.dto;

import javax.validation.constraints.NotNull;

public class UpdateReportuserInput {

	@NotNull(message = "userId Should not be null")
	private Long userId;
	@NotNull(message = "reportId Should not be null")
	private Long reportId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId){
		this.userId = userId;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId){
		this.reportId = reportId;
	}
}
