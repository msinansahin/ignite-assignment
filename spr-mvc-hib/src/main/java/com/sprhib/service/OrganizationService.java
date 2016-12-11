package com.sprhib.service;

import java.util.List;
import java.util.Map;

import com.sprhib.model.Organization;
import com.sprhib.utililty.OrganizationHasTeamsException;

/**
 * 
 * @author mehmetsinan.sahin
 *
 */
public interface OrganizationService {
	
	void addOrganization(Organization organization);
	void updateOrganization(Organization organization);
	Organization getOrganization(int id);
	
	/**
	 * 
	 * @param id
	 * @throws OrganizationHasTeamsException
	 */
	void deleteOrganization(int id) throws OrganizationHasTeamsException;
	List<Organization> getOrganizations();
	
	/**
	 * 
	 * @return key, value pairs
	 */
	Map<String, String> getLookUpOrganizations();

}
