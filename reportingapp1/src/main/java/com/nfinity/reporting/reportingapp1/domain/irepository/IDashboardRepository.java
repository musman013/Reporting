package com.nfinity.reporting.reportingapp1.domain.irepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;

import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@Repository
public interface IDashboardRepository extends JpaRepository<DashboardEntity, Long>,QuerydslPredicateExecutor<DashboardEntity> {

	@Query("select r from DashboardEntity r where r.id = ?1 and r.user.id = ?2")
	DashboardEntity findByDashboardIdAndUserId(Long dashboardId, Long userId);
}
