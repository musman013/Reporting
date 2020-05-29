package com.nfinity.reporting.reportingapp1.domain.dashboarduser;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import com.nfinity.reporting.reportingapp1.domain.model.DashboarduserEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboarduserId;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;


@Component
public interface IDashboarduserManager {
    // CRUD Operations
    public DashboarduserEntity create(DashboarduserEntity dashboarduser);

    void delete(DashboarduserEntity dashboarduser);

    public DashboarduserEntity update(DashboarduserEntity dashboarduser);

    public DashboarduserEntity findById(DashboarduserId dashboarduserId);
    
    public List<DashboarduserEntity> findByUserId(Long userId);
    
    public List<DashboarduserEntity> findByDashboardId(Long dashboardId);
    
    public Page<UserEntity> getAvailableUsersList(Long dashboardId, String search, Pageable pageable);
    
    public Page<UserEntity> getDashboardUsersList(Long dashboardId, String search, Pageable pageable);
    
    public List<DashboarduserEntity> updateRefreshFlag(Long dashboardId, Boolean refresh);
    
    public List<DashboarduserEntity> updateOwnerSharingStatus(Long dashboardId, Boolean status);
	
    public Page<DashboarduserEntity> findAll(Predicate predicate, Pageable pageable);
   
    //User
    public UserEntity getUser(DashboarduserId dashboarduserId);
   
    //Dashboard
    public DashboardEntity getDashboard(DashboarduserId dashboarduserId);
}
