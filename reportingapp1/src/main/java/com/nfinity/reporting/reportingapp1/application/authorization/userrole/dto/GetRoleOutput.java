package com.nfinity.reporting.reportingapp1.application.authorization.userrole.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetRoleOutput {
    
    private String displayName;
    private Long id;
    private String name;
    private Long userroleRoleId;
	private Long userroleUserId;
  
}
