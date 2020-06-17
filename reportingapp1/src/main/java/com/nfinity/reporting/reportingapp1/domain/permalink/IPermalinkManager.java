package com.nfinity.reporting.reportingapp1.domain.permalink;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

import javax.validation.constraints.Positive;
import com.nfinity.reporting.reportingapp1.domain.model.PermalinkEntity;

public interface IPermalinkManager {
    // CRUD Operations
    PermalinkEntity create(PermalinkEntity permalink);

    void delete(PermalinkEntity permalink);

    PermalinkEntity update(PermalinkEntity permalink);

    PermalinkEntity findById(UUID id);
	
    Page<PermalinkEntity> findAll(Predicate predicate, Pageable pageable);
}
