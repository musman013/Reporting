package com.nfinity.reporting.reportingapp1.application.resourceviewer.dto;

import com.nfinity.reporting.reportingapp1.application.permalink.dto.FindPermalinkByIdOutput;

public class ResourceOutput {

	FindPermalinkByIdOutput resourceInfo;
	Object data;

	public FindPermalinkByIdOutput getResourceInfo() {
		return resourceInfo;
	}
	public void setResourceInfo(FindPermalinkByIdOutput resource) {
		this.resourceInfo = resource;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

}
