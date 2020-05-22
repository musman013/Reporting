package com.nfinity.reporting.reportingapp1.domain.reportversion;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.nfinity.reporting.reportingapp1.domain.model.ReportversionEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportversionId;
import com.nfinity.reporting.reportingapp1.domain.irepository.IReportdashboardRepository;
import com.nfinity.reporting.reportingapp1.domain.irepository.IUserRepository;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IReportversionRepository;
import com.querydsl.core.types.Predicate;

@Repository
public class ReportversionManager implements IReportversionManager {

    @Autowired
    IReportversionRepository  _reportversionRepository;
    
    @Autowired
	IReportdashboardRepository  _reportdashboardRepository;
    
    @Autowired
	IUserRepository  _userRepository;
    
	public ReportversionEntity create(ReportversionEntity report) {

		return _reportversionRepository.save(report);
	}

	public void delete(ReportversionEntity report) {

		_reportversionRepository.delete(report);	
	}

	public ReportversionEntity update(ReportversionEntity report) {

		return _reportversionRepository.save(report);
	}

	public ReportversionEntity findById(ReportversionId reportversionId) {
    	Optional<ReportversionEntity> dbReportversion= _reportversionRepository.findById(reportversionId);
		if(dbReportversion.isPresent()) {
			ReportversionEntity existingReportversion = dbReportversion.get();
		    return existingReportversion;
		} else {
		    return null;
		}

	}
	
//	public ReportversionEntity findByReportIdAndVersionAndUserId(ReportversionId reportversionId)
//	{
//		return _reportversionRepository.findByReportIdAndVersionAndUserId(reportversionId.getUserId(),reportversionId.getReportId(), reportversionId.getVersion());
//	}
	
	public List<ReportversionEntity> findByUserId(Long userId)
	{
		return _reportversionRepository.findByUserId(userId);
	}

	public Page<ReportversionEntity> findAll(Predicate predicate, Pageable pageable) {

		return _reportversionRepository.findAll(predicate,pageable);
	}
  
   //User
	public UserEntity getUser(ReportversionId reportId) {
		
		Optional<ReportversionEntity> dbReportversion= _reportversionRepository.findById(reportId);
		if(dbReportversion.isPresent()) {
			ReportversionEntity existingReportversion = dbReportversion.get();
		    return existingReportversion.getUser();
		} else {
		    return null;
		}
	}
}
