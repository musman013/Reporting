package com.nfinity.reporting.reportingapp1.application.dashboard;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.application.dashboard.dto.*;
import com.nfinity.reporting.reportingapp1.application.dashboardversion.dto.CreateDashboardversionInput;
import com.nfinity.reporting.reportingapp1.application.dashboardversion.dto.CreateDashboardversionOutput;
import com.nfinity.reporting.reportingapp1.application.dashboardversion.dto.UpdateDashboardversionInput;
import com.nfinity.reporting.reportingapp1.application.dashboardversion.dto.UpdateDashboardversionOutput;
import com.nfinity.reporting.reportingapp1.application.report.dto.CreateReportInput;
import com.nfinity.reporting.reportingapp1.application.report.dto.FindReportByIdOutput;
import com.nfinity.reporting.reportingapp1.application.report.dto.ReportDetailsOutput;
import com.nfinity.reporting.reportingapp1.application.report.dto.UpdateReportInput;
import com.nfinity.reporting.reportingapp1.application.report.dto.UpdateReportOutput;
import com.nfinity.reporting.reportingapp1.application.reportversion.dto.UpdateReportversionInput;
import com.nfinity.reporting.reportingapp1.application.reportversion.dto.UpdateReportversionOutput;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboarduserEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportuserEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportversionEntity;
import com.nfinity.reporting.reportingapp1.domain.model.RoleEntity;

@Mapper(componentModel = "spring")
public interface DashboardMapper {

	@Mappings({ 
		@Mapping(source = "ownerId", target = "userId")                         
	}) 
	CreateDashboardversionInput creatDashboardInputToCreateDashboardversionInput(CreateDashboardInput dashboardDto);

	DashboardEntity createDashboardInputToDashboardEntity(CreateDashboardInput dashboardDto);

	CreateDashboardInput addNewReportToNewDashboardInputTocreatDashboardInput(AddNewReportToNewDashboardInput input);
	
	CreateDashboardInput addExistingReportToNewDashboardInputTocreatDashboardInput(AddExistingReportToNewDashboardInput input);
	
//	DashboardEntity createDashboardAndReportInputToDashboardEntity(AddNewReportToNewDashboardInput dashboardDto);

	DashboardEntity addExistingReportToNewDashboardInputToDashboardEntity(AddExistingReportToNewDashboardInput input);

	ReportEntity createDashboardAndReportInputToReportEntity(CreateReportInput reportDto);

	@Mappings({ 
		@Mapping(source = "entity.id", target = "id"), 
		@Mapping(source = "entity.user.id", target = "ownerId"),                   
		@Mapping(source = "entity.user.userName", target = "ownerDescriptiveField")                     
	}) 
	CreateDashboardOutput dashboardEntityAndCreateDashboardversionOutputToCreateDashboardOutput(DashboardEntity entity, CreateDashboardversionOutput dashboardversion);

	@Mappings({ 
		@Mapping(source = "dashboardversion.dashboardId", target = "id"), 
		@Mapping(source = "dashboardversion.userId", target = "ownerId"),                   
		@Mapping(source = "dashboardversion.user.userName", target = "ownerDescriptiveField")                     
	}) 
	CreateDashboardOutput dashboardEntityAndDashboardversionEntityToCreateDashboardOutput(DashboardEntity dashboard, DashboardversionEntity dashboardversion);

	
	UpdateDashboardversionInput updateDashboardInputToUpdateDashboardversionInput(UpdateDashboardInput dashboardDto);
	
	@Mappings({ 
		@Mapping(source = "entity.id", target = "id"), 
		@Mapping(source = "entity.user.id", target = "ownerId"),                   
		@Mapping(source = "entity.user.userName", target = "ownerDescriptiveField") 	
	}) 
	UpdateDashboardOutput dashboardEntityAndUpdateDashboardversionOutputToUpdateDashboardOutput(DashboardEntity entity, UpdateDashboardversionOutput dashboardversion);
	
	DashboardEntity updateDashboardInputToDashboardEntity(UpdateDashboardInput dashboardDto);

	@Mappings({ 
		@Mapping(source = "user.id", target = "ownerId")                    
	}) 
	UpdateDashboardOutput dashboardEntityToUpdateDashboardOutput(DashboardEntity entity);

	@Mappings({ 
		@Mapping(source = "user.id", target = "ownerId"),  
	//	@Mapping(source = "entity.id", target = "dashboardId"),
		@Mapping(source = "user.userName", target = "ownerDescriptiveField")                 
	}) 
	FindDashboardByIdOutput dashboardEntityToFindDashboardByIdOutput(DashboardEntity entity);
	
	@Mappings({ 
		@Mapping(source = "ownerId", target = "userId") 
		//@Mapping(source = "entity.id", target = "dashboardId")                
	}) 
	FindDashboardByIdOutput dashboardOutputToFindDashboardByIdOutput(CreateDashboardOutput entity);

	@Mappings({
		@Mapping(source = "dashboardversion.userId", target = "userId"), 
		@Mapping(source = "dashboard.user.id", target = "ownerId"),
		@Mapping(source = "dashboardversion.dashboardId", target = "id"),
	})
	FindDashboardByIdOutput dashboardEntitiesToFindDashboardByIdOutput(DashboardEntity dashboard, DashboardversionEntity dashboardversion, DashboarduserEntity dashboarduser);

	@Mappings({
		@Mapping(source = "dashboardversion.userId", target = "userId"),  
		@Mapping(source = "dashboard.user.id", target = "ownerId"),
		@Mapping(source = "dashboardversion.dashboardId", target = "id"),
	})
	DashboardDetailsOutput dashboardEntitiesToDashboardDetailsOutput(DashboardEntity dashboard, DashboardversionEntity dashboardversion, DashboarduserEntity dashboarduser);

	@Mappings({
		@Mapping(source = "user.id", target = "id"),                  
		@Mapping(source = "dashboard.id", target = "dashboardId"),
	})
	GetUserOutput userEntityToGetUserOutput(UserEntity user, DashboardEntity dashboard);
	
	@Mappings({
		@Mapping(source = "role.id", target = "id"),                  
		@Mapping(source = "dashboard.id", target = "dashboardId"),
	})
	GetRoleOutput roleEntityToGetRoleOutput(RoleEntity role, DashboardEntity dashboard);

}
