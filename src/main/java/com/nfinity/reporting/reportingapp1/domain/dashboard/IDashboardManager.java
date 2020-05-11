package com.nfinity.reporting.reportingapp1.domain.dashboard;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.validation.constraints.Positive;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;

public interface IDashboardManager {
    // CRUD Operations
    DashboardEntity create(DashboardEntity dashboard);

    void delete(DashboardEntity dashboard);

    DashboardEntity update(DashboardEntity dashboard);

    DashboardEntity findById(Long id);
    DashboardEntity findByDashboardIdAndUserId(Long id, Long userId);
	
    Page<DashboardEntity> findAll(Predicate predicate, Pageable pageable);
   
    //User
    public UserEntity getUser(Long dashboardId);
}
