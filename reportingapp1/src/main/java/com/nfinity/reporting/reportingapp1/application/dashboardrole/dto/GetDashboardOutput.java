package com.nfinity.reporting.reportingapp1.application.dashboardrole.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetDashboardOutput {

	private String description;
	private Long id;
	private String title;
	private Long dashboardroleRoleId;
	private Long dashboardroleDashboardId;

}
