package com.nfinity.reporting.reportingapp1.application.dashboarduser.dto;

public class CreateDashboarduserOutput {

	private Long userId;
	private Long dashboardId;
	private Boolean editable;
	private Boolean isResetted;
	private Boolean isRefreshed;
	private Boolean ownerSharingStatus;
	private Boolean recipientSharingStatus;
	private Boolean isAssignedByRole; 
	private String userDescriptiveField;

	public String getUserDescriptiveField() {
		return userDescriptiveField;
	}

	public void setUserDescriptiveField(String userDescriptiveField){
		this.userDescriptiveField = userDescriptiveField;
	}

	//	private String dashboardDescriptiveField;
	//
	//	public String getDashboardDescriptiveField() {
	//		return dashboardDescriptiveField;
	//	}
	//
	//	public void setDashboardDescriptiveField(String dashboardDescriptiveField){
	//		this.dashboardDescriptiveField = dashboardDescriptiveField;
	//	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId){
		this.userId = userId;
	}

	public Long getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(Long dashboardId){
		this.dashboardId = dashboardId;
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
