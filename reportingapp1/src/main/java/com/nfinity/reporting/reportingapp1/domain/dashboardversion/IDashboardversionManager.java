package com.nfinity.reporting.reportingapp1.domain.dashboardversion;

import com.querydsl.core.types.Predicate;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionId;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;

@Component
public interface IDashboardversionManager {
    // CRUD Operations
    DashboardversionEntity create(DashboardversionEntity dashboard);

    void delete(DashboardversionEntity dashboard);

    DashboardversionEntity update(DashboardversionEntity dashboard);

    DashboardversionEntity findById(DashboardversionId id);
    
 //   DashboardversionEntity findByDashboardversionIdAndUserId(Long id, Long userId);
    
    List<DashboardversionEntity> findByUserId(Long userId);
	
    Page<DashboardversionEntity> findAll(Predicate predicate, Pageable pageable);
   
    //User
    public UserEntity getUser(DashboardversionId dashboardId);
}
