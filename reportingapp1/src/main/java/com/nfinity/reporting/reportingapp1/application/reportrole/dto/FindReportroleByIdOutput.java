package com.nfinity.reporting.reportingapp1.application.reportrole.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FindReportroleByIdOutput {

	private Long roleId;
	private Long reportId;
	private String roleDescriptiveField;

}
