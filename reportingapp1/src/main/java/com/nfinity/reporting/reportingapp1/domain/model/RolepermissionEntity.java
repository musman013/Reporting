package com.nfinity.reporting.reportingapp1.domain.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "Rolepermission", schema = "reporting")
@IdClass(RolepermissionId.class)
public class RolepermissionEntity extends AbstractEntity {

	private static final long serialVersionUID = 1L; 
	
	@Id
	@Column(name = "permissionId", nullable = false)
	private Long permissionId; 
	
	@Id
	@Column(name = "roleId", nullable = false)
	private Long roleId; 

	@ManyToOne
	@JoinColumn(name = "permissionId", insertable=false, updatable=false)
	private PermissionEntity permission;

	@ManyToOne
	@JoinColumn(name = "roleId", insertable=false, updatable=false)
	private RoleEntity role;

	public RolepermissionEntity(Long permissionId, Long roleId) {
		this.roleId = roleId;
		this.permissionId = permissionId;
	}

}





