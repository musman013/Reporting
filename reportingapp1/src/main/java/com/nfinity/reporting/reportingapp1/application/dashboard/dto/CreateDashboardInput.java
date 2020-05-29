package com.nfinity.reporting.reportingapp1.application.dashboard.dto;

public class CreateDashboardInput {

	private String description;
	private Boolean isPublished;
	private String title;
	private Long ownerId;

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

	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}




}
