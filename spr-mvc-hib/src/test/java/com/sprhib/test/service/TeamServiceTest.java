package com.sprhib.test.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.sprhib.dao.TeamDAO;
import com.sprhib.model.Team;
import com.sprhib.service.TeamServiceImpl;
import com.sprhib.utililty.TeamHasMemberException;

/**
 * 
 * @author mehmetsinan.sahin
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TeamServiceTest {

	@Mock
	TeamDAO teamDAO;

	@InjectMocks
	TeamServiceImpl teamService;
	
	int TEAM_ID_HAS_NO_MEMBER = 1;
	int TEAM_ID_HAS_MEMBER = 2;
	
	@Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);
		Team team = new Team("team-name");
		team.setId(TEAM_ID_HAS_NO_MEMBER);
		when(teamService.getTeam(TEAM_ID_HAS_NO_MEMBER)).thenReturn(team);
		
		when(teamService.getTeams()).thenReturn(createTeams(4));
		
		when(teamDAO.hasAnyMember(TEAM_ID_HAS_MEMBER)).thenReturn(true);
    }
	
	@Test (expected=TeamHasMemberException.class)
	public void deleteTeamThatHasMember() {
		teamService.deleteTeam(TEAM_ID_HAS_MEMBER);
		verify(teamDAO).hasAnyMember(TEAM_ID_HAS_MEMBER);
	}	
	
	@Test
	public void deleteTeamThatHasNoMember() {
		teamService.deleteTeam(1);
		verify(teamDAO).hasAnyMember(TEAM_ID_HAS_NO_MEMBER);
		verify(teamDAO).deleteTeam(TEAM_ID_HAS_NO_MEMBER);
	}
	
	private List<Team> createTeams  (int count) {
		List<Team> result = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			Team team = new Team("team-name-" + i);
			team.setId(i);
			result.add(team);
		}
		return result;
	}
}
