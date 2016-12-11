package com.sprhib.dao;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.sprhib.model.Team;

@Repository
public class TeamDAOImpl extends BaseDAO implements TeamDAO {
	
	public void addTeam(Team team) {
		addModel(team);
	}

	public void updateTeam(Team team) {
		Team teamToUpdate = getTeam(team.getId());
		teamToUpdate.setName(team.getName());
		teamToUpdate.setRating(team.getRating());
		teamToUpdate.setOrganization(team.getOrganization());
		updateModel(teamToUpdate);
	}

	public Team getTeam(int id) {
		return (Team) getCurrentSession().get(Team.class, id);
	}

	public void deleteTeam(int id) {
		deleteModel(Team.class, id);
	}

	public List<Team> getTeams() {
		return getModels(Team.class);
	}

	@Override
	public boolean hasAnyMember(int teamId) {
		String sql = "select count(elements(t.members)) from Team t "
				+ "where t.id = :id";
		Object count = getCurrentSession().createQuery(sql)
				.setParameter("id", teamId)
				.getSingleResult();
		return Integer.parseInt(Objects.toString(count)) > 0;
	}

}
