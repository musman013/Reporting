package com.nfinity.reporting.reportingapp1.domain.irepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.transform.AliasToBeanResultTransformer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.nfinity.reporting.reportingapp1.application.report.dto.ReportDetailsOutput;

@Repository
@SuppressWarnings({"unchecked", "rawtypes"})
public class IReportRepositoryCustomImpl implements IReportRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired 
	private Environment env;

	@Override
	public Page<ReportDetailsOutput> getAllReportsByUserId(Long userId, String search, Pageable pageable) throws Exception {
		String qlString = "SELECT rv.*,u.editable,u.is_assigned_by_role,u.is_refreshed,u.is_resetted,u.owner_sharing_status,u.recipient_sharing_status, (CASE WHEN rv.user_id IN "
				+ "(Select owner_id from "+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".report r where r.owner_id =rv.user_id and r.id = rv.report_id) THEN 1 ELSE 0 END) AS shared_with_others, "
				+ "(CASE WHEN rv.user_id IN (Select user_id from "+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".reportuser ru where ru.user_id =rv.user_id and ru.report_id = rv.report_id) THEN 1 ELSE 0 END) AS shared_with_me "
				+ "FROM "+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".reportversion rv, "
				+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".reportuser u where rv.report_id = u.report_id and rv.user_id =:userId and rv.version = 'running'"
				+"	AND " + 
				"(:search is null OR rv.title ilike :search)";

		Query query = 
				entityManager.createNativeQuery(qlString)
				.setParameter("userId",userId)
				.setParameter("search","%" + search + "%")
				.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize());
		List<Object[]> results = query.getResultList();
		List<ReportDetailsOutput> finalResults = new ArrayList<>();
		for(Object[] obj : results){
			ReportDetailsOutput reportDetails = new ReportDetailsOutput();

			// Here you manually obtain value from object and map to your pojo setters
			reportDetails.setReportId(obj[0]!=null ? Long.parseLong(obj[0].toString()) : null);
			reportDetails.setUserId(obj[1]!=null ? Long.parseLong(obj[1].toString()) : null);
			reportDetails.setVersion(obj[2]!=null ? (obj[2].toString()) : null);
			reportDetails.setCtype(obj[3]!=null ? (obj[3].toString()) : null);
			reportDetails.setDescription(obj[4]!=null ? (obj[4].toString()) : null);
			JSONParser parser = new JSONParser();
			JSONObject json;
			try {
				json = (JSONObject) parser.parse(obj[5].toString());
				reportDetails.setQuery(json);
			} catch (ParseException e) {
				e.printStackTrace();
				throw new Exception("Error occured while parsing query");

			}

			reportDetails.setReportType(obj[6]!=null ? (obj[6].toString()) : null);
			reportDetails.setReportWidth(obj[7]!=null ? (obj[7].toString()) : null);
			reportDetails.setTitle(obj[8]!=null ? (obj[8].toString()) : null);
			reportDetails.setEditable(obj[9].toString() == "true" ? true : false);
			reportDetails.setIsAssignedByRole(obj[10].toString() == "true" ? true : false);
			reportDetails.setIsRefreshed(obj[11].toString() == "true" ? true : false);
			reportDetails.setIsResetted(obj[12].toString() == "true" ? true : false);
			reportDetails.setOwnerSharingStatus(obj[13].toString() == "true" ? true : false);
			reportDetails.setRecipientSharingStatus(obj[14].toString() == "true" ? true : false);
			reportDetails.setSharedWithOthers(Integer.parseInt(obj[15].toString()) == 0 ? false :true);
			reportDetails.setSharedWithMe(Integer.parseInt(obj[16].toString()) == 0 ? false :true);

			finalResults.add(reportDetails);
		}


		int totalRows = results.size();

		Page<ReportDetailsOutput> result = new PageImpl<ReportDetailsOutput>(finalResults, pageable, totalRows);

		return result;
	}


	@Override
	public Page<ReportDetailsOutput> getSharedReportsByUserId(Long userId, String search, Pageable pageable) throws Exception {
//		String qlString = "SELECT * FROM "+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".reportversion rv"
//				+env.getProperty("spring.jpa.properties.hibernate.default_schema")+".reportuser u WHERE rv.report_id IN " 
//				+ "(SELECT report_id FROM "+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".reportuser WHERE user_id= rv.user_id AND report_id = rv.report_id) "
//				+ "AND rv.user_id =:userId AND rv.report_id = u.report_id AND and rv.version = 'running'" 
//				+ "AND " + 
//				"(:search is null OR rv.title ilike :search)";
//		
//		
	String qlString = "SELECT rv.*,u.editable,u.is_assigned_by_role,u.is_refreshed,u.is_resetted,u.owner_sharing_status,u.recipient_sharing_status FROM "+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".reportversion rv, "
            + env.getProperty("spring.jpa.properties.hibernate.default_schema")+".reportuser u WHERE rv.report_id IN " + 
			"(SELECT report_id FROM "+ env.getProperty("spring.jpa.properties.hibernate.default_schema")+".reportuser WHERE user_id= rv.user_id and rv.report_id= report_id) " 
            + "and rv.report_id = u.report_id and rv.user_id =:userId and rv.version = 'running'"
			+ "AND " + 
			"(:search is null OR rv.title ilike :search)";

		Query query = 
				entityManager.createNativeQuery(qlString)
				.setParameter("userId",userId)
				.setParameter("search","%" + search + "%")
				.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize());
		List<Object[]> results = query.getResultList();
		List<ReportDetailsOutput> finalResults = new ArrayList<>();
		for(Object[] obj : results){
			ReportDetailsOutput reportDetails = new ReportDetailsOutput();

			// Here you manually obtain value from object and map to your pojo setters
			reportDetails.setReportId(obj[0]!=null ? Long.parseLong(obj[0].toString()) : null);
			reportDetails.setUserId(obj[1]!=null ? Long.parseLong(obj[1].toString()) : null);
			reportDetails.setVersion(obj[2]!=null ? (obj[2].toString()) : null);
			reportDetails.setCtype(obj[3]!=null ? (obj[3].toString()) : null);
			reportDetails.setDescription(obj[4]!=null ? (obj[4].toString()) : null);
			JSONParser parser = new JSONParser();
			JSONObject json;
			try {
				json = (JSONObject) parser.parse(obj[5].toString());
				reportDetails.setQuery(json);
			} catch (ParseException e) {
				e.printStackTrace();
				throw new Exception("Error occured while parsing query");

			}

			reportDetails.setReportType(obj[6]!=null ? (obj[6].toString()) : null);
			reportDetails.setReportWidth(obj[7]!=null ? (obj[7].toString()) : null);
			reportDetails.setTitle(obj[8]!=null ? (obj[8].toString()) : null);
			reportDetails.setEditable(obj[9].toString() == "true" ? true : false);
			reportDetails.setIsAssignedByRole(obj[10].toString() == "true" ? true : false);
			reportDetails.setIsRefreshed(obj[11].toString() == "true" ? true : false);
			reportDetails.setIsResetted(obj[12].toString() == "true" ? true : false);
			reportDetails.setOwnerSharingStatus(obj[13].toString() == "true" ? true : false);
			reportDetails.setRecipientSharingStatus(obj[14].toString() == "true" ? true : false);
			reportDetails.setSharedWithMe(true);
			
			finalResults.add(reportDetails);
		}


		int totalRows = results.size();

		Page<ReportDetailsOutput> result = new PageImpl<ReportDetailsOutput>(finalResults, pageable, totalRows);

		return result;
	}
}

