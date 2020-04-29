package com.nfinity.reporting.reportingapp1.domain.irepository;

import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardEntity;
import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@RepositoryRestResource(collectionResourceRel = "reportdashboard", path = "reportdashboard")
public interface IReportdashboardRepository extends JpaRepository<ReportdashboardEntity, ReportdashboardId>,QuerydslPredicateExecutor<ReportdashboardEntity> {

}
