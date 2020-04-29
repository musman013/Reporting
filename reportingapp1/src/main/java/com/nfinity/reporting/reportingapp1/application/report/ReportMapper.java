package com.nfinity.reporting.reportingapp1.application.report;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.application.report.dto.*;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;

@Mapper(componentModel = "spring")
public interface ReportMapper {

   ReportEntity createReportInputToReportEntity(CreateReportInput reportDto);
   
   @Mappings({ 
   @Mapping(source = "user.id", target = "userId"),                   
   @Mapping(source = "user.userName", target = "userDescriptiveField"),                    
   }) 
   CreateReportOutput reportEntityToCreateReportOutput(ReportEntity entity);

   ReportEntity updateReportInputToReportEntity(UpdateReportInput reportDto);

   @Mappings({ 
   @Mapping(source = "user.id", target = "userId"),                   
   @Mapping(source = "user.userName", target = "userDescriptiveField"),                    
   }) 
   UpdateReportOutput reportEntityToUpdateReportOutput(ReportEntity entity);

   @Mappings({ 
   @Mapping(source = "user.id", target = "userId"),                   
   @Mapping(source = "user.userName", target = "userDescriptiveField"),                    
   }) 
   FindReportByIdOutput reportEntityToFindReportByIdOutput(ReportEntity entity);


   @Mappings({
   @Mapping(source = "user.id", target = "id"),                  
   @Mapping(source = "report.id", target = "reportId"),
   })
   GetUserOutput userEntityToGetUserOutput(UserEntity user, ReportEntity report);

}
