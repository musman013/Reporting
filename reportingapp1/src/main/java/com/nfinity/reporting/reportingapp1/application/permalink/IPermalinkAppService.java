package com.nfinity.reporting.reportingapp1.application.permalink;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Positive;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nfinity.reporting.reportingapp1.commons.search.SearchCriteria;
import com.nfinity.reporting.reportingapp1.application.permalink.dto.*;

@Service
public interface IPermalinkAppService {

	CreatePermalinkOutput create(CreatePermalinkInput permalink);

    void delete(UUID id);

    UpdatePermalinkOutput update(UUID id, UpdatePermalinkInput input);

    FindPermalinkByIdOutput findById(UUID id);

    List<FindPermalinkByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;

}
