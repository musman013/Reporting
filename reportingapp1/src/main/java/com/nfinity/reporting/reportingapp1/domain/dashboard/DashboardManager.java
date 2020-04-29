package com.nfinity.reporting.reportingapp1.domain.dashboard;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IReportdashboardRepository;
import com.nfinity.reporting.reportingapp1.domain.irepository.IUserRepository;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IDashboardRepository;
import com.querydsl.core.types.Predicate;

@Repository
public class DashboardManager implements IDashboardManager {

    @Autowired
    IDashboardRepository  _dashboardRepository;
    
    @Autowired
	IReportdashboardRepository  _reportdashboardRepository;
    
    @Autowired
	IUserRepository  _userRepository;
    
	public DashboardEntity create(DashboardEntity dashboard) {

		return _dashboardRepository.save(dashboard);
	}

	public void delete(DashboardEntity dashboard) {

		_dashboardRepository.delete(dashboard);	
	}

	public DashboardEntity update(DashboardEntity dashboard) {

		return _dashboardRepository.save(dashboard);
	}

	public DashboardEntity findById(Long dashboardId) {
    	Optional<DashboardEntity> dbDashboard= _dashboardRepository.findById(dashboardId);
		if(dbDashboard.isPresent()) {
			DashboardEntity existingDashboard = dbDashboard.get();
		    return existingDashboard;
		} else {
		    return null;
		}

	}

	public Page<DashboardEntity> findAll(Predicate predicate, Pageable pageable) {

		return _dashboardRepository.findAll(predicate,pageable);
	}
  
   //User
	public UserEntity getUser(Long dashboardId) {
		
		Optional<DashboardEntity> dbDashboard= _dashboardRepository.findById(dashboardId);
		if(dbDashboard.isPresent()) {
			DashboardEntity existingDashboard = dbDashboard.get();
		    return existingDashboard.getUser();
		} else {
		    return null;
		}
	}
}
