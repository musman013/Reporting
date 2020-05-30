package com.nfinity.reporting.reportingapp1.application.dashboard;

import com.nfinity.reporting.reportingapp1.application.dashboard.dto.*;
import com.nfinity.reporting.reportingapp1.application.dashboardrole.DashboardroleAppService;
import com.nfinity.reporting.reportingapp1.application.dashboardrole.dto.CreateDashboardroleInput;
import com.nfinity.reporting.reportingapp1.application.dashboarduser.DashboarduserAppService;
import com.nfinity.reporting.reportingapp1.application.dashboarduser.dto.CreateDashboarduserInput;
import com.nfinity.reporting.reportingapp1.application.dashboardversion.DashboardversionAppService;
import com.nfinity.reporting.reportingapp1.application.dashboardversion.IDashboardversionMapper;
import com.nfinity.reporting.reportingapp1.application.dashboardversion.dto.CreateDashboardversionInput;
import com.nfinity.reporting.reportingapp1.application.dashboardversion.dto.CreateDashboardversionOutput;
import com.nfinity.reporting.reportingapp1.application.dashboardversion.dto.UpdateDashboardversionInput;
import com.nfinity.reporting.reportingapp1.application.dashboardversion.dto.UpdateDashboardversionOutput;
import com.nfinity.reporting.reportingapp1.application.dashboardversionreport.DashboardversionreportAppService;
import com.nfinity.reporting.reportingapp1.application.report.ReportAppService;
import com.nfinity.reporting.reportingapp1.application.report.ReportMapper;
import com.nfinity.reporting.reportingapp1.application.report.dto.CreateReportInput;
import com.nfinity.reporting.reportingapp1.application.report.dto.CreateReportOutput;
import com.nfinity.reporting.reportingapp1.application.report.dto.FindReportByIdOutput;
import com.nfinity.reporting.reportingapp1.application.report.dto.ShareReportInput;
import com.nfinity.reporting.reportingapp1.application.report.dto.UpdateReportInput;
import com.nfinity.reporting.reportingapp1.domain.dashboard.IDashboardManager;
import com.nfinity.reporting.reportingapp1.domain.dashboardrole.IDashboardroleManager;
import com.nfinity.reporting.reportingapp1.domain.dashboarduser.IDashboarduserManager;
import com.nfinity.reporting.reportingapp1.domain.dashboardversion.IDashboardversionManager;
import com.nfinity.reporting.reportingapp1.domain.dashboardversionreport.IDashboardversionreportManager;
import com.nfinity.reporting.reportingapp1.domain.model.QDashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportuserEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportuserId;
import com.nfinity.reporting.reportingapp1.domain.model.ReportversionEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportversionId;
import com.nfinity.reporting.reportingapp1.domain.model.RoleEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboarduserEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboarduserId;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionId;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionreportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionreportId;
import com.nfinity.reporting.reportingapp1.domain.authorization.user.UserManager;
import com.nfinity.reporting.reportingapp1.domain.authorization.userrole.IUserroleManager;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.domain.model.UserroleEntity;
import com.nfinity.reporting.reportingapp1.domain.report.IReportManager;
import com.nfinity.reporting.reportingapp1.domain.reportversion.IReportversionManager;
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
public class DashboardAppService implements IDashboardAppService {

	static final int case1=1;
	static final int case2=2;
	static final int case3=3;

	@Autowired
	private IDashboardManager _dashboardManager;

	@Autowired
	private DashboardversionreportAppService _reportDashboardAppService;

	@Autowired
	private ReportAppService _reportAppService;

	@Autowired
	private DashboardversionAppService _dashboardversionAppservice;

	@Autowired
	private IDashboardversionMapper dashboardversionMapper;

	@Autowired
	private DashboardroleAppService _dashboardroleAppservice;

	@Autowired
	private DashboarduserAppService _dashboarduserAppservice;

	@Autowired
	private IDashboardversionManager _dashboardversionManager;
	
	@Autowired
	private IReportversionManager _reportversionManager;

	@Autowired
	private IDashboardversionreportManager _reportDashboardManager;

	@Autowired
	private IDashboarduserManager _dashboarduserManager;

	@Autowired
	private IDashboardroleManager _dashboardroleManager;

	@Autowired
	private IUserroleManager _userroleManager;

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
		if(input.getOwnerId()!=null) {
			UserEntity foundUser = _userManager.findById(input.getOwnerId());
			if(foundUser!=null) {
				dashboard.setUser(foundUser);
			}
		}

		DashboardEntity createdDashboard = _dashboardManager.create(dashboard);
		CreateDashboardversionInput dashboardversion = mapper.creatDashboardInputToCreateDashboardversionInput(input);
		dashboardversion.setDashboardId(createdDashboard.getId());

		CreateDashboardversionOutput dashboardversionOutput = _dashboardversionAppservice.create(dashboardversion);

