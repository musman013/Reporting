package com.nfinity.reporting.reportingapp1.application.reportdashboard;

import com.nfinity.reporting.reportingapp1.application.reportdashboard.dto.*;
import com.nfinity.reporting.reportingapp1.domain.reportdashboard.IReportdashboardManager;
import com.nfinity.reporting.reportingapp1.domain.model.QReportdashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardId;
import com.nfinity.reporting.reportingapp1.domain.dashboard.DashboardManager;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.report.ReportManager;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.commons.search.*;
import com.nfinity.reporting.reportingapp1.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;

import java.util.*;
import org.springframework.cache.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

@Service
@Validated
public class ReportdashboardAppService implements IReportdashboardAppService {

    static final int case1=1;
	static final int case2=2;
	static final int case3=3;
	
	@Autowired
	private IReportdashboardManager _reportdashboardManager;

    @Autowired
	private DashboardManager _dashboardManager;
    @Autowired
	private ReportManager _reportManager;
	@Autowired
	private ReportdashboardMapper mapper;
	
	@Autowired
	private LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
	public CreateReportdashboardOutput create(CreateReportdashboardInput input) {

		ReportdashboardEntity reportdashboard = mapper.createReportdashboardInputToReportdashboardEntity(input);
	  	if(input.getDashboardId()!=null) {
			DashboardEntity foundDashboard = _dashboardManager.findById(input.getDashboardId());
			if(foundDashboard!=null) {
				reportdashboard.setDashboard(foundDashboard);
			}
		}
	  	if(input.getReportId()!=null) {
			ReportEntity foundReport = _reportManager.findById(input.getReportId());
			if(foundReport!=null) {
				reportdashboard.setReport(foundReport);
			}
		}
		ReportdashboardEntity createdReportdashboard = _reportdashboardManager.create(reportdashboard);
		
		return mapper.reportdashboardEntityToCreateReportdashboardOutput(createdReportdashboard);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@CacheEvict(value="Reportdashboard", key = "#p0")
	public UpdateReportdashboardOutput update(ReportdashboardId reportdashboardId , UpdateReportdashboardInput input) {

		ReportdashboardEntity reportdashboard = mapper.updateReportdashboardInputToReportdashboardEntity(input);
	  	if(input.getDashboardId()!=null) {
			DashboardEntity foundDashboard = _dashboardManager.findById(input.getDashboardId());
			if(foundDashboard!=null) {
				reportdashboard.setDashboard(foundDashboard);
			}
		}
	  	if(input.getReportId()!=null) {
			ReportEntity foundReport = _reportManager.findById(input.getReportId());
			if(foundReport!=null) {
				reportdashboard.setReport(foundReport);
			}
		}
		
		ReportdashboardEntity updatedReportdashboard = _reportdashboardManager.update(reportdashboard);
		
		return mapper.reportdashboardEntityToUpdateReportdashboardOutput(updatedReportdashboard);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@CacheEvict(value="Reportdashboard", key = "#p0")
	public void delete(ReportdashboardId reportdashboardId) {

		ReportdashboardEntity existing = _reportdashboardManager.findById(reportdashboardId) ; 
		_reportdashboardManager.delete(existing);
		
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Cacheable(value = "Reportdashboard", key = "#p0")
	public FindReportdashboardByIdOutput findById(ReportdashboardId reportdashboardId) {

		ReportdashboardEntity foundReportdashboard = _reportdashboardManager.findById(reportdashboardId);
		if (foundReportdashboard == null)  
			return null ; 
 	   
 	    FindReportdashboardByIdOutput output=mapper.reportdashboardEntityToFindReportdashboardByIdOutput(foundReportdashboard); 
		return output;
	}
    //Dashboard
	// ReST API Call - GET /reportdashboard/1/dashboard
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Cacheable (value = "Reportdashboard", key="#p0")
	public GetDashboardOutput getDashboard(ReportdashboardId reportdashboardId) {

		ReportdashboardEntity foundReportdashboard = _reportdashboardManager.findById(reportdashboardId);
		if (foundReportdashboard == null) {
			logHelper.getLogger().error("There does not exist a reportdashboard wth a id=%s", reportdashboardId);
			return null;
		}
		DashboardEntity re = _reportdashboardManager.getDashboard(reportdashboardId);
		return mapper.dashboardEntityToGetDashboardOutput(re, foundReportdashboard);
	}
	
    //Report
	// ReST API Call - GET /reportdashboard/1/report
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Cacheable (value = "Reportdashboard", key="#p0")
	public GetReportOutput getReport(ReportdashboardId reportdashboardId) {

		ReportdashboardEntity foundReportdashboard = _reportdashboardManager.findById(reportdashboardId);
		if (foundReportdashboard == null) {
			logHelper.getLogger().error("There does not exist a reportdashboard wth a id=%s", reportdashboardId);
			return null;
		}
		ReportEntity re = _reportdashboardManager.getReport(reportdashboardId);
		return mapper.reportEntityToGetReportOutput(re, foundReportdashboard);
	}
	
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Cacheable(value = "Reportdashboard")
	public List<FindReportdashboardByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception  {

		Page<ReportdashboardEntity> foundReportdashboard = _reportdashboardManager.findAll(search(search), pageable);
		List<ReportdashboardEntity> reportdashboardList = foundReportdashboard.getContent();
		Iterator<ReportdashboardEntity> reportdashboardIterator = reportdashboardList.iterator(); 
		List<FindReportdashboardByIdOutput> output = new ArrayList<>();

		while (reportdashboardIterator.hasNext()) {
			output.add(mapper.reportdashboardEntityToFindReportdashboardByIdOutput(reportdashboardIterator.next()));
		}
		return output;
	}
	
	public BooleanBuilder search(SearchCriteria search) throws Exception {

		QReportdashboardEntity reportdashboard= QReportdashboardEntity.reportdashboardEntity;
		if(search != null) {
			Map<String,SearchFields> map = new HashMap<>();
			for(SearchFields fieldDetails: search.getFields())
			{
				map.put(fieldDetails.getFieldName(),fieldDetails);
			}
			List<String> keysList = new ArrayList<String>(map.keySet());
			checkProperties(keysList);
			return searchKeyValuePair(reportdashboard, map,search.getJoinColumns());
		}
		return null;
	}
	
	public void checkProperties(List<String> list) throws Exception  {
		for (int i = 0; i < list.size(); i++) {
			if(!(
				list.get(i).replace("%20","").trim().equals("dashboard") ||
				list.get(i).replace("%20","").trim().equals("dashboardId") ||
				list.get(i).replace("%20","").trim().equals("report") ||
				list.get(i).replace("%20","").trim().equals("reportId")
			)) 
			{
			 throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!" );
			}
		}
	}
	
	public BooleanBuilder searchKeyValuePair(QReportdashboardEntity reportdashboard, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();
        
		for (Map.Entry<String, SearchFields> details : map.entrySet()) {
		}
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
        if(joinCol != null && joinCol.getKey().equals("dashboardId")) {
		    builder.and(reportdashboard.dashboard.id.eq(Long.parseLong(joinCol.getValue())));
		}
        }
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
        if(joinCol != null && joinCol.getKey().equals("reportId")) {
		    builder.and(reportdashboard.report.id.eq(Long.parseLong(joinCol.getValue())));
		}
        }
		return builder;
	}
	
	public ReportdashboardId parseReportdashboardKey(String keysString) {
		
		String[] keyEntries = keysString.split(",");
		ReportdashboardId reportdashboardId = new ReportdashboardId();
		
		Map<String,String> keyMap = new HashMap<String,String>();
		if(keyEntries.length > 1) {
			for(String keyEntry: keyEntries)
			{
				String[] keyEntryArr = keyEntry.split(":");
				if(keyEntryArr.length > 1) {
					keyMap.put(keyEntryArr[0], keyEntryArr[1]);					
				}
				else {
					return null;
				}
			}
		}
		else {
			return null;
		}
		
		reportdashboardId.setDashboardId(Long.valueOf(keyMap.get("dashboardId")));
		reportdashboardId.setReportId(Long.valueOf(keyMap.get("reportId")));
		return reportdashboardId;
		
	}	
	
    
	
}


