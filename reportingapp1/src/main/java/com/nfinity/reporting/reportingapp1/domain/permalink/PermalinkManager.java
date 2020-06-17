package com.nfinity.reporting.reportingapp1.domain.permalink;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.nfinity.reporting.reportingapp1.domain.model.PermalinkEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IPermalinkRepository;
import com.querydsl.core.types.Predicate;

@Repository
public class PermalinkManager implements IPermalinkManager {

    @Autowired
    IPermalinkRepository  _permalinkRepository;
    
	public PermalinkEntity create(PermalinkEntity permalink) {

		return _permalinkRepository.save(permalink);
	}

	public void delete(PermalinkEntity permalink) {

		_permalinkRepository.delete(permalink);	
	}

	public PermalinkEntity update(PermalinkEntity permalink) {

		return _permalinkRepository.save(permalink);
	}

	public PermalinkEntity findById(UUID permalinkId) {
    	Optional<PermalinkEntity> dbPermalink= _permalinkRepository.findById(permalinkId);
		if(dbPermalink.isPresent()) {
			PermalinkEntity existingPermalink = dbPermalink.get();
		    return existingPermalink;
		} else {
		    return null;
		}

	}

	public Page<PermalinkEntity> findAll(Predicate predicate, Pageable pageable) {

		return _permalinkRepository.findAll(predicate,pageable);
	}
}
