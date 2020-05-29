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
import com.nfinity.reporting.reportingapp1.application.report.ReportAppService;
import com.nfinity.reporting.reportingapp1.application.report.dto.*;
import com.nfinity.reporting.reportingapp1.domain.irepository.IReportRepository;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IUserRepository;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.application.authorization.user.UserAppService;
import com.nfinity.reporting.reportingapp1.application.dashboardversionreport.DashboardversionreportAppService;    

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
				properties = "spring.profiles.active=test")
public class ReportControllerTest {
	@Autowired
	private SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired 
	private IReportRepository report_repository;
	
	@Autowired 
	private IUserRepository userRepository;
	
	@SpyBean
	private ReportAppService reportAppService;
    
    @SpyBean
	private DashboardversionreportAppService reportdashboardAppService;
    
    @SpyBean
	private UserAppService userAppService;

	@SpyBean
	private LoggingHelper logHelper;

	@Mock
	private Logger loggerMock;

	private ReportEntity report;

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
		em.createNativeQuery("truncate table reporting.report").executeUpdate();
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

	public ReportEntity createEntity() {
		UserEntity user = createUserEntity();
		if(!userRepository.findAll().contains(user))
		{
			user=userRepository.save(user);
		}
	
		ReportEntity report = new ReportEntity();
  		report.setCtype("1");
  		report.setDescription("1");
		report.setId(1L);
  		//report.setQuery("1");
  		report.setReportType("1");
  		report.setTitle("1");
		report.setUser(user);
		
		return report;
	}

	public CreateReportInput createReportInput() {
	
	    CreateReportInput report = new CreateReportInput();
  		report.setCtype("2");
  		report.setDescription("2");
  	//	report.setQuery("2");
  		report.setReportType("2");
  		report.setTitle("2");
	    
		
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
		report.setUserId(user.getId());
		
		
		return report;
	}

	public ReportEntity createNewEntity() {
		ReportEntity report = new ReportEntity();
  		report.setCtype("3");
  		report.setDescription("3");
		report.setId(3L);
  	//	report.setQuery("3");
  		report.setReportType("3");
  		report.setTitle("3");
		return report;
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
		final ReportController reportController = new ReportController(reportAppService,reportdashboardAppService,userAppService,
	logHelper);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(reportController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		report= createEntity();
		List<ReportEntity> list= report_repository.findAll();
		if(!list.contains(report)) {
			report=report_repository.save(report);
		}

	}

	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/report/" + report.getId())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() throws Exception {

		mvc.perform(get("/report/111")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());

	}    
	@Test
	public void CreateReport_ReportDoesNotExist_ReturnStatusOk() throws Exception {
		CreateReportInput report = createReportInput();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(report);

		mvc.perform(post("/report").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

	}     

	@Test
	public void DeleteReport_IdIsNotValid_ThrowEntityNotFoundException() throws Exception {

        doReturn(null).when(reportAppService).findById(111L);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/report/111")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a report with a id=111"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 ReportEntity entity =  createNewEntity();
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
		
		entity = report_repository.save(entity);

		FindReportByIdOutput output= new FindReportByIdOutput();
  		output.setId(entity.getId());
        Mockito.when(reportAppService.findById(entity.getId())).thenReturn(output);
        
		mvc.perform(delete("/report/" + entity.getId())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateReport_ReportDoesNotExist_ReturnStatusNotFound() throws Exception {

        doReturn(null).when(reportAppService).findById(111L);

		UpdateReportInput report = new UpdateReportInput();
  		report.setCtype("111");
  		report.setDescription("111");
		report.setId(111L);
  	//	report.setQuery("111");
  		report.setReportType("111");
  		report.setTitle("111");

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(report);
		mvc.perform(put("/report/111").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isNotFound());

	}    

	@Test
	public void UpdateReport_ReportExists_ReturnStatusOk() throws Exception {
		ReportEntity entity =  createNewEntity();
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
		entity = report_repository.save(entity);
		FindReportByIdOutput output= new FindReportByIdOutput();
  		output.setCtype(entity.getCtype());
  		output.setDescription(entity.getDescription());
  		output.setId(entity.getId());
  		output.setQuery(entity.getQuery());
  		output.setReportType(entity.getReportType());
  		output.setTitle(entity.getTitle());
        Mockito.when(reportAppService.findById(entity.getId())).thenReturn(output);
        
		UpdateReportInput report = new UpdateReportInput();
  		report.setId(entity.getId());
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(report);
	
		mvc.perform(put("/report/" + entity.getId()).contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		ReportEntity de = createEntity();
		de.setId(entity.getId());
		report_repository.delete(de);

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/report?search=id[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}    

	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsNotValid_ThrowException() throws Exception {

		org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/report?search=reportid[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new Exception("Wrong URL Format: Property reportid not found!"));

	} 
	
	@Test
	public void GetReportdashboard_searchIsNotEmptyAndPropertyIsNotValid_ThrowException() throws Exception {
	
		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("reportId", "1");

		Mockito.when(reportAppService.parseReportdashboardJoinColumn("reportId")).thenReturn(joinCol);
		org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/report/1/reportdashboard?search=abc[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk())).hasCause(new Exception("Wrong URL Format: Property abc not found!"));
	
	}    
	
	@Test
	public void GetReportdashboard_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("reportId", "1");
		
        Mockito.when(reportAppService.parseReportdashboardJoinColumn("reportId")).thenReturn(joinCol);
		mvc.perform(get("/report/1/reportdashboard?search=reportId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetReportdashboard_searchIsNotEmpty() throws Exception {
	
		Mockito.when(reportAppService.parseReportdashboardJoinColumn(anyString())).thenReturn(null);
		mvc.perform(get("/report/1/reportdashboard?search=reportid[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isNotFound());
	}    
	@Test
	public void GetUser_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() throws Exception {
	
	    mvc.perform(get("/report/111/user")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isNotFound());
	
	}    
	
	@Test
	public void GetUser_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/report/" + report.getId()+ "/user")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
    

}
