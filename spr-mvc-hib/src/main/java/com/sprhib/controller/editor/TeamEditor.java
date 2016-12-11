package com.sprhib.controller.editor;

import java.beans.PropertyEditorSupport;

import com.sprhib.model.Team;
import com.sprhib.service.TeamService;

/**
 * 
 * @author mehmetsinan.sahin
 *
 */
public class TeamEditor extends PropertyEditorSupport  {

	TeamService teamService;
	
    public TeamEditor(TeamService teamService) {
		super();
		this.teamService = teamService;
	}

	@Override
    public void setAsText(String text) {
        Team team = teamService.getTeam(Integer.parseInt(text));
        this.setValue(team);
    }

    @Override
    public String getAsText() {
        Team team = (Team) this.getValue();
        return team.getName();
    }
}
