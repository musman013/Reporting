package com.nfinity.reporting.reportingapp1.application.dashboard.dto;

import java.util.List;
import com.nfinity.reporting.reportingapp1.application.report.dto.FindReportByIdOutput;

public class FindDashboardByIdOutput {

	private String description;
	private String title;
	private Long userId;
	private Long id;
	private Boolean editable;
	private Boolean isResetted;
	private Boolean isRefreshed;
	private Boolean ownerSharingStatus;
	private Boolean recipientSharingStatus;
	private Boolean isAssignedByRole;
	private Boolean isResetable;
	private Boolean isPublished;
	private Long ownerId;
	private String ownerDescriptiveField;
	private List<FindReportByIdOutput> reportDetails;

	public List<FindReportByIdOutput> getReportDetails() {
		return reportDetails;
	}

	public void setReportDetails(List<FindReportByIdOutput> reportDetails) {
		this.reportDetails = reportDetails;
	}

	
	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public Boolean getIsResetted() {
		return isResetted;
	}

	public void setIsResetted(Boolean isResetted) {
		this.isResetted = isResetted;
	}

	public Boolean getIsRefreshed() {
		return isRefreshed;
	}

	public void setIsRefreshed(Boolean isRefreshed) {
		this.isRefreshed = isRefreshed;
	}

	public Boolean getOwnerSharingStatus() {
		return ownerSharingStatus;
	}

	public void setOwnerSharingStatus(Boolean ownerSharingStatus) {
		this.ownerSharingStatus = ownerSharingStatus;
	}

	public Boolean getRecipientSharingStatus() {
		return recipientSharingStatus;
	}

	public void setRecipientSharingStatus(Boolean recipientSharingStatus) {
		this.recipientSharingStatus = recipientSharingStatus;
	}

	public Boolean getIsAssignedByRole() {
		return isAssignedByRole;
	}

	public void setIsAssignedByRole(Boolean isAssignedByRole) {
		this.isAssignedByRole = isAssignedByRole;
	}

	public Boolean getIsResetable() {
		return isResetable;
	}

	public void setIsResetable(Boolean isResetable) {
		this.isResetable = isResetable;
	}

	public void setOwnerDescriptiveField(String ownerDescriptiveField) {
		this.ownerDescriptiveField = ownerDescriptiveField;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId){
		this.ownerId = ownerId;
	}

	public String getOwnerDescriptiveField() {
		return ownerDescriptiveField;
	}

	public void setOwneserDescriptiveField(String ownerDescriptiveField){
		this.ownerDescriptiveField = ownerDescriptiveField;
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


}
