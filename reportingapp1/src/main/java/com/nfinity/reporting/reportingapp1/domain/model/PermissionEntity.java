package com.nfinity.reporting.reportingapp1.domain.model;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "Permission", schema = "reporting")
public class PermissionEntity extends AbstractEntity {
    
	private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "DisplayName", nullable = false, length = 128)
    private String displayName;

    @Basic
    @Column(name = "Name", nullable = false, length = 128,unique = true)
    private String name;
    
    public PermissionEntity(String name, String displayName) {
    	this.name = name;
    	this.displayName = displayName;
    }

    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL) 
    private Set<RolepermissionEntity> rolepermissionSet = new HashSet<RolepermissionEntity>(); 
  
    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL) 
    private Set<UserpermissionEntity> userpermissionSet = new HashSet<UserpermissionEntity>(); 
    
    public void addRolepermission(RolepermissionEntity rolepermission) {
		rolepermissionSet.add(rolepermission);
		rolepermission.setPermission(this);
	}

	public void removeRolepermission(RolepermissionEntity rolepermission) {
		rolepermissionSet.remove(rolepermission);
		rolepermission.setPermission(null);
	}
	
	public void addUserpermission(UserpermissionEntity userpermission) {
		userpermissionSet.add(userpermission);
		userpermission.setPermission(this);
	}

	public void removeUserpermission(UserpermissionEntity userpermission) {
		userpermissionSet.remove(userpermission);
		userpermission.setPermission(null);
	}


}
