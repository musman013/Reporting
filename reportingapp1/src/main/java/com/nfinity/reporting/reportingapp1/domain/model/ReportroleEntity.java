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
@Table(name = "reportrole", schema = "reporting")
@IdClass(ReportroleId.class)
@SuppressWarnings("serial")
public class ReportroleEntity implements Serializable {
	
	private Long reportId;
	private Long roleId;
	private Boolean editable;
	
	@Id
  	@Column(name = "reportId", nullable = false)
  	public Long getReportId() {
  		return reportId;
  	}
  	
  	public void setReportId(Long reportId) {
  		this.reportId = reportId;
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
  	@JoinColumn(name = "reportId", insertable=false, updatable=false)
  	public ReportEntity getReport() {
    	return report;
  	}
  	public void setReport(ReportEntity report) {
    	this.report = report;
  	}
  
  	private ReportEntity report;
  	
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
  	if(this.role != null) {
  	this.role.removeReportrole(this);
  	this.role = null;
  	}

  	if(this.report != null) {
  	this.report.removeReportRole(this);
  	this.report = null;
  	}

  	}
	
}
