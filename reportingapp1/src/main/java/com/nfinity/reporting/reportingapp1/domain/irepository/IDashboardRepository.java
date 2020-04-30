package com.nfinity.reporting.reportingapp1.domain.irepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;

import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@RepositoryRestResource(collectionResourceRel = "dashboard", path = "dashboard")
public interface IDashboardRepository extends JpaRepository<DashboardEntity, Long>,QuerydslPredicateExecutor<DashboardEntity> {

	@Query("select r from DashboardEntity r where r.id = ?1 and r.user.id = ?2")
	DashboardEntity findByDashboardIdAndUserId(Long dashboardId, Long userId);
}
