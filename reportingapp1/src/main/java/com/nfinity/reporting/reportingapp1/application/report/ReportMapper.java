package com.nfinity.reporting.reportingapp1.application.report;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.application.report.dto.*;
import com.nfinity.reporting.reportingapp1.application.reportversion.dto.CreateReportversionInput;
import com.nfinity.reporting.reportingapp1.application.reportversion.dto.CreateReportversionOutput;
import com.nfinity.reporting.reportingapp1.application.reportversion.dto.UpdateReportversionInput;
import com.nfinity.reporting.reportingapp1.application.reportversion.dto.UpdateReportversionOutput;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportuserEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportversionEntity;
import com.nfinity.reporting.reportingapp1.domain.model.RoleEntity;

@Mapper(componentModel = "spring")
public interface ReportMapper {

	ReportEntity createReportInputToReportEntity(CreateReportInput reportDto);
	
	@Mappings({ 
		@Mapping(source = "ownerId", target = "userId")                         
	}) 
	CreateReportversionInput createReportInputToCreateReportversionInput(CreateReportInput reportDto);

	@Mappings({ 
		@Mapping(source = "entity.id", target = "id"), 
		@Mapping(source = "entity.user.id", target = "ownerId"),                   
		@Mapping(source = "entity.user.userName", target = "ownerDescriptiveField")                  
	}) 
	CreateReportOutput reportEntityAndCreateReportversionOutputToCreateReportOutput(ReportEntity entity, CreateReportversionOutput reportversionOutput);

	ReportEntity updateReportInputToReportEntity(UpdateReportInput reportDto);
	

	UpdateReportversionInput updateReportInputToUpdateReportversionInput(UpdateReportInput reportDto);


	@Mappings({ 
		@Mapping(source = "entity.id", target = "id"), 
		@Mapping(source = "entity.user.id", target = "ownerId"),                   
		@Mapping(source = "entity.user.userName", target = "ownerDescriptiveField") 	
	}) 
	UpdateReportOutput reportEntityAndUpdateReportversionOutputToUpdateReportOutput(ReportEntity entity, UpdateReportversionOutput reportversion);

	@Mappings({
		@Mapping(source = "reportversion.userId", target = "userId"), 
		@Mapping(source = "report.user.id", target = "ownerId"),
		@Mapping(source = "reportversion.reportId", target = "reportId"),
	})
	FindReportByIdOutput reportEntitiesToFindReportByIdOutput(ReportEntity report, ReportversionEntity reportversion, ReportuserEntity reportuser);

	@Mappings({ 
		@Mapping(source = "user.id", target = "ownerId")	
	}) 
	FindReportByIdOutput reportEntityToFindReportByIdOutput(ReportEntity entity);

	@Mappings({
		@Mapping(source = "user.id", target = "id"),  
		@Mapping(source = "report.id", target = "reportId"),
	})
	GetUserOutput userEntityToGetUserOutput(UserEntity user, ReportEntity report);
	
	@Mappings({
		@Mapping(source = "role.id", target = "id"),  
		@Mapping(source = "report.id", target = "reportId"),
	})
	GetRoleOutput roleEntityToGetRoleOutput(RoleEntity role, ReportEntity report);
	
	@Mappings({
		@Mapping(source = "reportversion.userId", target = "userId"),  
		@Mapping(source = "report.user.id", target = "ownerId"),
		@Mapping(source = "reportversion.reportId", target = "reportId"),
	})
	ReportDetailsOutput reportEntitiesToReportDetialsOutput(ReportEntity report, ReportversionEntity reportversion, ReportuserEntity reportuser);

	
	ReportDetailsOutput mapInterfaceToDto(Object reportDetails);
	
}
