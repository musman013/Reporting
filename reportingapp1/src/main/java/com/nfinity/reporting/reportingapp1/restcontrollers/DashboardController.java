package com.nfinity.reporting.reportingapp1.restcontrollers;

import javax.persistence.EntityExistsException;
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
import com.nfinity.reporting.reportingapp1.domain.model.DashboarduserId;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionreportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionreportId;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.commons.application.OffsetBasedPageRequest;
import com.nfinity.reporting.reportingapp1.commons.domain.EmptyJsonResponse;
import com.nfinity.reporting.reportingapp1.application.dashboard.DashboardAppService;
import com.nfinity.reporting.reportingapp1.application.dashboard.dto.*;
import com.nfinity.reporting.reportingapp1.application.dashboarduser.DashboarduserAppService;
import com.nfinity.reporting.reportingapp1.application.dashboarduser.dto.FindDashboarduserByIdOutput;
import com.nfinity.reporting.reportingapp1.application.dashboardversionreport.DashboardversionreportAppService;
import com.nfinity.reporting.reportingapp1.application.dashboardversionreport.dto.FindDashboardversionreportByIdOutput;
import com.nfinity.reporting.reportingapp1.application.report.ReportAppService;
import com.nfinity.reporting.reportingapp1.application.report.dto.FindReportByIdOutput;
import com.nfinity.reporting.reportingapp1.application.report.dto.ShareReportInput;
import com.nfinity.reporting.reportingapp1.application.report.dto.UpdateReportInput;
import com.nfinity.reporting.reportingapp1.application.authorization.role.RoleAppService;
import com.nfinity.reporting.reportingapp1.application.authorization.role.dto.FindRoleByIdOutput;
import com.nfinity.reporting.reportingapp1.application.authorization.user.UserAppService;
import com.nfinity.reporting.reportingapp1.application.authorization.user.dto.FindUserByIdOutput;

import java.util.List;
import java.util.Map;
import com.nfinity.reporting.reportingapp1.commons.logging.LoggingHelper;

@RestController
@SuppressWarnings({"unchecked", "rawtypes"})
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private DashboardAppService _dashboardAppService;
	
	@Autowired
	private DashboarduserAppService _dashboarduserAppService;

	@Autowired
	private DashboardversionreportAppService  _reportdashboardAppService;
	
	@Autowired
	private ReportAppService _reportAppService;

	@Autowired
	private UserAppService  _userAppService;
	
	@Autowired
	private RoleAppService  _roleAppService;

	@Autowired
	private LoggingHelper logHelper;

	@Autowired
	private Environment env;


	public DashboardController(DashboardAppService dashboardAppService, DashboardversionreportAppService reportdashboardAppService, UserAppService userAppService,
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
		UserEntity user = _userAppService.getUser();
		dashboard.setOwnerId(user.getId());
		dashboard.setIsPublished(true);
		dashboard.setIsShareable(true);
		CreateDashboardOutput output=_dashboardAppService.create(dashboard);
		return new ResponseEntity(output, HttpStatus.OK);
	}

	// ------------ Delete dashboard ------------
	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_DELETE')")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String id) {
		UserEntity user = _userAppService.getUser();
		FindDashboardByIdOutput output = _dashboardAppService.findById(Long.valueOf(id));
		if (output == null) {
			logHelper.getLogger().error("There does not exist a dashboard with a id=%s", id);
			throw new EntityNotFoundException(
					String.format("There does not exist a dashboard with a id=%s", id));
		}
		
		FindDashboarduserByIdOutput dashboarduser = _dashboarduserAppService.findById(new DashboarduserId(Long.valueOf(id), user.getId()));

		if(output.getOwnerId() != user.getId() &&  dashboarduser == null) {
			logHelper.getLogger().error("You do not have access to update a dashboard with a id=%s", id);
			throw new EntityNotFoundException(
					String.format("You do not have access to update a dashboard with a id=%s", id));
		}
		
