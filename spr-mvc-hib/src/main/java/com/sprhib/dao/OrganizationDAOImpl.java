package com.sprhib.dao;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.sprhib.model.Organization;

/**
 * 
 * @author mehmetsinan.sahin
 *
 */
@Repository
public class OrganizationDAOImpl extends BaseDAO implements OrganizationDAO {

	@Override
	public void addOrganization(Organization organization) {
		addModel(organization);
	}

	@Override
	public void updateOrganization(Organization organization) {
		Organization orgToUpdate = getOrganization(organization.getId());
		orgToUpdate.setName(organization.getName());
		orgToUpdate.setTeams(organization.getTeams());
		updateModel(orgToUpdate);
	}

	@Override
	public Organization getOrganization(int id) {
		return getCurrentSession().get(Organization.class, id);
	}

	@Override
	public void deleteOrganization(int id) {
		deleteModel(Organization.class, id);
	}

	@Override
	public List<Organization> getOrganizations() {
		return getModels(Organization.class);
	}

	@Override
	public boolean hasAnyTeam(int organizationId) {
		String sql = "select count(elements(org.teams)) from Organization org "
				+ "where org.id = :id";
		Object count = getCurrentSession().createQuery(sql)
				.setParameter("id", organizationId)
				.getSingleResult();
		return Integer.parseInt(Objects.toString(count)) > 0;
	}
	
}
