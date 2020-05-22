package com.nfinity.reporting.reportingapp1.application.reportversion;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nfinity.reporting.reportingapp1.application.reportversion.dto.*;
import com.nfinity.reporting.reportingapp1.commons.search.SearchCriteria; 
import com.nfinity.reporting.reportingapp1.domain.model.ReportversionId;

@Service
public interface IReportversionAppService {
	
	CreateReportversionOutput create(CreateReportversionInput report);

    void delete(ReportversionId id);

    UpdateReportversionOutput update(ReportversionId id, UpdateReportversionInput input);

    FindReportversionByIdOutput findById(ReportversionId id);

    List<FindReportversionByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    
    //User
    GetUserOutput getUser(ReportversionId reportid);

}
