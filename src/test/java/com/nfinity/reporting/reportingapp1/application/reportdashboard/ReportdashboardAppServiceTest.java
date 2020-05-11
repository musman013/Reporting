package com.nfinity.reporting.reportingapp1.application.reportdashboard;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nfinity.reporting.reportingapp1.domain.reportdashboard.*;
import com.nfinity.reporting.reportingapp1.commons.search.*;
import com.nfinity.reporting.reportingapp1.application.reportdashboard.dto.*;
import com.nfinity.reporting.reportingapp1.domain.model.QReportdashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardId;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.dashboard.DashboardManager;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.domain.report.ReportManager;
import com.nfinity.reporting.reportingapp1.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

@RunWith(SpringJUnit4ClassRunner.class)
public class ReportdashboardAppServiceTest {

	@InjectMocks
	@Spy
	ReportdashboardAppService _appService;

	@Mock
	private ReportdashboardManager _reportdashboardManager;
	
    @Mock
	private DashboardManager  _dashboardManager;
	
    @Mock
	private ReportManager  _reportManager;
	
	@Mock
	private ReportdashboardMapper _mapper;

	@Mock
	private Logger loggerMock;

	@Mock
	private LoggingHelper logHelper;
	

    @Mock
    private ReportdashboardId reportdashboardId;
    
    private static Long ID=15L;
	 
	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(_appService);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void findReportdashboardById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {

		Mockito.when(_reportdashboardManager.findById(any(ReportdashboardId.class))).thenReturn(null);
		Assertions.assertThat(_appService.findById(reportdashboardId)).isEqualTo(null);
	}
	
	@Test
	public void findReportdashboardById_IdIsNotNullAndIdExists_ReturnReportdashboard() {

		ReportdashboardEntity reportdashboard = mock(ReportdashboardEntity.class);
		Mockito.when(_reportdashboardManager.findById(any(ReportdashboardId.class))).thenReturn(reportdashboard);
		Assertions.assertThat(_appService.findById(reportdashboardId)).isEqualTo(_mapper.reportdashboardEntityToFindReportdashboardByIdOutput(reportdashboard));
	}
	
	 @Test 
    public void createReportdashboard_ReportdashboardIsNotNullAndReportdashboardDoesNotExist_StoreReportdashboard() { 
 
       ReportdashboardEntity reportdashboardEntity = mock(ReportdashboardEntity.class); 
       CreateReportdashboardInput reportdashboard = new CreateReportdashboardInput();
   
		DashboardEntity dashboard= mock(DashboardEntity.class);
        reportdashboard.setDashboardId(Long.valueOf(ID));
		Mockito.when(_dashboardManager.findById(
        any(Long.class))).thenReturn(dashboard);
		ReportEntity report= mock(ReportEntity.class);
        reportdashboard.setReportId(Long.valueOf(ID));
		Mockito.when(_reportManager.findById(
        any(Long.class))).thenReturn(report);
		
        Mockito.when(_mapper.createReportdashboardInputToReportdashboardEntity(any(CreateReportdashboardInput.class))).thenReturn(reportdashboardEntity); 
        Mockito.when(_reportdashboardManager.create(any(ReportdashboardEntity.class))).thenReturn(reportdashboardEntity);
      
        Assertions.assertThat(_appService.create(reportdashboard)).isEqualTo(_mapper.reportdashboardEntityToCreateReportdashboardOutput(reportdashboardEntity)); 
    } 
    @Test
	public void createReportdashboard_ReportdashboardIsNotNullAndReportdashboardDoesNotExistAndChildIsNullAndChildIsNotMandatory_StoreReportdashboard() {

		ReportdashboardEntity reportdashboardEntity = mock(ReportdashboardEntity.class);
		CreateReportdashboardInput reportdashboard = mock(CreateReportdashboardInput.class);
		
		Mockito.when(_mapper.createReportdashboardInputToReportdashboardEntity(any(CreateReportdashboardInput.class))).thenReturn(reportdashboardEntity);
		Mockito.when(_reportdashboardManager.create(any(ReportdashboardEntity.class))).thenReturn(reportdashboardEntity);
		Assertions.assertThat(_appService.create(reportdashboard)).isEqualTo(_mapper.reportdashboardEntityToCreateReportdashboardOutput(reportdashboardEntity));

	}
	
