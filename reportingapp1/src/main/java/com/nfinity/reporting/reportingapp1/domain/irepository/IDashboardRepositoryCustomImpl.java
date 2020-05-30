package com.nfinity.reporting.reportingapp1.domain.irepository;

import java.util.ArrayList;
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

import com.nfinity.reporting.reportingapp1.application.dashboard.dto.DashboardDetailsOutput;

@Repository
@SuppressWarnings({"unchecked"})
public class IDashboardRepositoryCustomImpl implements IDashboardRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired 
	private Environment env;

	@Override
	public Page<DashboardDetailsOutput> getAllDashboardsByUserId(Long userId, String search, Pageable pageable) throws Exception {
		String qlString = ""
				+ "SELECT rep.*, "
				+ "       ru.is_resetted, "
				+ "       ru.owner_sharing_status, "
				+ "       ru.recipient_sharing_status, "
				+ "       ru.editable, "
				+ "       ru.is_assigned_by_role, "
				+ "       ru.is_refreshed "
				+ "FROM reporting.dashboarduser ru "
				+ "RIGHT OUTER JOIN "
				+ "  (SELECT rv.*, r.*, "
				+ "          (CASE "
				+ "               WHEN rv.dashboard_id NOT IN "
				+ "                      (SELECT dashboard_id "
				+ "                       FROM reporting.dashboarduser ru "
				+ "                       WHERE ru.dashboard_id = rv.dashboard_id) THEN 0 "
				+ "               ELSE 1 "
				+ "           END) AS shared_with_others, "
				+ "          (CASE "
				+ "               WHEN rv.user_id IN "
				+ "                      (SELECT user_id "
				+ "                       FROM reporting.dashboarduser ru "
				+ "                       WHERE ru.user_id =rv.user_id "
				+ "                         AND ru.dashboard_id = rv.dashboard_id) THEN 1 "
				+ "               ELSE 0 "
				+ "           END) AS shared_with_me "
				+ "   FROM reporting.dashboard r, "
				+ "        reporting.dashboardversion rv "
				+ "   WHERE rv.dashboard_id = r.id "
				+ "     AND rv.user_id = :userId "
				+ "     AND (:search is null OR rv.title ilike :search) "
				+ "     AND rv.version = 'running' ) AS rep ON ru.dashboard_id = rep.id and ru.user_id = rep.user_id";
		
		
//		"SELECT rv.*,u.editable,u.is_assigned_by_role,u.is_refreshed,u.is_resetted,u.owner_sharing_status,u.recipient_sharing_status, (CASE WHEN rv.user_id IN "
//		+ "(Select owner_id from "+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".report r where r.owner_id =rv.user_id and r.id = rv.report_id) THEN 1 ELSE 0 END) AS shared_with_others, "
//		+ "(CASE WHEN rv.user_id IN (Select user_id from "+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".reportuser ru where ru.user_id =rv.user_id and ru.report_id = rv.report_id) THEN 1 ELSE 0 END) AS shared_with_me "
//		+ "FROM "+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".reportversion rv, "
//		+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".reportuser u where rv.report_id = u.report_id and rv.user_id =:userId and rv.version = 'running'"
//		+"	AND " + 
//		"(:search is null OR rv.title ilike :search)";

		Query query = 
				entityManager.createNativeQuery(qlString)
				.setParameter("userId",userId)
				.setParameter("search","%" + search + "%")
				.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize());
		List<Object[]> results = query.getResultList();
		List<DashboardDetailsOutput> finalResults = new ArrayList<>();
