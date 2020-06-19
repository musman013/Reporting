package com.nfinity.reporting.reportingapp1.application.reportversion;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.nfinity.reporting.reportingapp1.application.reportversion.dto.CreateReportversionInput;
import com.nfinity.reporting.reportingapp1.application.reportversion.dto.CreateReportversionOutput;
import com.nfinity.reporting.reportingapp1.application.reportversion.dto.FindReportversionByIdOutput;
import com.nfinity.reporting.reportingapp1.application.reportversion.dto.GetUserOutput;
import com.nfinity.reporting.reportingapp1.application.reportversion.dto.UpdateReportversionInput;
import com.nfinity.reporting.reportingapp1.application.reportversion.dto.UpdateReportversionOutput;
import com.nfinity.reporting.reportingapp1.domain.model.ReportversionEntity;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;


@Mapper(componentModel = "spring")
public interface IReportversionMapper {
	ReportversionEntity createReportversionInputToReportversionEntity(CreateReportversionInput reportversionDto);

	@Mappings({ 
		@Mapping(source = "user.id", target = "userId"),
		@Mapping(source = "report.id", target = "reportId"),
		@Mapping(source = "user.userName", target = "userDescriptiveField")
	}) 
	CreateReportversionOutput reportversionEntityToCreateReportversionOutput(ReportversionEntity entity);

	@Mappings({ 
		@Mapping(source = "userId", target = "userId"),
		@Mapping(source = "version", target = "reportVersion")
	}) 
	ReportversionEntity reportversionEntityToReportversionEntity(ReportversionEntity entity,Long userId, String version);

	ReportversionEntity updateReportversionInputToReportversionEntity(UpdateReportversionInput reportversionDto);

	@Mappings({  
		@Mapping(source = "user.id", target = "userId"),  
		@Mapping(source = "report.id", target = "reportId"),
		@Mapping(source = "user.userName", target = "userDescriptiveField"),                    
	}) 
	UpdateReportversionOutput reportversionEntityToUpdateReportversionOutput(ReportversionEntity entity);

	@Mappings({ 
		@Mapping(source = "user.id", target = "userId"),          
		@Mapping(source = "report.id", target = "reportId"),
		@Mapping(source = "user.userName", target = "userDescriptiveField"),                    
	}) 
	FindReportversionByIdOutput reportversionEntityToFindReportversionByIdOutput(ReportversionEntity entity);


	@Mappings({
		@Mapping(source = "user.id", target = "id"),                  
		@Mapping(source = "reportversion.reportVersion", target = "reportVersion"),
	})
	GetUserOutput userEntityToGetUserOutput(UserEntity user, ReportversionEntity reportversion);

}