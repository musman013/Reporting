package com.nfinity.reporting.reportingapp1.domain.reportdashboard;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardId;
import com.nfinity.reporting.reportingapp1.domain.irepository.IDashboardRepository;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IReportRepository;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IReportdashboardRepository;
import com.querydsl.core.types.Predicate;

@Repository
public class ReportdashboardManager implements IReportdashboardManager {

    @Autowired
    IReportdashboardRepository  _reportdashboardRepository;
    
    @Autowired
	IDashboardRepository  _dashboardRepository;
    
    @Autowired
	IReportRepository  _reportRepository;
    
	public ReportdashboardEntity create(ReportdashboardEntity reportdashboard) {

		return _reportdashboardRepository.save(reportdashboard);
	}

	public void delete(ReportdashboardEntity reportdashboard) {

		_reportdashboardRepository.delete(reportdashboard);	
	}

	public ReportdashboardEntity update(ReportdashboardEntity reportdashboard) {

		return _reportdashboardRepository.save(reportdashboard);
	}

	public ReportdashboardEntity findById(ReportdashboardId reportdashboardId) {
    	Optional<ReportdashboardEntity> dbReportdashboard= _reportdashboardRepository.findById(reportdashboardId);
		if(dbReportdashboard.isPresent()) {
			ReportdashboardEntity existingReportdashboard = dbReportdashboard.get();
		    return existingReportdashboard;
		} else {
		    return null;
		}

	}

	public Page<ReportdashboardEntity> findAll(Predicate predicate, Pageable pageable) {

		return _reportdashboardRepository.findAll(predicate,pageable);
	}
  
   //Dashboard
	public DashboardEntity getDashboard(ReportdashboardId reportdashboardId) {
		
		Optional<ReportdashboardEntity> dbReportdashboard= _reportdashboardRepository.findById(reportdashboardId);
		if(dbReportdashboard.isPresent()) {
			ReportdashboardEntity existingReportdashboard = dbReportdashboard.get();
		    return existingReportdashboard.getDashboard();
		} else {
		    return null;
		}
	}
  
   //Report
	public ReportEntity getReport(ReportdashboardId reportdashboardId) {
		
		Optional<ReportdashboardEntity> dbReportdashboard= _reportdashboardRepository.findById(reportdashboardId);
		if(dbReportdashboard.isPresent()) {
			ReportdashboardEntity existingReportdashboard = dbReportdashboard.get();
		    return existingReportdashboard.getReport();
		} else {
		    return null;
		}
	}
}
