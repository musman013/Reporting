package com.nfinity.reporting.reportingapp1.domain.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Role", schema = "reporting")
public class RoleEntity implements Serializable {

    private Long id;
    private String displayName;
    private String name;

    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DisplayName", nullable = false, length = 128)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Basic
    @Column(name = "Name", nullable = false, length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleEntity)) return false;
        RoleEntity role = (RoleEntity) o;
        return id != null && id.equals(role.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true) 
    public Set<RolepermissionEntity> getRolepermissionSet() { 
      return rolepermissionSet; 
    } 
 
    public void setRolepermissionSet(Set<RolepermissionEntity> rolepermission) { 
      this.rolepermissionSet = rolepermission; 
    } 
 
    private Set<RolepermissionEntity> rolepermissionSet = new HashSet<RolepermissionEntity>(); 
  
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true) 
    public Set<UserroleEntity> getUserroleSet() { 
      return userroleSet; 
    } 
 
    public void setUserroleSet(Set<UserroleEntity> userrole) { 
      this.userroleSet = userrole; 
    } 
 
    private Set<UserroleEntity> userroleSet = new HashSet<UserroleEntity>(); 
    
  	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true) 
  	public Set<ReportroleEntity> getReportroleSet() { 
    	return reportroleSet; 
  	} 
 
  	public void setReportroleSet(Set<ReportroleEntity> reportrole) { 
    	this.reportroleSet = reportrole; 
  	} 
 
  	private Set<ReportroleEntity> reportroleSet = new HashSet<ReportroleEntity>(); 
  	
  	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true) 
  	public Set<DashboardroleEntity> getDashboardroleSet() { 
    	return dashboardroleSet; 
  	} 
 
  	public void setDashboardroleSet(Set<DashboardroleEntity> dashboardrole) { 
    	this.dashboardroleSet = dashboardrole; 
  	} 
 
  	private Set<DashboardroleEntity> dashboardroleSet = new HashSet<DashboardroleEntity>(); 
  	
    public RoleEntity() {
    }
    
    public void removeDashboardrole(DashboardroleEntity dr) {
        this.dashboardroleSet.remove(dr);
    }
  	
  	public void removeReportrole(ReportroleEntity rr) {
        this.reportroleSet.remove(rr);
    }
  	
  	public void removeUserrole(UserroleEntity rr) {
        this.userroleSet.remove(rr);
    }
  	
  	public void removeRolepermission(RolepermissionEntity rr) {
        this.rolepermissionSet.remove(rr);
    }
    

}
