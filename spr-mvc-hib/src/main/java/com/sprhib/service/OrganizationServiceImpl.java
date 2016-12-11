package com.sprhib.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprhib.dao.OrganizationDAO;
import com.sprhib.model.Organization;
import com.sprhib.utililty.OrganizationHasTeamsException;

/**
 * 
 * @author mehmetsinan.sahin
 *
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationDAO orgDAO;
	
	@Override
	public void addOrganization(Organization organization) {
		orgDAO.addOrganization(organization);
	}

	@Override
	public void updateOrganization(Organization organization) {
		orgDAO.updateOrganization(organization);
	}

	@Override
	public Organization getOrganization(int id) {
		return orgDAO.getOrganization(id);
	}

	@Override
	public void deleteOrganization(int id) {
		if (orgDAO.hasAnyTeam (id)) {
			throw new OrganizationHasTeamsException(id);
		}
		orgDAO.deleteOrganization(id);
	}

	@Override
	public List<Organization> getOrganizations() {
		return orgDAO.getOrganizations();
	}

	@Override
	public Map<String, String> getLookUpOrganizations() {
		Map<String, String> result = new HashMap<>();
		List<Organization> list = getOrganizations(); //assuming from cache
		list.forEach(org -> result.put(Objects.toString(org.getId()), org.getName()));
		return result;
	}

}
