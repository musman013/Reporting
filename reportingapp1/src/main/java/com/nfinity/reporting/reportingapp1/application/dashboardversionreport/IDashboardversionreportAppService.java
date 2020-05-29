package com.nfinity.reporting.reportingapp1.application.dashboardversionreport;

import java.util.List;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionreportId;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nfinity.reporting.reportingapp1.application.dashboardversionreport.dto.*;
import com.nfinity.reporting.reportingapp1.commons.search.SearchCriteria;

@Service
public interface IDashboardversionreportAppService {

	CreateDashboardversionreportOutput create(CreateDashboardversionreportInput reportdashboard);

    void delete(DashboardversionreportId reportdashboardId);

    UpdateDashboardversionreportOutput update(DashboardversionreportId reportdashboardId, UpdateDashboardversionreportInput input);

    FindDashboardversionreportByIdOutput findById(DashboardversionreportId reportdashboardId);

    List<FindDashboardversionreportByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;

	public DashboardversionreportId parseReportdashboardKey(String keysString);
    
    //Dashboard
    GetDashboardversionOutput getDashboard(DashboardversionreportId reportdashboardId);
    
    //Report
    GetReportOutput getReport(DashboardversionreportId reportdashboardId);
}
