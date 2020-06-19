package com.nfinity.reporting.reportingapp1.application.authorization.userrole.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateUserroleInput {

	@NotNull(message = "roleId Should not be null")
	private Long roleId;

	@NotNull(message = "id Should not be null")
	private Long userId;

}
