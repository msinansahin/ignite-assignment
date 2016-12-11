package com.sprhib.controller;

import java.util.logging.Level;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sprhib.model.Organization;
import com.sprhib.service.OrganizationService;
import com.sprhib.utililty.Action;
import com.sprhib.utililty.OrganizationHasTeamsException;

/**
 * 
 * @author mehmetsinan.sahin
 *
 */
@Controller
@RequestMapping(value="/organization")
public class OrganizationController extends AController {
	
	
	
	@Autowired
	private OrganizationService orgService;
	
	@GetMapping ("/add")
	public String addOrganizationPage(ModelMap modelMap) {
		addPageAttributesOfNew(modelMap);
		modelMap.addAttribute("organization", Organization.EMPTY);
		return ORGANIZATION_ADD_FORM;
	}
	
	@PostMapping (value="/add")
	public String addingOrganization(@Valid @ModelAttribute Organization organization, BindingResult bindingResult,
			ModelMap modelMap) {
		addPageAttributesOfNew(modelMap);
	
		if (bindingResult.hasErrors()) {
			modelMap.addAttribute("organization", organization);
			return ORGANIZATION_ADD_FORM;
		}
		orgService.addOrganization(organization);
		String message = getMessage("msg.successful.add", getMessage("organization"));
		modelMap.addAttribute("message", message);
		modelMap.addAttribute("organization", Organization.EMPTY);
		return ORGANIZATION_ADD_FORM;
	}
	
	@RequestMapping(value="/list")
	public String listOfOrganizations(ModelMap modelMap) {
		modelMap.addAttribute("organizations", orgService.getOrganizations());
		return ORGANIZATION_LIST;
	}
	
	@GetMapping (value="/edit/{id}")
	public String editOrganizationPage(@PathVariable Integer id, ModelMap modelMap) {
		modelMap.addAttribute("organization", orgService.getOrganization(id));
		addPageAttributesOfEdit(modelMap);
		return ORGANIZATION_EDIT_FORM;
	}
	
	@PostMapping(value="/edit/{id}")
	public String editingOrganization(@Valid @ModelAttribute Organization organization, BindingResult bindingResult, @PathVariable Integer id, 
			ModelMap modelMap) {
		addPageAttributesOfEdit(modelMap);
		addHomeActions(modelMap);
		if (bindingResult.hasErrors()) {
			modelMap.addAttribute("organization", organization);
			return ORGANIZATION_EDIT_FORM;
		}
		orgService.updateOrganization(organization);
		String message = getMessage("msg.successful.edit", getMessage("organization"));
		modelMap.addAttribute("message", message);
		return HOME;
	}
	
	@GetMapping(value="/delete/{id}")
	public String deleteOrganization(@PathVariable Integer id, ModelMap modelMap) {
		addHomeActions(modelMap);
		String message;
		boolean success;
		try {
			orgService.deleteOrganization(id);
			message = getMessage("msg.successful.delete", getMessage("organization"));
			success = true;
		} catch (OrganizationHasTeamsException e) {
			message = getMessage (e, e.getOrganizationId());
			success = false;
			LOGGER.log(Level.WARNING, message);
		}
		modelMap.addAttribute("message", message);
		modelMap.addAttribute("success", success);
		return HOME;
	}
	
	/**
	 * Adds new ogr page title and description attributes into request
	 * @param modelMap
	 */
	private void addPageAttributesOfNew (ModelMap modelMap) {
		modelMap.addAttribute("pageTitle", getMessage("add.organization"));
		modelMap.addAttribute("description", getMessage("add.organization.desc"));
	}
	
	/**
	 * Adds edit ogr page title and description attributes into request
	 * @param modelMap
	 */
	private void addPageAttributesOfEdit (ModelMap modelMap) {
		modelMap.addAttribute("pageTitle", getMessage("edit.organization"));
		modelMap.addAttribute("description", getMessage("edit.organization.desc"));
	}

	@Override
	protected Action[] createHomeActions() {
		Action [] actions = new Action[2];
		actions[0] = new Action("/organization/add", getMessage("add.organization"));
		actions[1] = new Action("/organization/list", getMessage("organizations"));
		return actions;
	}
	

}
