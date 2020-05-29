package com.nfinity.reporting.reportingapp1.application.dashboardrole;

import java.util.List;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardroleId;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nfinity.reporting.reportingapp1.commons.search.SearchCriteria;
import com.nfinity.reporting.reportingapp1.application.dashboardrole.dto.*;

@Service
public interface IDashboardroleAppService {

	CreateDashboardroleOutput create(CreateDashboardroleInput dashboardrole);

    void delete(DashboardroleId dashboardroleId);

    UpdateDashboardroleOutput update(DashboardroleId dashboardroleId, UpdateDashboardroleInput input);

    FindDashboardroleByIdOutput findById(DashboardroleId dashboardroleId);

    List<FindDashboardroleByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;

	public DashboardroleId parseDashboardroleKey(String keysString);
    
    //Dashboard
    GetDashboardOutput getDashboard(DashboardroleId dashboardroleId);
}
