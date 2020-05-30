package com.nfinity.reporting.reportingapp1.domain.reportdashboard;

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

import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.dashboardversionreport.DashboardversionreportManager;
import com.nfinity.reporting.reportingapp1.domain.irepository.IDashboardRepository;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IReportRepository;
import com.nfinity.reporting.reportingapp1.domain.model.ReportEntity;
import com.nfinity.reporting.reportingapp1.domain.model.ReportdashboardId;
import com.nfinity.reporting.reportingapp1.domain.irepository.IDashboardversionreportRepository;
import com.nfinity.reporting.reportingapp1.commons.logging.LoggingHelper;
import com.querydsl.core.types.Predicate;

@RunWith(SpringJUnit4ClassRunner.class)
public class ReportdashboardManagerTest {

	@InjectMocks
	DashboardversionreportManager _reportdashboardManager;
	
	@Mock
	IDashboardversionreportRepository  _reportdashboardRepository;
    
    @Mock
	IDashboardRepository  _dashboardRepository;
    
    @Mock
	IReportRepository  _reportRepository;
	@Mock
    private Logger loggerMock;
   
	@Mock
	private LoggingHelper logHelper;
	
	@Mock
	private ReportdashboardId reportdashboardId;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(_reportdashboardManager);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void findReportdashboardById_IdIsNotNullAndIdExists_ReturnReportdashboard() {
		ReportdashboardEntity reportdashboard =mock(ReportdashboardEntity.class);

        Optional<ReportdashboardEntity> dbReportdashboard = Optional.of((ReportdashboardEntity) reportdashboard);
		Mockito.<Optional<ReportdashboardEntity>>when(_reportdashboardRepository.findById(any(ReportdashboardId.class))).thenReturn(dbReportdashboard);
		Assertions.assertThat(_reportdashboardManager.findById(reportdashboardId)).isEqualTo(reportdashboard);
	}

	@Test 
	public void findReportdashboardById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {

	    Mockito.<Optional<ReportdashboardEntity>>when(_reportdashboardRepository.findById(any(ReportdashboardId.class))).thenReturn(Optional.empty());
		Assertions.assertThat(_reportdashboardManager.findById(reportdashboardId)).isEqualTo(null);
	}
	
	@Test
	public void createReportdashboard_ReportdashboardIsNotNullAndReportdashboardDoesNotExist_StoreReportdashboard() {

		ReportdashboardEntity reportdashboard =mock(ReportdashboardEntity.class);
		Mockito.when(_reportdashboardRepository.save(any(ReportdashboardEntity.class))).thenReturn(reportdashboard);
		Assertions.assertThat(_reportdashboardManager.create(reportdashboard)).isEqualTo(reportdashboard);
	}

	@Test
	public void deleteReportdashboard_ReportdashboardExists_RemoveReportdashboard() {

		ReportdashboardEntity reportdashboard =mock(ReportdashboardEntity.class);
		_reportdashboardManager.delete(reportdashboard);
		verify(_reportdashboardRepository).delete(reportdashboard);
	}

	@Test
	public void updateReportdashboard_ReportdashboardIsNotNullAndReportdashboardExists_UpdateReportdashboard() {
		
		ReportdashboardEntity reportdashboard =mock(ReportdashboardEntity.class);
		Mockito.when(_reportdashboardRepository.save(any(ReportdashboardEntity.class))).thenReturn(reportdashboard);
		Assertions.assertThat(_reportdashboardManager.update(reportdashboard)).isEqualTo(reportdashboard);
		
	}

	@Test
	public void findAll_PageableIsNotNull_ReturnPage() {
		Page<ReportdashboardEntity> reportdashboard = mock(Page.class);
		Pageable pageable = mock(Pageable.class);
		Predicate predicate = mock(Predicate.class);

		Mockito.when(_reportdashboardRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(reportdashboard);
		Assertions.assertThat(_reportdashboardManager.findAll(predicate,pageable)).isEqualTo(reportdashboard);
	}
	
    //Dashboard
	@Test
	public void getDashboard_if_ReportdashboardIdIsNotNull_returnDashboard() {

		ReportdashboardEntity reportdashboard = mock(ReportdashboardEntity.class);
		DashboardEntity dashboard = mock(DashboardEntity.class);
		
        Optional<ReportdashboardEntity> dbReportdashboard = Optional.of((ReportdashboardEntity) reportdashboard);
		Mockito.<Optional<ReportdashboardEntity>>when(_reportdashboardRepository.findById(any(ReportdashboardId.class))).thenReturn(dbReportdashboard);
		Mockito.when(reportdashboard.getDashboard()).thenReturn(dashboard);
		Assertions.assertThat(_reportdashboardManager.getDashboard(reportdashboardId)).isEqualTo(dashboard);

	}
	
    //Report
	@Test
	public void getReport_if_ReportdashboardIdIsNotNull_returnReport() {

		ReportdashboardEntity reportdashboard = mock(ReportdashboardEntity.class);
		ReportEntity report = mock(ReportEntity.class);
		
        Optional<ReportdashboardEntity> dbReportdashboard = Optional.of((ReportdashboardEntity) reportdashboard);
		Mockito.<Optional<ReportdashboardEntity>>when(_reportdashboardRepository.findById(any(ReportdashboardId.class))).thenReturn(dbReportdashboard);
		Mockito.when(reportdashboard.getReport()).thenReturn(report);
		Assertions.assertThat(_reportdashboardManager.getReport(reportdashboardId)).isEqualTo(report);

	}
	
}