    @Test
	public void updateReportdashboard_ReportdashboardIsNotNullAndReportdashboardDoesNotExistAndChildIsNullAndChildIsNotMandatory_ReturnUpdatedReportdashboard() {

		ReportdashboardEntity reportdashboardEntity = mock(ReportdashboardEntity.class);
		UpdateReportdashboardInput reportdashboard = mock(UpdateReportdashboardInput.class);
		
		Mockito.when(_mapper.updateReportdashboardInputToReportdashboardEntity(any(UpdateReportdashboardInput.class))).thenReturn(reportdashboardEntity);
		Mockito.when(_reportdashboardManager.update(any(ReportdashboardEntity.class))).thenReturn(reportdashboardEntity);
		Assertions.assertThat(_appService.update(reportdashboardId,reportdashboard)).isEqualTo(_mapper.reportdashboardEntityToUpdateReportdashboardOutput(reportdashboardEntity));
	}
	
		
	@Test
	public void updateReportdashboard_ReportdashboardIdIsNotNullAndIdExists_ReturnUpdatedReportdashboard() {

		ReportdashboardEntity reportdashboardEntity = mock(ReportdashboardEntity.class);
		UpdateReportdashboardInput reportdashboard= mock(UpdateReportdashboardInput.class);
		
		Mockito.when(_mapper.updateReportdashboardInputToReportdashboardEntity(any(UpdateReportdashboardInput.class))).thenReturn(reportdashboardEntity);
		Mockito.when(_reportdashboardManager.update(any(ReportdashboardEntity.class))).thenReturn(reportdashboardEntity);
		Assertions.assertThat(_appService.update(reportdashboardId,reportdashboard)).isEqualTo(_mapper.reportdashboardEntityToUpdateReportdashboardOutput(reportdashboardEntity));
	}
    
	@Test
	public void deleteReportdashboard_ReportdashboardIsNotNullAndReportdashboardExists_ReportdashboardRemoved() {

		ReportdashboardEntity reportdashboard= mock(ReportdashboardEntity.class);
		Mockito.when(_reportdashboardManager.findById(any(ReportdashboardId.class))).thenReturn(reportdashboard);
		
		_appService.delete(reportdashboardId); 
		verify(_reportdashboardManager).delete(reportdashboard);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<ReportdashboardEntity> list = new ArrayList<>();
		Page<ReportdashboardEntity> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindReportdashboardByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();
//		search.setType(1);
//		search.setValue("xyz");
//		search.setOperator("equals");

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_reportdashboardManager.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<ReportdashboardEntity> list = new ArrayList<>();
		ReportdashboardEntity reportdashboard = mock(ReportdashboardEntity.class);
		list.add(reportdashboard);
    	Page<ReportdashboardEntity> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindReportdashboardByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();
//		search.setType(1);
//		search.setValue("xyz");
//		search.setOperator("equals");
		output.add(_mapper.reportdashboardEntityToFindReportdashboardByIdOutput(reportdashboard));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_reportdashboardManager.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QReportdashboardEntity reportdashboard = QReportdashboardEntity.reportdashboardEntity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
		 Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
		Assertions.assertThat(_appService.searchKeyValuePair(reportdashboard,map,searchMap)).isEqualTo(builder);
	}
	
	@Test (expected = Exception.class)
	public void checkProperties_PropertyDoesNotExist_ThrowException() throws Exception {
		List<String> list = new ArrayList<>();
		list.add("xyz");
		_appService.checkProperties(list);
	}
	
	@Test
	public void checkProperties_PropertyExists_ReturnNothing() throws Exception {
		List<String> list = new ArrayList<>();
		_appService.checkProperties(list);
	}
	
