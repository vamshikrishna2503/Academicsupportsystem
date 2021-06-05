package com.academicsupportsystem.controllers;

import java.text.ParseException;
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

import com.academicsupportsystem.services.AppointmentService;

@Controller
public class AppointmentController {

	@Autowired
	AppointmentService appointmentService;
	
	@RequestMapping(value="student/appointments", method=RequestMethod.GET)
	public String getStudentAppointments(Model m, HttpSession session) {
		return appointmentService.getStudentAppointments(m, session);
	}
	
	@RequestMapping(value="staff/appointments", method=RequestMethod.GET)
	public String getStaffAppointments(Model m, HttpSession session) {
		return appointmentService.getStaffAppointments(m, session);
	}
	
	@RequestMapping(value="/student/appointments/{id}", method=RequestMethod.GET)
	public String getResetPassword(@PathVariable("id") int id,  Model m, HttpSession session, RedirectAttributes redirectAttributes) {
		
		return appointmentService.confirmAppointment(id, m, session, redirectAttributes);
	} 
	
	
	@RequestMapping(value="/staff/appointments", method=RequestMethod.POST)
	public String getResetPassword(@RequestParam Map<String, String> params,  Model m, HttpSession session, RedirectAttributes redirectAttributes) throws ParseException {
		
		return appointmentService.setAppointment(params, m, session, redirectAttributes);
	}
	
	
}
