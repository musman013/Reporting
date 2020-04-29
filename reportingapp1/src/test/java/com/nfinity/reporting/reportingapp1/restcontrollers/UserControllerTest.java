package com.nfinity.reporting.reportingapp1.restcontrollers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityExistsException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nfinity.reporting.reportingapp1.commons.logging.LoggingHelper;
import com.nfinity.reporting.reportingapp1.application.authorization.user.UserAppService;
import com.nfinity.reporting.reportingapp1.application.authorization.user.dto.*;
import com.nfinity.reporting.reportingapp1.domain.irepository.IUserRepository;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.application.dashboard.DashboardAppService;    
import com.nfinity.reporting.reportingapp1.application.report.ReportAppService;    
import org.springframework.security.crypto.password.PasswordEncoder;
import com.nfinity.reporting.reportingapp1.security.JWTAppService;
import com.nfinity.reporting.reportingapp1.application.authorization.userrole.UserroleAppService;
import com.nfinity.reporting.reportingapp1.application.authorization.userrole.dto.FindUserroleByIdOutput;
import com.nfinity.reporting.reportingapp1.application.authorization.userpermission.UserpermissionAppService;
import com.nfinity.reporting.reportingapp1.application.authorization.userpermission.dto.FindUserpermissionByIdOutput;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
				properties = "spring.profiles.active=test")
public class UserControllerTest {
	@Autowired
	private SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired 
	private IUserRepository user_repository;
	
	@SpyBean
	private UserAppService userAppService;
    
    @SpyBean
	private DashboardAppService dashboardAppService;
    
    @SpyBean
	private ReportAppService reportAppService;
    
	@SpyBean
    private PasswordEncoder pEncoder;
    
    @SpyBean
	private JWTAppService jwtAppService;
    
	@SpyBean
    private UserpermissionAppService userpermissionAppService;
    
    @SpyBean
    private UserroleAppService userroleAppService;

	@SpyBean
	private LoggingHelper logHelper;

	@Mock
	private Logger loggerMock;

	private UserEntity user;

	private MockMvc mvc;
	
	@Autowired
	EntityManagerFactory emf;
	
    static EntityManagerFactory emfs;
	
	@PostConstruct
	public void init() {
	this.emfs = emf;
	}

	@AfterClass
	public static void cleanup() {
		EntityManager em = emfs.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
		em.createNativeQuery("truncate table reporting.user").executeUpdate();
		em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
		em.getTransaction().commit();
	}

	@Autowired 
	private CacheManager cacheManager; 
	
	public void evictAllCaches(){ 
		for(String name : cacheManager.getCacheNames()) {
			cacheManager.getCache(name).clear(); 
		} 
	}

	public UserEntity createEntity() {
	
		UserEntity user = new UserEntity();
		user.setAccessFailedCount(1);
  		user.setAuthenticationSource("1");
  		user.setEmailAddress("1");
  		user.setEmailConfirmationCode("1");
  		user.setFirstName("1");
		user.setId(1L);
		user.setIsActive(false);
		user.setIsEmailConfirmed(false);
		user.setIsLockoutEnabled(false);
  		user.setIsPhoneNumberConfirmed("1");
		user.setLastLoginTime(new Date());
  		user.setLastName("1");
		user.setLockoutEndDateUtc(new Date());
  		user.setPassword("1");
  		user.setPasswordResetCode("1");
		user.setPasswordTokenExpiration(new Date());
  		user.setPhoneNumber("1");
		user.setProfilePictureId(1L);
		user.setTwoFactorEnabled(false);
  		user.setUserName("1");
		
		return user;
	}

	public CreateUserInput createUserInput() {
	
	    CreateUserInput user = new CreateUserInput();
		user.setAccessFailedCount(2);
  		user.setAuthenticationSource("2");
  		user.setEmailAddress("2");
  		user.setEmailConfirmationCode("2");
  		user.setFirstName("2");
		user.setIsActive(false);
		user.setIsEmailConfirmed(false);
		user.setIsLockoutEnabled(false);
  		user.setIsPhoneNumberConfirmed("2");
		user.setLastLoginTime(new Date());
  		user.setLastName("2");
		user.setLockoutEndDateUtc(new Date());
  		user.setPassword("2");
  		user.setPasswordResetCode("2");
		user.setPasswordTokenExpiration(new Date());
  		user.setPhoneNumber("2");
		user.setProfilePictureId(2L);
		user.setTwoFactorEnabled(false);
  		user.setUserName("2");
	    
		
		
		
		return user;
	}

