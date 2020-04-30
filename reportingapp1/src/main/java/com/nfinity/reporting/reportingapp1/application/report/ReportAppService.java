package com.nfinity.reporting.reportingapp1.application.report;

import com.nfinity.reporting.reportingapp1.application.report.dto.FindReportByIdOutput;
import com.nfinity.reporting.reportingapp1.application.report.dto.*;
import com.nfinity.reporting.reportingapp1.domain.report.IReportManager;
import com.nfinity.reporting.reportingapp1.domain.model.QReportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.domain.authorization.user.UserManager;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
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
public class ReportAppService implements IReportAppService {

    static final int case1=1;
	static final int case2=2;
	static final int case3=3;
	
	@Autowired
	private IReportManager _reportManager;

    @Autowired
	private UserManager _userManager;
	@Autowired
	private ReportMapper mapper;
	
	@Autowired
	private LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
	public CreateReportOutput create(CreateReportInput input) {

		ReportEntity report = mapper.createReportInputToReportEntity(input);
	  	if(input.getUserId()!=null) {
			UserEntity foundUser = _userManager.findById(input.getUserId());
			if(foundUser!=null) {
				report.setUser(foundUser);
			}
		}
		ReportEntity createdReport = _reportManager.create(report);
		
		return mapper.reportEntityToCreateReportOutput(createdReport);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@CacheEvict(value="Report", key = "#p0")
	public UpdateReportOutput update(Long  reportId, UpdateReportInput input) {

		ReportEntity report = mapper.updateReportInputToReportEntity(input);
	  	if(input.getUserId()!=null) {
			UserEntity foundUser = _userManager.findById(input.getUserId());
			if(foundUser!=null) {
				report.setUser(foundUser);
			}
		}
		
		ReportEntity updatedReport = _reportManager.update(report);
		
		return mapper.reportEntityToUpdateReportOutput(updatedReport);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@CacheEvict(value="Report", key = "#p0")
	public void delete(Long reportId) {

		ReportEntity existing = _reportManager.findById(reportId) ; 
		_reportManager.delete(existing);
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@CacheEvict(value="Report", key = "#p0")
	public void delete(Long reportId, Long userId) {

		ReportEntity existing = _reportManager.findByReportIdAndUserId(reportId, userId);
		_reportManager.delete(existing);
		
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Cacheable(value = "Report", key = "#p0")
	public FindReportByIdOutput findById(Long reportId) {

		ReportEntity foundReport = _reportManager.findById(reportId);
		if (foundReport == null)  
			return null ; 
 	   
 	    FindReportByIdOutput output=mapper.reportEntityToFindReportByIdOutput(foundReport); 
		return output;
	}
    //User
	// ReST API Call - GET /report/1/user
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Cacheable (value = "Report", key="#p0")
	public GetUserOutput getUser(Long reportId) {

		ReportEntity foundReport = _reportManager.findById(reportId);
		if (foundReport == null) {
			logHelper.getLogger().error("There does not exist a report wth a id=%s", reportId);
			return null;
		}
		UserEntity re = _reportManager.getUser(reportId);
		return mapper.userEntityToGetUserOutput(re, foundReport);
	}
	
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Cacheable(value = "Report", key = "#p0")
	public FindReportByIdOutput findByReportIdAndUserId(Long reportId, Long userId) {

		ReportEntity foundReport = _reportManager.findByReportIdAndUserId(reportId, userId);
		if (foundReport == null)  
			return null ; 
 	   
 	    FindReportByIdOutput output = mapper.reportEntityToFindReportByIdOutput(foundReport); 
		return output;
	}
    
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Cacheable(value = "Report", key = "#p0")
	public List<FindReportByIdOutput> findByUserId(Long userId) {

		List<ReportEntity> reportList = _reportManager.findByUserId(userId);
		if (reportList == null)  
			return null ; 
		
		Iterator<ReportEntity> reportIterator = reportList.iterator(); 
		List<FindReportByIdOutput> output = new ArrayList<>();

		while (reportIterator.hasNext()) {
			output.add(mapper.reportEntityToFindReportByIdOutput(reportIterator.next()));
		}
	
		return output;
	}
    
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Cacheable(value = "Report")
	public List<FindReportByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception  {

		Page<ReportEntity> foundReport = _reportManager.findAll(search(search), pageable);
		List<ReportEntity> reportList = foundReport.getContent();
		Iterator<ReportEntity> reportIterator = reportList.iterator(); 
		List<FindReportByIdOutput> output = new ArrayList<>();

		while (reportIterator.hasNext()) {
			output.add(mapper.reportEntityToFindReportByIdOutput(reportIterator.next()));
		}
		return output;
	}
	
	public BooleanBuilder search(SearchCriteria search) throws Exception {

		QReportEntity report= QReportEntity.reportEntity;
		if(search != null) {
			Map<String,SearchFields> map = new HashMap<>();
			for(SearchFields fieldDetails: search.getFields())
			{
				map.put(fieldDetails.getFieldName(),fieldDetails);
			}
			List<String> keysList = new ArrayList<String>(map.keySet());
			checkProperties(keysList);
			return searchKeyValuePair(report, map,search.getJoinColumns());
		}
		return null;
	}
	
	public void checkProperties(List<String> list) throws Exception  {
		for (int i = 0; i < list.size(); i++) {
			if(!(
				list.get(i).replace("%20","").trim().equals("userId") ||
				list.get(i).replace("%20","").trim().equals("ctype") ||
				list.get(i).replace("%20","").trim().equals("description") ||
				list.get(i).replace("%20","").trim().equals("id") ||
				list.get(i).replace("%20","").trim().equals("query") ||
				list.get(i).replace("%20","").trim().equals("reportType") ||
				list.get(i).replace("%20","").trim().equals("reportdashboard") ||
				list.get(i).replace("%20","").trim().equals("title") ||
				list.get(i).replace("%20","").trim().equals("user")
			)) 
			{
			 throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!" );
			}
		}
	}
	
	public BooleanBuilder searchKeyValuePair(QReportEntity report, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();
        
		for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if(details.getKey().replace("%20","").trim().equals("ctype")) {
				if(details.getValue().getOperator().equals("contains"))
					builder.and(report.ctype.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				else if(details.getValue().getOperator().equals("equals"))
					builder.and(report.ctype.eq(details.getValue().getSearchValue()));
				else if(details.getValue().getOperator().equals("notEqual"))
					builder.and(report.ctype.ne(details.getValue().getSearchValue()));
			}
            if(details.getKey().replace("%20","").trim().equals("description")) {
				if(details.getValue().getOperator().equals("contains"))
					builder.and(report.description.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				else if(details.getValue().getOperator().equals("equals"))
					builder.and(report.description.eq(details.getValue().getSearchValue()));
				else if(details.getValue().getOperator().equals("notEqual"))
					builder.and(report.description.ne(details.getValue().getSearchValue()));
			}
//            if(details.getKey().replace("%20","").trim().equals("query")) {
//				if(details.getValue().getOperator().equals("contains"))
//					builder.and(report.query.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
//				else if(details.getValue().getOperator().equals("equals"))
//					builder.and(report.query.eq(details.getValue().getSearchValue()));
//				else if(details.getValue().getOperator().equals("notEqual"))
//					builder.and(report.query.ne(details.getValue().getSearchValue()));
//			}
            if(details.getKey().replace("%20","").trim().equals("reportType")) {
				if(details.getValue().getOperator().equals("contains"))
					builder.and(report.reportType.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				else if(details.getValue().getOperator().equals("equals"))
					builder.and(report.reportType.eq(details.getValue().getSearchValue()));
				else if(details.getValue().getOperator().equals("notEqual"))
					builder.and(report.reportType.ne(details.getValue().getSearchValue()));
			}
            if(details.getKey().replace("%20","").trim().equals("title")) {
				if(details.getValue().getOperator().equals("contains"))
					builder.and(report.title.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				else if(details.getValue().getOperator().equals("equals"))
					builder.and(report.title.eq(details.getValue().getSearchValue()));
				else if(details.getValue().getOperator().equals("notEqual"))
					builder.and(report.title.ne(details.getValue().getSearchValue()));
			}
		}
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
        if(joinCol != null && joinCol.getKey().equals("userId")) {
		    builder.and(report.user.id.eq(Long.parseLong(joinCol.getValue())));
		}
        }
		return builder;
	}
	
	
	public Map<String,String> parseReportdashboardJoinColumn(String keysString) {
		
		Map<String,String> joinColumnMap = new HashMap<String,String>();
		joinColumnMap.put("reportId", keysString);
		return joinColumnMap;
	}
    
	
}


