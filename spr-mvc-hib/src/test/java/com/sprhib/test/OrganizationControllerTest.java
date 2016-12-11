package com.sprhib.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.sprhib.controller.AController;
import com.sprhib.init.BaseTestConfig;
import com.sprhib.model.Organization;
import com.sprhib.model.Team;
import com.sprhib.service.OrganizationService;
import com.sprhib.service.TeamService;

/**
 * 
 * @author mehmetsinan.sahin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = BaseTestConfig.class)
@Rollback (value=false)
public class OrganizationControllerTest {

	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private TeamService teamService;

	private MockMvc mockMvc;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testAddGet() throws Exception {
		mockMvc.perform(get("/organization/add"))
			.andExpect(status().isOk())
			.andExpect(view().name(AController.ORGANIZATION_ADD_FORM))
            .andExpect(forwardedUrl("/WEB-INF/pages/add-organization-form.jsp"));
	}
	
	@Test
	public void testEditGet() throws Exception {
		Organization organization = addOrganizationToDb("org-name");
		int orgId = organization.getId();
		
		mockMvc.perform(get("/organization/edit/" + orgId))
			.andExpect(status().isOk())
			.andExpect(view().name(AController.ORGANIZATION_EDIT_FORM))
            .andExpect(forwardedUrl("/WEB-INF/pages/edit-organization-form.jsp"))
            .andExpect(model().attribute("organization", organization));
	}

	@Test
	public void testAddPostSuccess() throws Exception {
		mockMvc.perform(post("/organization/add")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("name", "org-name"))
				.andExpect(view().name(AController.ORGANIZATION_ADD_FORM))
				.andExpect(model().attribute("organization", Organization.EMPTY));
		
		List<Organization> list = organizationService.getOrganizations();
		Organization testOrganization = new Organization("org-name") ;
		Assert.assertTrue(list.contains(testOrganization));
	}
	
	@Test
	public void testAddPostFail() throws Exception {
		mockMvc.perform(post("/organization/add")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("name", ""))
				.andExpect(view().name(AController.ORGANIZATION_ADD_FORM))
				.andExpect(model().hasErrors());
	}
	
	@Test
	public void testEditSuccess() throws Exception {
		Organization organization = addOrganizationToDb("org-name");
		int orgId = organization.getId();
		
		String updateName = "test to updatename";
		
		mockMvc.perform(post("/organization/edit/" + orgId)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("name", updateName))
					.andExpect(view().name(AController.HOME));
		
		Organization actual = organizationService.getOrganization(orgId);
		Assert.assertEquals(updateName, actual.getName());
	}
	
	@Test
	public void testDeleteSuccess() throws Exception {
		Organization organization = addOrganizationToDb("org-name");
		int orgId = organization.getId();
		
		mockMvc.perform(get("/organization/delete/" + orgId))
			.andExpect(view().name(AController.HOME))
			.andExpect(model().attribute("success", true));
		
		Assert.assertNull(organizationService.getOrganization(orgId));
	}
	
	@Test
	public void testDeleteFail() throws Exception {
		Organization organization = new Organization("org-name");
		organizationService.addOrganization(organization);
		Team team = new Team("team-name");
		team.setOrganization(organization);
		teamService.addTeam(team);
		
		int orgId = organization.getId();
		
		mockMvc.perform(get("/organization/delete/" + orgId))
			.andExpect(view().name(AController.HOME))
			.andExpect(model().attribute("success", false));
		
		Assert.assertNotNull(organizationService.getOrganization(orgId));
	}

	private Organization addOrganizationToDb (String orgName) {
		Organization organization = new Organization(orgName);
		organizationService.addOrganization(organization);
		return organization;
	}
	
}
