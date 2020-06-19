package com.nfinity.reporting.reportingapp1.application.dashboard.dto;

import java.util.List;

import com.nfinity.reporting.reportingapp1.application.report.dto.FindReportByIdOutput;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateDashboardOutput {

	private String description;
	private Long id;
	private String title;
	private Long ownerId;
	private String ownerDescriptiveField;
	private Boolean isShareable;
	private List<FindReportByIdOutput> reportDetails;
	private Long version;

}