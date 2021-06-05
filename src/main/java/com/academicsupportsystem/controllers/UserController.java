package com.academicsupportsystem.controllers;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.academicsupportsystem.services.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	public String getAdmin(Model m, HttpSession session) {

		return userService.getAdmin(m, session);
	}
	
	@RequestMapping(value="/staff", method=RequestMethod.GET)
	public String getStaff(Model m, HttpSession session) {
		return userService.getStaff(m, session);
	}
	
	@RequestMapping(value="/student", method=RequestMethod.GET)
	public String getStudent(Model m, HttpSession session) {
		return userService.getStudent(m, session);
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@RequestParam Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes){
		return userService.login(params, m, session, redirectAttributes);
	}

	@RequestMapping(value="/login-admin", method=RequestMethod.POST)
	public String loginAdmin(@RequestParam Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes){
		return userService.loginAdmin(params, m, session, redirectAttributes);
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		return userService.logout(session);
	}
	
	@RequestMapping(value="/admin/users", method=RequestMethod.GET)
	public String getUsers(Model m, HttpSession session) {
		
		return userService.getUsers(m, session);
	}
	
	@RequestMapping(value="/admin/users", method=RequestMethod.POST)
	public String addUsers(@RequestParam Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes) {
		return userService.createUser(params,m, session, redirectAttributes);
	}

	@RequestMapping(value="/admin/multiple_users", method=RequestMethod.POST)
	public String getUsers(@RequestParam MultipartFile file,  Model m, HttpSession session, RedirectAttributes redirectAttributes) {
		return userService.createMultipleUsers(file, m, session, redirectAttributes);
	}
	
	@RequestMapping(value="/users/reset_password/{id}", method=RequestMethod.GET)
	public String getResetPassword(@PathVariable("id") int id,  Model m, HttpSession session) {
		session.setAttribute("reset_password_user_id", id);
		return userService.getResetPassword(id, m, session);
	}
	
	@RequestMapping(value="/reset_password", method=RequestMethod.GET)
	public String getResetPasswordUser(Model m, HttpSession session) {
		
		return userService.getResetPassword(m, session);
	}
	
	@RequestMapping(value="/reset_password", method=RequestMethod.POST)
	public String resetPassword(@RequestParam Map<String, String> params,  Model m, HttpSession session, RedirectAttributes redirectAttributes) {
		
		return userService.resetUserPasswordAdmin(params, m, session, redirectAttributes);
	
	}
	
	@RequestMapping(value="/users/delete/{id}", method=RequestMethod.GET)
	public String deleteUser(@PathVariable("id") int id, Model m, HttpSession session, RedirectAttributes redirectAttributes) {
		return userService.deleteUser(id, m, session, redirectAttributes);
	}
	
	@RequestMapping(value="/users/edit/{id}", method=RequestMethod.GET)
	public String getEditUser(@PathVariable("id") int id,  Model m, HttpSession session) {
		
		return userService.getEditUser(id, m, session);
	}
	
	@RequestMapping(value="/users/edit/users/edit", method=RequestMethod.POST)
	public String editUser(@RequestParam Map<String, String> params,  Model m, HttpSession session, RedirectAttributes redirectAttributes)
	{
		return userService.editUser(params, m, session, redirectAttributes);
	}
	
}
