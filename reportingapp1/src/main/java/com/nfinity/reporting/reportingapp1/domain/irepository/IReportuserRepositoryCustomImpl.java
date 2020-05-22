package com.nfinity.reporting.reportingapp1.domain.irepository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.nfinity.reporting.reportingapp1.domain.model.ReportuserEntity;
import com.nfinity.reporting.reportingapp1.domain.model.UserEntity;

@Repository
@SuppressWarnings({"unchecked", "rawtypes"})
public class IReportuserRepositoryCustomImpl implements IReportuserRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired 
	private Environment env;

	@Override
	public Page<UserEntity> getAvailableReportusersList(Long reportId, String search, Pageable pageable) {
		String qlString = "SELECT * FROM "+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".user e WHERE" + 
				" e.id NOT IN (" + 
				" SELECT user_id FROM "+env.getProperty("spring.jpa.properties.hibernate.default_schema")+".reportuser WHERE report_id = :reportId" + 
				" )"+ " AND e.id NOT IN (SELECT owner_id FROM "+env.getProperty("spring.jpa.properties.hibernate.default_schema")+".report WHERE id = :reportId)" +
				" AND " + 
				" (:search is null OR e.user_name ilike :search)";
		
		Query query = entityManager.createNativeQuery(qlString, UserEntity.class)
				.setParameter("reportId",reportId)
				.setParameter("search","%" + search + "%")
				.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize());
		List results = query.getResultList();
		int totalRows = results.size();

		Page<UserEntity> result = new PageImpl<UserEntity>(results, pageable, totalRows);

		return result;
	}
	
	@Override
	public Page<UserEntity> getReportusersList(Long reportId, String search, Pageable pageable) {
		String qlString = "SELECT * FROM "+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".user e WHERE" + 
				" e.id IN (" + 
				" SELECT user_id FROM "+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".reportuser WHERE report_id = :reportId" + 
				" )" + 
				" AND " + 
				" (:search is null OR e.user_name ilike :search)";
		Query query = entityManager.createNativeQuery(qlString, UserEntity.class)
				.setParameter("reportId",reportId)
				.setParameter("search","%" + search + "%")
				.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize());

		List results = query.getResultList();
		int totalRows = results.size();

		Page<UserEntity> result = new PageImpl<UserEntity>(results, pageable, totalRows);

		return result;
	}
	
//	@Override
//	public List<ReportuserEntity> findByReportId(Long reportId)
//	{
//		
//		String qlString = "SELECT * FROM "+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".reportuser r WHERE" + 
//				" r.report_id = :reportId" ;
//		Query query = entityManager.createNativeQuery(qlString, ReportuserEntity.class)
//				.setParameter("reportId",reportId);
//				
//		List<ReportuserEntity> results = query.getResultList();
//		
//		return results;
//		
//	}

}
