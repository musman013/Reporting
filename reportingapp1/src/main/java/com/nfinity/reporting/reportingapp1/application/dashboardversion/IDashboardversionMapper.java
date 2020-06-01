package com.nfinity.reporting.reportingapp1.application.dashboardversion;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.application.dashboardversion.dto.*;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionreportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportversionEntity;

@Mapper(componentModel = "spring")
public interface IDashboardversionMapper {
	
   DashboardversionEntity createDashboardversionInputToDashboardversionEntity(CreateDashboardversionInput dashboardversionDto);

   @Mappings({ 
   @Mapping(source = "user.id", target = "userId"),                   
   @Mapping(source = "user.userName", target = "userDescriptiveField"),                    
   }) 
   CreateDashboardversionOutput dashboardversionEntityToCreateDashboardversionOutput(DashboardversionEntity entity);

   DashboardversionEntity updateDashboardversionInputToDashboardversionEntity(UpdateDashboardversionInput dashboardversionDto);

   @Mappings({ 
   @Mapping(source = "user.id", target = "userId"),                   
   @Mapping(source = "user.userName", target = "userDescriptiveField"),                    
   }) 
   UpdateDashboardversionOutput dashboardversionEntityToUpdateDashboardversionOutput(DashboardversionEntity entity);

   @Mappings({ 
   @Mapping(source = "user.id", target = "userId"),                   
   @Mapping(source = "user.userName", target = "userDescriptiveField"),                    
   }) 
   FindDashboardversionByIdOutput dashboardversionEntityToFindDashboardversionByIdOutput(DashboardversionEntity entity);

   @Mappings({ 
		@Mapping(source = "userId", target = "userId"),
		@Mapping(source = "version", target = "version")
	}) 
	DashboardversionEntity dashboardversionEntityToDashboardversionEntity(DashboardversionEntity entity,Long userId, String version);

   @Mappings({ 
		@Mapping(source = "userId", target = "userId"),
		@Mapping(source = "version", target = "dashboardVersion")
	}) 
    DashboardversionreportEntity dashboardversionreportEntityToDashboardversionreportEntity(DashboardversionreportEntity dashboardreport,Long userId,String version);
   
   @Mappings({
		@Mapping(source = "user.id", target = "id"),                  
		@Mapping(source = "dashboardversion.version", target = "dashboardVersion"),
	})
   GetUserOutput userEntityToGetUserOutput(UserEntity user, DashboardversionEntity dashboardversion);

}
