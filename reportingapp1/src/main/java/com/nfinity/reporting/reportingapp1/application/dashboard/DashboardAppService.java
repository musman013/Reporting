package com.nfinity.reporting.reportingapp1.application.dashboard;

import com.nfinity.reporting.reportingapp1.application.dashboard.dto.*;
import com.nfinity.reporting.reportingapp1.application.report.ReportMapper;
import com.nfinity.reporting.reportingapp1.application.report.dto.CreateReportInput;
import com.nfinity.reporting.reportingapp1.application.report.dto.FindReportByIdOutput;
import com.nfinity.reporting.reportingapp1.application.report.dto.UpdateReportInput;
import com.nfinity.reporting.reportingapp1.application.reportdashboard.IReportdashboardAppService;
import com.nfinity.reporting.reportingapp1.application.reportdashboard.ReportdashboardAppService;
import com.nfinity.reporting.reportingapp1.domain.dashboard.IDashboardManager;
import com.nfinity.reporting.reportingapp1.domain.model.QDashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.authorization.user.UserManager;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.domain.report.IReportManager;
import com.nfinity.reporting.reportingapp1.domain.reportdashboard.IReportdashboardManager;
import com.nfinity.reporting.reportingapp1.commons.search.*;
import com.nfinity.reporting.reportingapp1.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;

import java.util.*;
import org.springframework.cache.annotation.*;
import org.omg.stub.java.rmi._Remote_Stub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Validated
public class DashboardAppService implements IDashboardAppService {

    static final int case1=1;
	static final int case2=2;
	static final int case3=3;
	
	@Autowired
	private IDashboardManager _dashboardManager;
	
	@Autowired
	private ReportdashboardAppService _reportDashboardAppService;
	
	@Autowired
	private IReportdashboardManager _reportDashboardManager;

    @Autowired
	private UserManager _userManager;
	@Autowired
	private DashboardMapper mapper;
	
	@Autowired
	private ReportMapper reportMapper;
	
	@Autowired
	private IReportManager _reportManager;
	
