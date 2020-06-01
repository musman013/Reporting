package com.nfinity.reporting.reportingapp1.application.dashboard.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import com.nfinity.reporting.reportingapp1.application.report.dto.UpdateReportInput;

public class UpdateDashboardInput {

	private String description;
	@NotNull(message = "id Should not be null")
	private Long id;
	@NotNull
	private String title;
	private Long ownerId;
	private Long userId;
	private Boolean isPublished;
	List<UpdateReportInput> reportDetails= new ArrayList<>();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public List<UpdateReportInput> getReportDetails() {
		return reportDetails;
	}

	public void setReportDetails(List<UpdateReportInput> reportDetails) {
		this.reportDetails = reportDetails;
	}


}
