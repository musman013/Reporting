package com.nfinity.reporting.reportingapp1.domain.authorization.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IDashboardRepository;
import com.nfinity.reporting.reportingapp1.domain.irepository.IReportRepository;
import com.nfinity.reporting.reportingapp1.domain.irepository.IUserRepository;
import com.nfinity.reporting.reportingapp1.commons.logging.LoggingHelper;
import com.querydsl.core.types.Predicate;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserManagerTest {

	@InjectMocks
	UserManager _userManager;
	
	@Mock
	IUserRepository  _userRepository;
    
    @Mock
	IDashboardRepository  _dashboardRepository;
    
    @Mock
	IReportRepository  _reportRepository;
	@Mock
    private Logger loggerMock;
   
	@Mock
	private LoggingHelper logHelper;
	
	private static Long ID=15L;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(_userManager);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void findUserById_IdIsNotNullAndIdExists_ReturnUser() {
		UserEntity user =mock(UserEntity.class);

        Optional<UserEntity> dbUser = Optional.of((UserEntity) user);
		Mockito.<Optional<UserEntity>>when(_userRepository.findById(anyLong())).thenReturn(dbUser);
		Assertions.assertThat(_userManager.findById(ID)).isEqualTo(user);
	}

	@Test 
	public void findUserById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {

	    Mockito.<Optional<UserEntity>>when(_userRepository.findById(anyLong())).thenReturn(Optional.empty());
		Assertions.assertThat(_userManager.findById(ID)).isEqualTo(null);
	}
	
    @Test
	public void findUserByName_NameIsNotNullAndNameExists_ReturnAUser() {
		UserEntity userEntity = mock(UserEntity.class);

		Mockito.when(_userRepository.findByUserName(anyString())).thenReturn(userEntity);
		Assertions.assertThat(_userManager.findByUserName("User1")).isEqualTo(userEntity);
	}

	@Test 
	public void findUserByName_NameIsNotNullAndNameDoesNotExist_ReturnNull() {

		Mockito.when(_userRepository.findByUserName(anyString())).thenReturn(null);
		Assertions.assertThat(_userManager.findByUserName("User1")).isEqualTo(null);
	
	}
	
	@Test
	public void createUser_UserIsNotNullAndUserDoesNotExist_StoreUser() {

		UserEntity user =mock(UserEntity.class);
		Mockito.when(_userRepository.save(any(UserEntity.class))).thenReturn(user);
		Assertions.assertThat(_userManager.create(user)).isEqualTo(user);
	}

	@Test
	public void deleteUser_UserExists_RemoveUser() {

		UserEntity user =mock(UserEntity.class);
		_userManager.delete(user);
		verify(_userRepository).delete(user);
	}

	@Test
	public void updateUser_UserIsNotNullAndUserExists_UpdateUser() {
		
		UserEntity user =mock(UserEntity.class);
		Mockito.when(_userRepository.save(any(UserEntity.class))).thenReturn(user);
		Assertions.assertThat(_userManager.update(user)).isEqualTo(user);
		
	}

	@Test
	public void findAll_PageableIsNotNull_ReturnPage() {
		Page<UserEntity> user = mock(Page.class);
		Pageable pageable = mock(Pageable.class);
		Predicate predicate = mock(Predicate.class);

		Mockito.when(_userRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(user);
		Assertions.assertThat(_userManager.findAll(predicate,pageable)).isEqualTo(user);
	}
	
}
