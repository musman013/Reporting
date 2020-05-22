package com.nfinity.reporting.reportingapp1.application.reportuser;

import java.util.List;
import javax.validation.constraints.Positive;
import com.nfinity.reporting.reportingapp1.domain.model.ReportuserId;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nfinity.reporting.reportingapp1.commons.search.SearchCriteria;
import com.nfinity.reporting.reportingapp1.application.reportuser.dto.*;

@Service
public interface IReportuserAppService {

	CreateReportuserOutput create(CreateReportuserInput reportuser);

    void delete(ReportuserId reportuserId);

    UpdateReportuserOutput update(ReportuserId reportuserId, UpdateReportuserInput input);

    FindReportuserByIdOutput findById(ReportuserId reportuserId);

    List<FindReportuserByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;

	public ReportuserId parseReportuserKey(String keysString);
    
    
    //Report
    GetReportOutput getReport(ReportuserId reportuserId);
}