		return mapper.dashboardEntityAndCreateDashboardversionOutputToCreateDashboardOutput(createdDashboard,dashboardversionOutput);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	//	@CacheEvict(value="Dashboard", key = "#p0")
	public UpdateDashboardOutput update(Long  dashboardId, UpdateDashboardInput input) {

		DashboardversionId dashboardversionId = new DashboardversionId(input.getUserId(), dashboardId, "running");

		DashboardversionEntity rv = _dashboardversionManager.findById(dashboardversionId);
		UpdateDashboardversionInput dashboardversion = mapper.updateDashboardInputToUpdateDashboardversionInput(input);
		dashboardversion.setDashboardId(rv.getDashboard().getId());
		dashboardversion.setVersion(rv.getVersion());

		UpdateDashboardversionOutput dashboardversionOutput =  _dashboardversionAppservice.update(dashboardversionId, dashboardversion);

//		List<DashboarduserEntity> dashboarduserList = _dashboarduserManager.findByDashboardId(dashboardId);
//		for(DashboarduserEntity  dashboarduser : dashboarduserList)
//		{
//			dashboarduser.setIsResetted(false);
//			_dashboarduserManager.update(dashboarduser);
//		}
		
		DashboarduserEntity dashboarduser = _dashboarduserManager.findById(new DashboarduserId(dashboardId, input.getUserId()));
		if(dashboarduser !=null)
		{
			dashboarduser.setIsResetted(false);
			_dashboarduserManager.update(dashboarduser);
		}
		

		DashboardEntity foundDashboard = _dashboardManager.findById(dashboardId);
		if(foundDashboard.getUser() !=null && foundDashboard.getUser().getId() == input.getUserId())
		{
			foundDashboard.setIsPublished(false);
			_dashboardManager.update(foundDashboard);
		}

		return mapper.dashboardEntityAndUpdateDashboardversionOutputToUpdateDashboardOutput(foundDashboard,dashboardversionOutput);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	//	@CacheEvict(value="Dashboard", key = "#p0")
	public void delete(Long dashboardId) {

		DashboardEntity existing = _dashboardManager.findById(dashboardId) ; 

		//  _dashboardversionAppservice.delete(new DashboardversionId(existing.getUser().getId(), existing.getId(), "running"));
		// 	_dashboardversionAppservice.delete(new DashboardversionId(existing.getUser().getId(), existing.getId(), "published"));

		List<DashboarduserEntity> reportUserList = _dashboarduserManager.findByDashboardId(existing.getId());
		for(DashboarduserEntity reportuser : reportUserList)
		{
			reportuser.setOwnerSharingStatus(false);
			_dashboarduserManager.update(reportuser);
		}

		existing.setUser(null);
		existing.setIsPublished(true);
		_dashboardManager.update(existing);

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
	//	@Cacheable(value = "Dashboard", key = "#p0")
	public FindDashboardByIdOutput findById(Long dashboardId) {

		DashboardEntity foundDashboard = _dashboardManager.findById(dashboardId);
		if (foundDashboard == null)  
			return null ; 

		FindDashboardByIdOutput output=mapper.dashboardEntityToFindDashboardByIdOutput(foundDashboard); 
		return output;
	}

	public List<FindReportByIdOutput> setReportsList(Long id)
	{
		List<DashboardversionreportEntity> reportDashboardList = _reportDashboardManager.findByDashboardIdAndVersion(id,"running");
		
		List<FindReportByIdOutput> reportDetails = new ArrayList<>();
		for(DashboardversionreportEntity rd : reportDashboardList)
		{
	        ReportversionEntity reportversion = _reportversionManager.findById(new ReportversionId(rd.getUserId(), rd.getReport().getId(), "running"));
			FindReportByIdOutput output= reportMapper.reportEntityToFindReportByIdOutput(rd.getReport(), reportversion); 
			
			reportDetails.add(output);
			
		}

		return reportDetails;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	//	@Cacheable(value = "Dashboard", key = "#p0")
	public FindDashboardByIdOutput findByDashboardIdAndUserId(Long dashboardId, Long userId) {

		DashboardEntity foundDashboard = _dashboardManager.findByDashboardIdAndUserId(dashboardId, userId);
		DashboarduserEntity dashboarduser =  _dashboarduserManager.findById(new DashboarduserId(dashboardId, userId));

		if (foundDashboard == null && dashboardId == null)  
			return null ; 

		DashboardversionEntity dashboardVersion =_dashboardversionManager.findById(new DashboardversionId(userId, dashboardId, "running"));
		FindDashboardByIdOutput output = mapper.dashboardEntitiesToFindDashboardByIdOutput(foundDashboard,dashboardVersion, dashboarduser); 

		DashboardversionEntity publishedversion = _dashboardversionManager.findById(new DashboardversionId(foundDashboard.getUser().getId(), dashboardId, "published"));
		if(publishedversion == null)
		{
			output.setIsResetable(false);
		}
		else 
			output.setIsResetable(true);
		return output;

	}

	//User
	// ReST API Call - GET /dashboard/1/user
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	//	@Cacheable (value = "Dashboard", key="#p0")
	public GetUserOutput getUser(Long dashboardId) {

		DashboardEntity foundDashboard = _dashboardManager.findById(dashboardId);
		if (foundDashboard == null) {
			logHelper.getLogger().error("There does not exist a dashboard wth a id=%s", dashboardId);
			return null;
		}
		UserEntity re = _dashboardManager.getUser(dashboardId);
		return mapper.userEntityToGetUserOutput(re, foundDashboard);
	}

	//User
	// ReST API Call - GET /dashboard/1/user
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	//		@Cacheable (value = "Dashboard", key="#p0")
	public List<GetUserOutput> getUsersAssociatedWithDashboard(Long dashboardId, String search, Pageable pageable) {

		DashboardEntity foundDashboard = _dashboardManager.findById(dashboardId);
		Page<UserEntity> foundUser = _dashboarduserManager.getDashboardUsersList(dashboardId,search,pageable);
		List<UserEntity> userList = foundUser.getContent();
		Iterator<UserEntity> userIterator = userList.iterator();
		List<GetUserOutput> usersList = new ArrayList<>();

		while (userIterator.hasNext()) {
			UserEntity user = userIterator.next();
			usersList.add(mapper.userEntityToGetUserOutput(user, foundDashboard));
		}

		return usersList;
	}

	//User
	// ReST API Call - GET /dashboard/1/user
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	//	@Cacheable (value = "Dashboard", key="#p0")
	public List<GetUserOutput> getAvailableUsers(Long dashboardId, String search, Pageable pageable) {

		DashboardEntity foundDashboard = _dashboardManager.findById(dashboardId);
		Page<UserEntity> foundUser = _dashboarduserManager.getAvailableUsersList(dashboardId,search,pageable);
		List<UserEntity> userList = foundUser.getContent();
		Iterator<UserEntity> userIterator = userList.iterator();
		List<GetUserOutput> usersList = new ArrayList<>();

		while (userIterator.hasNext()) {
			UserEntity user = userIterator.next();
			usersList.add(mapper.userEntityToGetUserOutput(user, foundDashboard));
		}

		return usersList;
	}

	//Role
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	//	@Cacheable (value = "Dashboard", key="#p0")
	public List<GetRoleOutput> getRolesAssociatedWithDashboard(Long dashboardId, String search, Pageable pageable) {

		DashboardEntity foundDashboard = _dashboardManager.findById(dashboardId);
		Page<RoleEntity> foundRole = _dashboardroleManager.getDashboardRolesList(dashboardId,search,pageable);
		List<RoleEntity> roleList = foundRole.getContent();
		Iterator<RoleEntity> roleIterator = roleList.iterator();
		List<GetRoleOutput> rolesList = new ArrayList<>();

		while (roleIterator.hasNext()) {
			RoleEntity role = roleIterator.next();
			rolesList.add(mapper.roleEntityToGetRoleOutput(role, foundDashboard));
		}

		return rolesList;
	}

	//User
	// ReST API Call - GET /dashboard/1/user
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	//	@Cacheable (value = "Dashboard", key="#p0")
	public List<GetRoleOutput> getAvailableRoles(Long dashboardId, String search, Pageable pageable) {

		DashboardEntity foundDashboard = _dashboardManager.findById(dashboardId);
		Page<RoleEntity> foundRole = _dashboardroleManager.getAvailableRolesList(dashboardId,search,pageable);
		List<RoleEntity> roleList = foundRole.getContent();
		Iterator<RoleEntity> roleIterator = roleList.iterator();
		List<GetRoleOutput> rolesList = new ArrayList<>();

		while (roleIterator.hasNext()) {
			RoleEntity role = roleIterator.next();
			rolesList.add(mapper.roleEntityToGetRoleOutput(role, foundDashboard));
		}

		return rolesList;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public DashboardDetailsOutput shareDashboard(Long dashboardId, List<ShareReportInput> usersList, List<ShareReportInput> rolesList) {
		DashboardEntity dashboard = _dashboardManager.findById(dashboardId);
		DashboardversionEntity ownerPublishedVersion = _dashboardversionManager.findById(new DashboardversionId(dashboard.getUser().getId(), dashboard.getId(), "published"));

		List<Long> usersWithSharedReportsByRole = new ArrayList<>();
		for(ShareReportInput roleInput : rolesList)
		{
			CreateDashboardroleInput dashboardRoleInput = new CreateDashboardroleInput();
			dashboardRoleInput.setRoleId(roleInput.getId());
			dashboardRoleInput.setDashboardId(dashboardId);
			dashboardRoleInput.setEditable(roleInput.getEditable());
			_dashboardroleAppservice.create(dashboardRoleInput);

			List<UserroleEntity> userroleList = _userroleManager.findByRoleId(roleInput.getId());
			for(UserroleEntity userrole : userroleList)
			{
				usersWithSharedReportsByRole.add(userrole.getUserId());
				DashboarduserEntity dashboarduser = _dashboarduserManager.findById(new DashboarduserId(dashboardId, userrole.getUserId()));

				if(dashboarduser !=null ) {
					dashboarduser.setEditable(roleInput.getEditable());
					dashboarduser.setIsAssignedByRole(true);
					dashboarduser = _dashboarduserManager.update(dashboarduser);
					shareDashboardWithUser(dashboarduser,ownerPublishedVersion, roleInput.getEditable());

				}

				else {
					createDashboarduserAndDashboardVersion(ownerPublishedVersion,userrole.getUserId(), roleInput.getEditable(),true);

				}
			}

		}

		for(ShareReportInput userInput : usersList)
		{
			if(!usersWithSharedReportsByRole.contains(userInput.getId())) {

				DashboarduserEntity dashboarduser = _dashboarduserManager.findById(new DashboarduserId(dashboardId, userInput.getId()));

				if(dashboarduser !=null ) {
					dashboarduser.setEditable(userInput.getEditable());
					dashboarduser.setIsAssignedByRole(false);
					dashboarduser = _dashboarduserManager.update(dashboarduser);
					shareDashboardWithUser(dashboarduser,ownerPublishedVersion, userInput.getEditable());

				}

				else {
					createDashboarduserAndDashboardVersion(ownerPublishedVersion,userInput.getId(),userInput.getEditable(),false);
				}
			}
		}

		DashboardDetailsOutput dashboardDetails = mapper.dashboardEntitiesToDashboardDetailsOutput(dashboard, ownerPublishedVersion, null);
		dashboardDetails.setSharedWithOthers(true);
		return dashboardDetails;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void createDashboarduserAndDashboardVersion(DashboardversionEntity ownerReportversion, Long userId, Boolean editable, Boolean isAssigByRole)
	{

		CreateDashboarduserInput createDashboarduserInput = new CreateDashboarduserInput();
		createDashboarduserInput.setDashboardId(ownerReportversion.getDashboardId());
		createDashboarduserInput.setUserId(userId);
		createDashboarduserInput.setEditable(editable);
		createDashboarduserInput.setIsAssignedByRole(isAssigByRole);
		createDashboarduserInput.setIsResetted(true);
		createDashboarduserInput.setIsRefreshed(true);
		createDashboarduserInput.setOwnerSharingStatus(true);
		createDashboarduserInput.setRecipientSharingStatus(true);
		_dashboarduserAppservice.create(createDashboarduserInput);

		UserEntity user = _userManager.findById(userId);
		if(editable) {
			DashboardversionEntity publishedDashboardversion = dashboardversionMapper.dashboardversionEntityToDashboardversionEntity(ownerReportversion, user.getId(), "published"); 
			publishedDashboardversion.setUser(user);
			_dashboardversionManager.create(publishedDashboardversion);
			DashboardversionEntity runningDashboardversion = dashboardversionMapper.dashboardversionEntityToDashboardversionEntity(ownerReportversion, user.getId(), "running"); 
			runningDashboardversion.setUser(user);
			_dashboardversionManager.create(runningDashboardversion);
		}
		else {
			DashboardversionEntity runningdashboardversion = dashboardversionMapper.dashboardversionEntityToDashboardversionEntity(ownerReportversion, user.getId(), "running"); 

			runningdashboardversion.setUser(user);
			_dashboardversionManager.create(runningdashboardversion);
		}
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void shareDashboardWithUser(DashboarduserEntity dashboarduser, DashboardversionEntity ownerPublishedVersion, Boolean editable)
	{
		UserEntity user = _userManager.findById(dashboarduser.getUserId());
		DashboardversionEntity dashboardPublishedVersion = _dashboardversionManager.findById(new DashboardversionId(user.getId(),dashboarduser.getDashboardId(),"published"));

		if(dashboarduser.getEditable() && !editable) {

			if(dashboarduser.getIsResetted()) {
				if (dashboardPublishedVersion != null) {
					_dashboardversionManager.delete(dashboardPublishedVersion);	
				}

				DashboardversionEntity publishedVersion = dashboardversionMapper.dashboardversionEntityToDashboardversionEntity(ownerPublishedVersion, user.getId(), "running"); 
				publishedVersion.setUser(user);
				_dashboardversionManager.update(publishedVersion);
			}
			else if(!dashboarduser.getIsResetted()) {
				DashboardversionEntity publishedVersion = dashboardversionMapper.dashboardversionEntityToDashboardversionEntity(ownerPublishedVersion, user.getId(), "published"); 

				publishedVersion.setUser(user);
				_dashboardversionManager.update(publishedVersion);
			}


		} else if(!dashboarduser.getEditable() && !editable) {

			if(dashboardPublishedVersion !=null && !dashboarduser.getIsResetted()) {
				DashboardversionEntity publishedVersion = dashboardversionMapper.dashboardversionEntityToDashboardversionEntity(ownerPublishedVersion, user.getId(), "published"); 
				publishedVersion.setUser(user);
				_dashboardversionManager.update(publishedVersion);
			}
			else if(dashboarduser.getIsResetted()) {
				if (dashboardPublishedVersion != null) {
					_dashboardversionManager.delete(dashboardPublishedVersion);	
				}
				DashboardversionEntity publishedVersion = dashboardversionMapper.dashboardversionEntityToDashboardversionEntity(ownerPublishedVersion, user.getId(), "running"); 

				publishedVersion.setUser(user);
				_dashboardversionManager.update(publishedVersion);
			}


		} else if(dashboarduser.getEditable() && editable) {
			DashboardversionEntity publishedVersion = dashboardversionMapper.dashboardversionEntityToDashboardversionEntity(ownerPublishedVersion, user.getId(), "published"); 
			publishedVersion.setUser(user);
			_dashboardversionManager.update(publishedVersion);

		} else if(!dashboarduser.getEditable() && editable) {

			if(dashboardPublishedVersion !=null && !dashboarduser.getIsResetted()) {
				DashboardversionEntity publishedVersion = dashboardversionMapper.dashboardversionEntityToDashboardversionEntity(ownerPublishedVersion, user.getId(), "published"); 
				publishedVersion.setUser(user);
				_dashboardversionManager.update(publishedVersion);
			}
			else if(dashboarduser.getIsResetted()) {

				if (dashboardPublishedVersion != null) {
					_dashboardversionManager.delete(dashboardPublishedVersion);	
				}

				DashboardversionEntity publishedVersion = dashboardversionMapper.dashboardversionEntityToDashboardversionEntity(ownerPublishedVersion, user.getId(), "published"); 
				publishedVersion.setUser(user);
				_dashboardversionManager.create(publishedVersion);
			}

		}
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Cacheable(value = "Dashboard", key = "#p0")
	public DashboardDetailsOutput unshareDashboard(Long dashboardId, List<ShareReportInput> usersList, List<ShareReportInput> rolesList) {
		DashboardEntity dashboard = _dashboardManager.findById(dashboardId);
		DashboardversionEntity ownerPublishedVersion = _dashboardversionManager.findById(new DashboardversionId(dashboard.getUser().getId(), dashboardId, "published"));
		if(ownerPublishedVersion==null)
		{
			return null;
		}
		List<Long> usersWithSharedDashboardsByRole = new ArrayList<>();
		for(ShareReportInput roleInput : rolesList)
		{	
			List<UserroleEntity> userroleList = _userroleManager.findByRoleId(roleInput.getId());
			for(UserroleEntity userrole : userroleList)
			{
				usersWithSharedDashboardsByRole.add(userrole.getUserId());
				DashboarduserEntity dashboarduser = _dashboarduserManager.findById(new DashboarduserId(dashboardId, userrole.getUserId()));

				if(dashboarduser !=null ) {
					dashboarduser.setOwnerSharingStatus(false);
					dashboarduser = _dashboarduserManager.update(dashboarduser);
				}
			}

		}

		for(ShareReportInput userInput : usersList)
		{
			if(!usersWithSharedDashboardsByRole.contains(userInput.getId())) {

				DashboarduserEntity dashboarduser = _dashboarduserManager.findById(new DashboarduserId(dashboardId, userInput.getId()));

				if(dashboarduser != null ) {
					dashboarduser.setOwnerSharingStatus(false);
					dashboarduser = _dashboarduserManager.update(dashboarduser);
				}
			}
		}

		DashboardDetailsOutput dashboardDetails = mapper.dashboardEntitiesToDashboardDetailsOutput(dashboard, ownerPublishedVersion, null);
		dashboardDetails.setSharedWithOthers(false);
		return dashboardDetails;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<DashboardDetailsOutput> getDashboards(Long userId,String search, Pageable pageable) throws Exception
	{ 
		Page<DashboardDetailsOutput> foundDashboard = _dashboardManager.getDashboards(userId, search, pageable) ;
		List<DashboardDetailsOutput> dashboardList = foundDashboard.getContent();

		return dashboardList ;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<DashboardDetailsOutput> getSharedDashboards(Long userId,String search, Pageable pageable) throws Exception
	{ 
		Page<DashboardDetailsOutput> foundDashboard = _dashboardManager.getSharedDashboards(userId, search, pageable) ;
		List<DashboardDetailsOutput> dashboardList = foundDashboard.getContent();

		return dashboardList ;
	}


	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	//	@Cacheable(value = "Dashboard", key = "#p0")
	public DashboardDetailsOutput updateRecipientSharingStatus (Long userId, Long dashboardId, Boolean status) {

		DashboarduserEntity foundDashboarduser = _dashboarduserManager.findById(new DashboarduserId(dashboardId, userId));
		if(foundDashboarduser ==null)
			return null;
		foundDashboarduser.setRecipientSharingStatus(status);
		foundDashboarduser = _dashboarduserManager.update(foundDashboarduser);

		DashboardversionEntity foundDashboardversion = _dashboardversionManager.findById(new DashboardversionId(userId, dashboardId, "running"));
		DashboardEntity foundDashboard = _dashboardManager.findById(dashboardId);

		return mapper.dashboardEntitiesToDashboardDetailsOutput(foundDashboard,foundDashboardversion,foundDashboarduser);

	}

	//	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	//	@Cacheable(value = "Dashboard", key = "#p0")
	public DashboardDetailsOutput publishDashboard(Long userId, Long dashboardId) {

		DashboardEntity foundDashboard = _dashboardManager.findById(dashboardId);

		List<DashboarduserEntity> dashboarduserList = _dashboarduserManager.findByDashboardId(dashboardId);
		for(DashboarduserEntity dashboarduser: dashboarduserList)
		{
			dashboarduser.setIsRefreshed(false);
			_dashboarduserManager.update(dashboarduser);
		}

		foundDashboard.setIsPublished(true);
		foundDashboard = _dashboardManager.update(foundDashboard);
		DashboardversionEntity foundDashboardversion = _dashboardversionManager.findById(new DashboardversionId(userId, dashboardId, "running"));
		DashboardversionEntity publishedVersion = dashboardversionMapper.dashboardversionEntityToDashboardversionEntity(foundDashboardversion, userId, "published");
		_dashboardversionManager.update(publishedVersion);
		
		return mapper.dashboardEntitiesToDashboardDetailsOutput(foundDashboard,foundDashboardversion,null);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	//	@Cacheable(value = "Dashboard", key = "#p0")
	public DashboardDetailsOutput changeOwner(Long ownerId, Long dashboardId, Long newOwnerId) {

		DashboardEntity foundDashboard = _dashboardManager.findById(dashboardId);
		UserEntity foundUser = _userManager.findById(newOwnerId);

		DashboardversionEntity foundOwnerDashboardRunningversion = _dashboardversionManager.findById(new DashboardversionId(ownerId, dashboardId, "running"));
		DashboardversionEntity foundOwnerDashboardPublishedversion = _dashboardversionManager.findById(new DashboardversionId(ownerId, dashboardId, "published"));
		DashboardversionEntity dashboardRunningversion = dashboardversionMapper.dashboardversionEntityToDashboardversionEntity(foundOwnerDashboardRunningversion, foundUser.getId(), "running");
		dashboardRunningversion.setUser(foundUser);
		//	dashboardRunningversion.setUserId(foundUser.getId());
		_dashboardversionManager.create(dashboardRunningversion);

		DashboardversionEntity dashboardPublishedversion = dashboardversionMapper.dashboardversionEntityToDashboardversionEntity(foundOwnerDashboardPublishedversion, foundUser.getId(), "published");

		dashboardPublishedversion.setUser(foundUser);
		//	dashboardPublishedversion.setUserId(foundUser.getId());
		_dashboardversionManager.create(dashboardPublishedversion);


		//	 _dashboardversionManager.delete(foundOwnerDashboardPublishedversion);
		//	 _dashboardversionManager.delete(foundOwnerDashboardRunningversion);
		_dashboardversionAppservice.delete(new DashboardversionId(ownerId, dashboardId, "running"));
		_dashboardversionAppservice.delete(new DashboardversionId(ownerId, dashboardId, "published"));


		foundDashboard.setUser(foundUser);

		_dashboardManager.update(foundDashboard);


		return mapper.dashboardEntitiesToDashboardDetailsOutput(foundDashboard,dashboardRunningversion,null);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	//	@Cacheable(value = "Dashboard", key = "#p0")
	public DashboardDetailsOutput refreshDashboard(Long userId, Long dashboardId) {

		DashboarduserEntity foundDashboarduser = _dashboarduserManager.findById(new DashboarduserId(dashboardId, userId));
		DashboardEntity foundDashboard = _dashboardManager.findById(dashboardId);
		if(foundDashboarduser.getOwnerSharingStatus()) {
			
			DashboardversionEntity ownerPublishedversion = _dashboardversionManager.findById(new DashboardversionId(foundDashboard.getUser().getId(), dashboardId, "published"));
			UserEntity foundUser = _userManager.findById(userId);
			DashboardversionEntity publishedversion = dashboardversionMapper.dashboardversionEntityToDashboardversionEntity(ownerPublishedversion, userId, "published"); 
			publishedversion.setUser(foundUser);
			_dashboardversionManager.update(publishedversion);
			foundDashboarduser.setIsRefreshed(true);
			foundDashboarduser.setIsResetted(false);
			foundDashboarduser = _dashboarduserManager.update(foundDashboarduser);
			
			return mapper.dashboardEntitiesToDashboardDetailsOutput(foundDashboard,publishedversion,foundDashboarduser);

		}

		return null;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	//	@Cacheable(value = "Dashboard", key = "#p0")
	public DashboardDetailsOutput resetDashboard(Long userId, Long dashboardId) {

		DashboarduserEntity foundDashboarduser = _dashboarduserManager.findById(new DashboarduserId(dashboardId, userId));
		DashboardEntity foundDashboard = _dashboardManager.findById(dashboardId);

		DashboardversionEntity publishedversion = _dashboardversionManager.findById(new DashboardversionId(foundDashboard.getUser().getId(), dashboardId, "published"));
		if(publishedversion !=null)
		{
			DashboardversionEntity runningversion = dashboardversionMapper.dashboardversionEntityToDashboardversionEntity(publishedversion, userId, "running");
			//	runningversion.setVersion("running");
			_dashboardversionManager.update(runningversion);
			if(!foundDashboarduser.getEditable()) {
				_dashboardversionManager.delete(publishedversion);
			}

			foundDashboarduser.setIsResetted(true);
			foundDashboarduser = _dashboarduserManager.update(foundDashboarduser);

			return mapper.dashboardEntitiesToDashboardDetailsOutput(foundDashboard,publishedversion,foundDashboarduser);
		}

		return null;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Cacheable(value = "Dashboard")
	public List<FindDashboardByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception  {

		Page<DashboardEntity> foundDashboard = _dashboardManager.findAll(search(search), pageable);
		List<DashboardEntity> dashboardList = foundDashboard.getContent();
		Iterator<DashboardEntity> dashboardIterator = dashboardList.iterator(); 
		List<FindDashboardByIdOutput> output = new ArrayList<>();

		while (dashboardIterator.hasNext()) {
			DashboardEntity dashboard = dashboardIterator.next();
			DashboardversionEntity dashboardVersion =_dashboardversionManager.findById(new DashboardversionId(dashboard.getUser().getId(),dashboard.getId(), "running"));
			DashboarduserEntity dashboarduser =  _dashboarduserManager.findById(new DashboarduserId(dashboard.getId(), dashboardVersion.getUserId()));

			FindDashboardByIdOutput dashboardOutput  = mapper.dashboardEntitiesToFindDashboardByIdOutput(dashboard, dashboardVersion, dashboarduser); 
			DashboardversionEntity publishedversion = _dashboardversionManager.findById(new DashboardversionId(dashboard.getUser().getId(), dashboard.getId(), "published"));
			if(publishedversion == null)
			{
				dashboardOutput.setIsResetable(false);
			}
			else 
				dashboardOutput.setIsResetable(true);

			output.add(dashboardOutput); 

		}
		return output;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindDashboardByIdOutput addNewReportsToNewDashboard(AddNewReportToNewDashboardInput input)
	{
		//		DashboardEntity dashboard = mapper.createDashboardAndReportInputToDashboardEntity(input);
		//		UserEntity foundUser = null;
		//		if(input.getOwnerId()!=null) {
		//			foundUser = _userManager.findById(input.getOwnerId());
		//			if(foundUser!=null) {
		//				dashboard.setUser(foundUser);
		//			}
		//		}
		//
		//		DashboardEntity createdDashboard = _dashboardManager.create(dashboard);
		//		List<FindReportByIdOutput> reportsOutput =new ArrayList<>();
		//		List<ReportEntity> reportEntities = new ArrayList<>();
		//		for(CreateReportInput report : input.getReportDetails())
		//		{
		//			ReportEntity reportEntity = mapper.createDashboardAndReportInputToReportEntity(report);
		//			if(foundUser!=null) {
		//				reportEntity.setUser(foundUser);
		//			}
		//
		//			ReportEntity createdReport = _reportManager.create(reportEntity);
		//			reportEntities.add(createdReport);
		//			reportsOutput.add(reportMapper.reportEntityToFindReportByIdOutput(createdReport));
		//		}
		//
		//		_reportDashboardAppService.addReportsToDashboard(createdDashboard, reportEntities);
		//
		//		FindDashboardByIdOutput dashboardOuputDto = mapper.dashboardEntityToFindDashboardByIdOutput(createdDashboard);
		//		dashboardOuputDto.setReportDetails(reportsOutput);
		//
		//		return dashboardOuputDto;

		CreateDashboardInput dashboardInput = mapper.addNewReportToNewDashboardInputTocreatDashboardInput(input);
		CreateDashboardOutput createdDashboard = create(dashboardInput);

		List<FindReportByIdOutput> reportsOutput =new ArrayList<>();
		List<CreateReportOutput> reportList = new ArrayList<>();

		for(CreateReportInput report : input.getReportDetails())
		{
			report.setIsPublished(true);
			report.setOwnerId(createdDashboard.getOwnerId());
			CreateReportOutput createdReport = _reportAppService.create(report);
			reportList.add(createdReport);
			reportsOutput.add(reportMapper.createReportOutputToFindReportByIdOutput(createdReport));
		}

		_reportDashboardAppService.addReportsToDashboardRunningversion(createdDashboard, reportList);
		_reportDashboardAppService.addReportsToDashboardPublishedversion(createdDashboard, reportList);

		FindDashboardByIdOutput dashboardOuputDto = mapper.dashboardOutputToFindDashboardByIdOutput(createdDashboard);
		dashboardOuputDto.setReportDetails(reportsOutput);
		dashboardOuputDto.setIsResetable(false);
		return dashboardOuputDto;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindDashboardByIdOutput addNewReportsToExistingDashboard(AddNewReportToExistingDashboardInput input)
	{

		//		DashboardEntity dashboard = _dashboardManager.findById(input.getId());
		//		List<FindReportByIdOutput> reportsOutput =new ArrayList<>();
		//		List<ReportEntity> reportEntities = new ArrayList<>();
		//		for(CreateReportInput report : input.getReportDetails())
		//		{
		//			ReportEntity reportEntity = mapper.createDashboardAndReportInputToReportEntity(report);
		//			reportEntity.setUser(dashboard.getUser());
		//
		//			ReportEntity createdReport = _reportManager.create(reportEntity);
		//			reportEntities.add(createdReport);
		//			reportsOutput.add(reportMapper.reportEntityToFindReportByIdOutput(createdReport));
		//		}
		//
		//		_reportDashboardAppService.addReportsToDashboard(dashboard, reportEntities);
		//
		//		FindDashboardByIdOutput dashboardOuputDto = mapper.dashboardEntityToFindDashboardByIdOutput(dashboard);
		//		dashboardOuputDto.setReportDetails(reportsOutput);
		//
		//		return dashboardOuputDto;

		DashboardEntity dashboard = _dashboardManager.findById(input.getId());
		DashboardversionEntity dashboardversion = _dashboardversionManager.findById(new DashboardversionId(dashboard.getUser().getId(), dashboard.getId(),"running"));

		CreateDashboardOutput createdDashboard = mapper.dashboardEntityAndDashboardversionEntityToCreateDashboardOutput(dashboard, dashboardversion);

		List<FindReportByIdOutput> reportsOutput =new ArrayList<>();
		List<CreateReportOutput> reportList = new ArrayList<>();

		for(CreateReportInput report : input.getReportDetails())
		{
			report.setIsPublished(true);
			report.setOwnerId(createdDashboard.getOwnerId());
			CreateReportOutput createdReport = _reportAppService.create(report);
			reportList.add(createdReport);
			reportsOutput.add(reportMapper.createReportOutputToFindReportByIdOutput(createdReport));
		}

		_reportDashboardAppService.addReportsToDashboardRunningversion(createdDashboard, reportList);
		_reportDashboardAppService.addReportsToDashboardPublishedversion(createdDashboard, reportList);

		FindDashboardByIdOutput dashboardOuputDto = mapper.dashboardOutputToFindDashboardByIdOutput(createdDashboard);
		dashboardOuputDto.setReportDetails(reportsOutput);
		dashboardOuputDto.setIsResetable(true);
		
		return dashboardOuputDto;

	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindDashboardByIdOutput addExistingReportsToNewDashboard(AddExistingReportToNewDashboardInput input)
	{
//		DashboardEntity dashboard = mapper.addExistingReportToNewDashboardInputToDashboardEntity(input);
//		UserEntity foundUser = null;
//		if(input.getUserId()!=null) {
//			foundUser = _userManager.findById(input.getUserId());
//			if(foundUser!=null) {
//				dashboard.setUser(foundUser);
//			}
//		}
//
//		DashboardEntity createdDashboard = _dashboardManager.create(dashboard);
//		List<FindReportByIdOutput> reportsOutput =new ArrayList<>();
//		List<ReportEntity> reportEntities = new ArrayList<>();
//		for(UpdateReportInput report : input.getReportDetails())
//		{
//			ReportEntity reportEntity = _reportManager.findById(report.getId());
//			reportEntities.add(reportEntity);
//			reportsOutput.add(reportMapper.reportEntityToFindReportByIdOutput(reportEntity));
//		}
//
//		_reportDashboardAppService.addReportsToDashboard(createdDashboard, reportEntities);
//
//		FindDashboardByIdOutput dashboardOuputDto = mapper.dashboardEntityToFindDashboardByIdOutput(createdDashboard);
//		dashboardOuputDto.setReportDetails(reportsOutput);
//
//		return dashboardOuputDto;
		
		CreateDashboardInput dashboardInput = mapper.addExistingReportToNewDashboardInputTocreatDashboardInput(input);
		CreateDashboardOutput createdDashboard = create(dashboardInput);
		
		List<FindReportByIdOutput> reportsOutput =new ArrayList<>();
		List<CreateReportOutput> reportList = new ArrayList<>();
		for(UpdateReportInput report : input.getReportDetails())
		{
			ReportEntity reportEntity = _reportManager.findById(report.getId());
			ReportversionEntity reportversionEntity = _reportversionManager.findById(new ReportversionId(reportEntity.getUser().getId(),reportEntity.getId(),"running"));
			
			CreateReportOutput reportOutput = reportMapper.reportEntityAndReportversionEntityToCreateReportOutput(reportEntity, reportversionEntity);
			reportList.add(reportOutput);
			reportsOutput.add(reportMapper.createReportOutputToFindReportByIdOutput(reportOutput));
		}

		_reportDashboardAppService.addReportsToDashboardRunningversion(createdDashboard, reportList);
		_reportDashboardAppService.addReportsToDashboardPublishedversion(createdDashboard, reportList);

		FindDashboardByIdOutput dashboardOuputDto = mapper.dashboardOutputToFindDashboardByIdOutput(createdDashboard);
		dashboardOuputDto.setReportDetails(reportsOutput);
		dashboardOuputDto.setIsResetable(false);

		return dashboardOuputDto;

	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindDashboardByIdOutput addExistingReportsToExistingDashboard(AddExistingReportToExistingDashboardInput input)
	{
//		DashboardEntity dashboard = _dashboardManager.findById(input.getId());
//		List<ReportEntity> reportEntities = new ArrayList<>();
//		List<FindReportByIdOutput> reportsOutput =new ArrayList<>();
//		for(UpdateReportInput report : input.getReportDetails())
//		{
//			ReportEntity reportEntity = _reportManager.findById(report.getId());
//			
//			reportEntities.add(reportEntity);
//			reportsOutput.add(reportMapper.reportEntityToFindReportByIdOutput(reportEntity));
//		}
//
//		_reportDashboardAppService.addReportsToDashboard(dashboard, reportEntities);
//
//		FindDashboardByIdOutput dashboardOuputDto = mapper.dashboardEntityToFindDashboardByIdOutput(dashboard);
//		dashboardOuputDto.setReportDetails(reportsOutput);
//
//		return dashboardOuputDto;
		
		DashboardEntity dashboard = _dashboardManager.findById(input.getId());
		DashboardversionEntity dashboardversion = _dashboardversionManager.findById(new DashboardversionId(dashboard.getUser().getId(), dashboard.getId(),"running"));

		CreateDashboardOutput createdDashboard = mapper.dashboardEntityAndDashboardversionEntityToCreateDashboardOutput(dashboard, dashboardversion);

		List<FindReportByIdOutput> reportsOutput =new ArrayList<>();
		List<CreateReportOutput> reportList = new ArrayList<>();
		for(UpdateReportInput report : input.getReportDetails())
		{
			ReportEntity reportEntity = _reportManager.findById(report.getId());
			ReportversionEntity reportversionEntity = _reportversionManager.findById(new ReportversionId(reportEntity.getUser().getId(),reportEntity.getId(),"running"));
			
			CreateReportOutput reportOutput = reportMapper.reportEntityAndReportversionEntityToCreateReportOutput(reportEntity, reportversionEntity);
			reportList.add(reportOutput);
			reportsOutput.add(reportMapper.createReportOutputToFindReportByIdOutput(reportOutput));
		}

		_reportDashboardAppService.addReportsToDashboardRunningversion(createdDashboard, reportList);
		_reportDashboardAppService.addReportsToDashboardPublishedversion(createdDashboard, reportList);

		FindDashboardByIdOutput dashboardOuputDto = mapper.dashboardOutputToFindDashboardByIdOutput(createdDashboard);
		dashboardOuputDto.setReportDetails(reportsOutput);
		dashboardOuputDto.setIsResetable(true);
	//------	
		dashboard.setIsPublished(false);
		_dashboardManager.update(dashboard);
		
	//	DashboarduserEntity dashboarduser = _dashboarduserManager.findByDashboardId(new DashboarduserId());
		
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


