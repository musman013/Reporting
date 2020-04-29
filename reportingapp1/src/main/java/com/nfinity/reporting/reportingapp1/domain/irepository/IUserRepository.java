package com.nfinity.reporting.reportingapp1.domain.irepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity; 
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity; 
import java.util.List;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface IUserRepository extends JpaRepository<UserEntity, Long>,QuerydslPredicateExecutor<UserEntity> {

    @Query("select u from UserEntity u where u.userName = ?1")
    UserEntity findByUserName(String value);  

    UserEntity findByEmailAddress(String emailAddress);
    
    UserEntity findByPasswordResetCode(String passwordResetCode);
}
