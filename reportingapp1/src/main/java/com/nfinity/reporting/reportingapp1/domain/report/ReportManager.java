package com.nfinity.reporting.reportingapp1.domain.report;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IReportdashboardRepository;
import com.nfinity.reporting.reportingapp1.domain.irepository.IUserRepository;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IReportRepository;
import com.querydsl.core.types.Predicate;

@Repository
public class ReportManager implements IReportManager {

    @Autowired
    IReportRepository  _reportRepository;
    
    @Autowired
	IReportdashboardRepository  _reportdashboardRepository;
    
    @Autowired
	IUserRepository  _userRepository;
    
	public ReportEntity create(ReportEntity report) {

		return _reportRepository.save(report);
	}

	public void delete(ReportEntity report) {

		_reportRepository.delete(report);	
	}

	public ReportEntity update(ReportEntity report) {

		return _reportRepository.save(report);
	}

	public ReportEntity findById(Long reportId) {
    	Optional<ReportEntity> dbReport= _reportRepository.findById(reportId);
		if(dbReport.isPresent()) {
			ReportEntity existingReport = dbReport.get();
		    return existingReport;
		} else {
		    return null;
		}

	}

	public Page<ReportEntity> findAll(Predicate predicate, Pageable pageable) {

		return _reportRepository.findAll(predicate,pageable);
	}
  
   //User
	public UserEntity getUser(Long reportId) {
		
		Optional<ReportEntity> dbReport= _reportRepository.findById(reportId);
		if(dbReport.isPresent()) {
			ReportEntity existingReport = dbReport.get();
		    return existingReport.getUser();
		} else {
		    return null;
		}
	}
}