//		if(!user.getId().equals(output.getOwnerId()))
//		{
//			logHelper.getLogger().error("You do not have an access to delete a dashboard with a id=%s", id);
//			throw new EntityNotFoundException(
//					String.format("You do not have an access to delete a dashboard with a id=%s", id));
//		}
		
		_dashboardAppService.delete(Long.valueOf(id), user.getId());
	}
	
	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_DELETE')")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}/report/{reportId}", method = RequestMethod.DELETE)
	public void deleteReportFromDashboard(@PathVariable String id,@PathVariable String reportId) {
		UserEntity user = _userAppService.getUser();
		FindDashboardByIdOutput currentDashboard = _dashboardAppService.findById(Long.valueOf(id));

		if (currentDashboard == null) {
			logHelper.getLogger().error("There does not exist a dashboard with a id=%s", id);
			throw new EntityNotFoundException(
					String.format("There does not exist a dashboard with a id=%s", id));
		}

		FindDashboarduserByIdOutput dashboarduser = _dashboarduserAppService.findById(new DashboarduserId(Long.valueOf(id), user.getId()));

		if(currentDashboard.getOwnerId() != user.getId() &&  dashboarduser == null) {
			logHelper.getLogger().error("You do not have access to update a dashboard with a id=%s", id);
			throw new EntityNotFoundException(
					String.format("You do not have access to update a dashboard with a id=%s", id));
		}
		
		_dashboardAppService.deleteReportFromDashboard(Long.valueOf(id), Long.valueOf(reportId), user.getId());
	}

	// ------------ Update dashboard ------------
	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_UPDATE')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<UpdateDashboardOutput> update(@PathVariable String id,@RequestBody @Valid UpdateDashboardInput dashboard) {
		UserEntity user = _userAppService.getUser();
		FindDashboardByIdOutput currentDashboard = _dashboardAppService.findById(Long.valueOf(id));

		if (currentDashboard == null) {
			logHelper.getLogger().error("Unable to update. Dashboard with id {} not found.", id);
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

//		if (currentDashboard.getOwnerId() == null) {
//			logHelper.getLogger().error("Unable to update orphan dashboard with id {}.", id);
//			throw new EntityNotFoundException(
//					String.format("Unable to update orphan dashboard with id {}.", id));
//		}

		FindDashboarduserByIdOutput dashboarduser = _dashboarduserAppService.findById(new DashboarduserId(Long.valueOf(id), user.getId()));

		if(currentDashboard.getOwnerId() != user.getId() &&  dashboarduser == null) {
			logHelper.getLogger().error("You do not have access to update a dashboard with a id=%s", id);
			throw new EntityNotFoundException(
					String.format("You do not have access to update a dashboard with a id=%s", id));
		}
		
		dashboard.setUserId(user.getId());
	//	dashboard.setIsPublished(false);
		
		return new ResponseEntity(_dashboardAppService.update(Long.valueOf(id),dashboard), HttpStatus.OK);
		
	}

	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_READ')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<FindDashboardByIdOutput> findById(@PathVariable String id) {
		UserEntity user = _userAppService.getUser();
		FindDashboardByIdOutput output = _dashboardAppService.findByDashboardIdAndUserId(Long.valueOf(id), user.getId());
		if (output == null) {
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		output.setReportDetails(_dashboardAppService.setReportsList(Long.valueOf(id), user.getId()));
		
		return new ResponseEntity(output, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_CREATE')")
	@RequestMapping(value = "/addNewReportToNewDashboard", method = RequestMethod.POST)
	public ResponseEntity<FindDashboardByIdOutput> addNewReportsToNewDasboard(@RequestBody AddNewReportToNewDashboardInput input) {
		UserEntity user = _userAppService.getUser();
		input.setOwnerId(user.getId());
		input.setIsPublished(true);
		FindDashboardByIdOutput output  = _dashboardAppService.addNewReportsToNewDashboard(input);
		return new ResponseEntity(output, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_UPDATE')")
	@RequestMapping(value = "/addNewReportToExistingDashboard", method = RequestMethod.PUT)
	public ResponseEntity<FindDashboardByIdOutput> addNewReportsToExistingDasboard(@RequestBody AddNewReportToExistingDashboardInput input) {
		FindDashboardByIdOutput dashboard = _dashboardAppService.findById(input.getId());
    	if(dashboard == null)
    	{
    		logHelper.getLogger().error("There does not exist a dashboard with a id=%s", input.getId());
			throw new EntityNotFoundException(
					String.format("There does not exist a dashboard with a id=%s", input.getId()));
    	}
    	
		UserEntity user = _userAppService.getUser();
		FindDashboarduserByIdOutput dashboarduser = _dashboarduserAppService.findById(new DashboarduserId(Long.valueOf(input.getId()), user.getId()));

		if(dashboard.getOwnerId() != user.getId() &&  (dashboarduser == null || !dashboarduser.getEditable())) {
			logHelper.getLogger().error("You do not have access to add report to dashboard with id=%s", input.getId());
			throw new EntityNotFoundException(
					String.format("You do not have access to add report to dashboard dashboard with id=%s", input.getId()));
		}
		
		input.setOwnerId(user.getId());
    	FindDashboardByIdOutput output = _dashboardAppService.addNewReportsToExistingDashboard(input);
    
		return new ResponseEntity(output, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_CREATE')")
	@RequestMapping(value = "/addExistingReportToNewDashboard", method = RequestMethod.POST)
	public ResponseEntity<FindDashboardByIdOutput> addExistingReportsToNewDasboard(@RequestBody AddExistingReportToNewDashboardInput input) {
		for(ExistingReportInput reportInput : input.getReportDetails())
		{
			FindReportByIdOutput report = _reportAppService.findById(reportInput.getId());
			if(report == null) {
				logHelper.getLogger().error("There does not exist a report with a id=%s", reportInput.getId());
				throw new EntityNotFoundException(
						String.format("There does not exist a report with a id=%s", reportInput.getId()));
			}
		}
		UserEntity user = _userAppService.getUser();
		input.setOwnerId(user.getId());
		input.setIsPublished(true);
		FindDashboardByIdOutput output  = _dashboardAppService.addExistingReportsToNewDashboard(input);
		
		return new ResponseEntity(output, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_UPDATE')")
	@RequestMapping(value = "/addExistingReportToExistingDashboard", method = RequestMethod.PUT)
	public ResponseEntity<FindDashboardByIdOutput> addExistingReportsToExistingDasboard(@RequestBody AddExistingReportToExistingDashboardInput input) {
		FindDashboardByIdOutput dashboard = _dashboardAppService.findById(input.getId());
    	if(dashboard == null)
    	{
    		logHelper.getLogger().error("There does not exist a dashboard with a id=%s", input.getId());
			throw new EntityNotFoundException(
					String.format("There does not exist a dashboard with a id=%s", input.getId()));
    	}

		UserEntity user = _userAppService.getUser();
		FindDashboarduserByIdOutput dashboarduser = _dashboarduserAppService.findById(new DashboarduserId(Long.valueOf(input.getId()), user.getId()));

		if(dashboard.getOwnerId() != user.getId() &&  (dashboarduser == null || !dashboarduser.getEditable())) {
			logHelper.getLogger().error("You do not have access to add report to dashboard with id=%s", input.getId());
			throw new EntityNotFoundException(
					String.format("You do not have access to add report to dashboard dashboard with id=%s", input.getId()));
		}
		
		dashboard.setReportDetails(_dashboardAppService.setReportsList(input.getId(), user.getId()));
		
        for(FindReportByIdOutput reportInput :dashboard.getReportDetails())
        {
        	 if(input.getReportDetails().stream().filter(o -> o.getId().equals(reportInput.getId())).findFirst().isPresent()) {
            	logHelper.getLogger().error("Report already exist in dashboard with a id=%s", reportInput.getId());
    			throw new EntityNotFoundException(
    					String.format("Report already exist in dashboard with a id=%s", reportInput.getId()));
            }
        
        }
        
        for(ExistingReportInput reportInput : input.getReportDetails())
		{
			FindReportByIdOutput report = _reportAppService.findById(reportInput.getId());
			if(report == null) {
				logHelper.getLogger().error("There does not exist a report with a id=%s", reportInput.getId());
				throw new EntityNotFoundException(
						String.format("There does not exist a report with a id=%s", reportInput.getId()));
			}
		}
		
		input.setOwnerId(user.getId());
		FindDashboardByIdOutput output  = _dashboardAppService.addExistingReportsToExistingDashboard(input);
		return new ResponseEntity(output, HttpStatus.OK);
	}
	
	
//	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_READ')")
//	@RequestMapping(method = RequestMethod.GET)
//	public ResponseEntity find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort)throws Exception {
//		UserEntity user = _userAppService.getUser();
//		
//		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
//		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }
//
//		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
//
//		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
//		Map<String,String> joinColDetails=_userAppService.parseDashboardJoinColumn(user.getId().toString());
//		if(joinColDetails== null)
//		{
//			logHelper.getLogger().error("Invalid Join Column");
//			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
//		}
//		searchCriteria.setJoinColumns(joinColDetails);
//
//		List<FindDashboardByIdOutput> output = _dashboardAppService.find(searchCriteria,pageable);
//		if (output == null) {
//			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
//		}
//		
//		for(FindDashboardByIdOutput ds : output)
//			ds.setReportDetails(_dashboardAppService.setReportsList(Long.valueOf(ds.getId())));
//
//		return new ResponseEntity(output, HttpStatus.OK);
//	}   

//	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_READ')")
//	@RequestMapping(method = RequestMethod.GET)
//	public ResponseEntity find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws Exception {
//		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
//		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }
//
//		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
//		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
//
//		return ResponseEntity.ok(_dashboardAppService.find(searchCriteria,Pageable));
//	}

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

		List<FindDashboardversionreportByIdOutput> output = _reportdashboardAppService.find(searchCriteria,pageable);
		if (output == null) {
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(output, HttpStatus.OK);
	}   

	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_READ')")
	@RequestMapping(value = "/{id}/user", method = RequestMethod.GET)
	public ResponseEntity<GetUserOutput> getUser(@PathVariable String id,@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) {

		UserEntity user = _userAppService.getUser();
		FindDashboardByIdOutput currentDashboard = _dashboardAppService.findById(Long.valueOf(id));

		if (currentDashboard == null) {
			logHelper.getLogger().error("Dashboard with id {} not found.", id);
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		if (currentDashboard.getOwnerId() == null || user.getId() != currentDashboard.getOwnerId()) {
			logHelper.getLogger().error("Unable to get users for dashboard with id '{}'.", id);
			throw new EntityNotFoundException(
					String.format("Unable to get users for dashboard with id '{}'.", id));
		}

		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset),Integer.parseInt(limit), sort);

		List<GetUserOutput> output = _dashboardAppService.getUsersAssociatedWithDashboard(Long.valueOf(id),search,pageable);
		return new ResponseEntity(output, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_READ')")
	@RequestMapping(value = "/{id}/availableUser", method = RequestMethod.GET)
	public ResponseEntity<List<GetUserOutput>> getAvailableUser(@PathVariable String id,@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) {
		UserEntity user = _userAppService.getUser();
		FindDashboardByIdOutput currentDashboard = _dashboardAppService.findById(Long.valueOf(id));

		if (currentDashboard == null) {
			logHelper.getLogger().error("Dashboard with id {} not found.", id);
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		if (currentDashboard.getOwnerId() == null || user.getId() != currentDashboard.getOwnerId()) {
			logHelper.getLogger().error("Unable to get users for dashboard with id {}.", id);
			throw new EntityNotFoundException(
					String.format("Unable to users for dashboard with id {}.", id));
		}

		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);


		List<GetUserOutput> output = _dashboardAppService.getAvailableUsers(Long.valueOf(id),search,pageable);
		return new ResponseEntity(output, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_READ')")
	@RequestMapping(value = "/{id}/role", method = RequestMethod.GET)
	public ResponseEntity<List<GetRoleOutput>> getRole(@PathVariable String id,@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) {
		UserEntity user = _userAppService.getUser();
		FindDashboardByIdOutput currentDashboard = _dashboardAppService.findById(Long.valueOf(id));

		if (currentDashboard == null) {
			logHelper.getLogger().error("Dashboard with id {} not found.", id);
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		if (currentDashboard.getOwnerId() == null || user.getId() != currentDashboard.getOwnerId()) {
			logHelper.getLogger().error("Unable to get users for dashboard with id {}.", id);
			throw new EntityNotFoundException(
					String.format("Unable to users for dashboard with id {}.", id));
		}

		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);


		List<GetRoleOutput> output = _dashboardAppService.getRolesAssociatedWithDashboard(Long.valueOf(id),search,pageable);
		return new ResponseEntity(output, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_READ')")
	@RequestMapping(value = "/{id}/availableRole", method = RequestMethod.GET)
	public ResponseEntity<List<GetRoleOutput>> getAvailableRole(@PathVariable String id,@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) {
		UserEntity user = _userAppService.getUser();
		FindDashboardByIdOutput currentDashboard = _dashboardAppService.findById(Long.valueOf(id));

		if (currentDashboard == null) {
			logHelper.getLogger().error("Dashboard with id {} not found.", id);
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		if (currentDashboard.getOwnerId() == null || user.getId() != currentDashboard.getOwnerId()) {
			logHelper.getLogger().error("Unable to get users for dashboard with id {}.", id);
			throw new EntityNotFoundException(
					String.format("Unable to get users for dashboard with id {}.", id));
		}

		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);


		List<GetRoleOutput> output = _dashboardAppService.getAvailableRoles(Long.valueOf(id),search,pageable);
		return new ResponseEntity(output, HttpStatus.OK);
	}

	
	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_READ')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<DashboardDetailsOutput>> getDashboard(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws Exception {
		UserEntity user = _userAppService.getUser();

		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		List<DashboardDetailsOutput> output = _dashboardAppService.getDashboards(user.getId(), search,pageable);

		return new ResponseEntity(output, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_READ')")
	@RequestMapping(value = "/available/{reportId}" ,method = RequestMethod.GET)
	public ResponseEntity<List<DashboardDetailsOutput>> getAvailableDashboard(@PathVariable String reportId, @RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws Exception {
		UserEntity user = _userAppService.getUser();

		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		List<DashboardDetailsOutput> output = _dashboardAppService.getAvailableDashboards(user.getId(),Long.valueOf(reportId), search,pageable);

		return new ResponseEntity(output, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_READ')")
	@RequestMapping(value = "/shared" ,method = RequestMethod.GET)
	public ResponseEntity<List<DashboardDetailsOutput>> getSharedDashboard(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws Exception {
		UserEntity user = _userAppService.getUser();

		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

		List<DashboardDetailsOutput> output = _dashboardAppService.getSharedDashboards(user.getId(), search,pageable);

		return new ResponseEntity(output, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_UPDATE')")
	@RequestMapping(value = "/{id}/share", method = RequestMethod.PUT)
	public ResponseEntity<DashboardDetailsOutput> shareDashboard(@PathVariable String id, @RequestBody @Valid Map<String, List<ShareReportInput>> input) {
		UserEntity user = _userAppService.getUser();
		FindDashboardByIdOutput currentDashboard = _dashboardAppService.findById(Long.valueOf(id));

		if (currentDashboard == null) {
			logHelper.getLogger().error("Dashboard with id%s not found.", id);
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		if (currentDashboard.getOwnerId() == null || user.getId() != currentDashboard.getOwnerId()) {
			logHelper.getLogger().error("Unable to share dashboard with id '{}'.", id);
			throw new EntityNotFoundException(
					String.format("Unable to share dashboard with id {}.", id));
		}

		List<ShareReportInput> usersList = input.get("users");
		List<ShareReportInput> rolesList = input.get("roles");

		for(ShareReportInput roleInput : rolesList)
		{
			FindRoleByIdOutput foundRole = _roleAppService.findById(roleInput.getId());
			if(foundRole == null)
			{
				logHelper.getLogger().error("Role not found with id=%s", roleInput.getId());
				throw new EntityNotFoundException(
						String.format("Role not found with id=%s", roleInput.getId()));
			}
		}

		for(ShareReportInput userInput : usersList)
		{
			FindUserByIdOutput foundUser = _userAppService.findById(userInput.getId());
			if(foundUser == null)
			{
				logHelper.getLogger().error("User not found with id=%s", userInput.getId());
				throw new EntityNotFoundException(
						String.format("User not found with id=%s", userInput.getId()));
			}
		}

		DashboardDetailsOutput output = _dashboardAppService.shareDashboard(Long.valueOf(id), usersList, rolesList);

		return new ResponseEntity(output, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_UPDATE')")
	@RequestMapping(value = "/{id}/unshare", method = RequestMethod.PUT)
	public ResponseEntity<DashboardDetailsOutput> unshareDashboard(@PathVariable String id, @RequestBody @Valid Map<String, List<ShareReportInput>> input) {
		UserEntity user = _userAppService.getUser();
		FindDashboardByIdOutput currentDashboard = _dashboardAppService.findById(Long.valueOf(id));

		if (currentDashboard == null) {
			logHelper.getLogger().error("Dashboard with id {} not found.", id);
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		if (currentDashboard.getOwnerId() == null || user.getId() != currentDashboard.getOwnerId()) {
			logHelper.getLogger().error("Unable to unshare dashboard with id {}.", id);
			throw new EntityNotFoundException(
					String.format("Unable to unshare dashboard with id {}.", id));
		}

		List<ShareReportInput> usersList = input.get("users");
		List<ShareReportInput> rolesList = input.get("roles");

		for(ShareReportInput roleInput : rolesList)
		{
			FindRoleByIdOutput foundRole = _roleAppService.findById(roleInput.getId());
			if(foundRole == null)
			{
				logHelper.getLogger().error("Role not found with id=%s", roleInput.getId());
				throw new EntityNotFoundException(
						String.format("Role not found with id=%s", roleInput.getId()));
			}
		}

		for(ShareReportInput userInput : usersList)
		{
			FindUserByIdOutput foundUser = _userAppService.findById(userInput.getId());
			if(foundUser == null)
			{
				logHelper.getLogger().error("User not found with id=%s", userInput.getId());
				throw new EntityNotFoundException(
						String.format("User not found with id=%s", userInput.getId()));
			}
		}

		DashboardDetailsOutput output = _dashboardAppService.unshareDashboard(Long.valueOf(id), usersList, rolesList);
        if(output == null)
        {
        	logHelper.getLogger().error("Unable to found user published version with id=%s", id);
        	throw new EntityNotFoundException(
					String.format("Unable to found user published version with id=%s", id));
        }
		return new ResponseEntity(output, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_UPDATE')")
	@RequestMapping(value = "/{id}/updateRecipientSharingStatus", method = RequestMethod.PUT)
	public ResponseEntity<DashboardDetailsOutput> updateRecipientSharingStatus(@PathVariable String id, @RequestBody Boolean status) {
		UserEntity user = _userAppService.getUser();

		FindDashboardByIdOutput currentDashboard = _dashboardAppService.findById(Long.valueOf(id));

		if (currentDashboard == null) {
			logHelper.getLogger().error("Unable to update. Dashboard with id {} not found.", id);
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		DashboardDetailsOutput output = _dashboardAppService.updateRecipientSharingStatus(user.getId(), Long.valueOf(id), status);

		if(output == null)
		{
			logHelper.getLogger().error("Dashboard user does't exists");
        	throw new EntityNotFoundException(
					String.format("Dashboard user does't exists"));
		}
		return new ResponseEntity(output, HttpStatus.OK);

	}

	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_UPDATE')")
	@RequestMapping(value = "/{id}/publish", method = RequestMethod.PUT)
	public ResponseEntity<DashboardDetailsOutput> publishDashboard(@PathVariable String id) {
		UserEntity user = _userAppService.getUser();
		FindDashboardByIdOutput currentDashboard = _dashboardAppService.findById(Long.valueOf(id));

		if (currentDashboard == null) {
			logHelper.getLogger().error("Unable to update. Dashboard with id {} not found.", id);
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		if(!user.getId().equals(currentDashboard.getOwnerId())) {
			logHelper.getLogger().error("You do not have access to publish a dashboard with a id=%s", id);
			throw new EntityNotFoundException(
					String.format("You do not have access to publish a dashboard with a id=%s", id));
		}

		if(currentDashboard.getIsPublished())
		{
			logHelper.getLogger().error("Dashboard is already published with a id=%s", id);
			throw new EntityExistsException(
					String.format("Dashboard is already published with a id=%s", id));

		}

		DashboardDetailsOutput output = _dashboardAppService.publishDashboard(user.getId(), Long.valueOf(id));

		return new ResponseEntity(output, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_UPDATE')")
	@RequestMapping(value = "/{id}/changeOwner", method = RequestMethod.PUT)
	public ResponseEntity<DashboardDetailsOutput> changeOwner(@PathVariable String id,@RequestBody Long userId) {
		UserEntity user = _userAppService.getUser();
		FindDashboardByIdOutput currentDashboard = _dashboardAppService.findById(Long.valueOf(id));

		if (currentDashboard == null) {
			logHelper.getLogger().error("Unable to update. Dashboard with id {} not found.", id);
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}

		if(!user.getId().equals(currentDashboard.getOwnerId())) {
			logHelper.getLogger().error("You do not have access to update owner of a dashboard with a id=%s", id);
			throw new EntityNotFoundException(
					String.format("You do not have access to update owner of a dashboard with a id=%s", id));
		}

		if(currentDashboard.getOwnerId() == userId)
		{
			logHelper.getLogger().error("Dashboard is already owned by user with id= %s", id);
			throw new EntityExistsException(
					String.format("Dashboard is already owned by userwith a id=%s", id));
		}

		if(_userAppService.findById(userId) == null)
		{
			logHelper.getLogger().error("User does not exist with id=%s", id);
			throw new EntityNotFoundException(
					String.format("User does not exist with id=%s", id));
		}
		
		return new ResponseEntity(_dashboardAppService.changeOwner(currentDashboard.getOwnerId(), Long.valueOf(id), userId), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_UPDATE')")
	@RequestMapping(value = "/{id}/refresh", method = RequestMethod.PUT)
	public ResponseEntity<DashboardDetailsOutput> refreshDashboard(@PathVariable String id) {
		UserEntity user = _userAppService.getUser();

		FindDashboarduserByIdOutput dashboarduser = _dashboarduserAppService.findById(new DashboarduserId(Long.valueOf(id), user.getId()));
		if(dashboarduser == null)
		{
			logHelper.getLogger().error("No Dashboard is shared with id=%s", id);
			throw new EntityNotFoundException(
					String.format("No Dashboard is shared with id=%s", id));
		}

		DashboardDetailsOutput output = _dashboardAppService.refreshDashboard(user.getId(), Long.valueOf(id));

		if(output == null)
		{
			logHelper.getLogger().error("No updates available. Dashboard can not be refreshed");
			throw new EntityNotFoundException(
					String.format("No updates available. Dashboard can not be refreshed"));
		}

		return new ResponseEntity(output, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('DASHBOARDENTITY_UPDATE')")
	@RequestMapping(value = "/{id}/reset", method = RequestMethod.PUT)
	public ResponseEntity<DashboardDetailsOutput> resetDashboard(@PathVariable String id) {
		UserEntity user = _userAppService.getUser();

		FindDashboarduserByIdOutput dashboarduser = _dashboarduserAppService.findById(new DashboarduserId(Long.valueOf(id), user.getId()));
		if(dashboarduser == null)
		{
			logHelper.getLogger().error("No Dashboard is shared with id=%s", id);
			throw new EntityNotFoundException(
					String.format("No Dashboard is shared with id=%s", id));
		}

		DashboardDetailsOutput output = _dashboardAppService.resetDashboard(user.getId(), Long.valueOf(id));
		if(output == null) {
			logHelper.getLogger().error("Dashboard can not be resetted");
			throw new EntityNotFoundException(
					String.format("Dashboard can not be resetted"));
		}

		return new ResponseEntity(output, HttpStatus.OK);
	}



}

