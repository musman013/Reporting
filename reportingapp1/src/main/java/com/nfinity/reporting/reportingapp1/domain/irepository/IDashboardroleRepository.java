package com.nfinity.reporting.reportingapp1.domain.irepository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.nfinity.reporting.reportingapp1.domain.model.DashboardroleEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardroleId;

@JaversSpringDataAuditable
@Repository
public interface IDashboardroleRepository extends JpaRepository<DashboardroleEntity, DashboardroleId>,QuerydslPredicateExecutor<DashboardroleEntity> {

}
