package com.sprhib.test.integration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.sprhib.dao.TeamDAO;
import com.sprhib.init.BaseTestConfig;
import com.sprhib.init.JdbcForTestConfig;
import com.sprhib.model.Team;

/**
 * Tests are run in memory hsqldb database
 * @author mehmetsinan.sahin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = BaseTestConfig.class)
public class TeamDAOTest {

	@Autowired
	TeamDAO dao;
	
	@Autowired
	JdbcForTestConfig nativeSQL;
	
	static String TEAM_NAME = "org-name";
	static String TEAM_NAME_OTHER = "org-name-other";


	@Test
	@Transactional
	public void addTeamTest() {
		Team team = new Team(TEAM_NAME);
		dao.addTeam(team);
		String teamName = nativeSQL.select("select t.name from teams t where t.id = ?", 
				String.class, 
				team.getId());
		Assert.assertEquals(teamName, TEAM_NAME);
	}

	@Test
	@Transactional
	public void updateTeamTest() {
		Team team = new Team(TEAM_NAME);
		dao.addTeam(team);
		int teamId = team.getId();
		
		String testName = "TESTNAME";
		Team teamToUpdate = new Team(testName);
		teamToUpdate.setId(teamId);
		dao.updateTeam(teamToUpdate);
		
		String actualTeamName = nativeSQL.select("select o.name from teams o where o.id = ?", 
				String.class, 
				teamId);

		Assert.assertEquals(testName, actualTeamName);
	}

	@Test
	@Transactional
	public void getTeamTest() {
		Team team = new Team(TEAM_NAME);
		dao.addTeam(team);
		int teamId = team.getId();

		long count = nativeSQL.select("select count(1) from teams o where o.id = ?", 
				Long.class, 
				teamId);
		
		Assert.assertEquals(count, 1);
	}

	@Test
	@Transactional
	public void deleteTeam() {
		int testTeamId = 1;
		nativeSQL.getJdbcTemplate().execute(
				String.format("INSERT INTO teams(id, name, rating, organization_id) "
						+ "VALUES(%d, 'test', 3, null)", testTeamId));
		
		dao.deleteTeam(testTeamId);
		long testCount = nativeSQL.select("select count(1) from teams o where o.id = ?", 
				Long.class, 
				testTeamId);
		Assert.assertEquals(0L, testCount);
	}

	@Test
	@Transactional
	public void getTeams() {
		Team team = new Team(TEAM_NAME);
		dao.addTeam(team);
		team = new Team(TEAM_NAME_OTHER);
		dao.addTeam(team);
		
		int testSize = dao.getTeams().size();
		long testCount = nativeSQL.select("select count(1) from teams", Long.class);
		Assert.assertEquals(testCount, testSize);
		Assert.assertEquals(2, testSize);
	}

	@Test
	@Transactional
	public void hasAnyMemberTest() {
		Team team = new Team(TEAM_NAME);
		dao.addTeam(team);
		
		int teamId = team.getId();

		Assert.assertFalse(dao.hasAnyMember(teamId));

		int memberId = 2;
		nativeSQL.getJdbcTemplate().execute(
				String.format("INSERT INTO members(id, name) VALUES(%d, 'mem-name1')", memberId));
		nativeSQL.getJdbcTemplate().execute(
				String.format("INSERT INTO members_teams(members_id, teams_id)  VALUES(%d, %d)", memberId, teamId));
		
		Assert.assertTrue(dao.hasAnyMember(teamId));
	}
	
}
