package com.nfinity.reporting.reportingapp1.domain.irepository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.nfinity.reporting.reportingapp1.domain.model.DashboarduserEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboarduserId;

@JaversSpringDataAuditable
@Repository
public interface IDashboarduserRepository extends JpaRepository<DashboarduserEntity, DashboarduserId>,QuerydslPredicateExecutor<DashboarduserEntity> {

}
