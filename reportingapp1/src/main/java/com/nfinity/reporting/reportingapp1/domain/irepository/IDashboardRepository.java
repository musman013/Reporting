package com.nfinity.reporting.reportingapp1.domain.irepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardEntity; 
import java.util.List;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@RepositoryRestResource(collectionResourceRel = "dashboard", path = "dashboard")
public interface IDashboardRepository extends JpaRepository<DashboardEntity, Long>,QuerydslPredicateExecutor<DashboardEntity> {

}
