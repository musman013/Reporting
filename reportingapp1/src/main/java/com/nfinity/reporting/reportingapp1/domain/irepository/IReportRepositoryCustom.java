package com.nfinity.reporting.reportingapp1.domain.irepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nfinity.reporting.reportingapp1.application.report.dto.ReportDetailsOutput;

public interface IReportRepositoryCustom {
	
	Page<ReportDetailsOutput> getAllReportsByUserId(Long userId, String search, Pageable pageable) throws Exception;

	Page<ReportDetailsOutput> getSharedReportsByUserId(Long userId, String search, Pageable pageable) throws Exception;
		
}
