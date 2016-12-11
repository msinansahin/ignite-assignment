package com.sprhib.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprhib.dao.TeamDAO;
import com.sprhib.model.Team;
import com.sprhib.utililty.TeamHasMemberException;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {
	
	@Autowired
	private TeamDAO teamDAO;

	public void addTeam(Team team) {
		teamDAO.addTeam(team);		
	}

	public void updateTeam(Team team) {
		teamDAO.updateTeam(team);
	}

	public Team getTeam(int id) {
		return teamDAO.getTeam(id);
	}

	public void deleteTeam(int id) {
		if (teamDAO.hasAnyMember(id)) {
			throw new TeamHasMemberException(id);
		}
		teamDAO.deleteTeam(id);
	}

	public List<Team> getTeams() {
		return teamDAO.getTeams();
	}

	@Override
	public Map<String, String> getLookUpTeams() {
		Map<String, String> result = new HashMap<>();
		List<Team> list = getTeams(); //assuming will teams retrieved from cache
		list.forEach(org -> result.put(Objects.toString(org.getId()), org.getName()));
		return result;
	}

}
