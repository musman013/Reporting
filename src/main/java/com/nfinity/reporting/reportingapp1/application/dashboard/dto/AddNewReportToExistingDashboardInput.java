package com.nfinity.reporting.reportingapp1.application.dashboard.dto;

import java.util.ArrayList;
import java.util.List;

import com.nfinity.reporting.reportingapp1.application.report.dto.CreateReportInput;

public class AddNewReportToExistingDashboardInput {
	
	private Long id;

	private String description;

	private String title;

	private Long userId;

	List<CreateReportInput> reportDetails= new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId){
		this.userId = userId;
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
