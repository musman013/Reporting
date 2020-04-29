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
import com.nfinity.reporting.reportingapp1.application.dashboard.DashboardAppService;
import com.nfinity.reporting.reportingapp1.application.dashboard.dto.*;
import com.nfinity.reporting.reportingapp1.domain.irepository.IDashboardRepository;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IUserRepository;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.application.reportdashboard.ReportdashboardAppService;    
import com.nfinity.reporting.reportingapp1.application.authorization.user.UserAppService;    

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
				properties = "spring.profiles.active=test")
public class DashboardControllerTest {
	@Autowired
	private SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired 
	private IDashboardRepository dashboard_repository;
	
	@Autowired 
	private IUserRepository userRepository;
	
	@SpyBean
	private DashboardAppService dashboardAppService;
    
    @SpyBean
	private ReportdashboardAppService reportdashboardAppService;
    
    @SpyBean
	private UserAppService userAppService;

	@SpyBean
	private LoggingHelper logHelper;

	@Mock
	private Logger loggerMock;

	private DashboardEntity dashboard;

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
		em.createNativeQuery("truncate table reporting.dashboard").executeUpdate();
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

	public DashboardEntity createEntity() {
		UserEntity user = createUserEntity();
		if(!userRepository.findAll().contains(user))
		{
			user=userRepository.save(user);
		}
	
		DashboardEntity dashboard = new DashboardEntity();
  		dashboard.setDescription("1");
		dashboard.setId(1L);
  		dashboard.setTitle("1");
		dashboard.setUser(user);
		
		return dashboard;
	}

	public CreateDashboardInput createDashboardInput() {
	
	    CreateDashboardInput dashboard = new CreateDashboardInput();
  		dashboard.setDescription("2");
  		dashboard.setTitle("2");
	    
		
		UserEntity user = new UserEntity();
		user.setAccessFailedCount(2);
  		user.setAuthenticationSource("2");
  		user.setEmailAddress("2");
  		user.setEmailConfirmationCode("2");
  		user.setFirstName("2");
		user.setId(2L);
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
		user=userRepository.save(user);
		dashboard.setUserId(user.getId());
		
		
		return dashboard;
	}

	public DashboardEntity createNewEntity() {
		DashboardEntity dashboard = new DashboardEntity();
  		dashboard.setDescription("3");
		dashboard.setId(3L);
  		dashboard.setTitle("3");
		return dashboard;
	}
	
	public UserEntity createUserEntity() {
		UserEntity user = new UserEntity();
		user.setAccessFailedCount(1);
  		user.setAuthenticationSource("1");
  		user.setEmailAddress("1");
  		user.setEmailConfirmationCode("1");
  		user.setFirstName("1");
		user.setId(1L);
		user.setIsActive(true);
		user.setIsEmailConfirmed(true);
		user.setIsLockoutEnabled(true);
  		user.setIsPhoneNumberConfirmed("1");
		user.setLastLoginTime(new Date());
  		user.setLastName("1");
		user.setLockoutEndDateUtc(new Date());
  		user.setPassword("1");
  		user.setPasswordResetCode("1");
		user.setPasswordTokenExpiration(new Date());
  		user.setPhoneNumber("1");
		user.setProfilePictureId(1L);
		user.setTwoFactorEnabled(true);
  		user.setUserName("1");
		return user;
		 
	}

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		evictAllCaches();
		final DashboardController dashboardController = new DashboardController(dashboardAppService,reportdashboardAppService,userAppService,
	logHelper);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(dashboardController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		dashboard= createEntity();
		List<DashboardEntity> list= dashboard_repository.findAll();
		if(!list.contains(dashboard)) {
			dashboard=dashboard_repository.save(dashboard);
		}

	}

	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/dashboard/" + dashboard.getId())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() throws Exception {

