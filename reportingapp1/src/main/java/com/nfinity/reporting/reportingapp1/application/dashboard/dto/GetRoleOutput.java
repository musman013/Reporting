package com.nfinity.reporting.reportingapp1.application.dashboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetRoleOutput {

	private Long id;
	private String displayName;
	private String name;
	private Long dashboardId;
	private Boolean editable;
	
}
