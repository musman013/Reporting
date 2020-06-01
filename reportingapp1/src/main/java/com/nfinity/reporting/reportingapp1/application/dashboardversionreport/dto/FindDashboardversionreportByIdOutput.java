package com.nfinity.reporting.reportingapp1.application.dashboardversionreport.dto;

public class FindDashboardversionreportByIdOutput {

	private Long dashboardId;
	private Long reportId;
	private Long userId;
	private String dasboardVersion;
	private String reportWidth;
	private Long orderId;


	public Long getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(Long dashboardId){
		this.dashboardId = dashboardId;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId){
		this.reportId = reportId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDasboardVersion() {
		return dasboardVersion;
	}

	public void setDasboardVersion(String dasboardVersion) {
		this.dasboardVersion = dasboardVersion;
	}

	public String getReportWidth() {
		return reportWidth;
	}

	public void setReportWidth(String reportWidth) {
		this.reportWidth = reportWidth;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

}
