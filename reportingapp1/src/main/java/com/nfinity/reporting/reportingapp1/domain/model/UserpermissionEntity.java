package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;

@Entity
@Table(name = "Userpermission", schema = "reporting")
@IdClass(UserpermissionId.class)
public class UserpermissionEntity implements Serializable {

	private Boolean revoked;
    private Long permissionId;
  	private Long userId;
  	public UserpermissionEntity() {
  	}
  
  	@Basic
  	@Column(name = "revoked", nullable = true)
  	public Boolean getRevoked() {
    	return revoked;
  	}

  	public void setRevoked(Boolean revoked) {
    	this.revoked = revoked;
  	}

  	@ManyToOne
  	@JoinColumn(name = "permissionId", insertable=false, updatable=false)
  	public PermissionEntity getPermission() {
    	return permission;
  	}
  	public void setPermission(PermissionEntity permission) {
    	this.permission = permission;
  	}
  
  	private PermissionEntity permission;
 
  	@Id
  	@Column(name = "permissionId", nullable = false)
  	public Long getPermissionId() {
  		return permissionId;
  	}

  	public void setPermissionId(Long permissionId){
  		this.permissionId = permissionId;
  	}

  	@Id
  	@Column(name = "userId", nullable = false)
  	public Long getUserId() {
  		return userId;
  	}

  	public void setUserId(Long userId){
  		this.userId = userId;
  	}
  
  	@ManyToOne
  	@JoinColumn(name = "userId", insertable=false, updatable=false)
  	public UserEntity getUser() {
    	return user;
  	}
  	public void setUser(UserEntity user) {
    	this.user = user;
  	}
  
  	private UserEntity user;
 
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
