package com.nfinity.reporting.reportingapp1.application.dashboard;

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

import com.nfinity.reporting.reportingapp1.domain.dashboard.*;
import com.nfinity.reporting.reportingapp1.domain.dashboardversionreport.DashboardversionreportManager;
import com.nfinity.reporting.reportingapp1.commons.search.*;
import com.nfinity.reporting.reportingapp1.application.dashboard.dto.*;
import com.nfinity.reporting.reportingapp1.domain.model.QDashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.domain.authorization.user.UserManager;
import com.nfinity.reporting.reportingapp1.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

@RunWith(SpringJUnit4ClassRunner.class)
public class DashboardAppServiceTest {

	@InjectMocks
	@Spy
	DashboardAppService _appService;

	@Mock
	private DashboardManager _dashboardManager;
	
    @Mock
	private DashboardversionreportManager  _reportdashboardManager;
	
    @Mock
	private UserManager  _userManager;
	
	@Mock
	private DashboardMapper _mapper;

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
	public void findDashboardById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {

		Mockito.when(_dashboardManager.findById(anyLong())).thenReturn(null);
		Assertions.assertThat(_appService.findById(ID)).isEqualTo(null);
	}
	
	@Test
	public void findDashboardById_IdIsNotNullAndIdExists_ReturnDashboard() {

		DashboardEntity dashboard = mock(DashboardEntity.class);
		Mockito.when(_dashboardManager.findById(anyLong())).thenReturn(dashboard);
		Assertions.assertThat(_appService.findById(ID)).isEqualTo(_mapper.dashboardEntityToFindDashboardByIdOutput(dashboard));
	}
	
	 @Test 
    public void createDashboard_DashboardIsNotNullAndDashboardDoesNotExist_StoreDashboard() { 
 
       DashboardEntity dashboardEntity = mock(DashboardEntity.class); 
       CreateDashboardInput dashboard = new CreateDashboardInput();
   
		UserEntity user= mock(UserEntity.class);
        dashboard.setUserId(Long.valueOf(ID));
		Mockito.when(_userManager.findById(
        any(Long.class))).thenReturn(user);
		
        Mockito.when(_mapper.createDashboardInputToDashboardEntity(any(CreateDashboardInput.class))).thenReturn(dashboardEntity); 
        Mockito.when(_dashboardManager.create(any(DashboardEntity.class))).thenReturn(dashboardEntity);
      
        Assertions.assertThat(_appService.create(dashboard)).isEqualTo(_mapper.dashboardEntityToCreateDashboardOutput(dashboardEntity)); 
    } 
    @Test
	public void createDashboard_DashboardIsNotNullAndDashboardDoesNotExistAndChildIsNullAndChildIsNotMandatory_StoreDashboard() {

		DashboardEntity dashboardEntity = mock(DashboardEntity.class);
		CreateDashboardInput dashboard = mock(CreateDashboardInput.class);
		
		Mockito.when(_mapper.createDashboardInputToDashboardEntity(any(CreateDashboardInput.class))).thenReturn(dashboardEntity);
		Mockito.when(_dashboardManager.create(any(DashboardEntity.class))).thenReturn(dashboardEntity);
		Assertions.assertThat(_appService.create(dashboard)).isEqualTo(_mapper.dashboardEntityToCreateDashboardOutput(dashboardEntity));

	}
	
