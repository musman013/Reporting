package com.nfinity.reporting.reportingapp1.domain.dashboardversion;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IReportdashboardRepository;
import com.nfinity.reporting.reportingapp1.domain.irepository.IUserRepository;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IDashboardversionRepository;
import com.querydsl.core.types.Predicate;

@Component
public class DashboardversionManager implements IDashboardversionManager {

    @Autowired
    IDashboardversionRepository  _dashboardversionRepository;
    
    @Autowired
	IReportdashboardRepository  _reportdashboardRepository;
    
    @Autowired
	IUserRepository  _userRepository;
    
	public DashboardversionEntity create(DashboardversionEntity dashboardversion) {

		return _dashboardversionRepository.save(dashboardversion);
	}

	public void delete(DashboardversionEntity dashboardversion) {

		_dashboardversionRepository.delete(dashboardversion);	
	}

	public DashboardversionEntity update(DashboardversionEntity dashboardversion) {

		return _dashboardversionRepository.save(dashboardversion);
	}

	public DashboardversionEntity findById(Long dashboardversionId) {
    	Optional<DashboardversionEntity> dbDashboardversion= _dashboardversionRepository.findById(dashboardversionId);
		if(dbDashboardversion.isPresent()) {
			DashboardversionEntity existingDashboardversion = dbDashboardversion.get();
		    return existingDashboardversion;
		} else {
		    return null;
		}

	}
	
	public DashboardversionEntity findByDashboardversionIdAndUserId(Long dashboardversionId, Long userId)
	{
		return _dashboardversionRepository.findByDashboardversionIdAndUserId(dashboardversionId, userId);
	}

	public Page<DashboardversionEntity> findAll(Predicate predicate, Pageable pageable) {

		return _dashboardversionRepository.findAll(predicate,pageable);
	}
  
   //User
	public UserEntity getUser(Long dashboardversionId) {
		
		Optional<DashboardversionEntity> dbDashboardversion= _dashboardversionRepository.findById(dashboardversionId);
		if(dbDashboardversion.isPresent()) {
			DashboardversionEntity existingDashboardversion = dbDashboardversion.get();
		    return existingDashboardversion.getUser();
		} else {
		    return null;
		}
	}
}
