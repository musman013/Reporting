package com.nfinity.reporting.reportingapp1.application.dashboard.dto;

import javax.validation.constraints.NotNull;

public class CreateDashboardInput {

	private String description;
	private Boolean isPublished;
	@NotNull
	private String title;
	private Long ownerId;
	private Boolean isSharable;
	
	public Boolean getIsSharable() {
		return isSharable;
	}

	public void setIsSharable(Boolean isSharable) {
		this.isSharable = isSharable;
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

	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}




}
