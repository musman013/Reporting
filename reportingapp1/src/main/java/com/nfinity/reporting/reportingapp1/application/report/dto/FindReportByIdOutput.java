package com.nfinity.reporting.reportingapp1.application.report.dto;

import java.util.Date;

import org.json.simple.JSONObject;

import com.nfinity.reporting.reportingapp1.application.reportversion.dto.FindReportversionByIdOutput;

public class FindReportByIdOutput {

	private Long reportId;
	private Boolean isPublished;
	private String ctype;
	private String description;
	private JSONObject query;
	private String reportType;
	private String title;
	private String version;
	private String reportWidth;
	private Long userId;
	private Boolean editable;
	private Boolean isResetted;
	private Boolean isRefreshed;
	private Boolean ownerSharingStatus;
	private Boolean recipientSharingStatus;
	private Boolean isAssignedByRole;
	private Boolean isResetable;
	private Long ownerId;

	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	public Boolean getIsPublished() {
		return isPublished;
	}
	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}
	public String getCtype() {
		return ctype;
	}
	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public JSONObject getQuery() {
		return query;
	}
	public void setQuery(JSONObject query) {
		this.query = query;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getReportWidth() {
		return reportWidth;
	}
	public void setReportWidth(String reportWidth) {
		this.reportWidth = reportWidth;
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
 
}
