package com.nfinity.reporting.reportingapp1.domain.dashboardversionreport;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionreportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionreportId;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;

public interface IDashboardversionreportManager {
    // CRUD Operations
    DashboardversionreportEntity create(DashboardversionreportEntity reportdashboard);

    void delete(DashboardversionreportEntity reportdashboard);

    DashboardversionreportEntity update(DashboardversionreportEntity reportdashboard);

    DashboardversionreportEntity findById(DashboardversionreportId reportdashboardId);
    
    List<DashboardversionreportEntity> findByDashboardIdAndVersion(Long id, String version);
    
    public List<DashboardversionreportEntity> findByDashboardIdInDesc(Long id);
	
    Page<DashboardversionreportEntity> findAll(Predicate predicate, Pageable pageable);
   
    //Dashboard
    public DashboardversionEntity getDashboardversion(DashboardversionreportId reportdashboardId);
   
    //Report
    public ReportEntity getReport(DashboardversionreportId reportdashboardId);
}
