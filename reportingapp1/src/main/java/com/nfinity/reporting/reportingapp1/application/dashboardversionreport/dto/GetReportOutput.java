package com.nfinity.reporting.reportingapp1.application.dashboardversionreport.dto;

import org.json.simple.JSONObject;

public class GetReportOutput {
	private String ctype;
	private String description;
	private Long id;
	private JSONObject query;
	private String reportType;
	private String title;
	private String reportWidth;

	public String getReportWidth() {
		return reportWidth;
	}

	public void setReportWidth(String reportWidth) {
		this.reportWidth = reportWidth;
	}

	private Long reportdashboardDashboardId;

	public Long getReportdashboardDashboardId() {
		return reportdashboardDashboardId;
	}

	public void setReportdashboardDashboardId(Long reportdashboardDashboardId){
		this.reportdashboardDashboardId = reportdashboardDashboardId;
	}
	private Long reportdashboardReportId;

	public Long getReportdashboardReportId() {
		return reportdashboardReportId;
	}

	public void setReportdashboardReportId(Long reportdashboardReportId){
		this.reportdashboardReportId = reportdashboardReportId;
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
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
