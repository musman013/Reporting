package com.nfinity.reporting.reportingapp1.domain.irepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionreportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionreportId;
import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@Repository
public interface IDashboardversionreportRepository extends JpaRepository<DashboardversionreportEntity, DashboardversionreportId>,QuerydslPredicateExecutor<DashboardversionreportEntity> {

	
	@Query("select r from DashboardversionreportEntity r where r.dashboardversion.dashboardId = ?1 and r.dashboardversion.version =?2")
	List<DashboardversionreportEntity> findByDashboardIdAndVersion(Long id, String version);
	
	@Query("select r from DashboardversionreportEntity r where r.dashboardversion.dashboardId = ?1 ORDER BY r.orderId DESC")
	List<DashboardversionreportEntity> findByDashboardIdInDesc(Long id);
}
