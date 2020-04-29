package com.nfinity.reporting.reportingapp1.domain.irepository;

import com.nfinity.reporting.reportingapp1.domain.model.RolepermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.nfinity.reporting.reportingapp1.domain.model.RolepermissionEntity;
import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@RepositoryRestResource(collectionResourceRel = "rolepermission", path = "rolepermission")
public interface IRolepermissionRepository extends JpaRepository<RolepermissionEntity, RolepermissionId>,QuerydslPredicateExecutor<RolepermissionEntity> {

	   
}