    @Test
	public void updateDashboard_DashboardIsNotNullAndDashboardDoesNotExistAndChildIsNullAndChildIsNotMandatory_ReturnUpdatedDashboard() {

		DashboardEntity dashboardEntity = mock(DashboardEntity.class);
		UpdateDashboardInput dashboard = mock(UpdateDashboardInput.class);
		
		Mockito.when(_mapper.updateDashboardInputToDashboardEntity(any(UpdateDashboardInput.class))).thenReturn(dashboardEntity);
		Mockito.when(_dashboardManager.update(any(DashboardEntity.class))).thenReturn(dashboardEntity);
		Assertions.assertThat(_appService.update(ID,dashboard)).isEqualTo(_mapper.dashboardEntityToUpdateDashboardOutput(dashboardEntity));
	}
	
		
	@Test
	public void updateDashboard_DashboardIdIsNotNullAndIdExists_ReturnUpdatedDashboard() {

		DashboardEntity dashboardEntity = mock(DashboardEntity.class);
		UpdateDashboardInput dashboard= mock(UpdateDashboardInput.class);
		
		Mockito.when(_mapper.updateDashboardInputToDashboardEntity(any(UpdateDashboardInput.class))).thenReturn(dashboardEntity);
		Mockito.when(_dashboardManager.update(any(DashboardEntity.class))).thenReturn(dashboardEntity);
		Assertions.assertThat(_appService.update(ID,dashboard)).isEqualTo(_mapper.dashboardEntityToUpdateDashboardOutput(dashboardEntity));
	}
    
	@Test
	public void deleteDashboard_DashboardIsNotNullAndDashboardExists_DashboardRemoved() {

		DashboardEntity dashboard= mock(DashboardEntity.class);
		Mockito.when(_dashboardManager.findById(anyLong())).thenReturn(dashboard);
		
		_appService.delete(ID); 
		verify(_dashboardManager).delete(dashboard);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<DashboardEntity> list = new ArrayList<>();
		Page<DashboardEntity> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindDashboardByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();
//		search.setType(1);
//		search.setValue("xyz");
//		search.setOperator("equals");

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_dashboardManager.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<DashboardEntity> list = new ArrayList<>();
		DashboardEntity dashboard = mock(DashboardEntity.class);
		list.add(dashboard);
    	Page<DashboardEntity> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindDashboardByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();
//		search.setType(1);
//		search.setValue("xyz");
//		search.setOperator("equals");
		output.add(_mapper.dashboardEntityToFindDashboardByIdOutput(dashboard));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_dashboardManager.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QDashboardEntity dashboard = QDashboardEntity.dashboardEntity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
        map.put("description",searchFields);
		 Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
         builder.and(dashboard.description.eq("xyz"));
		Assertions.assertThat(_appService.searchKeyValuePair(dashboard,map,searchMap)).isEqualTo(builder);
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
        list.add("description");
        list.add("title");
		_appService.checkProperties(list);
	}
	
	@Test
	public void  search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
	
		Map<String,SearchFields> map = new HashMap<>();
		QDashboardEntity dashboard = QDashboardEntity.dashboardEntity;
		List<SearchFields> fieldsList= new ArrayList<>();
		SearchFields fields=new SearchFields();
		SearchCriteria search= new SearchCriteria();
		search.setType(3);
		search.setValue("xyz");
		search.setOperator("equals");
        fields.setFieldName("description");
        fields.setOperator("equals");
		fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
		BooleanBuilder builder = new BooleanBuilder();
        builder.or(dashboard.description.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QDashboardEntity.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void  search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}
   
   //User
	@Test
	public void GetUser_IfDashboardIdAndUserIdIsNotNullAndDashboardExists_ReturnUser() {
		DashboardEntity dashboard = mock(DashboardEntity.class);
		UserEntity user = mock(UserEntity.class);

		Mockito.when(_dashboardManager.findById(anyLong())).thenReturn(dashboard);
		Mockito.when(_dashboardManager.getUser(anyLong())).thenReturn(user);
		Assertions.assertThat(_appService.getUser(ID)).isEqualTo(_mapper.userEntityToGetUserOutput(user, dashboard));
	}

	@Test 
	public void GetUser_IfDashboardIdAndUserIdIsNotNullAndDashboardDoesNotExist_ReturnNull() {
		Mockito.when(_dashboardManager.findById(anyLong())).thenReturn(null);
		Assertions.assertThat(_appService.getUser(ID)).isEqualTo(null);
	}
	
	@Test
	public void ParseReportdashboardJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull()
	{
	    Map<String,String> joinColumnMap = new HashMap<String,String>();
		String keyString= "15";
		joinColumnMap.put("dashboardId", keyString);
		Assertions.assertThat(_appService.parseReportdashboardJoinColumn(keyString)).isEqualTo(joinColumnMap);
	}
}