	@Test
	public void  search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
	
		Map<String,SearchFields> map = new HashMap<>();
		QReportdashboardEntity reportdashboard = QReportdashboardEntity.reportdashboardEntity;
		List<SearchFields> fieldsList= new ArrayList<>();
		SearchFields fields=new SearchFields();
		SearchCriteria search= new SearchCriteria();
		search.setType(3);
		search.setValue("xyz");
		search.setOperator("equals");
        fields.setOperator("equals");
		fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
		BooleanBuilder builder = new BooleanBuilder();
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QReportdashboardEntity.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void  search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}
   
   //Dashboard
	@Test
	public void GetDashboard_IfReportdashboardIdAndDashboardIdIsNotNullAndReportdashboardExists_ReturnDashboard() {
		ReportdashboardEntity reportdashboard = mock(ReportdashboardEntity.class);
		DashboardEntity dashboard = mock(DashboardEntity.class);

		Mockito.when(_reportdashboardManager.findById(any(ReportdashboardId.class))).thenReturn(reportdashboard);
		Mockito.when(_reportdashboardManager.getDashboard(any(ReportdashboardId.class))).thenReturn(dashboard);
		Assertions.assertThat(_appService.getDashboard(reportdashboardId)).isEqualTo(_mapper.dashboardEntityToGetDashboardOutput(dashboard, reportdashboard));
	}

	@Test 
	public void GetDashboard_IfReportdashboardIdAndDashboardIdIsNotNullAndReportdashboardDoesNotExist_ReturnNull() {
		Mockito.when(_reportdashboardManager.findById(any(ReportdashboardId.class))).thenReturn(null);
		Assertions.assertThat(_appService.getDashboard(reportdashboardId)).isEqualTo(null);
	}
   
   //Report
	@Test
	public void GetReport_IfReportdashboardIdAndReportIdIsNotNullAndReportdashboardExists_ReturnReport() {
		ReportdashboardEntity reportdashboard = mock(ReportdashboardEntity.class);
		ReportEntity report = mock(ReportEntity.class);

		Mockito.when(_reportdashboardManager.findById(any(ReportdashboardId.class))).thenReturn(reportdashboard);
		Mockito.when(_reportdashboardManager.getReport(any(ReportdashboardId.class))).thenReturn(report);
		Assertions.assertThat(_appService.getReport(reportdashboardId)).isEqualTo(_mapper.reportEntityToGetReportOutput(report, reportdashboard));
	}

	@Test 
	public void GetReport_IfReportdashboardIdAndReportIdIsNotNullAndReportdashboardDoesNotExist_ReturnNull() {
		Mockito.when(_reportdashboardManager.findById(any(ReportdashboardId.class))).thenReturn(null);
		Assertions.assertThat(_appService.getReport(reportdashboardId)).isEqualTo(null);
	}
  
	@Test
	public void ParseReportdashboardKey_KeysStringIsNotEmptyAndKeyValuePairExists_ReturnReportdashboardId()
	{
		String keyString= "dashboardId:15,reportId:15";
	
		ReportdashboardId reportdashboardId = new ReportdashboardId();
		reportdashboardId.setDashboardId(Long.valueOf(ID));
		reportdashboardId.setReportId(Long.valueOf(ID));

		Assertions.assertThat(_appService.parseReportdashboardKey(keyString)).isEqualToComparingFieldByField(reportdashboardId);
	}
	
	@Test
	public void ParseReportdashboardKey_KeysStringIsEmpty_ReturnNull()
	{
		String keyString= "";
		Assertions.assertThat(_appService.parseReportdashboardKey(keyString)).isEqualTo(null);
	}
	
	@Test
	public void ParseReportdashboardKey_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull()
	{
		String keyString= "dashboardId";

		Assertions.assertThat(_appService.parseReportdashboardKey(keyString)).isEqualTo(null);
	}
	
}

