package com.nfinity.reporting.reportingapp1.application.dashboardrole.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateDashboardroleOutput {

    private Long roleId;
    private Long dashboardId;
	private String roleDescriptiveField;

}
