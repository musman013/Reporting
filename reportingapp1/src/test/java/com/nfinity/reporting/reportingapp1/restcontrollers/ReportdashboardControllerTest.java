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
import com.nfinity.reporting.reportingapp1.application.reportdashboard.ReportdashboardAppService;
import com.nfinity.reporting.reportingapp1.application.reportdashboard.dto.*;
import com.nfinity.reporting.reportingapp1.domain.irepository.IReportdashboardRepository;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IDashboardRepository;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IReportRepository;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.application.dashboard.DashboardAppService;    
import com.nfinity.reporting.reportingapp1.application.report.ReportAppService;    
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardId;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
				properties = "spring.profiles.active=test")
public class ReportdashboardControllerTest {
	@Autowired
	private SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired 
	private IReportdashboardRepository reportdashboard_repository;
	
	@Autowired 
	private IDashboardRepository dashboardRepository;
	
	@Autowired 
	private IReportRepository reportRepository;
	
	@SpyBean
	private ReportdashboardAppService reportdashboardAppService;
    
    @SpyBean
	private DashboardAppService dashboardAppService;
    
    @SpyBean
	private ReportAppService reportAppService;

	@SpyBean
	private LoggingHelper logHelper;

	@Mock
	private Logger loggerMock;

	private ReportdashboardEntity reportdashboard;

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
		em.createNativeQuery("truncate table reporting.reportdashboard").executeUpdate();
		em.createNativeQuery("truncate table reporting.dashboard").executeUpdate();
		em.createNativeQuery("truncate table reporting.report").executeUpdate();
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

	public ReportdashboardEntity createEntity() {
		DashboardEntity dashboard = createDashboardEntity();
		if(!dashboardRepository.findAll().contains(dashboard))
		{
			dashboard=dashboardRepository.save(dashboard);
		}
		ReportEntity report = createReportEntity();
		if(!reportRepository.findAll().contains(report))
		{
			report=reportRepository.save(report);
		}
	
		ReportdashboardEntity reportdashboard = new ReportdashboardEntity();
		reportdashboard.setDashboardId(1L);
		reportdashboard.setReportId(1L);
		reportdashboard.setDashboard(dashboard);
		reportdashboard.setReport(report);
		
		return reportdashboard;
	}

	public CreateReportdashboardInput createReportdashboardInput() {
	
	    CreateReportdashboardInput reportdashboard = new CreateReportdashboardInput();
		reportdashboard.setDashboardId(2L);
		reportdashboard.setReportId(2L);
	    
		DashboardEntity dashboard = new DashboardEntity();
  		dashboard.setDescription("2");
		dashboard.setId(2L);
  		dashboard.setTitle("2");
		dashboard=dashboardRepository.save(dashboard);
		reportdashboard.setDashboardId(dashboard.getId());
		
		ReportEntity report = new ReportEntity();
  		report.setCtype("2");
  		report.setDescription("2");
		report.setId(2L);
  		report.setQuery("2");
  		report.setReportType("2");
  		report.setTitle("2");
		report=reportRepository.save(report);
		reportdashboard.setReportId(report.getId());
		
		
		return reportdashboard;
	}

	public ReportdashboardEntity createNewEntity() {
		ReportdashboardEntity reportdashboard = new ReportdashboardEntity();
		reportdashboard.setDashboardId(3L);
		reportdashboard.setReportId(3L);
		return reportdashboard;
	}
	
