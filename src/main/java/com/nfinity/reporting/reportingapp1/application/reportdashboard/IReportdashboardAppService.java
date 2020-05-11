package com.nfinity.reporting.reportingapp1.application.reportdashboard;

import java.util.List;
import javax.validation.constraints.Positive;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardId;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nfinity.reporting.reportingapp1.commons.search.SearchCriteria;
import com.nfinity.reporting.reportingapp1.application.reportdashboard.dto.*;

@Service
public interface IReportdashboardAppService {

	CreateReportdashboardOutput create(CreateReportdashboardInput reportdashboard);

    void delete(ReportdashboardId reportdashboardId);

    UpdateReportdashboardOutput update(ReportdashboardId reportdashboardId, UpdateReportdashboardInput input);

    FindReportdashboardByIdOutput findById(ReportdashboardId reportdashboardId);

    List<FindReportdashboardByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;

	public ReportdashboardId parseReportdashboardKey(String keysString);
    
    //Dashboard
    GetDashboardOutput getDashboard(ReportdashboardId reportdashboardId);
    
    //Report
    GetReportOutput getReport(ReportdashboardId reportdashboardId);
}
