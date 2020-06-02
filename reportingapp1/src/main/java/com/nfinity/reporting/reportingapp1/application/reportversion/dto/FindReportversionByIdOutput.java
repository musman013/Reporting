package com.nfinity.reporting.reportingapp1.application.reportversion.dto;

import org.json.simple.JSONObject;

public class FindReportversionByIdOutput {

	private String ctype;
	private String description;
	private JSONObject query;
	private String reportType;
	private String title;
	private Long userId;
	private String userDescriptiveField;
	private Long reportId;
	private Boolean isAssignedByDashboard;

	public Boolean getIsAssignedByDashboard() {
		return isAssignedByDashboard;
	}

	public void setIsAssignedByDashboard(Boolean isAssignedByDashboard) {
		this.isAssignedByDashboard = isAssignedByDashboard;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	private String reportWidth;

	public String getReportWidth() {
		return reportWidth;
	}

	public void setReportWidth(String reportWidth) {
		this.reportWidth = reportWidth;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId){
		this.userId = userId;
	}

	public String getUserDescriptiveField() {
		return userDescriptiveField;
	}

	public void setUserDescriptiveField(String userDescriptiveField){
		this.userDescriptiveField = userDescriptiveField;
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
