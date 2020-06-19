package com.nfinity.reporting.reportingapp1.application.authorization.userpermission.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateUserpermissionInput {

    @NotNull(message = "permissionId Should not be null")
    private Long permissionId;
  
    @NotNull(message = "id Should not be null")
    private Long userId;
    private Boolean revoked;
    private Long version;
 
}
