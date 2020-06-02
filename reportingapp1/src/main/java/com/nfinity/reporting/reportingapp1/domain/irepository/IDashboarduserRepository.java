package com.nfinity.reporting.reportingapp1.domain.irepository;

import java.util.List;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nfinity.reporting.reportingapp1.domain.model.DashboarduserId;
import com.nfinity.reporting.reportingapp1.domain.model.DashboarduserEntity;

//@JaversSpringDataAuditable
@Repository
public interface IDashboarduserRepository extends JpaRepository<DashboarduserEntity, DashboarduserId>,
      QuerydslPredicateExecutor<DashboarduserEntity> , IDashboarduserRepositoryCustom{

	@Query("select r from DashboarduserEntity r where r.user.id = ?1")
	List<DashboarduserEntity> findByUserId(Long id);
	
	@Query("select r from DashboarduserEntity r where r.dashboard.id = ?1")
	List<DashboarduserEntity> findByDashboardId(Long id);
	
	@Transactional
	@Modifying
    @Query("UPDATE DashboarduserEntity r SET r.isRefreshed = ?1 WHERE r.dashboardId = ?2")
	List<DashboarduserEntity> updateRefreshFlag(Boolean refresh,Long dashboardId);
	
	@Transactional
	@Modifying
    @Query("UPDATE DashboarduserEntity r SET r.ownerSharingStatus = ?1 WHERE r.dashboardId = ?2")
	List<DashboarduserEntity> updateOwnerSharingStatus(Boolean status, Long dashboardId);
	
}
