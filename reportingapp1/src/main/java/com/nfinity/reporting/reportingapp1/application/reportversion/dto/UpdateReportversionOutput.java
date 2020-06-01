package com.nfinity.reporting.reportingapp1.application.reportversion.dto;

import org.json.simple.JSONObject;

public class UpdateReportversionOutput {

	private String ctype;
	private String description;
	private JSONObject query;
	private String reportType;
	private String title;
	private String version;
	private String reportWidth;
	private Long userId;
	private String userDescriptiveField;
	private Long reportId;
	private Boolean isCreatedInDashboard;

	public Boolean getIsCreatedInDashboard() {
		return isCreatedInDashboard;
	}

	public void setIsCreatedInDashboard(Boolean isCreatedInDashboard) {
		this.isCreatedInDashboard = isCreatedInDashboard;
	}


	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version){
		this.version = version;
	}
}