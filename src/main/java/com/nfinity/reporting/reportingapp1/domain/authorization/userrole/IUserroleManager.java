package com.nfinity.reporting.reportingapp1.domain.authorization.userrole;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.validation.constraints.Positive;
import com.nfinity.reporting.reportingapp1.domain.model.UserroleEntity;
import com.nfinity.reporting.reportingapp1.domain.model.UserroleId;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.domain.model.RoleEntity;

public interface IUserroleManager {

    // CRUD Operations
    UserroleEntity create(UserroleEntity userrole);

    void delete(UserroleEntity userrole);

    UserroleEntity update(UserroleEntity userrole);

    UserroleEntity findById(UserroleId userroleId);
	
    Page<UserroleEntity> findAll(Predicate predicate, Pageable pageable);
   
    //User
    public UserEntity getUser(UserroleId userroleId);
   
    //Role
    public RoleEntity getRole(UserroleId userroleId);
}
