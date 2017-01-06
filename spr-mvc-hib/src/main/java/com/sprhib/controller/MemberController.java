package com.sprhib.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sprhib.controller.editor.TeamEditor;
import com.sprhib.model.Member;
import com.sprhib.model.Team;
import com.sprhib.service.MemberService;
import com.sprhib.service.TeamService;
import com.sprhib.utililty.Action;

/**
 * 
 * @author mehmetsinan.sahin
 *
 */
@Controller
@RequestMapping(value="/member")
public class MemberController extends AController {
	
	
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private TeamService teamService;
	
	@GetMapping ("/add")
	public String addMemberPage(ModelMap modelMap) {
		addPageAttributesOfNew(modelMap);
		addTeamListToModel(modelMap);
		modelMap.addAttribute("member", Member.EMPTY);
		return MEMBER_ADD_FORM;
	}
	
	@PostMapping (value="/add")
	public String addingMember(@Valid @ModelAttribute Member member, BindingResult bindingResult,
			ModelMap modelMap) {
		addPageAttributesOfNew(modelMap);
		addTeamListToModel(modelMap);
		if (bindingResult.hasErrors()) {
			return MEMBER_ADD_FORM;
		}
		member.getTeamIds().forEach(n -> member.getTeams().add(teamService.getTeam(Integer.parseInt(n))));
		memberService.addMember(member);
		String message = getMessage("msg.successful.add", getMessage("member"));
		modelMap.addAttribute("message", message);
		modelMap.addAttribute("member", Member.EMPTY);
		return MEMBER_ADD_FORM;
	}
	
	@RequestMapping(value="/list")
	public String listOfMembers(ModelMap modelMap) {
		modelMap.addAttribute("members", memberService.getMembers());
		return MEMBER_LIST;
	}
	
	@GetMapping (value="/edit/{id}")
	public String editMemberPage(@PathVariable Integer id, ModelMap modelMap) {
		Member member = memberService.getMember(id);
		member.getTeams().forEach(n -> member.getTeamIds().add(n.getId().toString()));
		modelMap.addAttribute("member", member);
		addPageAttributesOfEdit(modelMap);
		addTeamListToModel(modelMap);
		return MEMBER_EDIT_FORM;
	}
	
	@PostMapping(value="/edit/{id}")
	public String editingMember(@Valid @ModelAttribute Member member, BindingResult bindingResult, @PathVariable Integer id, 
			ModelMap modelMap) {
		if (bindingResult.hasErrors()) {
			addPageAttributesOfEdit(modelMap);
			modelMap.addAttribute("member", member);
			addTeamListToModel(modelMap);
			return MEMBER_EDIT_FORM;
		}
		member.getTeams().clear();
		member.getTeamIds().forEach(n -> member.getTeams().add(teamService.getTeam(Integer.parseInt(n))));
		memberService.updateMember(member);
		String message = getMessage("msg.successful.edit", getMessage("member"));
		modelMap.addAttribute("message", message);
		addHomeActions(modelMap);
		return HOME;
	}
	
	@GetMapping(value="/delete/{id}")
	public String deleteMember(@PathVariable Integer id, ModelMap modelMap) {
		memberService.deleteMember(id);
		String message = getMessage("msg.successful.delete", getMessage("member"));
		modelMap.addAttribute("message", message);
		modelMap.addAttribute("success", true);
		addHomeActions(modelMap);
		return HOME;
	}
	
	/**
	 * Adds new ogr page title and description attributes into request
	 * @param modelMap
	 */
	private void addPageAttributesOfNew (ModelMap modelMap) {
		modelMap.addAttribute("pageTitle", getMessage("add.member"));
		modelMap.addAttribute("description", getMessage("add.member.desc"));
	}
	
	/**
	 * Adds edit ogr page title and description attributes into request
	 * @param modelMap
	 */
	private void addPageAttributesOfEdit (ModelMap modelMap) {
		modelMap.addAttribute("pageTitle", getMessage("edit.member"));
		modelMap.addAttribute("description", getMessage("edit.member.desc"));
	}

	@Override
	protected Action[] createHomeActions() {
		Action [] actions = new Action[2];
		actions[0] = new Action("/member/add", getMessage("add.member"));
		actions[1] = new Action("/member/list", getMessage("members"));
		return actions;
	}
	
	private void addTeamListToModel (ModelMap modelMap) {
		modelMap.addAttribute("teamList", teamService.getTeams());
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Team.class, new TeamEditor(teamService));
    }
	
}
