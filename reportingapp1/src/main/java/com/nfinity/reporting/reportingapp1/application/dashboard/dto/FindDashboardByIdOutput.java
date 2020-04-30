package com.nfinity.reporting.reportingapp1.application.dashboard.dto;

import java.util.Date;
import java.util.List;

import com.nfinity.reporting.reportingapp1.application.report.dto.FindReportByIdOutput;
public class FindDashboardByIdOutput {

	private String description;
	private Long id;
	private String title;
	private Long userId;
	private String userDescriptiveField;
	private List<FindReportByIdOutput> reportDetails;

	public List<FindReportByIdOutput> getReportDetails() {
		return reportDetails;
	}

	public void setReportDetails(List<FindReportByIdOutput> reportDetails) {
		this.reportDetails = reportDetails;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId){
		this.userId = userId;
	}

	public String getUserDescriptiveField() {
		return userDescriptiveField;
	}

	public void setUserDescriptiveField(String userDescriptiveField){
		this.userDescriptiveField = userDescriptiveField;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title){
		this.title = title;
	}


}