		mvc.perform(get("/dashboard/111")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());

	}    
	@Test
	public void CreateDashboard_DashboardDoesNotExist_ReturnStatusOk() throws Exception {
		CreateDashboardInput dashboard = createDashboardInput();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(dashboard);

		mvc.perform(post("/dashboard").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

	}     

	@Test
	public void DeleteDashboard_IdIsNotValid_ThrowEntityNotFoundException() throws Exception {

        doReturn(null).when(dashboardAppService).findById(111L);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/dashboard/111")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a dashboard with a id=111"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 DashboardEntity entity =  createNewEntity();
		UserEntity user = new UserEntity();
		user.setAccessFailedCount(3);
  		user.setAuthenticationSource("3");
  		user.setEmailAddress("3");
  		user.setEmailConfirmationCode("3");
  		user.setFirstName("3");
		user.setId(3L);
		user.setIsActive(true);
		user.setIsEmailConfirmed(true);
		user.setIsLockoutEnabled(true);
  		user.setIsPhoneNumberConfirmed("3");
		user.setLastLoginTime(new Date());
  		user.setLastName("3");
		user.setLockoutEndDateUtc(new Date());
  		user.setPassword("3");
  		user.setPasswordResetCode("3");
		user.setPasswordTokenExpiration(new Date());
  		user.setPhoneNumber("3");
		user.setProfilePictureId(3L);
		user.setTwoFactorEnabled(true);
  		user.setUserName("3");
		user=userRepository.save(user);
		
		entity.setUser(user);
		
		entity = dashboard_repository.save(entity);

		FindDashboardByIdOutput output= new FindDashboardByIdOutput();
  		output.setId(entity.getId());
        Mockito.when(dashboardAppService.findById(entity.getId())).thenReturn(output);
        
		mvc.perform(delete("/dashboard/" + entity.getId())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateDashboard_DashboardDoesNotExist_ReturnStatusNotFound() throws Exception {

        doReturn(null).when(dashboardAppService).findById(111L);

		UpdateDashboardInput dashboard = new UpdateDashboardInput();
  		dashboard.setDescription("111");
		dashboard.setId(111L);
  		dashboard.setTitle("111");

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(dashboard);
		mvc.perform(put("/dashboard/111").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isNotFound());

	}    

	@Test
	public void UpdateDashboard_DashboardExists_ReturnStatusOk() throws Exception {
		DashboardEntity entity =  createNewEntity();
		UserEntity user = new UserEntity();
		user.setAccessFailedCount(5);
  		user.setAuthenticationSource("5");
  		user.setEmailAddress("5");
  		user.setEmailConfirmationCode("5");
  		user.setFirstName("5");
		user.setId(5L);
		user.setIsActive(true);
		user.setIsEmailConfirmed(true);
		user.setIsLockoutEnabled(true);
  		user.setIsPhoneNumberConfirmed("5");
		user.setLastLoginTime(new Date());
  		user.setLastName("5");
		user.setLockoutEndDateUtc(new Date());
  		user.setPassword("5");
  		user.setPasswordResetCode("5");
		user.setPasswordTokenExpiration(new Date());
  		user.setPhoneNumber("5");
		user.setTwoFactorEnabled(true);
  		user.setUserName("5");
		user=userRepository.save(user);
		entity.setUser(user);
		entity = dashboard_repository.save(entity);
		FindDashboardByIdOutput output= new FindDashboardByIdOutput();
  		output.setDescription(entity.getDescription());
  		output.setId(entity.getId());
  		output.setTitle(entity.getTitle());
        Mockito.when(dashboardAppService.findById(entity.getId())).thenReturn(output);
        
		UpdateDashboardInput dashboard = new UpdateDashboardInput();
  		dashboard.setId(entity.getId());
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(dashboard);
	
		mvc.perform(put("/dashboard/" + entity.getId()).contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		DashboardEntity de = createEntity();
		de.setId(entity.getId());
		dashboard_repository.delete(de);

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/dashboard?search=id[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}    

	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsNotValid_ThrowException() throws Exception {

		org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/dashboard?search=dashboardid[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new Exception("Wrong URL Format: Property dashboardid not found!"));

	} 
	
	@Test
	public void GetReportdashboard_searchIsNotEmptyAndPropertyIsNotValid_ThrowException() throws Exception {
	
		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("dashboardId", "1");

		Mockito.when(dashboardAppService.parseReportdashboardJoinColumn("dashboardId")).thenReturn(joinCol);
		org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/dashboard/1/reportdashboard?search=abc[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk())).hasCause(new Exception("Wrong URL Format: Property abc not found!"));
	
	}    
	
	@Test
	public void GetReportdashboard_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("dashboardId", "1");
		
        Mockito.when(dashboardAppService.parseReportdashboardJoinColumn("dashboardId")).thenReturn(joinCol);
		mvc.perform(get("/dashboard/1/reportdashboard?search=dashboardId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetReportdashboard_searchIsNotEmpty() throws Exception {
	
		Mockito.when(dashboardAppService.parseReportdashboardJoinColumn(anyString())).thenReturn(null);
		mvc.perform(get("/dashboard/1/reportdashboard?search=dashboardid[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isNotFound());
	}    
	@Test
	public void GetUser_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() throws Exception {
	
	    mvc.perform(get("/dashboard/111/user")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isNotFound());
	
	}    
	
	@Test
	public void GetUser_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/dashboard/" + dashboard.getId()+ "/user")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
    

}
