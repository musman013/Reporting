package com.nfinity.reporting.reportingapp1.application.dashboardversion;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nfinity.reporting.reportingapp1.commons.search.SearchCriteria;
import com.nfinity.reporting.reportingapp1.application.dashboardversion.dto.*;

@Service
public interface IDashboardversionAppService {

	CreateDashboardversionOutput create(CreateDashboardversionInput dashboardversion);

    void delete(Long id);

    UpdateDashboardversionOutput update(Long id, UpdateDashboardversionInput input);

    FindDashboardversionByIdOutput findById(Long id);

    List<FindDashboardversionByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;

    //User
    GetUserOutput getUser(Long dashboardversionid);
}
