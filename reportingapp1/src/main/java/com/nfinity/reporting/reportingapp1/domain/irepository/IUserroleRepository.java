package com.nfinity.reporting.reportingapp1.domain.irepository;

import com.nfinity.reporting.reportingapp1.domain.model.UserroleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.nfinity.reporting.reportingapp1.domain.model.UserroleEntity;

import java.util.List;

import org.javers.spring.annotation.JaversSpringDataAuditable;

//@JaversSpringDataAuditable
@Repository
public interface IUserroleRepository extends JpaRepository<UserroleEntity, UserroleId>,QuerydslPredicateExecutor<UserroleEntity> {
    
	@Query("select e from UserroleEntity e where e.roleId = ?1")
	List<UserroleEntity> findByRoleId(Long roleId);

}
