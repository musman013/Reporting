package com.nfinity.reporting.reportingapp1.domain.dashboardrole;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardroleEntity;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardroleId;
import com.nfinity.reporting.reportingapp1.domain.irepository.IRoleRepository;
import com.nfinity.reporting.reportingapp1.domain.model.RoleEntity;
import com.nfinity.reporting.reportingapp1.domain.irepository.IDashboardRepository;
import com.nfinity.reporting.reportingapp1.domain.irepository.IDashboardroleRepository;
import com.nfinity.reporting.reportingapp1.domain.model.DashboardEntity;
import com.querydsl.core.types.Predicate;

@Component
public class DashboardroleManager implements IDashboardroleManager {

	@Autowired
	IDashboardroleRepository  _dashboardroleRepository;

	@Autowired
	IRoleRepository  _roleRepository;

	@Autowired
	IDashboardRepository  _dashboardRepository;

	public DashboardroleEntity create(DashboardroleEntity dashboardrole) {

		return _dashboardroleRepository.save(dashboardrole);
	}

	public void delete(DashboardroleEntity dashboardrole) {

		_dashboardroleRepository.delete(dashboardrole);	
	}

	public DashboardroleEntity update(DashboardroleEntity dashboardrole) {

		return _dashboardroleRepository.save(dashboardrole);
	}

	public DashboardroleEntity findById(DashboardroleId dashboardroleId) {

		Optional<DashboardroleEntity> dbDashboardrole= _dashboardroleRepository.findById(dashboardroleId);
		if(dbDashboardrole.isPresent()) {
			DashboardroleEntity existingDashboardrole = dbDashboardrole.get();
			return existingDashboardrole;
		} else {
			return null;
		}

	}

	public List<DashboardroleEntity> findByRoleId(Long id)
	{
		return _dashboardroleRepository.findByRoleId(id);
	}

	public Page<RoleEntity> getAvailableRolesList(Long dashboardId, String search, Pageable pageable)
	{
		return _dashboardroleRepository.getAvailableDashboardrolesList(dashboardId, search, pageable);
	}

	public Page<RoleEntity> getDashboardRolesList(Long dashboardId, String search, Pageable pageable) {
		return _dashboardroleRepository.getDashboardrolesList(dashboardId, search, pageable);
	}

	public Page<DashboardroleEntity> findAll(Predicate predicate, Pageable pageable) {

		return _dashboardroleRepository.findAll(predicate,pageable);
	}

	//Role
	public RoleEntity getRole(DashboardroleId dashboardroleId) {

		Optional<DashboardroleEntity> dbDashboardrole= _dashboardroleRepository.findById(dashboardroleId);
		if(dbDashboardrole.isPresent()) {
			DashboardroleEntity existingDashboardrole = dbDashboardrole.get();
			return existingDashboardrole.getRole();
		} else {
			return null;
		}
	}

	//Dashboard
	public DashboardEntity getDashboard(DashboardroleId dashboardroleId) {

		Optional<DashboardroleEntity> dbDashboardrole= _dashboardroleRepository.findById(dashboardroleId);
		if(dbDashboardrole.isPresent()) {
			DashboardroleEntity existingDashboardrole = dbDashboardrole.get();
			return existingDashboardrole.getDashboard();
		} else {
			return null;
		}
	}
}
