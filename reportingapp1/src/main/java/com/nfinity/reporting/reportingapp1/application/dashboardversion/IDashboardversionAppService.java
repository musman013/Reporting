package com.nfinity.reporting.reportingapp1.application.dashboardversion;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nfinity.reporting.reportingapp1.commons.search.SearchCriteria;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardversionId;
import com.nfinity.reporting.reportingapp1.application.dashboardversion.dto.*;

@Service
public interface IDashboardversionAppService {

	CreateDashboardversionOutput create(CreateDashboardversionInput dashboardversion);

    void delete(DashboardversionId id);

    UpdateDashboardversionOutput update(DashboardversionId id, UpdateDashboardversionInput input);

    FindDashboardversionByIdOutput findById(DashboardversionId id);

    List<FindDashboardversionByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;

    //User
    GetUserOutput getUser(DashboardversionId dashboardversionid);
}
