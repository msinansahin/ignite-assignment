package com.sprhib.controller;

import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sprhib.model.Organization;
import com.sprhib.model.Team;
import com.sprhib.service.OrganizationService;
import com.sprhib.service.TeamService;
import com.sprhib.utililty.Action;
import com.sprhib.utililty.TeamHasMemberException;

@Controller
@RequestMapping(value="/team")
public class TeamController extends AController {
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private OrganizationService orgService;
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public ModelAndView addTeamPage() {
		ModelAndView modelAndView = new ModelAndView(TEAM_ADD_FORM);
		modelAndView.addObject("team", Team.EMPTY);
		addOrganizationListToModel(modelAndView);
		return modelAndView;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public ModelAndView addingTeam(@Valid @ModelAttribute Team team, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView(TEAM_ADD_FORM);
		addOrganizationListToModel(modelAndView);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("team", team);
			return modelAndView;
		}
		
		Organization organization = team.getOrganization();
		if (organization != null && organization.isNew()) {
			team.setOrganization(null); //means no organization
		}
		
		teamService.addTeam(team);
		
		String message = getMessage("msg.successful.add", getMessage("team"));
		modelAndView.addObject("message", message);
		modelAndView.addObject("team", Team.EMPTY);
		return modelAndView;
	}
	
	@RequestMapping(value="/list")
	public ModelAndView listOfTeams() {
		ModelAndView modelAndView = new ModelAndView(TEAM_LIST);
		
		List<Team> teams = teamService.getTeams();
		Collections.sort(teams);
		modelAndView.addObject("teams", teams);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editTeamPage(@PathVariable Integer id) {
		ModelAndView modelAndView = new ModelAndView(TEAM_EDIT_FORM);
		Team team = teamService.getTeam(id);
		modelAndView.addObject("team",team);
		addOrganizationListToModel(modelAndView);
		return modelAndView;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public ModelAndView editingTeam(@Valid @ModelAttribute Team team, BindingResult bindingResult, @PathVariable Integer id) {
		ModelAndView modelAndView;
		
		if (bindingResult.hasErrors()) {
			modelAndView = new ModelAndView(TEAM_EDIT_FORM);
			modelAndView.addObject("team", team);
			return modelAndView;
		}
		
		modelAndView = new ModelAndView(HOME);
		addHomeActions(modelAndView.getModelMap());
		Organization organization = team.getOrganization();
		
		//means no organization
		if (organization != null && organization.isNew()) {
			team.setOrganization(null); 
		}

		teamService.updateTeam(team);
		
		String message = getMessage("msg.successful.edit", getMessage("team"));

		modelAndView.addObject("message", message);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteTeam(@PathVariable Integer id) {
		ModelAndView modelAndView = new ModelAndView(HOME);
		addHomeActions(modelAndView.getModelMap());
		String message;
		boolean success;
		try {
			teamService.deleteTeam(id);
			message = getMessage("msg.successful.delete", getMessage("team"));
			success = true;
		} catch (TeamHasMemberException e) {
			message = getMessage (e, e.getTeamId());
			success = false;
			LOGGER.log(Level.WARNING, message);
		}
		modelAndView.addObject("message", message);
		modelAndView.addObject("success", success);
		return modelAndView;
	}
	
	private void addOrganizationListToModel (ModelAndView model) {
		model.addObject("organizationList", new TreeMap<>(orgService.getLookUpOrganizations()));
	}

	@Override
	protected Action[] createHomeActions() {
		Action [] actions = new Action[2];
		actions[0] = new Action("/team/add", getMessage("add.team"));
		actions[1] = new Action("/team/list", getMessage("teams"));
		return actions;
	}

}
