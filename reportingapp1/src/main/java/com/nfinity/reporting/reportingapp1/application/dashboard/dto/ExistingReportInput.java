package com.nfinity.reporting.reportingapp1.application.dashboard.dto;

import javax.validation.constraints.NotNull;

public class ExistingReportInput {
	
	@NotNull
	private Long id;
	private String reportWidth;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReportWidth() {
		return reportWidth;
	}
	public void setReportWidth(String reportWidth) {
		this.reportWidth = reportWidth;
	}
	

}
