package com.nfinity.reporting.reportingapp1.application.dashboardversion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateDashboardversionOutput {

	private String description;
	private Long id;
	private String title;
	private Long userId;
	private String userDescriptiveField;

}