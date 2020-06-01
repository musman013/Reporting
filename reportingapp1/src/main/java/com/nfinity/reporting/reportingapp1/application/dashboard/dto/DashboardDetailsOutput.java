package com.nfinity.reporting.reportingapp1.application.dashboard.dto;

public class DashboardDetailsOutput {

	private Long userId;
	private Long id;
	private String description;
	private String version;
	private Boolean isPublished;
	private String title;
	private Long ownerId;
	private Boolean editable;
	private Boolean isResetted;
	private Boolean isRefreshed;
	private Boolean ownerSharingStatus;
	private Boolean recipientSharingStatus;
	private Boolean isAssignedByRole;
	private Boolean sharedWithMe;
	private Boolean sharedWithOthers;
	private Boolean isSharable;

	public Boolean getIsSharable() {
		return isSharable;
	}

	public void setIsSharable(Boolean isSharable) {
		this.isSharable = isSharable;
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Boolean getIsPublished() {
		return isPublished;
	}
	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
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
	public Boolean getSharedWithMe() {
		return sharedWithMe;
	}
	public void setSharedWithMe(Boolean sharedWithMe) {
		this.sharedWithMe = sharedWithMe;
	}
	public Boolean getSharedWithOthers() {
		return sharedWithOthers;
	}
	public void setSharedWithOthers(Boolean sharedWithOthers) {
		this.sharedWithOthers = sharedWithOthers;
	} 

}
