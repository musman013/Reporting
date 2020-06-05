package com.nfinity.reporting.reportingapp1.application.reportrole.dto;

import javax.validation.constraints.NotNull;

public class CreateReportroleInput {

	@NotNull(message = "roleId Should not be null")
	private Long roleId;

	@NotNull(message = "reportId Should not be null")
	private Long reportId;

	@NotNull(message = "editable Should not be null")
	private Boolean editable;
	
	@NotNull(message = "ownerSharingStatus Should not be null")
	private Boolean ownerSharingStatus;
	
	public Boolean getOwnerSharingStatus() {
		return ownerSharingStatus;
	}

	public void setOwnerSharingStatus(Boolean ownerSharingStatus) {
		this.ownerSharingStatus = ownerSharingStatus;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId){
		this.roleId = roleId;
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




}
