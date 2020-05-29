package com.nfinity.reporting.reportingapp1.domain.irepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nfinity.reporting.reportingapp1.domain.model.RoleEntity;

public interface IDashboardroleRepositoryCustom {

	Page<RoleEntity> getAvailableDashboardrolesList(Long dashboardId, String search, Pageable pageable);

	Page<RoleEntity> getDashboardrolesList(Long dashboardId, String search, Pageable pageable);

}
