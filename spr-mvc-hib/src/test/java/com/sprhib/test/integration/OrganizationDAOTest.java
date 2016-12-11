package com.sprhib.test.integration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.sprhib.dao.OrganizationDAO;
import com.sprhib.init.BaseTestConfig;
import com.sprhib.init.JdbcForTestConfig;
import com.sprhib.model.Organization;

/**
 * Tests are run in memory hsqldb database
 * 
 * @author mehmetsinan.sahin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = BaseTestConfig.class)
public class OrganizationDAOTest {

	@Autowired
	OrganizationDAO dao;
	
	@Autowired
	JdbcForTestConfig nativeSQL;
	
	static String ORG_NAME = "org-name";
	static String ORG_NAME_OTHER = "org-name-other";


	@Test
	@Transactional
	public void addOrganizationTest() {
		Organization organization = createOrganization(ORG_NAME);
		dao.addOrganization(organization);
		String orgName = nativeSQL.select("select o.name from organizations o where o.id = ?", String.class, organization.getId());
		Assert.assertEquals(orgName, ORG_NAME);
	}

	@Test
	@Transactional
	public void updateOrganizationTest() {
		Organization organization = createOrganization(ORG_NAME);
		dao.addOrganization(organization);
		int id = organization.getId();
		
		String orgName = "ORGNAME";
		Organization orgToUpdate = createOrganization(orgName);
		orgToUpdate.setId(id);
		dao.updateOrganization(orgToUpdate);
		
		String testOrgName = nativeSQL.select("select o.name from organizations o where o.id = ?", 
				String.class, 
				id);

		Assert.assertEquals(orgName, testOrgName);
	}

	@Test
	@Transactional
	public void getOrganizationTest() {
		Organization organization = createOrganization(ORG_NAME);
		dao.addOrganization(organization);
		Organization test = dao.getOrganization(organization.getId());
		Assert.assertEquals(test.getName(), ORG_NAME);
	}

	@Test
	@Transactional
	public void deleteOrganization() {
		Organization organization = createOrganization(ORG_NAME);
		dao.addOrganization(organization);
		int id = organization.getId();
		dao.deleteOrganization(id);
		long testCount = nativeSQL.select("select count(1) from organizations o where o.id = ?", 
				Long.class, 
				id);
		Assert.assertEquals(0L, testCount);
	}

	@Test
	@Transactional
	public void getOrganizations() {
		Organization organization = createOrganization(ORG_NAME);
		dao.addOrganization(organization);
		organization = createOrganization(ORG_NAME_OTHER);
		dao.addOrganization(organization);	
		
		int testSize = dao.getOrganizations().size();
		long testCount = nativeSQL.select("select count(1) from organizations", 
				Long.class);
		Assert.assertEquals(testCount, testSize);
		Assert.assertEquals(2, testSize);
		
	}

	@Test
	@Transactional
	public void hasAnyTeamTest() {
		Organization organization = createOrganization(ORG_NAME);
		dao.addOrganization(organization);
		Assert.assertFalse(dao.hasAnyTeam(organization.getId()));

		int id = organization.getId();
		
		Assert.assertFalse(dao.hasAnyTeam(id));

		nativeSQL.getJdbcTemplate().execute(
				String.format("INSERT INTO teams(id, name, rating, organization_id) "
						+ "VALUES(2, 'test', 3, %d)", id));
		
		Assert.assertTrue(dao.hasAnyTeam(id));
	}
	
	private Organization createOrganization (String orgName) {
		Organization organization = new Organization();
		organization.setName(orgName);
		return organization;
	}

}