	public UserEntity createNewEntity() {
		UserEntity user = new UserEntity();
		user.setAccessFailedCount(3);
  		user.setAuthenticationSource("3");
  		user.setEmailAddress("3");
  		user.setEmailConfirmationCode("3");
  		user.setFirstName("3");
		user.setId(3L);
		user.setIsActive(false);
		user.setIsEmailConfirmed(false);
		user.setIsLockoutEnabled(false);
  		user.setIsPhoneNumberConfirmed("3");
		user.setLastLoginTime(new Date());
  		user.setLastName("3");
		user.setLockoutEndDateUtc(new Date());
  		user.setPassword("3");
  		user.setPasswordResetCode("3");
		user.setPasswordTokenExpiration(new Date());
  		user.setPhoneNumber("3");
		user.setProfilePictureId(3L);
		user.setTwoFactorEnabled(false);
  		user.setUserName("3");
		return user;
	}
	

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		evictAllCaches();
		final UserController userController = new UserController(userAppService,dashboardAppService,reportAppService,
	pEncoder, userpermissionAppService,userroleAppService,jwtAppService,logHelper);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(userController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		user= createEntity();
		List<UserEntity> list= user_repository.findAll();
		if(!list.contains(user)) {
			user=user_repository.save(user);
		}

	}

	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/user/" + user.getId())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() throws Exception {

