package com.sprhib.test;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
import com.sprhib.model.Member;
import com.sprhib.model.Organization;
import com.sprhib.model.Team;
import com.sprhib.service.MemberService;
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
@Rollback (value=true)
public class TeamControllerTest {

	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private MemberService memberService;
	
	private MockMvc mockMvc;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void testAddGet() throws Exception {
		mockMvc.perform(get("/team/add"))
			.andExpect(status().isOk())
			.andExpect(view().name(AController.TEAM_ADD_FORM))
            .andExpect(forwardedUrl("/WEB-INF/pages/add-team-form.jsp"));
	}

	@Test
	public void testAddPostSuccess() throws Exception {
		Organization organization = new Organization("test-org-name");
		organizationService.addOrganization(organization);
		String testTeamName = "test-team-name";
		mockMvc.perform(post("/team/add")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("name", testTeamName)
			.param("rating", "3")
			.param("organization.id", Objects.toString(organization.getId())))
				.andExpect(view().name(AController.TEAM_ADD_FORM))
				.andExpect(model().attribute("team", Team.EMPTY));
		
		List<Team> list = teamService.getTeams();
		Team testTeam = new Team(testTeamName);
		Assert.assertTrue(list.contains(testTeam));
		
		Team actual = list.stream().filter(team -> team.equals(testTeam)).findAny().get();
		Assert.assertEquals(testTeamName, actual.getName());
		Assert.assertEquals(new Integer(3), actual.getRating());
		Assert.assertEquals(organization, actual.getOrganization());

	}
	
	@Test
	public void testAddPostFail() throws Exception {
		mockMvc.perform(post("/team/add")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("name", ""))
				.andExpect(view().name(AController.TEAM_ADD_FORM))
				.andExpect(model().hasErrors());
		
	}
	
	@Test
	public void testList() throws Exception {
		int teamCount = 3;
		List<Team> teams = new ArrayList<>();
		for (int i = 0; i < teamCount; i++) {
			Team team = new Team("name-" + i);
			teams.add(team);
			teamService.addTeam(team);
		}
		
		mockMvc.perform(get("/team/list"))
			.andExpect(view().name(AController.TEAM_LIST))
			.andExpect(model().attribute("teams", hasSize(teamService.getTeams().size())));
		
	}
	
	@Test
	public void testEditSuccess() throws Exception {
		Team team = createTeam("test to edit name");
		teamService.addTeam(team);
		int teamId = team.getId();
		String updateName = "test to updatename";
		mockMvc.perform(post("/team/edit/" + teamId)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("name", updateName)
				.param("rating", "5"))
					.andExpect(view().name(AController.HOME));

		
		Team actual = teamService.getTeam(teamId);
		Assert.assertEquals(updateName, actual.getName());
		Assert.assertEquals(new Integer(5), actual.getRating());
	}
	
	@Test
	public void testDeleteSuccess() throws Exception {
		Team team = createTeam("test to edit name");
		teamService.addTeam(team);
		int teamId = team.getId();
		
		mockMvc.perform(get("/team/delete/" + teamId))
			.andExpect(view().name(AController.HOME))
			.andExpect(model().attribute("success", true));
		
		team = teamService.getTeam(teamId);
		Assert.assertNull(team);
	}
	
	@Test
	public void testDeleteFail() throws Exception {
		Team team = createTeam("test to edit name");
		teamService.addTeam(team);
		Member member = new Member("mem-name");
		member.getTeams().add(team);
		memberService.addMember(member);
		
		int teamId = team.getId();
		
		mockMvc.perform(get("/team/delete/" + teamId))
			.andExpect(view().name(AController.HOME))
			.andExpect(model().attribute("success", false));
		
		team = teamService.getTeam(teamId);
		Assert.assertNotNull(team);
	}
	
	@Test
	public void testEditFail() throws Exception {
		Team team = createTeam("test to edit name");
		teamService.addTeam(team);
		int teamId = team.getId();
		String emptyName = "";
		mockMvc.perform(post("/team/edit/" + teamId)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("name", emptyName)
				.param("rating", "5"))
					.andExpect(view().name(AController.TEAM_EDIT_FORM))
					.andExpect(model().hasErrors());
		

	}
	
	private Team createTeam (String teamName) {
		Team team = new Team(teamName);
		team.setRating(2);
		return team;
	}

}
