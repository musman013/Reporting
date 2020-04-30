package com.nfinity.reporting.reportingapp1.domain.irepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardEntity; 
import java.util.List;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@RepositoryRestResource(collectionResourceRel = "report", path = "report")
public interface IReportRepository extends JpaRepository<ReportEntity, Long>,QuerydslPredicateExecutor<ReportEntity> {

	@Query("select r from ReportEntity r where r.id = ?1 and r.user.id = ?2")
	ReportEntity findByReportIdAndUserId(Long reportId, Long userId);
	
	@Query("select r from ReportEntity r where r.user.id = ?1")
	List<ReportEntity> findByUserId(Long userId);
	
}
