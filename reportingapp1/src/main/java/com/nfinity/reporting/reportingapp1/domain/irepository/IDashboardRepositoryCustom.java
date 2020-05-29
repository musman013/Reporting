package com.nfinity.reporting.reportingapp1.domain.irepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nfinity.reporting.reportingapp1.application.dashboard.dto.DashboardDetailsOutput;

public interface IDashboardRepositoryCustom {
	
	Page<DashboardDetailsOutput> getAllDashboardsByUserId(Long userId, String search, Pageable pageable) throws Exception;

	Page<DashboardDetailsOutput> getSharedDashboardsByUserId(Long userId, String search, Pageable pageable) throws Exception;
	
}
