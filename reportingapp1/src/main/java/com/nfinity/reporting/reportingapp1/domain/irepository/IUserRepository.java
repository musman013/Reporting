package com.nfinity.reporting.reportingapp1.domain.irepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import org.javers.spring.annotation.JaversSpringDataAuditable;

//@JaversSpringDataAuditable
@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long>,QuerydslPredicateExecutor<UserEntity> {

    @Query("select u from UserEntity u where u.userName = ?1")
    UserEntity findByUserName(String value);  

    UserEntity findByEmailAddress(String emailAddress);
    
    UserEntity findByPasswordResetCode(String passwordResetCode);
    
//    @Modifying
//    @Query("delete from UserEntity u where u.id= ?1")
//    void deleteUser (Long id);
}
