package com.nfinity.reporting.reportingapp1.application.authorization.rolepermission.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateRolepermissionOutput {

	private Long permissionId;
	private Long roleId;
	private String permissionDescriptiveField;
	private String roleDescriptiveField;

}
