package com.nfinity.reporting.reportingapp1.application.reportuser;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.nfinity.reporting.reportingapp1.application.reportuser.dto.*;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportuserEntity;

@Mapper(componentModel = "spring")
public interface IReportuserMapper {

	ReportuserEntity createReportuserInputToReportuserEntity(CreateReportuserInput reportuserDto);

	@Mappings({ 
		@Mapping(source = "userId", target = "userId"),
		@Mapping(source = "reportId", target = "reportId"),
		@Mapping(source = "user.userName", target = "userDescriptiveField"),                    
	//	@Mapping(source = "report.title", target = "reportDescriptiveField"),                    
	}) 
	CreateReportuserOutput reportuserEntityToCreateReportuserOutput(ReportuserEntity entity);

	ReportuserEntity updateReportuserInputToReportuserEntity(UpdateReportuserInput reportuserDto);

	@Mappings({ 
		@Mapping(source = "userId", target = "userId"),
		@Mapping(source = "reportId", target = "reportId"),
		@Mapping(source = "user.userName", target = "userDescriptiveField"),                    
	//	@Mapping(source = "report.title", target = "reportDescriptiveField"),                    
	}) 
	UpdateReportuserOutput reportuserEntityToUpdateReportuserOutput(ReportuserEntity entity);

	@Mappings({ 
		@Mapping(source = "userId", target = "userId"),
		@Mapping(source = "reportId", target = "reportId"),
		@Mapping(source = "user.userName", target = "userDescriptiveField"),                    
	//	@Mapping(source = "report.title", target = "reportDescriptiveField"),                    
	}) 
	FindReportuserByIdOutput reportuserEntityToFindReportuserByIdOutput(ReportuserEntity entity);

	@Mappings({
		@Mapping(source = "reportuser.userId", target = "reportuserUserId"),
	//	@Mapping(source = "reportuser.reportId", target = "reportuserReportId"),
	})
	GetReportOutput reportEntityToGetReportOutput(ReportEntity report, ReportuserEntity reportuser);

}
