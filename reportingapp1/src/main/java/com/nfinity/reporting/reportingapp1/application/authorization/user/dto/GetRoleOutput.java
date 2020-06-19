package com.nfinity.reporting.reportingapp1.application.authorization.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetRoleOutput {
	
    private Long id;
    private String displayName;
    private String name;
    private Long userId;
  	private String userDescriptiveField;
  	
}

