package com.sprhib.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.ModelMap;

import com.sprhib.utililty.Action;
import com.sprhib.utililty.BaseError;

/**
 * 
 * @author mehmetsinan.sahin
 *
 */
public abstract class AController {
	
	protected final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(this.getClass().getName());

	public static final String ATTR_HOME_ACTIONS = "actions";
	public static final String HOME = "home";
	public static final String ORGANIZATION_ADD_FORM = "add-organization-form";
	public static final String ORGANIZATION_EDIT_FORM = "edit-organization-form";
	public static final String ORGANIZATION_LIST = "list-of-ogranizations";

	public static final String MEMBER_ADD_FORM = "add-member-form";
	public static final String MEMBER_EDIT_FORM = "edit-member-form";
	public static final String MEMBER_LIST = "list-of-members";
	
	public static final String TEAM_ADD_FORM = "add-team-form";
	public static final String TEAM_EDIT_FORM = "edit-team-form";
	public static final String TEAM_LIST = "list-of-teams";
	
	@Autowired @Qualifier ("messageSource")
	protected MessageSource labelSource;
	
	/**
	 * home actions to display after forwarding when an action is processed
	 */
	private Action [] homeActions;
	
	/**
	 * Helper method for getting message form label source
	 * @return
	 */
	public String getMessage (String code, Object ... args) {
		return labelSource.getMessage(code, args, getUserLocale());
	}
	
	/**
	 * Helper method for getting user locale<br>
	 * It can be used in controllers easily
	 * @return user locale 
	 */
	public static Locale getUserLocale() {
		return LocaleContextHolder.getLocale();
	}
	
	public String getMessage(BaseError e, Object ...args) {
		return getMessage(e.getLabelCode(), args);
	}
	
	/**
	 * Use this when forward to home in any action
	 * @param modelMap
	 */
	protected void addHomeActions (ModelMap modelMap) {
		modelMap.addAttribute(ATTR_HOME_ACTIONS, getHomeActions());
	}
	
	/**
	 * @return the homeActions
	 */
	private Action [] getHomeActions() {
		if (homeActions == null) {
			homeActions = createHomeActions();
		}
		return homeActions;
	}

	/**
	 * 
	 * @return home action array for the domain subject
	 */
	protected abstract Action [] createHomeActions ();


}
