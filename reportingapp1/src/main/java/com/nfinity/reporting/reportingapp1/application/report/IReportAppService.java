package com.nfinity.reporting.reportingapp1.application.report;

import java.util.List;
import javax.validation.constraints.Positive;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nfinity.reporting.reportingapp1.commons.search.SearchCriteria;
import com.nfinity.reporting.reportingapp1.application.report.dto.*;

@Service
public interface IReportAppService {

	CreateReportOutput create(CreateReportInput report);

    void delete(Long id);

    UpdateReportOutput update(Long id, UpdateReportInput input);

    FindReportByIdOutput findById(Long id);

    List<FindReportByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    
    //User
    GetUserOutput getUser(Long reportid);
}
