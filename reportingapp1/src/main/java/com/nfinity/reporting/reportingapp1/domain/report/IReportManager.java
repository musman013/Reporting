package com.nfinity.reporting.reportingapp1.domain.report;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.validation.constraints.Positive;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;

public interface IReportManager {
    // CRUD Operations
    ReportEntity create(ReportEntity report);

    void delete(ReportEntity report);

    ReportEntity update(ReportEntity report);

    ReportEntity findById(Long id);
	
    Page<ReportEntity> findAll(Predicate predicate, Pageable pageable);
   
    //User
    public UserEntity getUser(Long reportId);
}
