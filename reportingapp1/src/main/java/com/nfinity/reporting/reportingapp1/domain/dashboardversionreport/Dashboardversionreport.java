package com.nfinity.reporting.reportingapp1.domain.dashboardversionreport;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionreportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionreportId;
import com.nfinity.reporting.reportingapp1.domain.irepository.IDashboardRepository;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IReportRepository;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IDashboardversionreportRepository;
import com.querydsl.core.types.Predicate;

@Repository
public class Dashboardversionreport implements IDashboardversionreportManager {

    @Autowired
    IDashboardversionreportRepository  _reportdashboardRepository;
    
    @Autowired
	IDashboardRepository  _dashboardRepository;
    
    @Autowired
	IReportRepository  _reportRepository;
    
	public DashboardversionreportEntity create(DashboardversionreportEntity reportdashboard) {

		return _reportdashboardRepository.save(reportdashboard);
	}

	public void delete(DashboardversionreportEntity reportdashboard) {

		_reportdashboardRepository.delete(reportdashboard);	
	}

	public DashboardversionreportEntity update(DashboardversionreportEntity reportdashboard) {

		return _reportdashboardRepository.save(reportdashboard);
	}

	public DashboardversionreportEntity findById(DashboardversionreportId reportdashboardId) {
    	Optional<DashboardversionreportEntity> dbDashboardversionreport= _reportdashboardRepository.findById(reportdashboardId);
		if(dbDashboardversionreport.isPresent()) {
			DashboardversionreportEntity existingDashboardversionreport = dbDashboardversionreport.get();
		    return existingDashboardversionreport;
		} else {
		    return null;
		}

	}
	
	public List<DashboardversionreportEntity> findByDashboardId(Long id)
	{
		return _reportdashboardRepository.findByDashboardId(id);
	}

	public Page<DashboardversionreportEntity> findAll(Predicate predicate, Pageable pageable) {

		return _reportdashboardRepository.findAll(predicate,pageable);
	}
  
   //Dashboard
	public DashboardversionEntity getDashboardversion(DashboardversionreportId reportdashboardId) {
		
		Optional<DashboardversionreportEntity> dbDashboardversionreport= _reportdashboardRepository.findById(reportdashboardId);
		if(dbDashboardversionreport.isPresent()) {
			DashboardversionreportEntity existingDashboardversionreport = dbDashboardversionreport.get();
		    return existingDashboardversionreport.getDashboardversion();
		} else {
		    return null;
		}
	}
  
   //Report
	public ReportEntity getReport(DashboardversionreportId reportdashboardId) {
		
		Optional<DashboardversionreportEntity> dbDashboardversionreport= _reportdashboardRepository.findById(reportdashboardId);
		if(dbDashboardversionreport.isPresent()) {
			DashboardversionreportEntity existingDashboardversionreport = dbDashboardversionreport.get();
		    return existingDashboardversionreport.getReport();
		} else {
		    return null;
		}
	}
}