	public DashboardEntity createDashboardEntity() {
		DashboardEntity dashboard = new DashboardEntity();
  		dashboard.setDescription("1");
		dashboard.setId(1L);
  		dashboard.setTitle("1");
		return dashboard;
		 
	}
	public ReportEntity createReportEntity() {
		ReportEntity report = new ReportEntity();
  		report.setCtype("1");
  		report.setDescription("1");
		report.setId(1L);
  		report.setQuery("1");
  		report.setReportType("1");
  		report.setTitle("1");
		return report;
		 
	}

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		evictAllCaches();
		final ReportdashboardController reportdashboardController = new ReportdashboardController(reportdashboardAppService,dashboardAppService,reportAppService,
	logHelper);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(reportdashboardController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		reportdashboard= createEntity();
		List<ReportdashboardEntity> list= reportdashboard_repository.findAll();
		if(!list.contains(reportdashboard)) {
			reportdashboard=reportdashboard_repository.save(reportdashboard);
		}

	}

	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/reportdashboard/dashboardId:" + reportdashboard.getDashboardId() + ",reportId:" + reportdashboard.getReportId() )
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() throws Exception {

		mvc.perform(get("/reportdashboard/dashboardId:111,reportId:111")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());

	}    
	@Test
	public void CreateReportdashboard_ReportdashboardDoesNotExist_ReturnStatusOk() throws Exception {
		CreateReportdashboardInput reportdashboard = createReportdashboardInput();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(reportdashboard);

		mvc.perform(post("/reportdashboard").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

	}     

	@Test
	public void DeleteReportdashboard_IdIsNotValid_ThrowEntityNotFoundException() throws Exception {

        doReturn(null).when(reportdashboardAppService).findById(new ReportdashboardId(111L, 111L));
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/reportdashboard/dashboardId:111,reportId:111")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a reportdashboard with a id=dashboardId:111,reportId:111"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 ReportdashboardEntity entity =  createNewEntity();
		DashboardEntity dashboard = new DashboardEntity();
  		dashboard.setDescription("3");
		dashboard.setId(3L);
  		dashboard.setTitle("3");
		dashboard=dashboardRepository.save(dashboard);
		
		entity.setDashboardId(dashboard.getId());
		entity.setDashboard(dashboard);
		ReportEntity report = new ReportEntity();
  		report.setCtype("3");
  		report.setDescription("3");
		report.setId(3L);
  		report.setQuery("3");
  		report.setReportType("3");
  		report.setTitle("3");
		report=reportRepository.save(report);
		
		entity.setReportId(report.getId());
		entity.setReport(report);
		
		entity = reportdashboard_repository.save(entity);

		FindReportdashboardByIdOutput output= new FindReportdashboardByIdOutput();
  		output.setDashboardId(entity.getDashboardId());
  		output.setReportId(entity.getReportId());
	    Mockito.when(reportdashboardAppService.findById(new ReportdashboardId(entity.getDashboardId(), entity.getReportId()))).thenReturn(output);
   
		mvc.perform(delete("/reportdashboard/dashboardId:"+ entity.getDashboardId()+ ",reportId:"+ entity.getReportId())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateReportdashboard_ReportdashboardDoesNotExist_ReturnStatusNotFound() throws Exception {

        doReturn(null).when(reportdashboardAppService).findById(new ReportdashboardId(111L, 111L));

		UpdateReportdashboardInput reportdashboard = new UpdateReportdashboardInput();
		reportdashboard.setDashboardId(111L);
		reportdashboard.setReportId(111L);

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(reportdashboard);
		mvc.perform(put("/reportdashboard/dashboardId:111,reportId:111").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isNotFound());

	}    

	@Test
	public void UpdateReportdashboard_ReportdashboardExists_ReturnStatusOk() throws Exception {
		ReportdashboardEntity entity =  createNewEntity();
		DashboardEntity dashboard = new DashboardEntity();
  		dashboard.setDescription("5");
		dashboard.setId(5L);
  		dashboard.setTitle("5");
		dashboard=dashboardRepository.save(dashboard);
		entity.setDashboardId(dashboard.getId());
		entity.setDashboard(dashboard);
		ReportEntity report = new ReportEntity();
  		report.setCtype("5");
  		report.setDescription("5");
		report.setId(5L);
  		report.setQuery("5");
  		report.setReportType("5");
  		report.setTitle("5");
		report=reportRepository.save(report);
		entity.setReportId(report.getId());
		entity.setReport(report);
		entity = reportdashboard_repository.save(entity);
		FindReportdashboardByIdOutput output= new FindReportdashboardByIdOutput();
  		output.setDashboardId(entity.getDashboardId());
  		output.setReportId(entity.getReportId());
	    Mockito.when(reportdashboardAppService.findById(new ReportdashboardId(entity.getDashboardId(), entity.getReportId()))).thenReturn(output);
        
		UpdateReportdashboardInput reportdashboard = new UpdateReportdashboardInput();
  		reportdashboard.setDashboardId(entity.getDashboardId());
  		reportdashboard.setReportId(entity.getReportId());
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(reportdashboard);
	
		mvc.perform(put("/reportdashboard/dashboardId:"+ entity.getDashboardId()+ ",reportId:"+ entity.getReportId()).contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		ReportdashboardEntity de = createEntity();
		de.setDashboardId(entity.getDashboardId());
		de.setReportId(entity.getReportId());
		reportdashboard_repository.delete(de);

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/reportdashboard?search=dashboardId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}    

	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsNotValid_ThrowException() throws Exception {

		org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/reportdashboard?search=reportdashboarddashboardId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new Exception("Wrong URL Format: Property reportdashboarddashboardId not found!"));

	} 
	
	@Test
	public void GetDashboard_IdIsNotEmptyAndIdIsNotValid_ThrowException() throws Exception {
	
		org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/reportdashboard/dashboardId:111/dashboard")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk())).hasCause(new EntityNotFoundException("Invalid id=dashboardId:111"));
	
	}    
	@Test
	public void GetDashboard_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() throws Exception {
	
	    mvc.perform(get("/reportdashboard/dashboardId:111,reportId:111/dashboard")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isNotFound());
	
	}    
	
	@Test
	public void GetDashboard_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/reportdashboard/dashboardId:" + reportdashboard.getDashboardId()+ ",reportId:" + reportdashboard.getReportId()+ "/dashboard")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	@Test
	public void GetReport_IdIsNotEmptyAndIdIsNotValid_ThrowException() throws Exception {
	
		org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/reportdashboard/dashboardId:111/report")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk())).hasCause(new EntityNotFoundException("Invalid id=dashboardId:111"));
	
	}    
	@Test
	public void GetReport_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() throws Exception {
	
	    mvc.perform(get("/reportdashboard/dashboardId:111,reportId:111/report")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isNotFound());
	
	}    
	
	@Test
	public void GetReport_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/reportdashboard/dashboardId:" + reportdashboard.getDashboardId()+ ",reportId:" + reportdashboard.getReportId()+ "/report")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
    

}
