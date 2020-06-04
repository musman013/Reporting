package com.nfinity.reporting.reportingapp1.domain.irepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionreportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionreportId;

//@JaversSpringDataAuditable
@Repository
public interface IDashboardversionreportRepository extends JpaRepository<DashboardversionreportEntity, DashboardversionreportId>,QuerydslPredicateExecutor<DashboardversionreportEntity> {

	
	@Query("select r from DashboardversionreportEntity r where r.dashboardversion.dashboardId = ?1 and r.dashboardversion.version =?2 and r.dashboardversion.userId = ?3")
	List<DashboardversionreportEntity> findByDashboardIdAndVersionAndUserId(Long dashboardId, String version, Long userId);
	
	@Query("select r from DashboardversionreportEntity r where r.dashboardversion.dashboardId = ?1 and r.dashboardversion.version =?2 and r.dashboardversion.userId = ?3 ORDER BY r.orderId DESC")
	List<DashboardversionreportEntity> findByDashboardIdAndVersionAndUserIdInDesc(Long id, String version, Long userId);

	@Query("select r from DashboardversionreportEntity r where r.report.id = ?1 and r.dashboardversion.userId = ?2 and r.dashboardversion.version =?3")
	List<DashboardversionreportEntity> findByReportIdAndUserIdAndVersion(Long reportId, Long uerId, String version);
	
	@Query("select r from DashboardversionreportEntity r where r.dashboardversion.dashboardId = ?1")
	List<DashboardversionreportEntity> findByDashboardId(Long dashboardId);
	
	@Query("select r from DashboardversionreportEntity r where r.dashboardversion.dashboardId = ?1 and r.report.id = ?2 and r.dashboardversion.userId != ?3 and r.dashboardversion.version =?4")
	List<DashboardversionreportEntity> findByIdIfUserIdNotSame(Long dashboardId, Long reportId, Long userId, String version);
	
}
