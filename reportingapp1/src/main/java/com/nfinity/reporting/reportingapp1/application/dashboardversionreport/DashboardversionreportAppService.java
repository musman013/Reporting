package com.nfinity.reporting.reportingapp1.application.dashboardversionreport;

import com.nfinity.reporting.reportingapp1.application.dashboard.dto.CreateDashboardOutput;
import com.nfinity.reporting.reportingapp1.application.dashboardversionreport.dto.*;
import com.nfinity.reporting.reportingapp1.application.report.dto.CreateReportOutput;
import com.nfinity.reporting.reportingapp1.domain.model.QDashboardversionreportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionreportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionreportId;
import com.nfinity.reporting.reportingapp1.domain.dashboardversion.DashboardversionManager;
import com.nfinity.reporting.reportingapp1.domain.dashboardversionreport.IDashboardversionreportManager;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionId;
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

@Service
@Validated
public class DashboardversionreportAppService implements IDashboardversionreportAppService {

	static final int case1=1;
	static final int case2=2;
	static final int case3=3;

	@Autowired
	private IDashboardversionreportManager _reportdashboardManager;

	@Autowired
	private DashboardversionManager _dashboardversionManager;

	@Autowired
	private ReportManager _reportManager;
	@Autowired
	private DashboardversionreportMapper mapper;

	@Autowired
	private LoggingHelper logHelper;

	@Transactional(propagation = Propagation.REQUIRED)
	public CreateDashboardversionreportOutput create(CreateDashboardversionreportInput input) {

		DashboardversionreportEntity reportdashboard = mapper.createReportdashboardInputToDashboardversionreportEntity(input);
		if(input.getDashboardId()!=null) {
			DashboardversionEntity foundDashboard = _dashboardversionManager.findById(new DashboardversionId(input.getUserId(),input.getDashboardId(), input.getVersion()));
			if(foundDashboard!=null) {
				reportdashboard.setDashboardversion(foundDashboard);
			}
		}
		if(input.getReportId()!=null) {
			ReportEntity foundReport = _reportManager.findById(input.getReportId());
			if(foundReport!=null) {
				reportdashboard.setReport(foundReport);
			}
		}
		DashboardversionreportEntity createdReportdashboard = _reportdashboardManager.create(reportdashboard);

		return mapper.reportdashboardEntityToCreateReportdashboardOutput(createdReportdashboard);
	}

	public Boolean addReportsToDashboard(DashboardEntity dashboard, List<ReportEntity> reportsList)
	{
		Boolean status = true;
		DashboardversionreportEntity reportdashboard = new DashboardversionreportEntity();
		reportdashboard.setDashboardId(dashboard.getId());
		// 	reportdashboard.setDashboard(dashboard);

		for(ReportEntity report : reportsList)
		{
			reportdashboard.setReport(report);
			reportdashboard.setReportId(report.getId());

			//	DashboardversionreportEntity createdReportdashboard = _reportdashboardManager.create(reportdashboard);

			//    		if(createdReportdashboard == null)
			//    		{
			//    		status = false;	
			//    		}
		}

		return status;
	}

	public Boolean addReportsToDashboardRunningversion(CreateDashboardOutput dashboard, List<CreateReportOutput> reportsList)
	{
		Boolean status = true;
		DashboardversionreportEntity reportdashboard = new DashboardversionreportEntity();
		DashboardversionEntity dashboardrunningVersion = _dashboardversionManager.findById(new DashboardversionId(dashboard.getOwnerId(),dashboard.getId(), "running"));
		if(dashboardrunningVersion !=null) {
			reportdashboard.setDashboardId(dashboardrunningVersion.getDashboardId());
			reportdashboard.setDashboardVersion(dashboardrunningVersion.getVersion());
			reportdashboard.setUserId(dashboardrunningVersion.getUserId());

			reportdashboard.setDashboardversion(dashboardrunningVersion);
		}

		List<DashboardversionreportEntity> list = _reportdashboardManager.findByDashboardIdInDesc(reportdashboard.getDashboardId());

		Long count = 1L;
		if(list != null && !list.isEmpty())
		{
			count = list.get(0).getOrderId();
			count ++;
		}

		for(CreateReportOutput report : reportsList)
		{
			ReportEntity foundReport = _reportManager.findById(report.getId());
			if(foundReport !=null) {
				reportdashboard.setReport(foundReport);
				reportdashboard.setReportId(foundReport.getId());
			}

			reportdashboard.setReportWidth(report.getReportWidth());
			reportdashboard.setOrderId(count);
			DashboardversionreportEntity createdReportdashboard = _reportdashboardManager.create(reportdashboard);

			if(createdReportdashboard == null)
			{
				status = false;	
			}
			count++;
		}

		return status;
	}

