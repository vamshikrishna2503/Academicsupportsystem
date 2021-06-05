package com.academicsupportsystem.services;

import java.beans.Encoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.academicsupportsystem.models.Appointment;
import com.academicsupportsystem.models.Query;
import com.academicsupportsystem.models.User;
import com.academicsupportsystem.models.UserRole;
import com.academicsupportsystem.repositories.AppointmentRepository;
import com.academicsupportsystem.repositories.UserRepository;

@Service
public class AppointmentService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AppointmentRepository appointmentRepository;
	
	@Autowired
    JavaMailSender javaMailSender;
	
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm", Locale.ENGLISH);
	
	public String getStaffAppointments(Model m, HttpSession session) {
		
		User user = userRepository.findById(Integer.parseInt(session.getAttribute("id").toString())).get();
		//BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		List<User> students = userRepository.findByRole(UserRole.STUDENT);
		
		if(user != null && user.getRole() == UserRole.STAFF) {
		List<Appointment> appointmentsList = new ArrayList<>();
		appointmentRepository.findAll().forEach(a -> {if(a.getSetBy().equals(user)) {
			appointmentsList.add(a);
		}});
		
		
		m.addAttribute("id", user.getId());
		m.addAttribute("firstName", user.getFirstName());
		m.addAttribute("lastName", user.getLastName());
		
		m.addAttribute("students", students);
		m.addAttribute("appointments", appointmentsList);
		
		return "appointment_staff";
		
		}
		
		else {
			return "redirect";
		}
	}
	
	public String getStudentAppointments(Model m, HttpSession session) {
		User user = userRepository.findById(Integer.parseInt(session.getAttribute("id").toString())).get();
		
		
		if(user != null && user.getRole() == UserRole.STUDENT) {
		List<Appointment> appointmentsList = new ArrayList<>();
		appointmentRepository.findAll().forEach(a -> {if(a.getSetFor().equals(user)) {
			appointmentsList.add(a);
		}});
		
		
		m.addAttribute("id", user.getId());
		m.addAttribute("firstName", user.getFirstName());
		m.addAttribute("lastName", user.getLastName());
		
		m.addAttribute("appointments", appointmentsList);
		
		return "appointment_student";
		
		}
		
		else {
			return "redirect";
		}
	}
	
	
	public String confirmAppointment(int id,  Model m, HttpSession session, RedirectAttributes redirectAttributes) {
		
		User user = userRepository.findById(Integer.parseInt(session.getAttribute("id").toString())).get();
		
		
		if(user != null && user.getRole() == UserRole.STUDENT) {
			
			Appointment appointment = appointmentRepository.findById(id).get();
			
			if(appointment != null) {
				
				appointment.setIsConfirmed(true);
				appointmentRepository.save(appointment);
				
				redirectAttributes.addFlashAttribute("success", "Appointment confirmed successfully!");
				
				
				
				return "redirect:/student/appointments";
			}
		}
		
		return "redirect";
	}
	
	public String setAppointment(Map<String, String> params,  Model m, HttpSession session, RedirectAttributes redirectAttributes) throws ParseException {
		
		User user = userRepository.findById(Integer.parseInt(session.getAttribute("id").toString())).get();
		
		
		if(user != null && user.getRole() == UserRole.STAFF) {
			
				User setFor = userRepository.findById(Integer.parseInt(params.get("student"))).get();
				String purpose = params.get("purpose");
				String description = params.get("description").equals("") ? null : params.get("description");
				Date date = (Date) formatter.parse(params.get("date"));

				List<Appointment> appointments = appointmentRepository.findAllBySetFor(setFor);

				if(appointments != null){
					for(Appointment appointment: appointments){
						if(appointment.getDate().equals(date)){
							Calendar c = Calendar.getInstance();
							c.setTime(date);
							c.add(Calendar.DAY_OF_YEAR, 7);
							date = c.getTime();
						}
					}
				}

				Appointment appointment = new Appointment(date, purpose, description, user, setFor);
				
				appointment = appointmentRepository.save(appointment);
				if(appointment != null) {
					
					String body = "You have a new appointment on "+date.toString()+", by "+user.getFirstName()+" "+user.getLastName()+"."
							+ "\nYou can check the details in your account.";
	   				String subject = "Academic Support System";
	   				String email = setFor.getEmail();
	   				
	   				try {
	   			        SimpleMailMessage msg = new SimpleMailMessage();
	   			        msg.setTo(email);

	   			        msg.setSubject(subject);
	   			        msg.setText(body);
	   			        javaMailSender.send(msg);
	   					
	   					}catch(Exception e) {
	   						System.out.println(e.getMessage());
	   					}
					redirectAttributes.addFlashAttribute("success", "Appointment is set for the student!");
					
					return "redirect:/staff/appointments";
				}
		
		}
		
		return "redirect";
	}
}

