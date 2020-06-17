package com.nfinity.reporting.reportingapp1.domain.irepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.nfinity.reporting.reportingapp1.domain.model.PermalinkEntity;
@Repository
public interface IPermalinkRepository extends JpaRepository<PermalinkEntity, UUID>,QuerydslPredicateExecutor<PermalinkEntity> {

}
