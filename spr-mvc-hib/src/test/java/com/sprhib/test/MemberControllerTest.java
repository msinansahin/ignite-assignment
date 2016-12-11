package com.sprhib.test;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.sprhib.model.Team;
import com.sprhib.service.MemberService;
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
public class MemberControllerTest {

	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private MemberService memberService;

	private MockMvc mockMvc;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void testAddGet() throws Exception {
		mockMvc.perform(get("/member/add"))
			.andExpect(status().isOk())
			.andExpect(view().name(AController.MEMBER_ADD_FORM))
            .andExpect(forwardedUrl("/WEB-INF/pages/add-member-form.jsp"));
	}
	
	@Test
	public void testEditGet() throws Exception {
		Member member = addMemberToDb("mem-name");
		
		//Update member with two teams
		Set<Team> teams = new HashSet<>();
		Team team1 = new Team("t1");
		Team team2 = new Team("t1");
		teamService.addTeam(team1);
		teamService.addTeam(team2);
		teams.add(team1);
		teams.add(team2);
		member.setTeams(teams);
		memberService.updateMember(member);
		
		
		int memberId = member.getId();
		
		
		//test members' teams added into model with size 2
		mockMvc.perform(get("/member/edit/" + memberId))
			.andExpect(status().isOk())
			.andExpect(view().name(AController.MEMBER_EDIT_FORM))
            .andExpect(forwardedUrl("/WEB-INF/pages/edit-member-form.jsp"))
            .andExpect(model().attribute("member", member))
            .andExpect(model().attribute("member", hasProperty("teams", hasSize(2))));
	}

	@Test
	public void testAddPostSuccess() throws Exception {
		mockMvc.perform(post("/member/add")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("name", "member-name"))
				.andExpect(view().name(AController.MEMBER_ADD_FORM))
				.andExpect(model().attribute("member", Member.EMPTY));
		
		List<Member> list = memberService.getMembers();
		Member testMember = new Member("member-name");
		Assert.assertTrue(list.contains(testMember));
	}
	
	@Test
	public void testAddPostFail() throws Exception {
		mockMvc.perform(post("/member/add")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("name", ""))
				.andExpect(view().name(AController.MEMBER_ADD_FORM))
				.andExpect(model().hasErrors());
	}
	
	@Test
	public void testEditSuccess() throws Exception {
		Member member = addMemberToDb("mem-name");
		int memberId = member.getId();
		
		String updateName = "test to updatename";
		
		mockMvc.perform(post("/member/edit/" + memberId)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("name", updateName))
					.andExpect(view().name(AController.HOME));
		
		Member actual = memberService.getMember(memberId);
		Assert.assertEquals(updateName, actual.getName());
	}
	
	@Test
	public void testDeleteSuccess() throws Exception {
		Member member = addMemberToDb("mem-name");
		int membeId = member.getId();
		
		mockMvc.perform(get("/member/delete/" + membeId))
			.andExpect(view().name(AController.HOME))
			.andExpect(model().attribute("success", true));
		
		Assert.assertNull(memberService.getMember(membeId));
	}
	
	private Member addMemberToDb (String memberName) {
		Member member = new Member("mem-name");
		memberService.addMember(member);
		return member;
	}
	

}