	@Autowired
	private LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
	public CreateDashboardOutput create(CreateDashboardInput input) {

		DashboardEntity dashboard = mapper.createDashboardInputToDashboardEntity(input);
	  	if(input.getUserId()!=null) {
			UserEntity foundUser = _userManager.findById(input.getUserId());
			if(foundUser!=null) {
				dashboard.setUser(foundUser);
			}
		}
		DashboardEntity createdDashboard = _dashboardManager.create(dashboard);
		
		return mapper.dashboardEntityToCreateDashboardOutput(createdDashboard);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@CacheEvict(value="Dashboard", key = "#p0")
	public UpdateDashboardOutput update(Long  dashboardId, UpdateDashboardInput input) {

		DashboardEntity dashboard = mapper.updateDashboardInputToDashboardEntity(input);
	  	if(input.getUserId()!=null) {
			UserEntity foundUser = _userManager.findById(input.getUserId());
			if(foundUser!=null) {
				dashboard.setUser(foundUser);
			}
		}
		
		DashboardEntity updatedDashboard = _dashboardManager.update(dashboard);
		
		return mapper.dashboardEntityToUpdateDashboardOutput(updatedDashboard);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@CacheEvict(value="Dashboard", key = "#p0")
	public void delete(Long dashboardId) {

		DashboardEntity existing = _dashboardManager.findById(dashboardId) ; 
		_dashboardManager.delete(existing);
		
	}
	
//	@Transactional(propagation = Propagation.REQUIRED)
//	@CacheEvict(value="Dashboard", key = "#p0")
//	public void delete(Long dashboardId, Long userId) {
//
//		DashboardEntity existing = _dashboardManager.findByDashboardIdAndUserId(dashboardId, userId);
//		_dashboardManager.delete(existing);
//		
//	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Cacheable(value = "Dashboard", key = "#p0")
	public FindDashboardByIdOutput findById(Long dashboardId) {

		DashboardEntity foundDashboard = _dashboardManager.findById(dashboardId);
		if (foundDashboard == null)  
			return null ; 
 	   
 	    FindDashboardByIdOutput output=mapper.dashboardEntityToFindDashboardByIdOutput(foundDashboard); 
		return output;
	}
	
	 public List<FindReportByIdOutput> setReportsList(Long id)
	 {
		 List<ReportdashboardEntity> reportDashboardList = _reportDashboardManager.findByDashboardId(id);
		 List<FindReportByIdOutput> reportDetails = new ArrayList<>();
		 for(ReportdashboardEntity rd : reportDashboardList)
		 {
			 
			 FindReportByIdOutput output= reportMapper.reportEntityToFindReportByIdOutput(rd.getReport()); 
			 reportDetails.add(output);
		 }
		 
		 return reportDetails;
		 
	 }
	
//	@Transactional(propagation = Propagation.NOT_SUPPORTED)
//	@Cacheable(value = "Dashboard", key = "#p0")
//	public FindDashboardByIdOutput findByDashboardIdAndUserId(Long dashboardId, Long userId) {
//
//		DashboardEntity foundDashboard = _dashboardManager.findByDashboardIdAndUserId(dashboardId, userId);
//		if (foundDashboard == null)  
//			return null ; 
// 	   
// 	    FindDashboardByIdOutput output=mapper.dashboardEntityToFindDashboardByIdOutput(foundDashboard); 
//		return output;
//	}
	 
    //User
	// ReST API Call - GET /dashboard/1/user
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Cacheable (value = "Dashboard", key="#p0")
	public GetUserOutput getUser(Long dashboardId) {

		DashboardEntity foundDashboard = _dashboardManager.findById(dashboardId);
		if (foundDashboard == null) {
			logHelper.getLogger().error("There does not exist a dashboard wth a id=%s", dashboardId);
			return null;
		}
		UserEntity re = _dashboardManager.getUser(dashboardId);
		return mapper.userEntityToGetUserOutput(re, foundDashboard);
	}
	
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Cacheable(value = "Dashboard")
	public List<FindDashboardByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception  {

		Page<DashboardEntity> foundDashboard = _dashboardManager.findAll(search(search), pageable);
		List<DashboardEntity> dashboardList = foundDashboard.getContent();
		Iterator<DashboardEntity> dashboardIterator = dashboardList.iterator(); 
		List<FindDashboardByIdOutput> output = new ArrayList<>();

		while (dashboardIterator.hasNext()) {
			output.add(mapper.dashboardEntityToFindDashboardByIdOutput(dashboardIterator.next()));
		}
		return output;
	}
    
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindDashboardByIdOutput addNewReportsToNewDashboard(AddNewReportToNewDashboardInput input)
    {
    	DashboardEntity dashboard = mapper.createDashboardAndReportInputToDashboardEntity(input);
    	UserEntity foundUser = null;
    	if(input.getUserId()!=null) {
			foundUser = _userManager.findById(input.getUserId());
			if(foundUser!=null) {
				dashboard.setUser(foundUser);
			}
		}
    	
    	DashboardEntity createdDashboard = _dashboardManager.create(dashboard);
    	List<FindReportByIdOutput> reportsOutput =new ArrayList<>();
    	List<ReportEntity> reportEntities = new ArrayList<>();
    	for(CreateReportInput report : input.getReportDetails())
    	{
    		ReportEntity reportEntity = mapper.createDashboardAndReportInputToReportEntity(report);
    		if(foundUser!=null) {
    			reportEntity.setUser(foundUser);
			}
    		
    		ReportEntity createdReport = _reportManager.create(reportEntity);
    		reportEntities.add(createdReport);
    		reportsOutput.add(reportMapper.reportEntityToFindReportByIdOutput(createdReport));
    	}
    	
    	 _reportDashboardAppService.addReportsToDashboard(createdDashboard, reportEntities);
    	
    	FindDashboardByIdOutput dashboardOuputDto = mapper.dashboardEntityToFindDashboardByIdOutput(createdDashboard);
    	dashboardOuputDto.setReportDetails(reportsOutput);
    	
    	return dashboardOuputDto;
    	
    }
    
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindDashboardByIdOutput addNewReportsToExistingDashboard(AddNewReportToExistingDashboardInput input)
    {
    	DashboardEntity dashboard = _dashboardManager.findById(input.getId());
    	List<FindReportByIdOutput> reportsOutput =new ArrayList<>();
    	List<ReportEntity> reportEntities = new ArrayList<>();
    	for(CreateReportInput report : input.getReportDetails())
    	{
    		ReportEntity reportEntity = mapper.createDashboardAndReportInputToReportEntity(report);
    		reportEntity.setUser(dashboard.getUser());
    		
    		ReportEntity createdReport = _reportManager.create(reportEntity);
    		reportEntities.add(createdReport);
    		reportsOutput.add(reportMapper.reportEntityToFindReportByIdOutput(createdReport));
    	}
    	
    	_reportDashboardAppService.addReportsToDashboard(dashboard, reportEntities);
    	
    	FindDashboardByIdOutput dashboardOuputDto = mapper.dashboardEntityToFindDashboardByIdOutput(dashboard);
    	dashboardOuputDto.setReportDetails(reportsOutput);
    	
    	return dashboardOuputDto;
    	
    	
    }
    
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindDashboardByIdOutput addExistingReportsToNewDashboard(AddExistingReportToNewDashboardInput input)
    {
    	DashboardEntity dashboard = mapper.addExistingReportToNewDashboardInputToDashboardEntity(input);
    	UserEntity foundUser = null;
    	if(input.getUserId()!=null) {
			foundUser = _userManager.findById(input.getUserId());
			if(foundUser!=null) {
				dashboard.setUser(foundUser);
			}
		}
    	
    	DashboardEntity createdDashboard = _dashboardManager.create(dashboard);
    	List<FindReportByIdOutput> reportsOutput =new ArrayList<>();
    	List<ReportEntity> reportEntities = new ArrayList<>();
    	for(UpdateReportInput report : input.getReportDetails())
    	{
    		ReportEntity reportEntity = _reportManager.findById(report.getId());
    		reportEntities.add(reportEntity);
    		reportsOutput.add(reportMapper.reportEntityToFindReportByIdOutput(reportEntity));
    	}
    	
    	 _reportDashboardAppService.addReportsToDashboard(createdDashboard, reportEntities);
    	
    	FindDashboardByIdOutput dashboardOuputDto = mapper.dashboardEntityToFindDashboardByIdOutput(createdDashboard);
    	dashboardOuputDto.setReportDetails(reportsOutput);
    	
    	return dashboardOuputDto;
    	
    	
    }
    
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindDashboardByIdOutput addExistingReportsToExistingDashboard(AddExistingReportToExistingDashboardInput input)
    {
    	DashboardEntity dashboard = _dashboardManager.findById(input.getId());
    	List<ReportEntity> reportEntities = new ArrayList<>();
    	List<FindReportByIdOutput> reportsOutput =new ArrayList<>();
    	for(UpdateReportInput report : input.getReportDetails())
    	{
    		ReportEntity reportEntity = _reportManager.findById(report.getId());
    		reportEntities.add(reportEntity);
    		reportsOutput.add(reportMapper.reportEntityToFindReportByIdOutput(reportEntity));
    	}
    	
    	 _reportDashboardAppService.addReportsToDashboard(dashboard, reportEntities);
    	
    	FindDashboardByIdOutput dashboardOuputDto = mapper.dashboardEntityToFindDashboardByIdOutput(dashboard);
    	dashboardOuputDto.setReportDetails(reportsOutput);
    	
    	return dashboardOuputDto;
    	
    }
    
	
	public BooleanBuilder search(SearchCriteria search) throws Exception {

		QDashboardEntity dashboard= QDashboardEntity.dashboardEntity;
		if(search != null) {
			Map<String,SearchFields> map = new HashMap<>();
			for(SearchFields fieldDetails: search.getFields())
			{
				map.put(fieldDetails.getFieldName(),fieldDetails);
			}
			List<String> keysList = new ArrayList<String>(map.keySet());
			checkProperties(keysList);
			return searchKeyValuePair(dashboard, map,search.getJoinColumns());
		}
		return null;
	}
	
	public void checkProperties(List<String> list) throws Exception  {
		for (int i = 0; i < list.size(); i++) {
			if(!(
				list.get(i).replace("%20","").trim().equals("userId") ||
				list.get(i).replace("%20","").trim().equals("description") ||
				list.get(i).replace("%20","").trim().equals("id") ||
				list.get(i).replace("%20","").trim().equals("reportdashboard") ||
				list.get(i).replace("%20","").trim().equals("title") ||
				list.get(i).replace("%20","").trim().equals("user")
			)) 
			{
			 throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!" );
			}
		}
	}
	
	public BooleanBuilder searchKeyValuePair(QDashboardEntity dashboard, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();
        
		for (Map.Entry<String, SearchFields> details : map.entrySet()) {
			if(details.getKey().replace("%20","").trim().equals("isPublished")) {
				if(details.getValue().getOperator().equals("equals") && (details.getValue().getSearchValue().equalsIgnoreCase("true") || details.getValue().getSearchValue().equalsIgnoreCase("false")))
					builder.and(dashboard.isPublished.eq(Boolean.parseBoolean(details.getValue().getSearchValue())));
				else if(details.getValue().getOperator().equals("notEqual") && (details.getValue().getSearchValue().equalsIgnoreCase("true") || details.getValue().getSearchValue().equalsIgnoreCase("false")))
					builder.and(dashboard.isPublished.ne(Boolean.parseBoolean(details.getValue().getSearchValue())));
			}
		}
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
        if(joinCol != null && joinCol.getKey().equals("ownerId")) {
		    builder.and(dashboard.user.id.eq(Long.parseLong(joinCol.getValue())));
		}
        }
		return builder;
	}
	
	
	public Map<String,String> parseReportdashboardJoinColumn(String keysString) {
		
		Map<String,String> joinColumnMap = new HashMap<String,String>();
		joinColumnMap.put("dashboardId", keysString);
		return joinColumnMap;
	}
    
	
}


