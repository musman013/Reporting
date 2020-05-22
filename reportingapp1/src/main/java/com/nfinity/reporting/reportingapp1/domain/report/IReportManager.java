package com.nfinity.reporting.reportingapp1.domain.report;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import javax.validation.constraints.Positive;

import com.nfinity.reporting.reportingapp1.application.report.dto.IReportDetailsOutput;
import com.nfinity.reporting.reportingapp1.application.report.dto.ReportDetailsOutput;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;

public interface IReportManager {
    // CRUD Operations
    ReportEntity create(ReportEntity report);

    void delete(ReportEntity report);

    ReportEntity update(ReportEntity report);

    ReportEntity findById(Long id);
	
    Page<ReportEntity> findAll(Predicate predicate, Pageable pageable);
    
    public Page<ReportDetailsOutput> getReports(Long userId,String search, Pageable pageable) throws Exception;
   
    public Page<ReportDetailsOutput> getSharedReports(Long userId,String search, Pageable pageable) throws Exception;
    
    ReportEntity findByReportIdAndUserId(Long id, Long userId);
    
    List<ReportEntity> findByUserId(Long userId);
    //User
    public UserEntity getUser(Long reportId);
}
