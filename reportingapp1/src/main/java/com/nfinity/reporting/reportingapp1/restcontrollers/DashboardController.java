package com.nfinity.reporting.reportingapp1.restcontrollers;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.nfinity.reporting.reportingapp1.commons.search.SearchCriteria;
import com.nfinity.reporting.reportingapp1.commons.search.SearchUtils;
import com.nfinity.reporting.reportingapp1.commons.application.OffsetBasedPageRequest;
import com.nfinity.reporting.reportingapp1.commons.domain.EmptyJsonResponse;
import com.nfinity.reporting.reportingapp1.application.dashboard.DashboardAppService;
import com.nfinity.reporting.reportingapp1.application.dashboard.dto.*;
import com.nfinity.reporting.reportingapp1.application.reportdashboard.ReportdashboardAppService;
import com.nfinity.reporting.reportingapp1.application.reportdashboard.dto.FindReportdashboardByIdOutput;
import com.nfinity.reporting.reportingapp1.application.authorization.user.UserAppService;
import java.util.List;
import java.util.Map;
import com.nfinity.reporting.reportingapp1.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private DashboardAppService _dashboardAppService;
    
    @Autowired
	private ReportdashboardAppService  _reportdashboardAppService;
    
    @Autowired
	private UserAppService  _userAppService;

	@Autowired
	private LoggingHelper logHelper;

	@Autowired
	private Environment env;
	
	
    
    public DashboardController(DashboardAppService dashboardAppService, ReportdashboardAppService reportdashboardAppService, UserAppService userAppService,
	 LoggingHelper helper) {
		super();
		this._dashboardAppService = dashboardAppService;
    	this._reportdashboardAppService = reportdashboardAppService;
    	this._userAppService = userAppService;
		this.logHelper = helper;
	}

    @PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_CREATE')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CreateDashboardOutput> create(@RequestBody @Valid CreateDashboardInput dashboard) {
		CreateDashboardOutput output=_dashboardAppService.create(dashboard);
		return new ResponseEntity(output, HttpStatus.OK);
	}
   
	// ------------ Delete dashboard ------------
	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_DELETE')")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String id) {
    FindDashboardByIdOutput output = _dashboardAppService.findById(Long.valueOf(id));
	if (output == null) {
		logHelper.getLogger().error("There does not exist a dashboard with a id=%s", id);
		throw new EntityNotFoundException(
			String.format("There does not exist a dashboard with a id=%s", id));
	}
    _dashboardAppService.delete(Long.valueOf(id));
    }
    
	
	// ------------ Update dashboard ------------
    @PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_UPDATE')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<UpdateDashboardOutput> update(@PathVariable String id, @RequestBody @Valid UpdateDashboardInput dashboard) {
	    FindDashboardByIdOutput currentDashboard = _dashboardAppService.findById(Long.valueOf(id));
			
		if (currentDashboard == null) {
			logHelper.getLogger().error("Unable to update. Dashboard with id {} not found.", id);
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}
		
	    return new ResponseEntity(_dashboardAppService.update(Long.valueOf(id),dashboard), HttpStatus.OK);
	}
    @PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_READ')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<FindDashboardByIdOutput> findById(@PathVariable String id) {
    FindDashboardByIdOutput output = _dashboardAppService.findById(Long.valueOf(id));
		if (output == null) {
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity(output, HttpStatus.OK);
	}
    
    @PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_READ')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws Exception {
		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
		
		return ResponseEntity.ok(_dashboardAppService.find(searchCriteria,Pageable));
	}
    
    @PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_READ')")
	@RequestMapping(value = "/{id}/reportdashboard", method = RequestMethod.GET)
	public ResponseEntity getReportdashboard(@PathVariable String id, @RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort)throws Exception {
   		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
		Map<String,String> joinColDetails=_dashboardAppService.parseReportdashboardJoinColumn(id);
		if(joinColDetails== null)
		{
			logHelper.getLogger().error("Invalid Join Column");
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}
		searchCriteria.setJoinColumns(joinColDetails);
		
    	List<FindReportdashboardByIdOutput> output = _reportdashboardAppService.find(searchCriteria,pageable);
		if (output == null) {
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity(output, HttpStatus.OK);
	}   
 
    @PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_READ')")
	@RequestMapping(value = "/{id}/user", method = RequestMethod.GET)
	public ResponseEntity<GetUserOutput> getUser(@PathVariable String id) {
    GetUserOutput output= _dashboardAppService.getUser(Long.valueOf(id));
		if (output == null) {
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(output, HttpStatus.OK);
	}


}

