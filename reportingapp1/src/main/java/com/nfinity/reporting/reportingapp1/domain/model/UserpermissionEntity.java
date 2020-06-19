package com.nfinity.reporting.reportingapp1.domain.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "Userpermission", schema = "reporting")
@IdClass(UserpermissionId.class)
public class UserpermissionEntity extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
  	@Basic
  	@Column(name = "revoked", nullable = true)
  	private Boolean revoked;

  	@Id
  	@Column(name = "permissionId", nullable = false)
  	private Long permissionId;

  	@Id
  	@Column(name = "userId", nullable = false)
  	private Long userId;
  
  	@ManyToOne
  	@JoinColumn(name = "userId", insertable=false, updatable=false)
  	private UserEntity user;
  	
  	@ManyToOne
  	@JoinColumn(name = "permissionId", insertable=false, updatable=false)
  	private PermissionEntity permission;
 
 
	@PreRemove
  	private void dismissParent() {
  	//SYNCHRONIZING THE OTHER SIDE OF RELATIONSHIP
  	if(this.user != null) {
  	this.user.removeUserpermission(this);
  	this.user = null;
  	}

  	if(this.permission != null) {
  	this.permission.removeUserpermission(this);
  	this.permission = null;
  	}

  	}

}
