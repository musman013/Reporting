package com.nfinity.reporting.reportingapp1.application.dashboard;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.application.dashboard.dto.*;
import com.nfinity.reporting.reportingapp1.application.report.dto.CreateReportInput;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;

@Mapper(componentModel = "spring")
public interface DashboardMapper {

   DashboardEntity createDashboardInputToDashboardEntity(CreateDashboardInput dashboardDto);
   
   DashboardEntity createDashboardAndReportInputToDashboardEntity(AddNewReportToNewDashboardInput dashboardDto);
   
   DashboardEntity addExistingReportToNewDashboardInputToDashboardEntity(AddExistingReportToNewDashboardInput input);
   
   ReportEntity createDashboardAndReportInputToReportEntity(CreateReportInput reportDto);
   
   @Mappings({ 
   @Mapping(source = "user.id", target = "userId"),                   
   @Mapping(source = "user.userName", target = "userDescriptiveField"),                    
   }) 
   CreateDashboardOutput dashboardEntityToCreateDashboardOutput(DashboardEntity entity);

   DashboardEntity updateDashboardInputToDashboardEntity(UpdateDashboardInput dashboardDto);

   @Mappings({ 
   @Mapping(source = "user.id", target = "userId"),                   
   @Mapping(source = "user.userName", target = "userDescriptiveField"),                    
   }) 
   UpdateDashboardOutput dashboardEntityToUpdateDashboardOutput(DashboardEntity entity);

   @Mappings({ 
   @Mapping(source = "user.id", target = "userId"),                   
   @Mapping(source = "user.userName", target = "userDescriptiveField"),                    
   }) 
   FindDashboardByIdOutput dashboardEntityToFindDashboardByIdOutput(DashboardEntity entity);


   @Mappings({
   @Mapping(source = "user.id", target = "id"),                  
   @Mapping(source = "dashboard.id", target = "dashboardId"),
   })
   GetUserOutput userEntityToGetUserOutput(UserEntity user, DashboardEntity dashboard);

}
