package com.sprhib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sprhib.utililty.Action;

@Controller
public class LinkController extends AController {
	
	@RequestMapping(value="/")
	public ModelAndView mainPage() {
		ModelAndView modelAndView = new ModelAndView("home");
		addHomeActions(modelAndView.getModelMap());
		return modelAndView;
	}
	
	@RequestMapping(value="/index")
	public ModelAndView indexPage() {
		return mainPage();
	}

	@Override
	protected Action[] createHomeActions() {
		Action[] actions = new Action [3];
		actions[0] = new Action("/organization/add", getMessage("add.organization"));
		actions[1] = new Action("/team/add", getMessage("add.team"));
		actions[2] = new Action("/member/add", getMessage("add.member"));
		return actions;
	}
}