		mvc.perform(get("/user/111")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());

	}    
	@Test
	public void CreateUser_UserDoesNotExist_ReturnStatusOk() throws Exception {
        Mockito.doReturn(null).when(userAppService).findByUserName(anyString());
	  
		CreateUserInput user = createUserInput();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(user);
		
		mvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(json))
		  .andExpect(status().isOk());
		 
		 user_repository.delete(createNewEntity());
	}  
    
	@Test
	public void CreateUser_UserAlreadyExists_ThrowEntityExistsException() throws Exception {
	    FindUserByUserNameOutput output= new FindUserByUserNameOutput();
  		output.setEmailAddress("1");
  		output.setFirstName("1");
		output.setId(1L);
  		output.setLastName("1");
  		output.setUserName("1");

        Mockito.doReturn(output).when(userAppService).findByUserName(anyString());
	    CreateUserInput user = createUserInput();
	    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(user);
       
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(post("/user")
        		.contentType(MediaType.APPLICATION_JSON).content(json))
         .andExpect(status().isOk())).hasCause(new EntityExistsException("There already exists a user with UserName =" + user.getUserName()));
	} 

	@Test
	public void DeleteUser_IdIsNotValid_ThrowEntityNotFoundException() throws Exception {

        doReturn(null).when(userAppService).findById(111L);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/user/111")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a user with a id=111"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 UserEntity entity =  createNewEntity();
		
		entity = user_repository.save(entity);

		FindUserByIdOutput output= new FindUserByIdOutput();
  		output.setEmailAddress(entity.getEmailAddress());
  		output.setFirstName(entity.getFirstName());
  		output.setId(entity.getId());
  		output.setLastName(entity.getLastName());
  		output.setUserName(entity.getUserName());
        Mockito.when(userAppService.findById(entity.getId())).thenReturn(output);
        
		mvc.perform(delete("/user/" + entity.getId())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateUser_UserDoesNotExist_ReturnStatusNotFound() throws Exception {

        doReturn(null).when(userAppService).findById(111L);

		UpdateUserInput user = new UpdateUserInput();
		user.setAccessFailedCount(111);
  		user.setAuthenticationSource("111");
  		user.setEmailAddress("111");
  		user.setEmailConfirmationCode("111");
  		user.setFirstName("111");
		user.setId(111L);
		user.setIsActive(true);
		user.setIsEmailConfirmed(true);
		user.setIsLockoutEnabled(true);
  		user.setIsPhoneNumberConfirmed("111");
		user.setLastLoginTime(new Date());
  		user.setLastName("111");
		user.setLockoutEndDateUtc(new Date());
  		user.setPasswordResetCode("111");
		user.setPasswordTokenExpiration(new Date());
  		user.setPhoneNumber("111");
		user.setProfilePictureId(111L);
		user.setTwoFactorEnabled(true);
  		user.setUserName("111");

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(user);
		mvc.perform(put("/user/111").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isNotFound());

	}    

	@Test
	public void UpdateUser_UserExists_ReturnStatusOk() throws Exception {
		UserEntity entity =  createNewEntity();
		entity = user_repository.save(entity);
		FindUserWithAllFieldsByIdOutput output= new FindUserWithAllFieldsByIdOutput();
  		output.setAccessFailedCount(entity.getAccessFailedCount());
  		output.setAuthenticationSource(entity.getAuthenticationSource());
  		output.setEmailAddress(entity.getEmailAddress());
  		output.setEmailConfirmationCode(entity.getEmailConfirmationCode());
  		output.setFirstName(entity.getFirstName());
  		output.setId(entity.getId());
  		output.setIsActive(entity.getIsActive());
  		output.setIsEmailConfirmed(entity.getIsEmailConfirmed());
  		output.setIsLockoutEnabled(entity.getIsLockoutEnabled());
  		output.setIsPhoneNumberConfirmed(entity.getIsPhoneNumberConfirmed());
  		output.setLastLoginTime(entity.getLastLoginTime());
  		output.setLastName(entity.getLastName());
  		output.setLockoutEndDateUtc(entity.getLockoutEndDateUtc());
  		output.setPassword(entity.getPassword());
  		output.setPasswordResetCode(entity.getPasswordResetCode());
  		output.setPasswordTokenExpiration(entity.getPasswordTokenExpiration());
  		output.setPhoneNumber(entity.getPhoneNumber());
  		output.setProfilePictureId(entity.getProfilePictureId());
  		output.setTwoFactorEnabled(entity.getTwoFactorEnabled());
  		output.setUserName(entity.getUserName());
		Mockito.when(userAppService.findWithAllFieldsById(entity.getId())).thenReturn(output);
		UpdateUserInput user = new UpdateUserInput();
  		user.setEmailAddress(entity.getEmailAddress());
  		user.setFirstName(entity.getFirstName());
  		user.setId(entity.getId());
  		user.setLastName(entity.getLastName());
  		user.setPassword(entity.getPassword());
  		user.setUserName(entity.getUserName());
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(user);
	
		mvc.perform(put("/user/" + entity.getId()).contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		UserEntity de = createEntity();
		de.setId(entity.getId());
		user_repository.delete(de);

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/user?search=id[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}    

	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsNotValid_ThrowException() throws Exception {

		org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/user?search=userid[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new Exception("Wrong URL Format: Property userid not found!"));

	} 
	
	@Test
	public void GetDashboard_searchIsNotEmptyAndPropertyIsNotValid_ThrowException() throws Exception {
	
		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("userId", "1");

		Mockito.when(userAppService.parseDashboardJoinColumn("userId")).thenReturn(joinCol);
		org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/user/1/dashboard?search=abc[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk())).hasCause(new Exception("Wrong URL Format: Property abc not found!"));
	
	}    
	
	@Test
	public void GetDashboard_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("userId", "1");
		
        Mockito.when(userAppService.parseDashboardJoinColumn("userId")).thenReturn(joinCol);
		mvc.perform(get("/user/1/dashboard?search=userId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetDashboard_searchIsNotEmpty() throws Exception {
	
		Mockito.when(userAppService.parseDashboardJoinColumn(anyString())).thenReturn(null);
		mvc.perform(get("/user/1/dashboard?search=userid[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isNotFound());
	}    
	@Test
	public void GetReport_searchIsNotEmptyAndPropertyIsNotValid_ThrowException() throws Exception {
	
		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("userId", "1");

		Mockito.when(userAppService.parseReportJoinColumn("userId")).thenReturn(joinCol);
		org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/user/1/report?search=abc[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk())).hasCause(new Exception("Wrong URL Format: Property abc not found!"));
	
	}    
	
	@Test
	public void GetReport_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("userId", "1");
		
        Mockito.when(userAppService.parseReportJoinColumn("userId")).thenReturn(joinCol);
		mvc.perform(get("/user/1/report?search=userId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetReport_searchIsNotEmpty() throws Exception {
	
		Mockito.when(userAppService.parseReportJoinColumn(anyString())).thenReturn(null);
		mvc.perform(get("/user/1/report?search=userid[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isNotFound());
	}    
    
	@Test
	public void GetUserrole_searchIsNotEmptyAndPropertyIsNotValid_ThrowException() throws Exception {
	
		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("roleid", "1");
		
		Mockito.when(userAppService.parseUserroleJoinColumn(any(String.class))).thenReturn(joinCol);
		org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/user/2/userrole?search=abc[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk())).hasCause(new Exception("Wrong URL Format: Property abc not found!"));
	
	}    
	
	@Test
	public void GetUserrole_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("roleId", "1");

		Mockito.when(userAppService.parseUserroleJoinColumn(any(String.class))).thenReturn(joinCol);

		mvc.perform(get("/user/1/userrole?search=roleId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetUserrole_searchIsNotEmpty() throws Exception {
	
		Mockito.when(userAppService.parseUserroleJoinColumn(any(String.class))).thenReturn(null);
		mvc.perform(get("/user/1/userrole?search=roleid[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isNotFound());
	}      
	 
	@Test
	public void GetUserpermission_searchIsNotEmptyAndPropertyIsNotValid_ThrowException() throws Exception {
	
		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("permissionid", "1");
		
		Mockito.when(userAppService.parseUserpermissionJoinColumn(any(String.class))).thenReturn(joinCol);
		org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/user/2/userpermission?search=abc[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk())).hasCause(new Exception("Wrong URL Format: Property abc not found!"));
	
	}    
	
	@Test
	public void GetUserpermission_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("permissionId", "1");

		Mockito.when(userAppService.parseUserpermissionJoinColumn(any(String.class))).thenReturn(joinCol);

		mvc.perform(get("/user/1/userpermission?search=permissionId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetUserpermission_searchIsNotEmpty() throws Exception {
	
		Mockito.when(userAppService.parseUserpermissionJoinColumn(any(String.class))).thenReturn(null);
		mvc.perform(get("/user/1/userpermission?search=permissionid[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isNotFound());
	}      

}
