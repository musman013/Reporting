package com.nfinity.reporting.reportingapp1.domain.irepository;

import com.nfinity.reporting.reportingapp1.domain.model.UserpermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.nfinity.reporting.reportingapp1.domain.model.UserpermissionEntity;
import org.javers.spring.annotation.JaversSpringDataAuditable;

//@JaversSpringDataAuditable
@Repository
public interface IUserpermissionRepository extends JpaRepository<UserpermissionEntity, UserpermissionId>,QuerydslPredicateExecutor<UserpermissionEntity> {
   
}
