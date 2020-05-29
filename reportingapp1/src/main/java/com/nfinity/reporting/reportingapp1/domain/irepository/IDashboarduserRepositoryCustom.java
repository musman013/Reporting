package com.nfinity.reporting.reportingapp1.domain.irepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;

public interface IDashboarduserRepositoryCustom {

	Page<UserEntity> getAvailableDashboardusersList(Long dashboardId, String search, Pageable pageable);

	Page<UserEntity> getDashboardusersList(Long dashboardId, String search, Pageable pageable);

}
