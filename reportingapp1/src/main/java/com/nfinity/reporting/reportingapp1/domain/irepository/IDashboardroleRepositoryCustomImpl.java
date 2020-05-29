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

import com.nfinity.reporting.reportingapp1.domain.model.RoleEntity;

@Repository
@SuppressWarnings({"unchecked", "rawtypes"})
public class IDashboardroleRepositoryCustomImpl implements IDashboardroleRepositoryCustom {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired 
	private Environment env;

	@Override
	public Page<RoleEntity> getAvailableDashboardrolesList(Long dashboardId, String search, Pageable pageable) {
		String qlString = "SELECT * FROM "+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".role e WHERE" + 
				"	e.id NOT IN (" + 
				"		SELECT role_id FROM "+env.getProperty("spring.jpa.properties.hibernate.default_schema")+".dashboardrole WHERE dashboard_id = :dashboardId" + 
				"	)" + 
				"	AND " + 
				"	(:search is null OR e.name ilike :search)";
		
		Query query = entityManager.createNativeQuery(qlString, RoleEntity.class)
				.setParameter("dashboardId",dashboardId)
				.setParameter("search","%" + search + "%")
				.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize());
		List results = query.getResultList();
		int totalRows = results.size();

		Page<RoleEntity> result = new PageImpl<RoleEntity>(results, pageable, totalRows);

		return result;
	}
	
	@Override
	public Page<RoleEntity> getDashboardrolesList(Long dashboardId, String search, Pageable pageable) {
		String qlString = "SELECT * FROM "+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".role e WHERE" + 
				"	e.id IN (" + 
				"		SELECT role_id FROM "+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".dashboardrole WHERE dashboard_id = :dashboardId" + 
				"	)" + 
				"	AND " + 
				"	(:search is null OR e.name ilike :search)";
		Query query = entityManager.createNativeQuery(qlString, RoleEntity.class)
				.setParameter("dashboardId",dashboardId)
				.setParameter("search","%" + search + "%")
				.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize());

		List results = query.getResultList();
		int totalRows = results.size();

		Page<RoleEntity> result = new PageImpl<RoleEntity>(results, pageable, totalRows);

		return result;
	}

}

