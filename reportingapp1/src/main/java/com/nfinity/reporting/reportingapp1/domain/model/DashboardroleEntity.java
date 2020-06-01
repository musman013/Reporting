package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

@Entity
@Table(name = "dashboardrole", schema = "reporting")
@IdClass(DashboardroleId.class)
public class DashboardroleEntity implements Serializable {
	
	private Long dashboardId;
	private Long roleId;
	private Boolean editable;
	
	@Id
  	@Column(name = "dashboardId", nullable = false)
  	public Long getDashboardId() {
  		return dashboardId;
  	}
  	
  	public void setDashboardId(Long dashboardId) {
  		this.dashboardId = dashboardId;
  	}
	
  	@Id
  	@Column(name = "roleId", nullable = false)
  	public Long getRoleId() {
  		return roleId;
  	}
  	
  	public void setRoleId(Long roleId) {
  		this.roleId = roleId;
  	}
  	
  	@Basic
	@Column(name = "editable", nullable = false)
	public Boolean getEditable() {
		return editable;
	}
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
	
	@ManyToOne
  	@JoinColumn(name = "dashboardId", insertable=false, updatable=false)
  	public DashboardEntity getDashboard() {
    	return dashboard;
  	}
  	public void setDashboard(DashboardEntity dashboard) {
    	this.dashboard = dashboard;
  	}
  
  	private DashboardEntity dashboard;
  	
  	@ManyToOne
  	@JoinColumn(name = "roleId", insertable=false, updatable=false)
  	public RoleEntity getRole() {
    	return role;
  	}
  	public void setRole(RoleEntity role) {
    	this.role = role;
  	}
  
  	private RoleEntity role;
  	
  	@PreRemove
  	private void dismissParent() {
  	//SYNCHRONIZING THE OTHER SIDE OF RELATIONSHIP
  	if(this.dashboard != null) {
  	this.dashboard.removeDashboardrole(this);
  	this.dashboard = null;
  	}

  	if(this.role != null) {
  	this.role.removeDashboardrole(this);
  	this.role = null;
  	}

  	}

}
