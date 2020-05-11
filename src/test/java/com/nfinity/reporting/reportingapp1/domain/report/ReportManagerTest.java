package com.nfinity.reporting.reportingapp1.domain.report;

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

import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IReportdashboardRepository;
import com.nfinity.reporting.reportingapp1.domain.irepository.IUserRepository;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IReportRepository;
import com.nfinity.reporting.reportingapp1.commons.logging.LoggingHelper;
import com.querydsl.core.types.Predicate;

@RunWith(SpringJUnit4ClassRunner.class)
public class ReportManagerTest {

	@InjectMocks
	ReportManager _reportManager;
	
	@Mock
	IReportRepository  _reportRepository;
    
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
		MockitoAnnotations.initMocks(_reportManager);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void findReportById_IdIsNotNullAndIdExists_ReturnReport() {
		ReportEntity report =mock(ReportEntity.class);

        Optional<ReportEntity> dbReport = Optional.of((ReportEntity) report);
		Mockito.<Optional<ReportEntity>>when(_reportRepository.findById(anyLong())).thenReturn(dbReport);
		Assertions.assertThat(_reportManager.findById(ID)).isEqualTo(report);
	}

	@Test 
	public void findReportById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {

	    Mockito.<Optional<ReportEntity>>when(_reportRepository.findById(anyLong())).thenReturn(Optional.empty());
		Assertions.assertThat(_reportManager.findById(ID)).isEqualTo(null);
	}
	
	@Test
	public void createReport_ReportIsNotNullAndReportDoesNotExist_StoreReport() {

		ReportEntity report =mock(ReportEntity.class);
		Mockito.when(_reportRepository.save(any(ReportEntity.class))).thenReturn(report);
		Assertions.assertThat(_reportManager.create(report)).isEqualTo(report);
	}

	@Test
	public void deleteReport_ReportExists_RemoveReport() {

		ReportEntity report =mock(ReportEntity.class);
		_reportManager.delete(report);
		verify(_reportRepository).delete(report);
	}

	@Test
	public void updateReport_ReportIsNotNullAndReportExists_UpdateReport() {
		
		ReportEntity report =mock(ReportEntity.class);
		Mockito.when(_reportRepository.save(any(ReportEntity.class))).thenReturn(report);
		Assertions.assertThat(_reportManager.update(report)).isEqualTo(report);
		
	}

	@Test
	public void findAll_PageableIsNotNull_ReturnPage() {
		Page<ReportEntity> report = mock(Page.class);
		Pageable pageable = mock(Pageable.class);
		Predicate predicate = mock(Predicate.class);

		Mockito.when(_reportRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(report);
		Assertions.assertThat(_reportManager.findAll(predicate,pageable)).isEqualTo(report);
	}
	
    //User
	@Test
	public void getUser_if_ReportIdIsNotNull_returnUser() {

		ReportEntity report = mock(ReportEntity.class);
		UserEntity user = mock(UserEntity.class);
		
        Optional<ReportEntity> dbReport = Optional.of((ReportEntity) report);
		Mockito.<Optional<ReportEntity>>when(_reportRepository.findById(anyLong())).thenReturn(dbReport);
		Mockito.when(report.getUser()).thenReturn(user);
		Assertions.assertThat(_reportManager.getUser(ID)).isEqualTo(user);

	}
	
}