	public Boolean addReportsToDashboardPublishedversion(CreateDashboardOutput dashboard, List<CreateReportOutput> reportsList)
	{
		Boolean status = true;
		DashboardversionreportEntity reportdashboard = new DashboardversionreportEntity();
		DashboardversionEntity dashboardPublishedVersion = _dashboardversionManager.findById(new DashboardversionId(dashboard.getOwnerId(),dashboard.getId(), "published"));
		if(dashboardPublishedVersion !=null) {
			reportdashboard.setDashboardId(dashboardPublishedVersion.getDashboardId());
			reportdashboard.setDashboardVersion(dashboardPublishedVersion.getVersion());
			reportdashboard.setUserId(dashboardPublishedVersion.getUserId());

			reportdashboard.setDashboardversion(dashboardPublishedVersion);
		}

		List<DashboardversionreportEntity> list = _reportdashboardManager.findByDashboardIdInDesc(reportdashboard.getDashboardId());

		Long count = 1L;
		if(list != null && !list.isEmpty())
		{
			count = list.get(0).getOrderId();
			count ++;
		}

		for(CreateReportOutput report : reportsList)
		{
			ReportEntity foundReport = _reportManager.findById(report.getId());
			if(foundReport !=null) {
				reportdashboard.setReport(foundReport);
				reportdashboard.setReportId(foundReport.getId());
			}

			reportdashboard.setReportWidth(report.getReportWidth());
			reportdashboard.setOrderId(count);
			DashboardversionreportEntity createdReportdashboard = _reportdashboardManager.create(reportdashboard);

			if(createdReportdashboard == null)
			{
				status = false;	
			}
			count++;
		}

		return status;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@CacheEvict(value="Reportdashboard", key = "#p0")
	public UpdateDashboardversionreportOutput update(DashboardversionreportId reportdashboardId , UpdateDashboardversionreportInput input) {

		DashboardversionreportEntity reportdashboard = mapper.updateReportdashboardInputToDashboardversionreportEntity(input);
		if(input.getDashboardId()!=null) {
			DashboardversionEntity foundDashboard = _dashboardversionManager.findById(new DashboardversionId(input.getUserId(),input.getDashboardId(), input.getVersion()));
			if(foundDashboard!=null) {
				reportdashboard.setDashboardversion(foundDashboard);
			}
		}
		if(input.getReportId()!=null) {
			ReportEntity foundReport = _reportManager.findById(input.getReportId());
			if(foundReport!=null) {
				reportdashboard.setReport(foundReport);
			}
		}

		DashboardversionreportEntity updatedReportdashboard = _reportdashboardManager.update(reportdashboard);

		return mapper.reportdashboardEntityToUpdateReportdashboardOutput(updatedReportdashboard);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@CacheEvict(value="Reportdashboard", key = "#p0")
	public void delete(DashboardversionreportId reportdashboardId) {

		DashboardversionreportEntity existing = _reportdashboardManager.findById(reportdashboardId) ; 
		_reportdashboardManager.delete(existing);

	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Cacheable(value = "Reportdashboard", key = "#p0")
	public FindDashboardversionreportByIdOutput findById(DashboardversionreportId reportdashboardId) {

		DashboardversionreportEntity foundReportdashboard = _reportdashboardManager.findById(reportdashboardId);
		if (foundReportdashboard == null)  
			return null ; 

		FindDashboardversionreportByIdOutput output=mapper.reportdashboardEntityToFindReportdashboardByIdOutput(foundReportdashboard); 
		return output;
	}
	//Dashboard
	// ReST API Call - GET /reportdashboard/1/dashboard
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Cacheable (value = "Reportdashboard", key="#p0")
	public GetDashboardversionOutput getDashboard(DashboardversionreportId reportdashboardId) {

		DashboardversionreportEntity foundReportdashboard = _reportdashboardManager.findById(reportdashboardId);
		if (foundReportdashboard == null) {
			logHelper.getLogger().error("There does not exist a reportdashboard wth a id=%s", reportdashboardId);
			return null;
		}
		DashboardversionEntity re = _reportdashboardManager.getDashboardversion(reportdashboardId);
		return mapper.dashboardversionEntityToGetDashboardversionOutput(re, foundReportdashboard);
	}

	//Report
	// ReST API Call - GET /reportdashboard/1/report
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Cacheable (value = "Reportdashboard", key="#p0")
	public GetReportOutput getReport(DashboardversionreportId reportdashboardId) {

		DashboardversionreportEntity foundReportdashboard = _reportdashboardManager.findById(reportdashboardId);
		if (foundReportdashboard == null) {
			logHelper.getLogger().error("There does not exist a reportdashboard wth a id=%s", reportdashboardId);
			return null;
		}
		ReportEntity re = _reportdashboardManager.getReport(reportdashboardId);
		return mapper.reportEntityToGetReportOutput(re, foundReportdashboard);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Cacheable(value = "Reportdashboard")
	public List<FindDashboardversionreportByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception  {

		Page<DashboardversionreportEntity> foundReportdashboard = _reportdashboardManager.findAll(search(search), pageable);
		List<DashboardversionreportEntity> reportdashboardList = foundReportdashboard.getContent();
		Iterator<DashboardversionreportEntity> reportdashboardIterator = reportdashboardList.iterator(); 
		List<FindDashboardversionreportByIdOutput> output = new ArrayList<>();

		while (reportdashboardIterator.hasNext()) {
			output.add(mapper.reportdashboardEntityToFindReportdashboardByIdOutput(reportdashboardIterator.next()));
		}
		return output;
	}

	public BooleanBuilder search(SearchCriteria search) throws Exception {

		QDashboardversionreportEntity reportdashboard= QDashboardversionreportEntity.dashboardversionreportEntity;
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
					list.get(i).replace("%20","").trim().equals("dashboardversion") ||
					list.get(i).replace("%20","").trim().equals("dashboardId") ||
					list.get(i).replace("%20","").trim().equals("userId") ||
					list.get(i).replace("%20","").trim().equals("version") ||
					list.get(i).replace("%20","").trim().equals("report") ||
					list.get(i).replace("%20","").trim().equals("reportId")
					)) 
			{
				throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!" );
			}
		}
	}

	public BooleanBuilder searchKeyValuePair(QDashboardversionreportEntity reportdashboard, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();

		for (Map.Entry<String, SearchFields> details : map.entrySet()) {
		}
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
			if(joinCol != null && joinCol.getKey().equals("dashboardId")) {
				builder.and(reportdashboard.dashboardversion.dashboardId.eq(Long.parseLong(joinCol.getValue())));
			}

			if(joinCol != null && joinCol.getKey().equals("userId")) {
				builder.and(reportdashboard.dashboardversion.userId.eq(Long.parseLong(joinCol.getValue())));
			}

			if(joinCol != null && joinCol.getKey().equals("version")) {
				builder.and(reportdashboard.dashboardversion.version.eq((joinCol.getValue())));
			}

			if(joinCol != null && joinCol.getKey().equals("reportId")) {
				builder.and(reportdashboard.report.id.eq(Long.parseLong(joinCol.getValue())));
			}
		}
		return builder;
	}

	public DashboardversionreportId parseReportdashboardKey(String keysString) {

		String[] keyEntries = keysString.split(",");
		DashboardversionreportId reportdashboardId = new DashboardversionreportId();

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
		reportdashboardId.setDashboardId(Long.valueOf(keyMap.get("userId")));
		reportdashboardId.setDashboardId(Long.valueOf(keyMap.get("version")));
		reportdashboardId.setReportId(Long.valueOf(keyMap.get("reportId")));
		return reportdashboardId;

	}	



}


