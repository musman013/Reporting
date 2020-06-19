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
import org.springframework.security.crypto.password.PasswordEncoder;
import com.nfinity.reporting.reportingapp1.application.authorization.userrole.UserroleAppService;
import com.nfinity.reporting.reportingapp1.application.authorization.userrole.dto.FindUserroleByIdOutput;
import com.nfinity.reporting.reportingapp1.application.authorization.userpermission.UserpermissionAppService;
import com.nfinity.reporting.reportingapp1.application.authorization.userpermission.dto.FindUserpermissionByIdOutput;
import com.nfinity.reporting.reportingapp1.commons.search.SearchCriteria;
import com.nfinity.reporting.reportingapp1.commons.search.SearchUtils;
import com.nfinity.reporting.reportingapp1.commons.application.OffsetBasedPageRequest;
import com.nfinity.reporting.reportingapp1.commons.domain.EmptyJsonResponse;
import com.nfinity.reporting.reportingapp1.application.authorization.user.UserAppService;
import com.nfinity.reporting.reportingapp1.application.authorization.user.dto.*;
import com.nfinity.reporting.reportingapp1.application.dashboard.DashboardAppService;
import com.nfinity.reporting.reportingapp1.application.dashboard.dto.FindDashboardByIdOutput;
import com.nfinity.reporting.reportingapp1.application.report.ReportAppService;
import com.nfinity.reporting.reportingapp1.application.report.dto.FindReportByIdOutput;
import com.nfinity.reporting.reportingapp1.domain.model.UserpermissionEntity;
import com.nfinity.reporting.reportingapp1.domain.model.RoleEntity;
import com.nfinity.reporting.reportingapp1.domain.authorization.user.IUserManager;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.security.JWTAppService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.nfinity.reporting.reportingapp1.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserAppService _userAppService;

	@Autowired
	private LoggingHelper logHelper;

	@Autowired
	private Environment env;

	@Autowired
	private PasswordEncoder pEncoder;

	@Autowired
	private UserpermissionAppService _userpermissionAppService;

	@Autowired
	private UserroleAppService _userroleAppService;

	@Autowired
	private JWTAppService _jwtAppService;

	public UserController(UserAppService userAppService,
			PasswordEncoder pEncoder, UserpermissionAppService userpermissionAppService, UserroleAppService userroleAppService,JWTAppService jwtAppService, LoggingHelper helper) {
		super();
		this._userAppService = userAppService;
		this.pEncoder = pEncoder;
		this._jwtAppService = jwtAppService;
		this._userpermissionAppService = userpermissionAppService;
		this._userroleAppService = userroleAppService;
		this.logHelper = helper;
	}

	@PreAuthorize("hasAnyAuthority('USERENTITY_CREATE')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CreateUserOutput> create(@RequestBody @Valid CreateUserInput user) {
		FindUserByUserNameOutput foundUser = _userAppService.findByUserName(user.getUserName());

		if (foundUser != null) {
			logHelper.getLogger().error("There already exists a user with a UserName=%s", user.getUserName());
			throw new EntityExistsException(
					String.format("There already exists a user with UserName =%s", user.getUserName()));
		}
		user.setPassword(pEncoder.encode(user.getPassword()));
		foundUser = _userAppService.findByEmailAddress(user.getEmailAddress());

		if (foundUser != null) {
			logHelper.getLogger().error("There already exists a user with a email =%s", user.getEmailAddress());
			throw new EntityExistsException(
					String.format("There already exists a user with email =%s", user.getEmailAddress()));
		}
		CreateUserOutput output=_userAppService.create(user);
		return new ResponseEntity(output, HttpStatus.OK);
	}

	// ------------ Delete user ------------
	@PreAuthorize("hasAnyAuthority('USERENTITY_DELETE')")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String id) {
		
		FindUserByIdOutput output = _userAppService.findById(Long.valueOf(id));
		Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("There does not exist a user with a id=%s", id)));
		
		_userAppService.delete(Long.valueOf(id));
	}

	// -------Update User Profile -----------------
	@RequestMapping(value = "/updateProfile/{id}", method = RequestMethod.PUT)
	public ResponseEntity<UpdateUserOutput> UpdateProfile(@PathVariable String id, @RequestBody @Valid UpdateUserInput user) {

		if (!_userAppService.getUser().getId().toString().equals(id)) {
			logHelper.getLogger().error("Unable to update. You don't have access to update this user.");
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.FORBIDDEN);
		}
		
		FindUserByUserNameOutput userOutput = _userAppService.findByEmailAddress(user.getEmailAddress());
		if(userOutput != null && userOutput.getId() != user.getId())
		{
			logHelper.getLogger().error("There already exists a user with a email=%s", user.getEmailAddress());
			throw new EntityExistsException(
					String.format("There already exists a user with a email=%s", user.getEmailAddress()));
		}

		FindUserWithAllFieldsByIdOutput currentUser = _userAppService.findWithAllFieldsById(Long.valueOf(id));
		user.setUserName(currentUser.getUserName());
		user.setIsActive(currentUser.getIsActive());
		user.setPassword(currentUser.getPassword());
		
		return new ResponseEntity(_userAppService.update(Long.valueOf(id),user), HttpStatus.OK);
	}

	// ------------ Update user ------------
	@PreAuthorize("hasAnyAuthority('USERENTITY_UPDATE')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<UpdateUserOutput> update(@PathVariable String id, @RequestBody @Valid UpdateUserInput user) {
		
		FindUserWithAllFieldsByIdOutput currentUser = _userAppService.findWithAllFieldsById(Long.valueOf(id));
		Optional.ofNullable(currentUser).orElseThrow(() -> new EntityNotFoundException(String.format("Unable to update. User with id=%s not found.", id)));
			
		user.setPassword(pEncoder.encode(currentUser.getPassword()));
		if(currentUser.getIsActive() && !user.getIsActive()) { 
			_jwtAppService.deleteAllUserTokens(currentUser.getUserName());
		} 
		
		user.setVersion(currentUser.getVersion());
		return new ResponseEntity(_userAppService.update(Long.valueOf(id),user), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('USERENTITY_READ')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<FindUserByIdOutput> findById(@PathVariable String id) {
		
		FindUserByIdOutput output = _userAppService.findById(Long.valueOf(id));
		Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));
		
		return new ResponseEntity(output, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('USERENTITY_READ')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws Exception {
		
		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

		return ResponseEntity.ok(_userAppService.find(searchCriteria,Pageable));
	}

	//    @PreAuthorize("hasAnyAuthority('USERENTITY_READ')")
	//	@RequestMapping(value = "/{id}/dashboard", method = RequestMethod.GET)
	//	public ResponseEntity getDashboard(@PathVariable String id, @RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort)throws Exception {
	//   		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
	//		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }
	//
	//		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
	//		
	//		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
	//		Map<String,String> joinColDetails=_userAppService.parseDashboardJoinColumn(id);
	//		if(joinColDetails== null)
	//		{
	//			logHelper.getLogger().error("Invalid Join Column");
	//			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
	//		}
	//		searchCriteria.setJoinColumns(joinColDetails);
	//		
	//    	List<FindDashboardByIdOutput> output = _dashboardAppService.find(searchCriteria,pageable);
	//		if (output == null) {
	//			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
	//		}
	//		
	//		return new ResponseEntity(output, HttpStatus.OK);
	//	}   
	// 
	//    
	//    @PreAuthorize("hasAnyAuthority('USERENTITY_READ')")
	//	@RequestMapping(value = "/{id}/report", method = RequestMethod.GET)
	//	public ResponseEntity getReport(@PathVariable String id, @RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort)throws Exception {
	//   		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
	//		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }
	//
	//		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
	//		
	//		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
	//		Map<String,String> joinColDetails=_userAppService.parseReportJoinColumn(id);
	//		if(joinColDetails== null)
	//		{
	//			logHelper.getLogger().error("Invalid Join Column");
	//			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
	//		}
	//		searchCriteria.setJoinColumns(joinColDetails);
	//		
	//    	List<FindReportByIdOutput> output = _reportAppService.find(searchCriteria,pageable);
	//		if (output == null) {
	//			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
	//		}
	//		
	//		return new ResponseEntity(output, HttpStatus.OK);
	//	}   

	@PreAuthorize("hasAnyAuthority('USERENTITY_READ')")
	@RequestMapping(value = "/{id}/userpermission", method = RequestMethod.GET)
	public ResponseEntity getUserpermission(@PathVariable String id, @RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort)throws Exception {
		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
		Map<String,String> joinColDetails=_userAppService.parseUserpermissionJoinColumn(id);
		Optional.ofNullable(joinColDetails).orElseThrow(() -> new EntityNotFoundException(String.format("Invalid Join Column")));
		
		searchCriteria.setJoinColumns(joinColDetails);
		
		List<FindUserpermissionByIdOutput> output = _userpermissionAppService.find(searchCriteria,pageable);
		Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));
		
		return new ResponseEntity(output, HttpStatus.OK);
	}  

	@PreAuthorize("hasAnyAuthority('USERENTITY_READ')")
	@RequestMapping(value = "/{id}/userrole", method = RequestMethod.GET)
	public ResponseEntity getUserrole(@PathVariable String id, @RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort)throws Exception {
		
		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
		Map<String,String> joinColDetails=_userAppService.parseUserroleJoinColumn(id);
		Optional.ofNullable(joinColDetails).orElseThrow(() -> new EntityNotFoundException(String.format("Invalid Join Column")));
		
		searchCriteria.setJoinColumns(joinColDetails);

		List<FindUserroleByIdOutput> output = _userroleAppService.find(searchCriteria,pageable);
		Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));
		
		return new ResponseEntity(output, HttpStatus.OK);
	}   


}

