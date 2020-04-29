package com.nfinity.reporting.reportingapp1.application.dashboard;

import java.util.List;
import javax.validation.constraints.Positive;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nfinity.reporting.reportingapp1.commons.search.SearchCriteria;
import com.nfinity.reporting.reportingapp1.application.dashboard.dto.*;

@Service
public interface IDashboardAppService {

	CreateDashboardOutput create(CreateDashboardInput dashboard);

    void delete(Long id);

    UpdateDashboardOutput update(Long id, UpdateDashboardInput input);

    FindDashboardByIdOutput findById(Long id);

    List<FindDashboardByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;

    
    //User
    GetUserOutput getUser(Long dashboardid);
}
