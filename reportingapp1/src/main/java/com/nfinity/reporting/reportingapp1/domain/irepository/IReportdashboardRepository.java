package com.nfinity.reporting.reportingapp1.domain.irepository;

import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardEntity;
import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@Repository
public interface IReportdashboardRepository extends JpaRepository<ReportdashboardEntity, ReportdashboardId>,QuerydslPredicateExecutor<ReportdashboardEntity> {

	
	@Query("select r from ReportdashboardEntity r where r.dashboard.id = ?1")
	List<ReportdashboardEntity> findByDashboardId(Long id);
}
