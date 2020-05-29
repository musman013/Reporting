package com.nfinity.reporting.reportingapp1.domain.irepository;

import java.util.List;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.nfinity.reporting.reportingapp1.domain.model.DashboardroleEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardroleId;
import com.nfinity.reporting.reportingapp1.domain.model.ReportroleEntity;

@JaversSpringDataAuditable
@Repository
public interface IDashboardroleRepository extends JpaRepository<DashboardroleEntity, DashboardroleId>,
        QuerydslPredicateExecutor<DashboardroleEntity>,IDashboardroleRepositoryCustom {

	@Query("select r from DashboardroleEntity r where r.role.id = ?1")
	List<DashboardroleEntity> findByRoleId(Long id);
}
