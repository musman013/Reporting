package com.nfinity.reporting.reportingapp1.application.report;

import com.nfinity.reporting.reportingapp1.application.reportuser.IReportuserAppService;
import com.nfinity.reporting.reportingapp1.application.reportuser.dto.CreateReportuserInput;
import com.nfinity.reporting.reportingapp1.application.reportversion.IReportversionMapper;
import com.nfinity.reporting.reportingapp1.application.reportversion.ReportversionAppService;
import com.nfinity.reporting.reportingapp1.application.reportversion.dto.CreateReportversionInput;
import com.nfinity.reporting.reportingapp1.application.reportversion.dto.CreateReportversionOutput;
import com.nfinity.reporting.reportingapp1.application.reportversion.dto.UpdateReportversionInput;
import com.nfinity.reporting.reportingapp1.application.reportversion.dto.UpdateReportversionOutput;
import com.nfinity.reporting.reportingapp1.application.report.dto.*;
import com.nfinity.reporting.reportingapp1.application.reportrole.IReportroleAppService;
import com.nfinity.reporting.reportingapp1.application.reportrole.dto.CreateReportroleInput;
import com.nfinity.reporting.reportingapp1.domain.report.IReportManager;
import com.nfinity.reporting.reportingapp1.domain.reportrole.IReportroleManager;
import com.nfinity.reporting.reportingapp1.domain.reportuser.IReportuserManager;
import com.nfinity.reporting.reportingapp1.domain.reportversion.IReportversionManager;
import com.nfinity.reporting.reportingapp1.domain.model.QReportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportuserEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportuserId;
import com.nfinity.reporting.reportingapp1.domain.model.ReportversionEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportversionId;
import com.nfinity.reporting.reportingapp1.domain.model.RoleEntity;
import com.nfinity.reporting.reportingapp1.domain.authorization.user.UserManager;
import com.nfinity.reporting.reportingapp1.domain.authorization.userrole.UserroleManager;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.domain.model.UserroleEntity;
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
public class ReportAppService implements IReportAppService {

	static final int case1=1;
	static final int case2=2;
	static final int case3=3;

	@Autowired
	private IReportManager _reportManager;

	@Autowired
	private IReportuserManager _reportuserManager;

	@Autowired
	private IReportversionManager _reportversionManager;

	@Autowired
	private UserManager _userManager;

	@Autowired
	private ReportversionAppService _reportversionAppservice;

	@Autowired
	private UserroleManager _userroleManager;

	@Autowired
	private IReportuserAppService _reportuserAppservice;

	@Autowired
	private IReportroleAppService _reportroleAppservice;

	@Autowired
	private IReportroleManager _reportroleManager;

	@Autowired
	private ReportMapper mapper;

	@Autowired
	private IReportversionMapper reportversionMapper;

	@Autowired
	private LoggingHelper logHelper;

	@Transactional(propagation = Propagation.REQUIRED)
	public CreateReportOutput create(CreateReportInput input) {

		ReportEntity report = mapper.createReportInputToReportEntity(input);
		if(input.getOwnerId()!=null) {
			UserEntity foundUser = _userManager.findById(input.getOwnerId());
			if(foundUser!=null) {
				report.setUser(foundUser);
			}
		}

		ReportEntity createdReport = _reportManager.create(report);
		CreateReportversionInput reportversion = mapper.createReportInputToCreateReportversionInput(input);
		reportversion.setReportId(createdReport.getId());

		CreateReportversionOutput reportversionOutput = _reportversionAppservice.create(reportversion);

		return mapper.reportEntityAndCreateReportversionOutputToCreateReportOutput(createdReport, reportversionOutput);
	}

