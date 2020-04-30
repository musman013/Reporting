package com.nfinity.reporting.reportingapp1.domain.reportdashboard;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import javax.validation.constraints.Positive;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardId;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;

public interface IReportdashboardManager {
    // CRUD Operations
    ReportdashboardEntity create(ReportdashboardEntity reportdashboard);

    void delete(ReportdashboardEntity reportdashboard);

    ReportdashboardEntity update(ReportdashboardEntity reportdashboard);

    ReportdashboardEntity findById(ReportdashboardId reportdashboardId);
    
    List<ReportdashboardEntity> findByDashboardId(Long id);
	
    Page<ReportdashboardEntity> findAll(Predicate predicate, Pageable pageable);
   
    //Dashboard
    public DashboardEntity getDashboard(ReportdashboardId reportdashboardId);
   
    //Report
    public ReportEntity getReport(ReportdashboardId reportdashboardId);
}
