package com.nfinity.reporting.reportingapp1.application.reportuser.dto;

import javax.validation.constraints.NotNull;

public class CreateReportuserInput {

	@NotNull(message = "userId Should not be null")
	private Long userId;

	@NotNull(message = "reportId Should not be null")
	private Long reportId;
	
	private Boolean editable;
	private Boolean isResetted;
	private Boolean isRefreshed;
	private Boolean ownerSharingStatus;
	private Boolean recipientSharingStatus;
	private Boolean isAssignedByRole;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId){
		this.userId = userId;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId){
		this.reportId = reportId;
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

	

}
