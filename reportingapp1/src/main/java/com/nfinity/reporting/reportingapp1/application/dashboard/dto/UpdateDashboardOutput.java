package com.nfinity.reporting.reportingapp1.application.dashboard.dto;

public class UpdateDashboardOutput {

	private String description;
	private Long id;
	private String title;
	private Long ownerId;
	private String ownerDescriptiveField;
	private Boolean isShareable;

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