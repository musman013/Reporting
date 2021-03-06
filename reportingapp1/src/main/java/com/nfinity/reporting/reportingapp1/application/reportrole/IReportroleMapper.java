package com.nfinity.reporting.reportingapp1.application.reportrole;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.nfinity.reporting.reportingapp1.domain.model.RoleEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.application.reportrole.dto.*;
import com.nfinity.reporting.reportingapp1.domain.model.ReportroleEntity;

@Mapper(componentModel = "spring")
public interface IReportroleMapper {

   ReportroleEntity createReportroleInputToReportroleEntity(CreateReportroleInput reportroleDto);
   
   @Mappings({ 
	   @Mapping(source = "roleId", target = "roleId"),
	   @Mapping(source = "reportId", target = "reportId"),
       @Mapping(source = "role.name", target = "roleDescriptiveField"),                    
//   @Mapping(source = "report.title", target = "reportDescriptiveField"),                    
   }) 
   CreateReportroleOutput reportroleEntityToCreateReportroleOutput(ReportroleEntity entity);

   ReportroleEntity updateReportroleInputToReportroleEntity(UpdateReportroleInput reportroleDto);

   @Mappings({ 
	   @Mapping(source = "roleId", target = "roleId"),
	   @Mapping(source = "reportId", target = "reportId"),
   @Mapping(source = "role.name", target = "roleDescriptiveField"),                    
  // @Mapping(source = "report.title", target = "reportDescriptiveField"),                    
   }) 
   UpdateReportroleOutput reportroleEntityToUpdateReportroleOutput(ReportroleEntity entity);

   @Mappings({ 
	   @Mapping(source = "roleId", target = "roleId"),
	   @Mapping(source = "reportId", target = "reportId"),
   @Mapping(source = "role.name", target = "roleDescriptiveField"),                    
 //  @Mapping(source = "report.title", target = "reportDescriptiveField"),                    
   }) 
   FindReportroleByIdOutput reportroleEntityToFindReportroleByIdOutput(ReportroleEntity entity);

   @Mappings({
   @Mapping(source = "reportrole.roleId", target = "reportroleRoleId"),
   @Mapping(source = "reportrole.reportId", target = "reportroleReportId"),
   })
   GetReportOutput reportEntityToGetReportOutput(ReportEntity report, ReportroleEntity reportrole);

}
