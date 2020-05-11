package com.nfinity.reporting.reportingapp1.domain.authorization.user;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.validation.constraints.Positive;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;

public interface IUserManager {
    // CRUD Operations
    UserEntity create(UserEntity user);

    void delete(UserEntity user);

    UserEntity update(UserEntity user);

    UserEntity findById(Long id);
	
	UserEntity findByUserName(String userName);

	UserEntity findByEmailAddress(String emailAddress);
    
    UserEntity findByPasswordResetCode(String passwordResetCode);
	
    Page<UserEntity> findAll(Predicate predicate, Pageable pageable);
}