	@Transactional(propagation = Propagation.REQUIRED)
//	@CacheEvict(value="Report", key = "#p0")
	public UpdateReportOutput update(Long  reportId, UpdateReportInput input) {

	//	ReportEntity report = mapper.updateReportInputToReportEntity(input);
		
		ReportversionId reportversionId = new ReportversionId(input.getUserId(), reportId, "running");

		ReportversionEntity rv = _reportversionManager.findById(reportversionId);
		UpdateReportversionInput reportversion = mapper.updateReportInputToUpdateReportversionInput(input);
		reportversion.setReportId(rv.getReport().getId());
		reportversion.setVersion(rv.getVersion());
		
		UpdateReportversionOutput reportversionOutput =  _reportversionAppservice.update(reportversionId, reportversion);

//		List<ReportuserEntity> reportuserList = _reportuserManager.findByReportId(reportId);
//		for(ReportuserEntity  reportuser : reportuserList)
//		{
//			reportuser.setIsResetted(false);
//			_reportuserManager.update(reportuser);
//		}
		
		ReportuserEntity reportuser = _reportuserManager.findById(new ReportuserId(reportId, input.getUserId()));
		if(reportuser !=null)
		{
			reportuser.setIsResetted(false);
			_reportuserManager.update(reportuser);
		}
		
		ReportEntity foundReport = _reportManager.findById(reportId);
		if(foundReport.getUser() !=null && foundReport.getUser().getId() == input.getUserId())
		{
			foundReport.setIsPublished(false);
			_reportManager.update(foundReport);
		}
		

		return mapper.reportEntityAndUpdateReportversionOutputToUpdateReportOutput(foundReport,reportversionOutput);
	
	}

	@Transactional(propagation = Propagation.REQUIRED)
//	@CacheEvict(value="Report", key = "#p0")
	public void delete(Long reportId, Long userId) {

		ReportEntity existing = _reportManager.findById(reportId) ; 
		
		_reportversionAppservice.delete(new ReportversionId(userId, reportId, "running"));
		_reportversionAppservice.delete(new ReportversionId(userId, reportId, "published"));
	
		List<ReportuserEntity> reportUserList = _reportuserManager.findByReportId(existing.getId());
		for(ReportuserEntity reportuser : reportUserList)
		{
			reportuser.setOwnerSharingStatus(false);
			_reportuserManager.update(reportuser);
		}
	
		existing.setUser(null);
		existing.setIsPublished(true);
		_reportManager.update(existing);

	}

	//	@Transactional(propagation = Propagation.REQUIRED)
	//	@CacheEvict(value="Report", key = "#p0")
	//	public void delete(Long reportId, Long userId) {
	//
	//		ReportEntity existing = _reportManager.findByReportIdAndUserId(reportId, userId);
	//		_reportManager.delete(existing);
	//		
	//	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
//	@Cacheable(value = "Report", key = "#p0")
	public FindReportByIdOutput findById(Long reportId) {

		ReportEntity foundReport = _reportManager.findById(reportId);
		if (foundReport == null)  
			return null ; 

		ReportversionEntity reportversion = _reportversionManager.findById(new ReportversionId(foundReport.getUser().getId(), foundReport.getId(), "running"));
		
		FindReportByIdOutput output=mapper.reportEntityToFindReportByIdOutput(foundReport,reportversion); 
		return output;
	}
	//User
	// ReST API Call - GET /report/1/user
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
//	@Cacheable (value = "Report", key="#p0")
	public GetUserOutput getUser(Long reportId) {

		ReportEntity foundReport = _reportManager.findById(reportId);
		if (foundReport == null) {
			logHelper.getLogger().error("There does not exist a report with a id=%s", reportId);
			return null;
		}
		UserEntity re = _reportManager.getUser(reportId);
		return mapper.userEntityToGetUserOutput(re, foundReport);
	}

	//User
	// ReST API Call - GET /report/1/user
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
//	@Cacheable (value = "Report", key="#p0")
	public List<GetUserOutput> getUsersAssociatedWithReport(Long reportId, String search, Pageable pageable) {

		ReportEntity foundReport = _reportManager.findById(reportId);
		Page<UserEntity> foundUser = _reportuserManager.getReportUsersList(reportId,search,pageable);
		List<UserEntity> userList = foundUser.getContent();
		Iterator<UserEntity> userIterator = userList.iterator();
		List<GetUserOutput> usersList = new ArrayList<>();

		while (userIterator.hasNext()) {
			UserEntity user = userIterator.next();
			usersList.add(mapper.userEntityToGetUserOutput(user, foundReport));
		}

		return usersList;
	}

	//User
	// ReST API Call - GET /report/1/user
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
//	@Cacheable (value = "Report", key="#p0")
	public List<GetUserOutput> getAvailableUsers(Long reportId, String search, Pageable pageable) {

		ReportEntity foundReport = _reportManager.findById(reportId);
		Page<UserEntity> foundUser = _reportuserManager.getAvailableUsersList(reportId,search,pageable);
		List<UserEntity> userList = foundUser.getContent();
		Iterator<UserEntity> userIterator = userList.iterator();
		List<GetUserOutput> usersList = new ArrayList<>();

		while (userIterator.hasNext()) {
			UserEntity user = userIterator.next();
			usersList.add(mapper.userEntityToGetUserOutput(user, foundReport));
		}

		return usersList;
	}

