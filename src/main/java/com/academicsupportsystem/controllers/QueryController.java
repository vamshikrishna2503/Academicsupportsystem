package com.academicsupportsystem.controllers;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.academicsupportsystem.services.QueryService;

@Controller
public class QueryController {

	@Autowired
	QueryService queryService;
	
	@RequestMapping(value="/student/queries", method=RequestMethod.GET)
	public String getStudentQueries(Model m, HttpSession session) {
		return queryService.getStudentQueries(m, session);
	}
	
	@RequestMapping(value="/staff/queries", method=RequestMethod.GET)
	public String getStaffQueries(Model m, HttpSession session) {
		return queryService.getStaffQueries(m, session);
	}

	@RequestMapping(value="/staff/queries/{id}", method=RequestMethod.GET)
	public String getReplyQuery(@PathVariable("id") int id, Model m, HttpSession session)
	{
		return queryService.getReplyQuery(id, m, session);
	}
	
	@RequestMapping(value="/staff/message", method=RequestMethod.GET)
	public String getStaffMessage(Model m, HttpSession session) {
		return queryService.getStaffMessage(m, session);
	}
	
	@RequestMapping(value="/student/queries", method=RequestMethod.POST)
	public String addQuery(@RequestParam Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes) {
		
		return queryService.addQuery(params,m, session, redirectAttributes);
	}
	
	@RequestMapping(value="/staff/queries", method=RequestMethod.POST)
	public String replyQuery(@RequestParam Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes) {
		
		return queryService.replyQuery(params,m, session, redirectAttributes);
	}
	
	@RequestMapping(value="/staff/message", method=RequestMethod.POST)
	public String sendMessage(@RequestParam Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes) {
		
		return queryService.sendMessage(params,m, session, redirectAttributes);
	}
	
	
}
