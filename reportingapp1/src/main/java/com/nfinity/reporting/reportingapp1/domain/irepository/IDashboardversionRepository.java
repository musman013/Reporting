package com.nfinity.reporting.reportingapp1.domain.irepository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionEntity;

@JaversSpringDataAuditable
@Repository
public interface IDashboardversionRepository extends JpaRepository<DashboardversionEntity, Long>,QuerydslPredicateExecutor<DashboardversionEntity> {

	@Query("select r from DashboardversionEntity r where r.id = ?1 and r.user.id = ?2")
	DashboardversionEntity findByDashboardversionIdAndUserId(Long dashboardversionId, Long userId);
}
