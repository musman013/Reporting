package com.nfinity.reporting.reportingapp1.domain.reportuser;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import com.nfinity.reporting.reportingapp1.domain.model.ReportuserEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportuserId;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;


@Component
public interface IReportuserManager {
    // CRUD Operations
    public ReportuserEntity create(ReportuserEntity reportuser);

    void delete(ReportuserEntity reportuser);

    public ReportuserEntity update(ReportuserEntity reportuser);

    public ReportuserEntity findById(ReportuserId reportuserId);
    
    public List<ReportuserEntity> findByUserId(Long userId);
    
    public List<ReportuserEntity> findByReportId(Long reportId);
    
    public Page<UserEntity> getAvailableUsersList(Long reportId, String search, Pageable pageable);
    
    public Page<UserEntity> getReportUsersList(Long reportId, String search, Pageable pageable);
    
    public List<ReportuserEntity> updateRefreshFlag(Long reportId, Boolean refresh);
    
    public List<ReportuserEntity> updateOwnerSharingStatus(Long reportId, Boolean status);
	
    public Page<ReportuserEntity> findAll(Predicate predicate, Pageable pageable);
   
    //User
    public UserEntity getUser(ReportuserId reportuserId);
   
    //Report
    public ReportEntity getReport(ReportuserId reportuserId);
}
