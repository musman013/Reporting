package com.nfinity.reporting.reportingapp1.domain.authorization.user;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IDashboardRepository;
import com.nfinity.reporting.reportingapp1.domain.irepository.IReportRepository;
import com.nfinity.reporting.reportingapp1.domain.irepository.IUserRepository;
import com.querydsl.core.types.Predicate;

@Repository
public class UserManager implements IUserManager {

    @Autowired
    IUserRepository  _userRepository;
    
    @Autowired
	IDashboardRepository  _dashboardRepository;
    
    @Autowired
	IReportRepository  _reportRepository;
    
	public UserEntity create(UserEntity user) {

		return _userRepository.save(user);
	}

	public void delete(UserEntity user) {

		_userRepository.delete(user);	
	}

	public UserEntity update(UserEntity user) {

		return _userRepository.save(user);
	}

	public UserEntity findById(Long userId) {
    	Optional<UserEntity> dbUser= _userRepository.findById(userId);
		if(dbUser.isPresent()) {
			UserEntity existingUser = dbUser.get();
		    return existingUser;
		} else {
		    return null;
		}

	}
	public UserEntity findByUserName(String userName) {
		return  _userRepository.findByUserName(userName);
	}

	public UserEntity findByEmailAddress(String emailAddress) {
		return  _userRepository.findByEmailAddress(emailAddress);
	}
	public UserEntity findByPasswordResetCode(String passwordResetCode) {
		return  _userRepository.findByPasswordResetCode(passwordResetCode);
	}

	public Page<UserEntity> findAll(Predicate predicate, Pageable pageable) {

		return _userRepository.findAll(predicate,pageable);
	}
}
