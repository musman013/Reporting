package com.nfinity.reporting.reportingapp1.application.reportversion.dto;

import org.json.simple.JSONObject;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateReportversionInput {
	
	private String ctype;
	private String description;
	private JSONObject query;
	private String reportType;
	private String title;
	private String reportVersion;
	private Long userId;
	private String userDescriptiveField;
	private String reportWidth;
	private Long reportId;
	private Boolean isAssignedByDashboard;
	private Long version;

}
