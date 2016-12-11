package com.sprhib.dao;

import java.util.List;

import com.sprhib.model.Organization;

/**
 * 
 * @author mehmetsinan.sahin
 *
 */
public interface OrganizationDAO {
	
	void addOrganization(Organization organization);
	void updateOrganization(Organization organization);
	Organization getOrganization(int id);
	void deleteOrganization(int id);
	List<Organization> getOrganizations();
	boolean hasAnyTeam(int organizationId);
}
