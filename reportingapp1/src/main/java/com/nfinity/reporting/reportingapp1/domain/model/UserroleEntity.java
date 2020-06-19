package com.nfinity.reporting.reportingapp1.domain.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "Userrole", schema = "reporting")
@IdClass(UserroleId.class)
public class UserroleEntity extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
  	@Column(name = "roleId", nullable = false)
	private Long roleId;
	
	@Id
  	@Column(name = "userId", nullable = false)
  	private Long userId;
 
  	@ManyToOne
  	@JoinColumn(name = "roleId", insertable=false, updatable=false)
  	private RoleEntity role;
  	
  	@ManyToOne
  	@JoinColumn(name = "userId", insertable=false, updatable=false)
  	private UserEntity user;
  	
  	@PreRemove
  	private void dismissParent() {
  	//SYNCHRONIZING THE OTHER SIDE OF RELATIONSHIP
  	if(this.user != null) {
  	this.user.removeUserrole(this);
  	this.user = null;
  	}

  	if(this.role != null) {
  	this.role.removeUserrole(this);
  	this.role = null;
  	}

  	}
}

  
      


