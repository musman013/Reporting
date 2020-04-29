package com.nfinity.reporting.reportingapp1.domain.authorization.userpermission;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.nfinity.reporting.reportingapp1.domain.model.UserpermissionEntity;
import com.nfinity.reporting.reportingapp1.domain.model.UserpermissionId;
import com.nfinity.reporting.reportingapp1.domain.irepository.IUserRepository;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IPermissionRepository;
import com.nfinity.reporting.reportingapp1.domain.model.PermissionEntity;

import com.nfinity.reporting.reportingapp1.domain.irepository.IUserpermissionRepository;
import com.querydsl.core.types.Predicate;

@Repository
public class UserpermissionManager implements IUserpermissionManager {

    @Autowired
    IUserpermissionRepository  _userpermissionRepository;
    
    @Autowired
	IUserRepository  _userRepository;
    
    @Autowired
	IPermissionRepository  _permissionRepository;
    
	public UserpermissionEntity create(UserpermissionEntity userpermission) {

		return _userpermissionRepository.save(userpermission);
	}

	public void delete(UserpermissionEntity userpermission) {

		_userpermissionRepository.delete(userpermission);	
	}

	public UserpermissionEntity update(UserpermissionEntity userpermission) {

		return _userpermissionRepository.save(userpermission);
	}

	public UserpermissionEntity findById(UserpermissionId userpermissionId ) {
    
    Optional<UserpermissionEntity> dbUserpermission= _userpermissionRepository.findById(userpermissionId);
		if(dbUserpermission.isPresent()) {
			UserpermissionEntity existingUserpermission = dbUserpermission.get();
		    return existingUserpermission;
		} else {
		    return null;
		}
    
	}

	public Page<UserpermissionEntity> findAll(Predicate predicate, Pageable pageable) {

		return _userpermissionRepository.findAll(predicate,pageable);
	}

    //User
	public UserEntity getUser(UserpermissionId userpermissionId) {
		
		Optional<UserpermissionEntity> dbUserpermission= _userpermissionRepository.findById(userpermissionId);
		if(dbUserpermission.isPresent()) {
			UserpermissionEntity existingUserpermission = dbUserpermission.get();
		    return existingUserpermission.getUser();
		} else {
		    return null;
		}

	}
	
   //Permission
	public PermissionEntity getPermission(UserpermissionId userpermissionId) {
		
		Optional<UserpermissionEntity> dbUserpermission= _userpermissionRepository.findById(userpermissionId);
		if(dbUserpermission.isPresent()) {
			UserpermissionEntity existingUserpermission = dbUserpermission.get();
		    return existingUserpermission.getPermission();
		} else {
		    return null;
		}
	}
	
}
