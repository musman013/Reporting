package com.nfinity.reporting.reportingapp1.application.resourceviewer.dto;

import com.nfinity.reporting.reportingapp1.application.permalink.dto.FindPermalinkByIdOutput;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResourceOutput {

	FindPermalinkByIdOutput resourceInfo;
	Object data;

}