//		if(results == null || results.isEmpty())
//		{
//			qlString = "SELECT rv.*,r.*," + 
//					"(CASE WHEN rv.dashboard_id NOT IN (Select dashboard_id from reporting.dashboarduser ru where ru.dashboard_id = rv.dashboard_id) THEN 0 ELSE 1 END) AS shared_with_others, " + 
//					"(CASE WHEN rv.user_id IN (Select user_id from reporting.dashboarduser ru where ru.user_id =rv.user_id and ru.dashboard_id = rv.dashboard_id) THEN 1 ELSE 0 END) AS shared_with_me " + 
//					"FROM reporting.dashboardversion rv, reporting.dashboard r " + 
//					"where rv.dashboard_id = r.id and rv.user_id =:userId and rv.version = 'running' "+
//					" AND " + 
//					"(:search is null OR rv.title ilike :search)";
//			
//			query = entityManager.createNativeQuery(qlString)
//					.setParameter("userId",userId)
//					.setParameter("search","%" + search + "%")
//					.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
//					.setMaxResults(pageable.getPageSize());
//			results = query.getResultList();
//			
//			for(Object[] obj : results){
//				DashboardDetailsOutput dashboardDetails = new DashboardDetailsOutput();
//
//				// Here you manually obtain value from object and map to your pojo setters
//				dashboardDetails.setDashboardId(obj[0]!=null ? Long.parseLong(obj[0].toString()) : null);
//				dashboardDetails.setUserId(obj[1]!=null ? Long.parseLong(obj[1].toString()) : null);
//				dashboardDetails.setVersion(obj[2]!=null ? (obj[2].toString()) : null);
//			    dashboardDetails.setDescription(obj[3]!=null ? (obj[3].toString()) : null);
//				dashboardDetails.setTitle(obj[4]!=null ? (obj[4].toString()) : null);
//				dashboardDetails.setOwnerId(obj[7]!=null ? Long.parseLong(obj[7].toString()) : null);
//				dashboardDetails.setSharedWithOthers(Integer.parseInt(obj[8].toString()) == 0 ? false :true);
//				dashboardDetails.setSharedWithMe(Integer.parseInt(obj[9].toString()) == 0 ? false :true);
//			
//				finalResults.add(dashboardDetails);
//			}
//		}
//		
//		else
//		{
//		
		
		for(Object[] obj : results){
			DashboardDetailsOutput dashboardDetails = new DashboardDetailsOutput();

			// Here you manually obtain value from object and map to your pojo setters
			dashboardDetails.setDashboardId(obj[0]!=null ? Long.parseLong(obj[0].toString()) : null);
			dashboardDetails.setUserId(obj[1]!=null ? Long.parseLong(obj[1].toString()) : null);
			dashboardDetails.setVersion(obj[2]!=null ? (obj[2].toString()) : null);
			dashboardDetails.setDescription(obj[3]!=null ? (obj[3].toString()) : null);
			dashboardDetails.setTitle(obj[4]!=null ? (obj[4].toString()) : null);
	
			dashboardDetails.setIsPublished(obj[6].toString() == "true" ? true : false);
			dashboardDetails.setOwnerId(obj[7]!=null ? Long.parseLong(obj[7].toString()) : null);
			
			dashboardDetails.setSharedWithOthers(Integer.parseInt(obj[8].toString()) == 0 ? false :true);
			dashboardDetails.setSharedWithMe(Integer.parseInt(obj[9].toString()) == 0 ? false :true);
			
			
			dashboardDetails.setIsResetted(obj[10] != null && obj[10].toString() == "true" ? true : false);
			dashboardDetails.setOwnerSharingStatus(obj[11] != null && obj[11].toString() == "true" ? true : false);
			dashboardDetails.setRecipientSharingStatus(obj[12] != null && obj[12].toString() == "true" ? true : false);
			dashboardDetails.setEditable(obj[13] != null && obj[13].toString() == "true" ? true : false);
			dashboardDetails.setIsAssignedByRole(obj[14] != null && obj[14].toString() == "true" ? true : false);
			dashboardDetails.setIsRefreshed(obj[15] != null && obj[15].toString() == "true" ? true : false);
			

			finalResults.add(dashboardDetails);
//		}

		}
		int totalRows = results.size();

		Page<DashboardDetailsOutput> result = new PageImpl<DashboardDetailsOutput>(finalResults, pageable, totalRows);

		return result;
	}


	@Override
	public Page<DashboardDetailsOutput> getSharedDashboardsByUserId(Long userId, String search, Pageable pageable) throws Exception {
			
		String qlString = "SELECT rv.*,u.editable,u.is_assigned_by_role,u.is_refreshed,u.is_resetted,u.owner_sharing_status,u.recipient_sharing_status,r.owner_id FROM "+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".dashboardversion rv, "
				+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".dashboarduser u, " + env.getProperty("spring.jpa.properties.hibernate.default_schema")+".dashboard r "
				+ "WHERE rv.dashboard_id IN " + 
				"(SELECT dashboard_id FROM "+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".dashboarduser WHERE user_id= rv.user_id and rv.dashboard_id= dashboard_id) " 
				+ "and rv.dashboard_id = u.dashboard_id and rv.dashboard_id = r.id and rv.user_id =:userId and rv.version = 'running' "
				+ "AND " + 
				"(:search is null OR rv.title ilike :search)";


		Query query = 
				entityManager.createNativeQuery(qlString)
				.setParameter("userId",userId)
				.setParameter("search","%" + search + "%")
				.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize());
		List<Object[]> results = query.getResultList();
		List<DashboardDetailsOutput> finalResults = new ArrayList<>();
		for(Object[] obj : results){
			DashboardDetailsOutput dashboardDetails = new DashboardDetailsOutput();

			// Here you manually obtain value from object and map to your pojo setters
			dashboardDetails.setDashboardId(obj[0]!=null ? Long.parseLong(obj[0].toString()) : null);
			dashboardDetails.setUserId(obj[1]!=null ? Long.parseLong(obj[1].toString()) : null);
			dashboardDetails.setVersion(obj[2]!=null ? (obj[2].toString()) : null);
			dashboardDetails.setDescription(obj[3]!=null ? (obj[3].toString()) : null);
			dashboardDetails.setTitle(obj[4]!=null ? (obj[4].toString()) : null);
			dashboardDetails.setEditable(obj[5].toString() == "true" ? true : false);
			dashboardDetails.setIsAssignedByRole(obj[6].toString() == "true" ? true : false);
			dashboardDetails.setIsRefreshed(obj[7].toString() == "true" ? true : false);
			dashboardDetails.setIsResetted(obj[8].toString() == "true" ? true : false);
			dashboardDetails.setOwnerSharingStatus(obj[9].toString() == "true" ? true : false);
			dashboardDetails.setRecipientSharingStatus(obj[10].toString() == "true" ? true : false);
			dashboardDetails.setOwnerId(obj[11]!=null ? Long.parseLong(obj[11].toString()) : null);
			dashboardDetails.setSharedWithMe(true);

			finalResults.add(dashboardDetails);
		}


		int totalRows = results.size();

		Page<DashboardDetailsOutput> result = new PageImpl<DashboardDetailsOutput>(finalResults, pageable, totalRows);

		return result;
	}
}
