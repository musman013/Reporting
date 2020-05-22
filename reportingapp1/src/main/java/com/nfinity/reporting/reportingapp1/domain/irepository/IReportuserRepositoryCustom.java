package com.nfinity.reporting.reportingapp1.domain.irepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nfinity.reporting.reportingapp1.domain.model.ReportuserEntity;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;


public interface IReportuserRepositoryCustom {

	Page<UserEntity> getAvailableReportusersList(Long reportId, String search, Pageable pageable);
	
	Page<UserEntity> getReportusersList(Long reportId, String search, Pageable pageable);
	
//	public List<ReportuserEntity> findByReportId(Long reportId);
	
}
