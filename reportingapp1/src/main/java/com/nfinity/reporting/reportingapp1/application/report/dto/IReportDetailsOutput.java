package com.nfinity.reporting.reportingapp1.application.report.dto;

import org.json.simple.JSONObject;

public class IReportDetailsOutput {

//	Long getReportId();
//	Boolean getIsPublished();
//	String getCtype();
//	String getDescription();
//	JSONObject getQuery();
//	String getReportType();
//	String getTitle();
//	String getVersion();
//	String getReportWidth();
////	Long getOwnerId();
//	Long getUserId();
////	Boolean getEditable();
////	Boolean isResetted;
////	Boolean isRefreshed;
////	Boolean ownerSharingStatus;
////	Boolean recipientSharingStatus;
////	Boolean isAssignedByRole;
//	Boolean getSharedWithMe();
//	Boolean getSharedWithOthers();
	
	private Long report_id;
	private String ctype;
	private String description;
	private JSONObject query;
	private String report_type;
	private String title;
	private String version;
	private String report_width;
	private Long owner_id;
	private Long user_id;
//	private Boolean editable;
//	private Boolean isResetted;
//	private Boolean isRefreshed;
//	private Boolean ownerSharingStatus;
//	private Boolean recipientSharingStatus;
//	private Boolean isAssignedByRole;
	private Boolean shared_with_me;
	private Boolean shared_with_others;
	public Long getReport_id() {
		return report_id;
	}
	public void setReport_id(Long report_id) {
		this.report_id = report_id;
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
	public String getReport_type() {
		return report_type;
	}
	public void setReport_type(String report_type) {
		this.report_type = report_type;
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
	public String getReport_width() {
		return report_width;
	}
	public void setReport_width(String report_width) {
		this.report_width = report_width;
	}
	public Long getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(Long owner_id) {
		this.owner_id = owner_id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Boolean getShared_with_me() {
		return shared_with_me;
	}
	public void setShared_with_me(Boolean shared_with_me) {
		this.shared_with_me = shared_with_me;
	}
	public Boolean getShared_with_others() {
		return shared_with_others;
	}
	public void setShared_with_others(Boolean shared_with_others) {
		this.shared_with_others = shared_with_others;
	}
	
	
}
