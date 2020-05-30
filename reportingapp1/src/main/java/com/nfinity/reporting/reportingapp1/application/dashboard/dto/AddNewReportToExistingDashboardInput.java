package com.nfinity.reporting.reportingapp1.application.dashboard.dto;

import java.util.ArrayList;
import java.util.List;

import com.nfinity.reporting.reportingapp1.application.report.dto.CreateReportInput;

public class AddNewReportToExistingDashboardInput {
	
	private Long id;
	private String description;
	private String title;
	private Long ownerId;
    private Boolean isPublished;
	
	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	List<CreateReportInput> reportDetails= new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId){
		this.ownerId = ownerId;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public List<CreateReportInput> getReportDetails() {
		return reportDetails;
	}

	public void setReportDetails(List<CreateReportInput> reportDetails) {
		this.reportDetails = reportDetails;
	}


}
