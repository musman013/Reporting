package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reportuser", schema = "reporting")
@IdClass(ReportuserId.class)
@SuppressWarnings("serial")
public class ReportuserEntity implements Serializable {
	
	private Long reportId;
	private Long userId;
	private Boolean editable;
	private Boolean isResetted;
	private Boolean isRefreshed;
	private Boolean ownerSharingStatus;
	private Boolean recipientSharingStatus;
	private Boolean isAssignedByRole; 
	
	@Id
  	@Column(name = "reportId", nullable = false)
  	public Long getReportId() {
  		return reportId;
  	}
  	
  	public void setReportId(Long reportId) {
  		this.reportId = reportId;
  	}
	
  	@Id
  	@Column(name = "userId", nullable = false)
  	public Long getUserId() {
  		return userId;
  	}
  	
  	public void setUserId(Long userId) {
  		this.userId = userId;
  	}
  	
  	@Basic
	@Column(name = "editable", nullable = false)
	public Boolean getEditable() {
		return editable;
	}
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
	
	@Basic
	@Column(name = "isResetted", nullable = false)
	public Boolean getIsResetted() {
		return isResetted;
	}

	public void setIsResetted(Boolean isResetted) {
		this.isResetted = isResetted;
	}

	@Basic
	@Column(name = "isRefreshed", nullable = false)
	public Boolean getIsRefreshed() {
		return isRefreshed;
	}

	public void setIsRefreshed(Boolean isRefreshed) {
		this.isRefreshed = isRefreshed;
	}

	@Basic
	@Column(name = "ownerSharingStatus", nullable = false)
	public Boolean getOwnerSharingStatus() {
		return ownerSharingStatus;
	}

	public void setOwnerSharingStatus(Boolean ownerSharingStatus) {
		this.ownerSharingStatus = ownerSharingStatus;
	}

	@Basic
	@Column(name = "recipientSharingStatus", nullable = false)
	public Boolean getRecipientSharingStatus() {
		return recipientSharingStatus;
	}

	public void setRecipientSharingStatus(Boolean recipientSharingStatus) {
		this.recipientSharingStatus = recipientSharingStatus;
	}

	@Basic
	@Column(name = "isAssignedByRole", nullable = false)
	public Boolean getIsAssignedByRole() {
		return isAssignedByRole;
	}

	public void setIsAssignedByRole(Boolean isAssignedByRole) {
		this.isAssignedByRole = isAssignedByRole;
	}

	@ManyToOne
  	@JoinColumn(name = "reportId", insertable=false, updatable=false)
  	public ReportEntity getReport() {
    	return report;
  	}
  	public void setReport(ReportEntity report) {
    	this.report = report;
  	}
  
  	private ReportEntity report;
  	
  	@ManyToOne
  	@JoinColumn(name = "userId", insertable=false, updatable=false)
  	public UserEntity getUser() {
    	return user;
  	}
  	public void setUser(UserEntity user) {
    	this.user = user;
  	}
  
  	private UserEntity user;
	
}
