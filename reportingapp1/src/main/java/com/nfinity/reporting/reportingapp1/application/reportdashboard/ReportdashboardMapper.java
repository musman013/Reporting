package com.nfinity.reporting.reportingapp1.application.reportdashboard;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.application.reportdashboard.dto.*;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardEntity;

@Mapper(componentModel = "spring")
public interface ReportdashboardMapper {

   ReportdashboardEntity createReportdashboardInputToReportdashboardEntity(CreateReportdashboardInput reportdashboardDto);
   
   @Mappings({ 
   @Mapping(source = "dashboardId", target = "dashboardId"),
   @Mapping(source = "reportId", target = "reportId"),
 //  @Mapping(source = "dashboard.title", target = "dashboardDescriptiveField"),                    
 //  @Mapping(source = "report.title", target = "reportDescriptiveField"),                    
   }) 
   CreateReportdashboardOutput reportdashboardEntityToCreateReportdashboardOutput(ReportdashboardEntity entity);

   ReportdashboardEntity updateReportdashboardInputToReportdashboardEntity(UpdateReportdashboardInput reportdashboardDto);

   @Mappings({ 
   @Mapping(source = "dashboardId", target = "dashboardId"),
   @Mapping(source = "reportId", target = "reportId"),
//   @Mapping(source = "dashboard.title", target = "dashboardDescriptiveField"),                    
//   @Mapping(source = "report.title", target = "reportDescriptiveField"),                    
   }) 
   UpdateReportdashboardOutput reportdashboardEntityToUpdateReportdashboardOutput(ReportdashboardEntity entity);

   @Mappings({ 
	   @Mapping(source = "dashboardId", target = "dashboardId"),
	   @Mapping(source = "reportId", target = "reportId"),
//   @Mapping(source = "dashboard.title", target = "dashboardDescriptiveField"),                    
//   @Mapping(source = "report.title", target = "reportDescriptiveField"),                    
   }) 
   FindReportdashboardByIdOutput reportdashboardEntityToFindReportdashboardByIdOutput(ReportdashboardEntity entity);


   @Mappings({
   @Mapping(source = "reportdashboard.dashboardId", target = "reportdashboardDashboardId"),
   @Mapping(source = "reportdashboard.reportId", target = "reportdashboardReportId"),
   })
   GetDashboardOutput dashboardEntityToGetDashboardOutput(DashboardEntity dashboard, ReportdashboardEntity reportdashboard);

   @Mappings({
   @Mapping(source = "reportdashboard.dashboardId", target = "reportdashboardDashboardId"),
   @Mapping(source = "reportdashboard.reportId", target = "reportdashboardReportId"),
   })
   GetReportOutput reportEntityToGetReportOutput(ReportEntity report, ReportdashboardEntity reportdashboard);

}
