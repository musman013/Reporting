package com.nfinity.reporting.reportingapp1.application.dashboardversion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateDashboardversionInput {

	private String description;
	private String title;
	private Long userId;
	private Long dashboardId;

}
