package com.nfinity.reporting.reportingapp1.application.authorization.user;

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

import com.nfinity.reporting.reportingapp1.domain.authorization.user.*;
import com.nfinity.reporting.reportingapp1.commons.search.*;
import com.nfinity.reporting.reportingapp1.application.authorization.user.dto.*;
import com.nfinity.reporting.reportingapp1.domain.model.QUserEntity;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.dashboard.DashboardManager;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.domain.report.ReportManager;
import com.nfinity.reporting.reportingapp1.domain.authorization.role.RoleManager;
import com.nfinity.reporting.reportingapp1.domain.model.RoleEntity;
import com.nfinity.reporting.reportingapp1.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserAppServiceTest {

	@InjectMocks
	@Spy
	UserAppService _appService;

	@Mock
	private UserManager _userManager;
	
    @Mock
	private DashboardManager  _dashboardManager;
	
    @Mock
	private ReportManager  _reportManager;
	
	@Mock
	private IUserMapper _mapper;

	@Mock
	private Logger loggerMock;

	@Mock
	private LoggingHelper logHelper;
	
	@Mock
	private RoleManager _roleManager;

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
	public void findUserById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {

		Mockito.when(_userManager.findById(anyLong())).thenReturn(null);
		Assertions.assertThat(_appService.findById(ID)).isEqualTo(null);
	}
	
	@Test
	public void findUserById_IdIsNotNullAndIdExists_ReturnUser() {

		UserEntity user = mock(UserEntity.class);
		Mockito.when(_userManager.findById(anyLong())).thenReturn(user);
		Assertions.assertThat(_appService.findById(ID)).isEqualTo(_mapper.userEntityToFindUserByIdOutput(user));
	}
	
	 @Test 
    public void createUser_UserIsNotNullAndUserDoesNotExist_StoreUser() { 
 
       UserEntity userEntity = mock(UserEntity.class); 
       CreateUserInput user = new CreateUserInput();
   
		
        Mockito.when(_mapper.createUserInputToUserEntity(any(CreateUserInput.class))).thenReturn(userEntity); 
        Mockito.when(_userManager.create(any(UserEntity.class))).thenReturn(userEntity);
      
        Assertions.assertThat(_appService.create(user)).isEqualTo(_mapper.userEntityToCreateUserOutput(userEntity)); 
    } 
	@Test
	public void updateUser_UserIdIsNotNullAndIdExists_ReturnUpdatedUser() {

		UserEntity userEntity = mock(UserEntity.class);
		UpdateUserInput user= mock(UpdateUserInput.class);
		
		Mockito.when(_mapper.updateUserInputToUserEntity(any(UpdateUserInput.class))).thenReturn(userEntity);
		Mockito.when(_userManager.update(any(UserEntity.class))).thenReturn(userEntity);
		Assertions.assertThat(_appService.update(ID,user)).isEqualTo(_mapper.userEntityToUpdateUserOutput(userEntity));
	}
    
	@Test
	public void deleteUser_UserIsNotNullAndUserExists_UserRemoved() {

		UserEntity user= mock(UserEntity.class);
		Mockito.when(_userManager.findById(anyLong())).thenReturn(user);
		
		_appService.delete(ID); 
		verify(_userManager).delete(user);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<UserEntity> list = new ArrayList<>();
		Page<UserEntity> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindUserByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();
//		search.setType(1);
//		search.setValue("xyz");
//		search.setOperator("equals");

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_userManager.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<UserEntity> list = new ArrayList<>();
		UserEntity user = mock(UserEntity.class);
		list.add(user);
    	Page<UserEntity> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindUserByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();
//		search.setType(1);
//		search.setValue("xyz");
//		search.setOperator("equals");
		output.add(_mapper.userEntityToFindUserByIdOutput(user));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_userManager.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QUserEntity user = QUserEntity.userEntity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
        map.put("authenticationSource",searchFields);
		 Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
         builder.and(user.authenticationSource.eq("xyz"));
		Assertions.assertThat(_appService.searchKeyValuePair(user,map,searchMap)).isEqualTo(builder);
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
        list.add("authenticationSource");
        list.add("emailAddress");
        list.add("emailConfirmationCode");
        list.add("firstName");
        list.add("isPhoneNumberConfirmed");
        list.add("lastName");
        list.add("password");
        list.add("passwordResetCode");
        list.add("phoneNumber");
        list.add("userName");
		_appService.checkProperties(list);
	}
	
	@Test
	public void  search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
	
		Map<String,SearchFields> map = new HashMap<>();
		QUserEntity user = QUserEntity.userEntity;
		List<SearchFields> fieldsList= new ArrayList<>();
		SearchFields fields=new SearchFields();
		SearchCriteria search= new SearchCriteria();
		search.setType(3);
		search.setValue("xyz");
		search.setOperator("equals");
        fields.setFieldName("authenticationSource");
        fields.setOperator("equals");
		fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
		BooleanBuilder builder = new BooleanBuilder();
        builder.or(user.authenticationSource.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QUserEntity.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void  search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}
	
	@Test 
	public void findUserByName_NameIsNotNullAndUserDoesNotExist_ReturnNull() {
		Mockito.when(_userManager.findByUserName(anyString())).thenReturn(null);	
		Assertions.assertThat(_appService.findByUserName("User1")).isEqualTo(null);	
	}

	@Test
	public void findUserByName_NameIsNotNullAndUserExists_ReturnAUser() {

		UserEntity user = mock(UserEntity.class);
		Mockito.when(_userManager.findByUserName(anyString())).thenReturn(user);
		Assertions.assertThat(_appService.findByUserName("User1")).isEqualTo(_mapper.userEntityToCreateUserOutput(user));
	}

    @Test
    public void parseUserpermissionJoinColumn_StringIsNotNull_ReturnMap() {
		
		Map<String,String> joinColumnMap = new HashMap<String,String>();
		String keyString= "15";
		joinColumnMap.put("UserId", "15");
		Assertions.assertThat(_appService.parseUserpermissionJoinColumn(keyString)).isEqualTo(joinColumnMap);
		
	}
	
	@Test
    public void parseUserroleJoinColumn_StringIsNotNull_ReturnMap() {
		
		Map<String,String> joinColumnMap = new HashMap<String,String>();
		String keyString= "15";
		joinColumnMap.put("UserId", "15");
		Assertions.assertThat(_appService.parseUserroleJoinColumn(keyString)).isEqualTo(joinColumnMap);
		
	}
	
	@Test
	public void ParseDashboardJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull()
	{
	    Map<String,String> joinColumnMap = new HashMap<String,String>();
		String keyString= "15";
		joinColumnMap.put("userId", keyString);
		Assertions.assertThat(_appService.parseDashboardJoinColumn(keyString)).isEqualTo(joinColumnMap);
	}
	@Test
	public void ParseReportJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull()
	{
	    Map<String,String> joinColumnMap = new HashMap<String,String>();
		String keyString= "15";
		joinColumnMap.put("userId", keyString);
		Assertions.assertThat(_appService.parseReportJoinColumn(keyString)).isEqualTo(joinColumnMap);
	}
}

