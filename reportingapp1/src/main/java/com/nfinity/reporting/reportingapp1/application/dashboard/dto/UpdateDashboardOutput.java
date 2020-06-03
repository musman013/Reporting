package com.nfinity.reporting.reportingapp1.application.dashboard.dto;

import java.util.List;

import com.nfinity.reporting.reportingapp1.application.report.dto.FindReportByIdOutput;

public class UpdateDashboardOutput {

	private String description;
	private Long id;
	private String title;
	private Long ownerId;
	private String ownerDescriptiveField;
	private Boolean isShareable;
	private List<FindReportByIdOutput> reportDetails;


	public List<FindReportByIdOutput> getReportDetails() {
		return reportDetails;
	}

	public void setReportDetails(List<FindReportByIdOutput> reportDetails) {
		this.reportDetails = reportDetails;
	}

	public Boolean getIsShareable() {
		return isShareable;
	}

	public void setIsShareable(Boolean isShareable) {
		this.isShareable = isShareable;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public String getOwnerDescriptiveField() {
		return ownerDescriptiveField;
	}

	public void setOwnerDescriptiveField(String ownerDescriptiveField){
		this.ownerDescriptiveField = ownerDescriptiveField;
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