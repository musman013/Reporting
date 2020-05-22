package com.nfinity.reporting.reportingapp1.application.reportrole;

import java.util.List;
import com.nfinity.reporting.reportingapp1.domain.model.ReportroleId;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nfinity.reporting.reportingapp1.commons.search.SearchCriteria;
import com.nfinity.reporting.reportingapp1.application.reportrole.dto.*;

@Service
public interface IReportroleAppService {

	CreateReportroleOutput create(CreateReportroleInput reportrole);

    void delete(ReportroleId reportroleId);

    UpdateReportroleOutput update(ReportroleId reportroleId, UpdateReportroleInput input);

    FindReportroleByIdOutput findById(ReportroleId reportroleId);

    List<FindReportroleByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;

	public ReportroleId parseReportroleKey(String keysString);
    
    //Report
    GetReportOutput getReport(ReportroleId reportroleId);
}
