package com.sprhib.test.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.sprhib.dao.OrganizationDAO;
import com.sprhib.model.Organization;
import com.sprhib.service.OrganizationServiceImpl;
import com.sprhib.utililty.OrganizationHasTeamsException;

/**
 * 
 * @author mehmetsinan.sahin
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class OrganizationServiceTest {

	@Mock
	OrganizationDAO organizationDAO;

	@InjectMocks
	OrganizationServiceImpl organizationService;
	
	int ORGANIZATION_ID_HAS_TEAM = 2;
	
	@Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);
		Organization organization = new Organization("org-name");
		when(organizationService.getOrganization(1)).thenReturn(organization);
		when(organizationService.getOrganizations()).thenReturn(createOrganizations(3));
		
		when(organizationDAO.hasAnyTeam(ORGANIZATION_ID_HAS_TEAM)).thenReturn(true);
    }

	@Test
	public void testGetOrganization () {
		Organization organization = new Organization("org-name");
		Assert.assertEquals(organization, organizationService.getOrganization(1));
		
		organization.setName("abc");
		Assert.assertNotEquals(organization, organizationService.getOrganization(1));
		
		//verify organizationDAO.getOrganization was called twice
		verify(organizationDAO, times(2)).getOrganization(1);

	}
	
	@Test
	public void getOrganizationsTest() {
		Assert.assertEquals(3, organizationService.getOrganizations().size());
		verify(organizationDAO).getOrganizations();
	}
	
	@Test (expected=OrganizationHasTeamsException.class)
	public void deleteOrganizationThatHasTeam() {
		organizationService.deleteOrganization(ORGANIZATION_ID_HAS_TEAM);
		verify(organizationDAO).hasAnyTeam(ORGANIZATION_ID_HAS_TEAM);
	}
	
	@Test
	public void deleteOrganizationThatHasNoTeam() {
		organizationService.deleteOrganization(1);
		verify(organizationDAO).hasAnyTeam(1);
		verify(organizationDAO).deleteOrganization(1);
	}

	private List<Organization> createOrganizations  (int count) {
		List<Organization> result = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			Organization org = new Organization("name-" + i);
			org.setId(i);
			result.add(org);
		}
		return result;
	}
}