	//Role
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
//	@Cacheable (value = "Report", key="#p0")
	public List<GetRoleOutput> getRolesAssociatedWithReport(Long reportId, String search, Pageable pageable) {

		ReportEntity foundReport = _reportManager.findById(reportId);
		Page<RoleEntity> foundRole = _reportroleManager.getReportRolesList(reportId,search,pageable);
		List<RoleEntity> roleList = foundRole.getContent();
		Iterator<RoleEntity> roleIterator = roleList.iterator();
		List<GetRoleOutput> rolesList = new ArrayList<>();

		while (roleIterator.hasNext()) {
			RoleEntity role = roleIterator.next();
			rolesList.add(mapper.roleEntityToGetRoleOutput(role, foundReport));
		}

		return rolesList;
	}

	//User
	// ReST API Call - GET /report/1/user
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
//	@Cacheable (value = "Report", key="#p0")
	public List<GetRoleOutput> getAvailableRoles(Long reportId, String search, Pageable pageable) {

		ReportEntity foundReport = _reportManager.findById(reportId);
		Page<RoleEntity> foundRole = _reportroleManager.getAvailableRolesList(reportId,search,pageable);
		List<RoleEntity> roleList = foundRole.getContent();
		Iterator<RoleEntity> roleIterator = roleList.iterator();
		List<GetRoleOutput> rolesList = new ArrayList<>();

		while (roleIterator.hasNext()) {
			RoleEntity role = roleIterator.next();
			rolesList.add(mapper.roleEntityToGetRoleOutput(role, foundReport));
		}

		return rolesList;

	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
//	@Cacheable(value = "Report", key = "#p0")
	public FindReportByIdOutput findByReportIdAndUserId(Long reportId, Long userId) {

		ReportEntity foundReport = _reportManager.findByReportIdAndUserId(reportId, userId);
		ReportuserEntity reportuser =  _reportuserManager.findById(new ReportuserId(reportId, userId));

		if (foundReport == null && reportuser == null )  {
			return null;
		}

		ReportversionEntity reportVersion =_reportversionManager.findById(new ReportversionId(userId, reportId, "running"));
		FindReportByIdOutput output  = mapper.reportEntitiesToFindReportByIdOutput(foundReport, reportVersion, reportuser); 
		ReportversionEntity publishedversion = _reportversionManager.findById(new ReportversionId(userId, reportId, "published"));

		
		if(reportuser != null)
		{
			output.setSharedWithMe(true);
		}
		
		List<ReportuserEntity> reportuserList = _reportuserManager.findByReportId(reportId);
		if(reportuserList !=null && !reportuserList.isEmpty()) {
			output.setSharedWithOthers(true);
		}
		if(publishedversion == null)
		{
			output.setIsResetable(false);
		}
		else 
			output.setIsResetable(true);
		return output;

	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
//	@Cacheable(value = "Report", key = "#p0")
	public ReportDetailsOutput updateRecipientSharingStatus (Long userId, Long reportId, Boolean status) {

		ReportuserEntity foundReportuser = _reportuserManager.findById(new ReportuserId(reportId, userId));
        if(foundReportuser ==null)
        	return null;
		foundReportuser.setRecipientSharingStatus(status);
		foundReportuser = _reportuserManager.update(foundReportuser);

		ReportversionEntity foundReportversion = _reportversionManager.findById(new ReportversionId(userId, reportId, "running"));
		ReportEntity foundReport = _reportManager.findById(reportId);

		return mapper.reportEntitiesToReportDetailsOutput(foundReport,foundReportversion,foundReportuser);

	}

//	@Transactional(propagation = Propagation.NOT_SUPPORTED)
//	@Cacheable(value = "Report", key = "#p0")
	public ReportDetailsOutput publishReport(Long userId, Long reportId) {

		ReportEntity foundReport = _reportManager.findById(reportId);
		
		List<ReportuserEntity> reportuserList = _reportuserManager.findByReportId(reportId);
		for(ReportuserEntity reportuser: reportuserList)
		{
			reportuser.setIsRefreshed(false);
			_reportuserManager.update(reportuser);
		}
		
		foundReport.setIsPublished(true);
		foundReport = _reportManager.update(foundReport);
		ReportversionEntity foundReportversion = _reportversionManager.findById(new ReportversionId(userId, reportId, "running"));
        
		ReportversionEntity publishedVersion = reportversionMapper.reportversionEntityToReportversionEntity(foundReportversion, userId, "published");
		_reportversionManager.update(publishedVersion);
		
		return mapper.reportEntitiesToReportDetailsOutput(foundReport,foundReportversion,null);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
//	@Cacheable(value = "Report", key = "#p0")
	public ReportDetailsOutput changeOwner(Long ownerId, Long reportId, Long newOwnerId) {

		ReportEntity foundReport = _reportManager.findById(reportId);
		UserEntity foundUser = _userManager.findById(newOwnerId);

		ReportversionEntity foundOwnerReportRunningversion = _reportversionManager.findById(new ReportversionId(ownerId, reportId, "running"));
		ReportversionEntity foundOwnerReportPublishedversion = _reportversionManager.findById(new ReportversionId(ownerId, reportId, "published"));
		ReportversionEntity reportRunningversion = reportversionMapper.reportversionEntityToReportversionEntity(foundOwnerReportRunningversion, foundUser.getId(), "running");
		reportRunningversion.setUser(foundUser);
	   _reportversionManager.create(reportRunningversion);

	    ReportversionEntity reportPublishedversion = reportversionMapper.reportversionEntityToReportversionEntity(foundOwnerReportPublishedversion, foundUser.getId(), "published");
		
		reportPublishedversion.setUser(foundUser);
		 _reportversionManager.create(reportPublishedversion);

	
		_reportversionAppservice.delete(new ReportversionId(ownerId, reportId, "running"));
	    _reportversionAppservice.delete(new ReportversionId(ownerId, reportId, "published"));
				

	    foundReport.setUser(foundUser);
		
		 _reportManager.update(foundReport);
		 
		
		return mapper.reportEntitiesToReportDetailsOutput(foundReport,reportRunningversion,null);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
//	@Cacheable(value = "Report", key = "#p0")
	public ReportDetailsOutput refreshReport(Long userId, Long reportId) {

		ReportuserEntity foundReportuser = _reportuserManager.findById(new ReportuserId(reportId, userId));
		ReportEntity foundReport = _reportManager.findById(reportId);
		
		if(foundReportuser.getOwnerSharingStatus()) {
			
			ReportversionEntity ownerPublishedversion = _reportversionManager.findById(new ReportversionId(foundReport.getUser().getId(), reportId, "published"));
			UserEntity foundUser = _userManager.findById(userId);
			
			ReportversionEntity publishedversion = reportversionMapper.reportversionEntityToReportversionEntity(ownerPublishedversion, userId, "published"); 
			publishedversion.setUser(foundUser);
			_reportversionManager.update(publishedversion);
			foundReportuser.setIsRefreshed(true);
			foundReportuser.setIsResetted(false);
			foundReportuser = _reportuserManager.update(foundReportuser);
			
			ReportversionEntity runningversion = _reportversionManager.findById(new ReportversionId(userId, reportId, "running"));
			return mapper.reportEntitiesToReportDetailsOutput(foundReport,runningversion,foundReportuser);
		}

		return null;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
//	@Cacheable(value = "Report", key = "#p0")
	public ReportDetailsOutput resetReport(Long userId, Long reportId) {

		ReportuserEntity foundReportuser = _reportuserManager.findById(new ReportuserId(reportId, userId));
		ReportEntity foundReport = _reportManager.findById(reportId);


		ReportversionEntity publishedversion = _reportversionManager.findById(new ReportversionId(foundReport.getUser().getId(), reportId, "published"));
		if(publishedversion !=null)
		{
			ReportversionEntity runningversion = reportversionMapper.reportversionEntityToReportversionEntity(publishedversion, userId, "running");
		//	runningversion.setVersion("running");
			runningversion=_reportversionManager.update(runningversion);
			if(!foundReportuser.getEditable()) {
				_reportversionManager.delete(publishedversion);
			}

			foundReportuser.setIsResetted(true);
			foundReportuser = _reportuserManager.update(foundReportuser);

			return mapper.reportEntitiesToReportDetailsOutput(foundReport,runningversion,foundReportuser);
		}

		return null;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public ReportDetailsOutput shareReport(Long reportId, List<ShareReportInput> usersList, List<ShareReportInput> rolesList) {
		ReportEntity report = _reportManager.findById(reportId);
		ReportversionEntity ownerPublishedVersion = _reportversionManager.findById(new ReportversionId(report.getUser().getId(), report.getId(), "published"));

		List<Long> usersWithSharedReportsByRole = new ArrayList<>();
		for(ShareReportInput roleInput : rolesList)
		{
			CreateReportroleInput reportRoleInput = new CreateReportroleInput();
			reportRoleInput.setRoleId(roleInput.getId());
			reportRoleInput.setReportId(reportId);
			reportRoleInput.setEditable(roleInput.getEditable());
			_reportroleAppservice.create(reportRoleInput);

			List<UserroleEntity> userroleList = _userroleManager.findByRoleId(roleInput.getId());
			for(UserroleEntity userrole : userroleList)
			{
				usersWithSharedReportsByRole.add(userrole.getUserId());
				ReportuserEntity reportuser = _reportuserManager.findById(new ReportuserId(reportId, userrole.getUserId()));

				if(reportuser !=null ) {
					reportuser.setEditable(roleInput.getEditable());
					reportuser.setIsAssignedByRole(true);
					reportuser = _reportuserManager.update(reportuser);
					shareReportWithUser(reportuser,ownerPublishedVersion, roleInput.getEditable());

				}

				else {
					createReportuserAndReportVersion(ownerPublishedVersion,userrole.getUserId(), roleInput.getEditable(),true);
					//					CreateReportuserInput createReportuserInput = new CreateReportuserInput();
					//					createReportuserInput.setReportId(reportId);
					//					createReportuserInput.setUserId(userrole.getUserId());
					//					createReportuserInput.setEditable(roleInput.getEditable());
					//					createReportuserInput.setIsAssignedByRole(true);
					//					createReportuserInput.setIsResetted(true);
					//					createReportuserInput.setIsRefreshed(true);
					//					createReportuserInput.setOwnerSharingStatus(true);
					//					createReportuserInput.setRecipientSharingStatus(true);
					//
					//					_reportuserAppservice.create(createReportuserInput);


				}
			}
			
			

		}

		for(ShareReportInput userInput : usersList)
		{
			if(!usersWithSharedReportsByRole.contains(userInput.getId())) {

				ReportuserEntity reportuser = _reportuserManager.findById(new ReportuserId(reportId, userInput.getId()));

				if(reportuser !=null ) {
					reportuser.setEditable(userInput.getEditable());
					reportuser.setIsAssignedByRole(false);
					reportuser = _reportuserManager.update(reportuser);
					shareReportWithUser(reportuser,ownerPublishedVersion, userInput.getEditable());

				}

				else {
					createReportuserAndReportVersion(ownerPublishedVersion,userInput.getId(),userInput.getEditable(),false);
				}
			}
		}

		ReportDetailsOutput reportDetails = mapper.reportEntitiesToReportDetailsOutput(report, ownerPublishedVersion, null);
		reportDetails.setSharedWithOthers(true);
		return reportDetails;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void createReportuserAndReportVersion(ReportversionEntity ownerReportversion, Long userId, Boolean editable, Boolean isAssigByRole)
	{

		CreateReportuserInput createReportuserInput = new CreateReportuserInput();
		createReportuserInput.setReportId(ownerReportversion.getReportId());
		createReportuserInput.setUserId(userId);
		createReportuserInput.setEditable(editable);
		createReportuserInput.setIsAssignedByRole(isAssigByRole);
		createReportuserInput.setIsResetted(true);
		createReportuserInput.setIsRefreshed(true);
		createReportuserInput.setOwnerSharingStatus(true);
		createReportuserInput.setRecipientSharingStatus(true);
		_reportuserAppservice.create(createReportuserInput);

		UserEntity user = _userManager.findById(userId);
		if(editable) {
			ReportversionEntity publishedreportversion = reportversionMapper.reportversionEntityToReportversionEntity(ownerReportversion, user.getId(), "published"); 
			publishedreportversion.setUser(user);
			_reportversionManager.create(publishedreportversion);
			ReportversionEntity runningreportversion = reportversionMapper.reportversionEntityToReportversionEntity(ownerReportversion, user.getId(), "running"); 
			runningreportversion.setUser(user);
			_reportversionManager.create(runningreportversion);
		}
		else {
			ReportversionEntity runningreportversion = reportversionMapper.reportversionEntityToReportversionEntity(ownerReportversion, user.getId(), "running"); 

			runningreportversion.setUser(user);
			_reportversionManager.create(runningreportversion);
		}
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void shareReportWithUser(ReportuserEntity reportuser, ReportversionEntity ownerPublishedVersion, Boolean editable)
	{
		UserEntity user = _userManager.findById(reportuser.getUserId());

		ReportversionEntity reportPublishedVersion = _reportversionManager.findById(new ReportversionId(user.getId(),reportuser.getReportId(),"published"));

		if(reportuser.getEditable() && !editable) {

			if(reportuser.getIsResetted()) {
				if (reportPublishedVersion != null) {
					_reportversionManager.delete(reportPublishedVersion);	
				}
				
				ReportversionEntity publishedVersion = reportversionMapper.reportversionEntityToReportversionEntity(ownerPublishedVersion, user.getId(), "running"); 
				publishedVersion.setUser(user);
				_reportversionManager.update(publishedVersion);
			}
			else if(!reportuser.getIsResetted()) {
				ReportversionEntity publishedVersion = reportversionMapper.reportversionEntityToReportversionEntity(ownerPublishedVersion, user.getId(), "published"); 
				
				publishedVersion.setUser(user);
				_reportversionManager.update(publishedVersion);
			}


		} else if(!reportuser.getEditable() && !editable) {

			if(reportPublishedVersion !=null && !reportuser.getIsResetted()) {
				ReportversionEntity publishedVersion = reportversionMapper.reportversionEntityToReportversionEntity(ownerPublishedVersion, user.getId(), "published"); 
				publishedVersion.setUser(user);
				_reportversionManager.update(publishedVersion);
			}
			else if(reportuser.getIsResetted()) {
				if (reportPublishedVersion != null) {
					_reportversionManager.delete(reportPublishedVersion);	
				}
				ReportversionEntity publishedVersion = reportversionMapper.reportversionEntityToReportversionEntity(ownerPublishedVersion, user.getId(), "running"); 
				
				publishedVersion.setUser(user);
				_reportversionManager.update(publishedVersion);
			}


		} else if(reportuser.getEditable() && editable) {
			//	ReportversionEntity ownerPublishedVersion = _reportversionManager.findById(new ReportversionId(report.getUser().getId(), report.getId(), "published"));
			ReportversionEntity publishedVersion = reportversionMapper.reportversionEntityToReportversionEntity(ownerPublishedVersion, user.getId(), "published"); 
			
			publishedVersion.setUser(user);
			//publishedVersion.setUserId(user.getId());
			_reportversionManager.update(publishedVersion);

		} else if(!reportuser.getEditable() && editable) {

			if(reportPublishedVersion !=null && !reportuser.getIsResetted()) {
				//		ReportversionEntity ownerPublishedVersion = _reportversionManager.findById(new ReportversionId(report.getUser().getId(), report.getId(), "published"));
				ReportversionEntity publishedVersion = reportversionMapper.reportversionEntityToReportversionEntity(ownerPublishedVersion, user.getId(), "published"); 
				
				publishedVersion.setUser(user);
				_reportversionManager.update(publishedVersion);
			}
			else if(reportuser.getIsResetted()) {
				if (reportPublishedVersion != null) {
					_reportversionManager.delete(reportPublishedVersion);	
				}
				//		ReportversionEntity ownerPublishedVersion = _reportversionManager.findById(new ReportversionId(report.getUser().getId(), report.getId(), "published"));
				ReportversionEntity publishedVersion = reportversionMapper.reportversionEntityToReportversionEntity(ownerPublishedVersion, user.getId(), "published"); 
				publishedVersion.setUser(user);
			//	publishedVersion.setUserId(user.getId());
				_reportversionManager.create(publishedVersion);
			}

		}
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Cacheable(value = "Report", key = "#p0")
	public ReportDetailsOutput unshareReport(Long reportId, List<ShareReportInput> usersList, List<ShareReportInput> rolesList) {
		ReportEntity report = _reportManager.findById(reportId);
		ReportversionEntity ownerPublishedVersion = _reportversionManager.findById(new ReportversionId(report.getUser().getId(), reportId, "published"));
        if(ownerPublishedVersion==null)
        {
        	return null;
        }
		List<Long> usersWithSharedReportsByRole = new ArrayList<>();
		for(ShareReportInput roleInput : rolesList)
		{	
			List<UserroleEntity> userroleList = _userroleManager.findByRoleId(roleInput.getId());
			for(UserroleEntity userrole : userroleList)
			{
				usersWithSharedReportsByRole.add(userrole.getUserId());
				ReportuserEntity reportuser = _reportuserManager.findById(new ReportuserId(reportId, userrole.getUserId()));

				if(reportuser !=null ) {
					reportuser.setOwnerSharingStatus(false);
					reportuser = _reportuserManager.update(reportuser);
				}
				//				else {
				//                     return null;
				//				}
			}

		}

		for(ShareReportInput userInput : usersList)
		{
			if(!usersWithSharedReportsByRole.contains(userInput.getId())) {

				ReportuserEntity reportuser = _reportuserManager.findById(new ReportuserId(reportId, userInput.getId()));

				if(reportuser != null ) {
					reportuser.setOwnerSharingStatus(false);
					reportuser = _reportuserManager.update(reportuser);
				}
				//				else {
				//					return null;
				//				}
			}
		}

		ReportDetailsOutput reportDetails = mapper.reportEntitiesToReportDetailsOutput(report, ownerPublishedVersion, null);
		reportDetails.setSharedWithOthers(false);
		return reportDetails;
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<ReportDetailsOutput> getReports(Long userId,String search, Pageable pageable) throws Exception
	{ 
		Page<ReportDetailsOutput> foundReport = _reportManager.getReports(userId, search, pageable) ;
		List<ReportDetailsOutput> reportList = foundReport.getContent();

		return reportList ;
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<ReportDetailsOutput> getSharedReports(Long userId,String search, Pageable pageable) throws Exception
	{ 
		Page<ReportDetailsOutput> foundReport = _reportManager.getSharedReports(userId, search, pageable) ;
		List<ReportDetailsOutput> reportList = foundReport.getContent();

		return reportList ;
	}


	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Cacheable(value = "Report")
	public List<FindReportByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception  {

		Page<ReportEntity> foundReport = _reportManager.findAll(search(search), pageable);
		List<ReportEntity> reportList = foundReport.getContent();
		Iterator<ReportEntity> reportIterator = reportList.iterator(); 
		List<FindReportByIdOutput> output = new ArrayList<>();

		while (reportIterator.hasNext()) {
			ReportEntity report = reportIterator.next();
			ReportversionEntity reportVersion =_reportversionManager.findById(new ReportversionId(report.getUser().getId(),report.getId(), "running"));
			ReportuserEntity reportuser =  _reportuserManager.findById(new ReportuserId(report.getId(), reportVersion.getUserId()));

			FindReportByIdOutput reportOutput  = mapper.reportEntitiesToFindReportByIdOutput(report, reportVersion, reportuser); 
			ReportversionEntity publishedversion = _reportversionManager.findById(new ReportversionId(report.getUser().getId(), report.getId(), "published"));
			if(publishedversion == null)
			{
				reportOutput.setIsResetable(false);
			}
			else 
				reportOutput.setIsResetable(true);

			output.add(reportOutput); 

		}
		return output;
	}

	public void getAllReportsbyUserId(Long userId)
	{

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
			if(details.getKey().replace("%20","").trim().equals("isPublished")) {
				if(details.getValue().getOperator().equals("equals") && (details.getValue().getSearchValue().equalsIgnoreCase("true") || details.getValue().getSearchValue().equalsIgnoreCase("false")))
					builder.and(report.isPublished.eq(Boolean.parseBoolean(details.getValue().getSearchValue())));
				else if(details.getValue().getOperator().equals("notEqual") && (details.getValue().getSearchValue().equalsIgnoreCase("true") || details.getValue().getSearchValue().equalsIgnoreCase("false")))
					builder.and(report.isPublished.ne(Boolean.parseBoolean(details.getValue().getSearchValue())));
			}
		}
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
			if(joinCol != null && joinCol.getKey().equals("ownerId")) {
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


