package com.nfinity.reporting.reportingapp1.domain.reportrole;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import com.nfinity.reporting.reportingapp1.domain.model.ReportroleEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportroleId;
import com.nfinity.reporting.reportingapp1.domain.model.RoleEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;

@Component
public interface IReportroleManager {
    // CRUD Operations
    ReportroleEntity create(ReportroleEntity reportrole);

    void delete(ReportroleEntity reportrole);

    ReportroleEntity update(ReportroleEntity reportrole);

    ReportroleEntity findById(ReportroleId reportroleId);
    
    List<ReportroleEntity> findByRoleId(Long id);
    
    public Page<RoleEntity> getAvailableRolesList(Long reportId, String search, Pageable pageable);
    
    public Page<RoleEntity> getReportRolesList(Long reportId, String search, Pageable pageable);
    
	
    Page<ReportroleEntity> findAll(Predicate predicate, Pageable pageable);
   
    //Role
    public RoleEntity getRole(ReportroleId reportroleId);
   
    //Report
    public ReportEntity getReport(ReportroleId reportroleId);
}
