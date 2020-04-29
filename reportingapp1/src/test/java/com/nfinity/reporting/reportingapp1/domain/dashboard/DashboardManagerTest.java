package com.nfinity.reporting.reportingapp1.domain.dashboard;

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

import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IReportdashboardRepository;
import com.nfinity.reporting.reportingapp1.domain.irepository.IUserRepository;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IDashboardRepository;
import com.nfinity.reporting.reportingapp1.commons.logging.LoggingHelper;
import com.querydsl.core.types.Predicate;

@RunWith(SpringJUnit4ClassRunner.class)
public class DashboardManagerTest {

	@InjectMocks
	DashboardManager _dashboardManager;
	
	@Mock
	IDashboardRepository  _dashboardRepository;
    
    @Mock
	IReportdashboardRepository  _reportdashboardRepository;
    
    @Mock
	IUserRepository  _userRepository;
	@Mock
    private Logger loggerMock;
   
	@Mock
	private LoggingHelper logHelper;
	
	private static Long ID=15L;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(_dashboardManager);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void findDashboardById_IdIsNotNullAndIdExists_ReturnDashboard() {
		DashboardEntity dashboard =mock(DashboardEntity.class);

        Optional<DashboardEntity> dbDashboard = Optional.of((DashboardEntity) dashboard);
		Mockito.<Optional<DashboardEntity>>when(_dashboardRepository.findById(anyLong())).thenReturn(dbDashboard);
		Assertions.assertThat(_dashboardManager.findById(ID)).isEqualTo(dashboard);
	}

	@Test 
	public void findDashboardById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {

	    Mockito.<Optional<DashboardEntity>>when(_dashboardRepository.findById(anyLong())).thenReturn(Optional.empty());
		Assertions.assertThat(_dashboardManager.findById(ID)).isEqualTo(null);
	}
	
	@Test
	public void createDashboard_DashboardIsNotNullAndDashboardDoesNotExist_StoreDashboard() {

		DashboardEntity dashboard =mock(DashboardEntity.class);
		Mockito.when(_dashboardRepository.save(any(DashboardEntity.class))).thenReturn(dashboard);
		Assertions.assertThat(_dashboardManager.create(dashboard)).isEqualTo(dashboard);
	}

	@Test
	public void deleteDashboard_DashboardExists_RemoveDashboard() {

		DashboardEntity dashboard =mock(DashboardEntity.class);
		_dashboardManager.delete(dashboard);
		verify(_dashboardRepository).delete(dashboard);
	}

	@Test
	public void updateDashboard_DashboardIsNotNullAndDashboardExists_UpdateDashboard() {
		
		DashboardEntity dashboard =mock(DashboardEntity.class);
		Mockito.when(_dashboardRepository.save(any(DashboardEntity.class))).thenReturn(dashboard);
		Assertions.assertThat(_dashboardManager.update(dashboard)).isEqualTo(dashboard);
		
	}

	@Test
	public void findAll_PageableIsNotNull_ReturnPage() {
		Page<DashboardEntity> dashboard = mock(Page.class);
		Pageable pageable = mock(Pageable.class);
		Predicate predicate = mock(Predicate.class);

		Mockito.when(_dashboardRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(dashboard);
		Assertions.assertThat(_dashboardManager.findAll(predicate,pageable)).isEqualTo(dashboard);
	}
	
    //User
	@Test
	public void getUser_if_DashboardIdIsNotNull_returnUser() {

		DashboardEntity dashboard = mock(DashboardEntity.class);
		UserEntity user = mock(UserEntity.class);
		
        Optional<DashboardEntity> dbDashboard = Optional.of((DashboardEntity) dashboard);
		Mockito.<Optional<DashboardEntity>>when(_dashboardRepository.findById(anyLong())).thenReturn(dbDashboard);
		Mockito.when(dashboard.getUser()).thenReturn(user);
		Assertions.assertThat(_dashboardManager.getUser(ID)).isEqualTo(user);

	}
	
}
