package com.nfinity.reporting.reportingapp1.application.report;

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

import com.nfinity.reporting.reportingapp1.domain.report.*;
import com.nfinity.reporting.reportingapp1.commons.search.*;
import com.nfinity.reporting.reportingapp1.application.report.dto.*;
import com.nfinity.reporting.reportingapp1.domain.model.QReportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.reportdashboard.ReportdashboardManager;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.domain.authorization.user.UserManager;
import com.nfinity.reporting.reportingapp1.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

@RunWith(SpringJUnit4ClassRunner.class)
public class ReportAppServiceTest {

	@InjectMocks
	@Spy
	ReportAppService _appService;

	@Mock
	private ReportManager _reportManager;
	
    @Mock
	private ReportdashboardManager  _reportdashboardManager;
	
    @Mock
	private UserManager  _userManager;
	
	@Mock
	private ReportMapper _mapper;

	@Mock
	private Logger loggerMock;

	@Mock
	private LoggingHelper logHelper;
	

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
	public void findReportById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {

		Mockito.when(_reportManager.findById(anyLong())).thenReturn(null);
		Assertions.assertThat(_appService.findById(ID)).isEqualTo(null);
	}
	
	@Test
	public void findReportById_IdIsNotNullAndIdExists_ReturnReport() {

		ReportEntity report = mock(ReportEntity.class);
		Mockito.when(_reportManager.findById(anyLong())).thenReturn(report);
		Assertions.assertThat(_appService.findById(ID)).isEqualTo(_mapper.reportEntityToFindReportByIdOutput(report));
	}
	
	 @Test 
    public void createReport_ReportIsNotNullAndReportDoesNotExist_StoreReport() { 
 
       ReportEntity reportEntity = mock(ReportEntity.class); 
       CreateReportInput report = new CreateReportInput();
   
		UserEntity user= mock(UserEntity.class);
        report.setUserId(Long.valueOf(ID));
		Mockito.when(_userManager.findById(
        any(Long.class))).thenReturn(user);
		
        Mockito.when(_mapper.createReportInputToReportEntity(any(CreateReportInput.class))).thenReturn(reportEntity); 
        Mockito.when(_reportManager.create(any(ReportEntity.class))).thenReturn(reportEntity);
      
        Assertions.assertThat(_appService.create(report)).isEqualTo(_mapper.reportEntityToCreateReportOutput(reportEntity)); 
    } 
    @Test
	public void createReport_ReportIsNotNullAndReportDoesNotExistAndChildIsNullAndChildIsNotMandatory_StoreReport() {

		ReportEntity reportEntity = mock(ReportEntity.class);
		CreateReportInput report = mock(CreateReportInput.class);
		
		Mockito.when(_mapper.createReportInputToReportEntity(any(CreateReportInput.class))).thenReturn(reportEntity);
		Mockito.when(_reportManager.create(any(ReportEntity.class))).thenReturn(reportEntity);
		Assertions.assertThat(_appService.create(report)).isEqualTo(_mapper.reportEntityToCreateReportOutput(reportEntity));

	}
	
    @Test
	public void updateReport_ReportIsNotNullAndReportDoesNotExistAndChildIsNullAndChildIsNotMandatory_ReturnUpdatedReport() {

		ReportEntity reportEntity = mock(ReportEntity.class);
		UpdateReportInput report = mock(UpdateReportInput.class);
		
		Mockito.when(_mapper.updateReportInputToReportEntity(any(UpdateReportInput.class))).thenReturn(reportEntity);
		Mockito.when(_reportManager.update(any(ReportEntity.class))).thenReturn(reportEntity);
		Assertions.assertThat(_appService.update(ID,report)).isEqualTo(_mapper.reportEntityToUpdateReportOutput(reportEntity));
	}
	
		
	@Test
	public void updateReport_ReportIdIsNotNullAndIdExists_ReturnUpdatedReport() {

		ReportEntity reportEntity = mock(ReportEntity.class);
		UpdateReportInput report= mock(UpdateReportInput.class);
		
		Mockito.when(_mapper.updateReportInputToReportEntity(any(UpdateReportInput.class))).thenReturn(reportEntity);
		Mockito.when(_reportManager.update(any(ReportEntity.class))).thenReturn(reportEntity);
		Assertions.assertThat(_appService.update(ID,report)).isEqualTo(_mapper.reportEntityToUpdateReportOutput(reportEntity));
	}
    
	@Test
	public void deleteReport_ReportIsNotNullAndReportExists_ReportRemoved() {

		ReportEntity report= mock(ReportEntity.class);
		Mockito.when(_reportManager.findById(anyLong())).thenReturn(report);
		
		_appService.delete(ID); 
		verify(_reportManager).delete(report);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<ReportEntity> list = new ArrayList<>();
		Page<ReportEntity> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindReportByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();
//		search.setType(1);
//		search.setValue("xyz");
//		search.setOperator("equals");

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_reportManager.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<ReportEntity> list = new ArrayList<>();
		ReportEntity report = mock(ReportEntity.class);
		list.add(report);
    	Page<ReportEntity> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindReportByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();
//		search.setType(1);
//		search.setValue("xyz");
//		search.setOperator("equals");
		output.add(_mapper.reportEntityToFindReportByIdOutput(report));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_reportManager.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QReportEntity report = QReportEntity.reportEntity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
        map.put("ctype",searchFields);
		 Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
         builder.and(report.ctype.eq("xyz"));
		Assertions.assertThat(_appService.searchKeyValuePair(report,map,searchMap)).isEqualTo(builder);
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
        list.add("ctype");
        list.add("description");
        list.add("query");
        list.add("reportType");
        list.add("title");
		_appService.checkProperties(list);
	}
	
	@Test
	public void  search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
	
		Map<String,SearchFields> map = new HashMap<>();
		QReportEntity report = QReportEntity.reportEntity;
		List<SearchFields> fieldsList= new ArrayList<>();
		SearchFields fields=new SearchFields();
		SearchCriteria search= new SearchCriteria();
		search.setType(3);
		search.setValue("xyz");
		search.setOperator("equals");
        fields.setFieldName("ctype");
        fields.setOperator("equals");
		fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
		BooleanBuilder builder = new BooleanBuilder();
        builder.or(report.ctype.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QReportEntity.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void  search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}
   
   //User
	@Test
	public void GetUser_IfReportIdAndUserIdIsNotNullAndReportExists_ReturnUser() {
		ReportEntity report = mock(ReportEntity.class);
		UserEntity user = mock(UserEntity.class);

		Mockito.when(_reportManager.findById(anyLong())).thenReturn(report);
		Mockito.when(_reportManager.getUser(anyLong())).thenReturn(user);
		Assertions.assertThat(_appService.getUser(ID)).isEqualTo(_mapper.userEntityToGetUserOutput(user, report));
	}

	@Test 
	public void GetUser_IfReportIdAndUserIdIsNotNullAndReportDoesNotExist_ReturnNull() {
		Mockito.when(_reportManager.findById(anyLong())).thenReturn(null);
		Assertions.assertThat(_appService.getUser(ID)).isEqualTo(null);
	}
	
	@Test
	public void ParseReportdashboardJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull()
	{
	    Map<String,String> joinColumnMap = new HashMap<String,String>();
		String keyString= "15";
		joinColumnMap.put("reportId", keyString);
		Assertions.assertThat(_appService.parseReportdashboardJoinColumn(keyString)).isEqualTo(joinColumnMap);
	}
}

