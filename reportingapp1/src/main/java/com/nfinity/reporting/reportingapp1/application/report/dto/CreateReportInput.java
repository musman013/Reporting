package com.nfinity.reporting.reportingapp1.application.report.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.json.simple.JSONObject;

public class CreateReportInput {
	
	private Long ownerId;
	private Boolean isPublished;
	private String ctype;
	private String description;
	private JSONObject query;
	@Length(max = 255, message = "reportType must be less than 255 characters")
	private String reportType;
	@NotNull
	private String title;
	private String reportWidth;
	private Boolean isAssignedByDashboard;
	

	public Boolean getIsAssignedByDashboard() {
		return isAssignedByDashboard;
	}

	public void setIsAssignedByDashboard(Boolean isAssignedByDashboard) {
		this.isAssignedByDashboard = isAssignedByDashboard;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId){
		this.ownerId = ownerId;
	}
	
	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	public String getReportWidth() {
		return reportWidth;
	}

	public void setReportWidth(String reportWidth) {
		this.reportWidth = reportWidth;
	}
	
	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype){
		this.ctype = ctype;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public JSONObject getQuery() {
		return query;
	}

	public void setQuery(JSONObject query){
		this.query = query;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType){
		this.reportType = reportType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title){
		this.title = title;
	}

}
