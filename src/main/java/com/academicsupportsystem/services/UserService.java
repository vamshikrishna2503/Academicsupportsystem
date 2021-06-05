package com.academicsupportsystem.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.academicsupportsystem.models.*;
import com.academicsupportsystem.repositories.AppointmentRepository;
import com.academicsupportsystem.repositories.DatabaseFileRepository;
import com.academicsupportsystem.repositories.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.academicsupportsystem.repositories.UserRepository;
import com.academicsupportsystem.utils.PasswordGenerator;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AppointmentRepository appointmentRepository;

	@Autowired
	DatabaseFileRepository databaseFileRepository;

	@Autowired
	QueryRepository queryRepository;

	@Autowired
    JavaMailSender javaMailSender;

	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	public String login(Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes) {

		ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
		String email = params.get("email");
		UserRole role = UserRole.valueOf(params.get("role"));
		String password = params.get("password");

		
		for(User user: users) {
			if(user.getEmail().equals(email) && bCryptPasswordEncoder.matches(password, user.getPassword())) {
				
				if(user.isActive() == true && user.getRole() == role)
				{
					if(role == UserRole.ADMIN)
					{
						session.setAttribute("id", user.getId());
						m.addAttribute("id", user.getId());
						m.addAttribute("firstName", user.getFirstName());
						m.addAttribute("lastName", user.getLastName());
						
						return "dashboard_admin";
					}else if(role == UserRole.STAFF) {
						session.setAttribute("id", user.getId());
						m.addAttribute("id", user.getId());
						m.addAttribute("firstName", user.getFirstName());
						m.addAttribute("lastName", user.getLastName());

						return "dashboard_staff";
					}else if(role == UserRole.STUDENT) {
						session.setAttribute("id", user.getId());
						m.addAttribute("id", user.getId());
						m.addAttribute("firstName", user.getFirstName());
						m.addAttribute("lastName", user.getLastName());

						return "dashboard_student";
					}
				}

			}
			
			
		}
		
		redirectAttributes.addFlashAttribute("error", "Username/Password/Role incorrect!");
		return "redirect:/";
	}

	public String loginAdmin(Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes) {

		ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
		String email = params.get("email");
		UserRole role = UserRole.ADMIN;
		String password = params.get("password");

		for(User user: users) {
			if(user.getEmail().equals(email) && bCryptPasswordEncoder.matches(password, user.getPassword())) {

				if(user.isActive() && user.getRole() == role)
				{

						session.setAttribute("id", user.getId());
						m.addAttribute("id", user.getId());
						m.addAttribute("firstName", user.getFirstName());
						m.addAttribute("lastName", user.getLastName());

						return "dashboard_admin";

				}

			}


		}
		redirectAttributes.addFlashAttribute("error", "Username/Password/Role incorrect!");
		return "redirect:/";
	}

	public String logout(HttpSession session) {
		session.removeAttribute("id");
		return "redirect:/";
	}
	
	
	public String createUser(Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes) {
		Optional<User> user = userRepository.findById((Integer) session.getAttribute("id"));
		if(!user.equals(null) && user.get().getRole() == UserRole.ADMIN) {
			String firstName = params.get("firstName");
			String lastName = params.get("lastName");
			String email = params.get("email");
			String password = PasswordGenerator.generatePassword();
			UserRole role = UserRole.valueOf(params.get("role"));
			String department = params.get("department");
			String secondaryEmail = params.get("secondary-email");

			User newUser;
			if(role.equals(UserRole.STUDENT))
			{
				int semester = Integer.parseInt(params.get("semester"));
				newUser = new User(email, bCryptPasswordEncoder.encode(password), role, firstName, lastName, department, semester, secondaryEmail);
			}
			else
				newUser = new User(email, bCryptPasswordEncoder.encode(password), role, firstName, lastName, department, secondaryEmail);

			newUser = userRepository.save(newUser);

			if(newUser != null) {
				redirectAttributes.addFlashAttribute("success", "User created successfully!");
				
				String body = "Welcome to Academic Support System.\nPassword for your new account is : " + password + ".\nYou can change it once you Sign in";
   				String subject = "Academic Support System";
   				
   				try {
   			        SimpleMailMessage msg = new SimpleMailMessage();
   			        msg.setTo(email);

   			        msg.setSubject(subject);
   			        msg.setText(body);
   			        javaMailSender.send(msg);
   					
   					}catch(Exception e) {
   						System.out.println(e.getMessage());
   					}
			}
			else
				redirectAttributes.addFlashAttribute("error", "The was an error creating user!");
			
	
		}
		
		return "redirect:users";
	}
	
	
	public String createMultipleUsers(MultipartFile file, Model m, HttpSession session, RedirectAttributes redirectAttributes) {
		
		Optional<User> user = userRepository.findById((Integer) session.getAttribute("id"));
		
		if (!file.isEmpty() && !user.equals(null) && user.get().getRole() == UserRole.ADMIN) {
			try {
		           byte[] bytes = file.getBytes();
		           String completeData = new String(bytes);
		           String[] rows = completeData.split("\n");
		           
		           
		           for(String row: rows) {
		        	   
		        	   	String[] columns = row.split(",");
		        	   	
		        	   	String firstName = columns[0].replaceAll("[^a-zA-Z0-9]", "");
		   				String lastName = columns[1];
		   				String email = columns[2];
		   				String secondaryEmail = columns[3];
		   				String password = PasswordGenerator.generatePassword();
		   				UserRole role = UserRole.valueOf(columns[4].trim().equals("ACADEMIC ADMIN") ? "STAFF" : columns[4].trim());
						String department = columns[5];

					   User newUser;
						if(role.equals(UserRole.STUDENT))
						{
							int semester = Integer.parseInt(columns[6].substring(0, 1));
							newUser = new User(email, bCryptPasswordEncoder.encode(password), role, firstName, lastName, department, semester, secondaryEmail);
						}
						else
		   					 newUser = new User(email, bCryptPasswordEncoder.encode(password), role, firstName, lastName, department, secondaryEmail);
		   				
			   				userRepository.save(newUser);
		   				
		   				String body = "Welcome to Academic Support System.\nPassword for your new account is : " + password + ".\nYou can change it once you Sign in";
		   				String subject = "Academic Support System";
		   				
		   				try {
		   			        SimpleMailMessage msg = new SimpleMailMessage();
		   			        msg.setTo(email);

		   			        msg.setSubject(subject);
		   			        msg.setText(body);
		   			        javaMailSender.send(msg);
		   					
		   					}catch(Exception e) {
		   						System.out.println(e.getMessage());
		   					}
		   				
		           }
		
		      redirectAttributes.addFlashAttribute("success", "Users created successfully!");
			
			}catch(Exception e) {
				System.out.println(e.getMessage());
				redirectAttributes.addFlashAttribute("error", "There was an error creating some users!");
			}
	
		}
		
		return "redirect:users";
	}
	
	public String editUser(Map<String, String> params,  Model m, HttpSession session, RedirectAttributes redirectAttributes)
	{
		Optional<User> admin = userRepository.findById((Integer) session.getAttribute("id"));
		if(!admin.equals(null) && admin.get().getRole() == UserRole.ADMIN) {
			
			int id = Integer.parseInt(params.get("id"));
			String firstName = params.get("firstName");
			String lastName = params.get("lastName");
			String email = params.get("email");
			String password = params.get("password");
			boolean isActive = Boolean.parseBoolean(params.get("isActive"));
			UserRole role = UserRole.valueOf(params.get("role"));

			User user = new User(id, email, bCryptPasswordEncoder.encode(password), role, firstName, lastName, isActive);
			userRepository.save(user);
				
			redirectAttributes.addFlashAttribute("success", user.getFirstName()+ " "+user.getLastName()+" updated successfully!");
			return "redirect:/admin/users";
		}
		else {
				redirectAttributes.addFlashAttribute("error", "There was an error updating user!");
			}
			
			
		
		
		return "redirect: edit_user";
	}
	public String getAdmin(Model m, HttpSession session) {

		if(session.getAttribute("id") == null){
			return "admin_login";
		}
		Optional<User> user = userRepository.findById((Integer) session.getAttribute("id"));
		if(!user.equals(null) && user.get().getRole() == UserRole.ADMIN) {
			m.addAttribute("id", user.get().getId());
			m.addAttribute("firstName", user.get().getFirstName());
			m.addAttribute("lastName", user.get().getLastName());
			
			return "dashboard_admin";
		}
		return "redirect";
	}
	
	
	public String getStaff(Model m, HttpSession session) {
		Optional<User> user = userRepository.findById((Integer) session.getAttribute("id"));
		if(!user.equals(null) && user.get().getRole() == UserRole.STAFF) {
			m.addAttribute("id", user.get().getId());
			m.addAttribute("firstName", user.get().getFirstName());
			m.addAttribute("lastName", user.get().getLastName());
			
			return "dashboard_staff";
		}
		return "redirect";
	}
	
	public String getStudent(Model m, HttpSession session) {
		Optional<User> user = userRepository.findById((Integer) session.getAttribute("id"));
		if(!user.equals(null) && user.get().getRole() == UserRole.STUDENT) {
			m.addAttribute("id", user.get().getId());
			m.addAttribute("firstName", user.get().getFirstName());
			m.addAttribute("lastName", user.get().getLastName());
			
			return "dashboard_student";
		}
		return "redirect";
	}
	
	public String getUsers(Model m, HttpSession session) {
		
		Optional<User> user = userRepository.findById((Integer) session.getAttribute("id"));
		if(!user.equals(null) && user.get().getRole() == UserRole.ADMIN) {
		
			
			List<User> users = (List<User>)userRepository.findAll();
			
			m.addAttribute("id", user.get().getId());
			m.addAttribute("firstName", user.get().getFirstName());
			m.addAttribute("lastName", user.get().getLastName());
			m.addAttribute("role", user.get().getRole().toString());
			
			m.addAttribute("list", users);
			
			return "users";
		}
		else 
			return "redirect";
		
	}
	
	public String getEditUser(int id, Model m, HttpSession session) {
		Optional<User> user = userRepository.findById((Integer) session.getAttribute("id"));
		if(!user.equals(null) && user.get().getRole() == UserRole.ADMIN) {
		
		m.addAttribute("firstName", user.get().getFirstName());
		m.addAttribute("lastName", user.get().getLastName());
		m.addAttribute("id", id);
		
		return "edit_user";
		
		}
		return "redirect:users";
	}
	
	
	public String getResetPassword(int id, Model m, HttpSession session) {
		Optional<User> user = userRepository.findById((Integer) session.getAttribute("id"));
		if(!user.equals(null) && user.get().getRole() == UserRole.ADMIN) {
		
		m.addAttribute("firstName", user.get().getFirstName());
		m.addAttribute("lastName", user.get().getLastName());
		m.addAttribute("id_user", id);
		
		return "reset_password";
		
		}
		return "redirect:users";
	}
	
	public String getResetPassword(Model m, HttpSession session) {
		Optional<User> user = userRepository.findById((Integer) session.getAttribute("id"));
		if(!user.equals(null) && user.get().getRole() == UserRole.STAFF || user.get().getRole() == UserRole.STUDENT) {
		
		
		m.addAttribute("firstName", user.get().getFirstName());
		m.addAttribute("lastName", user.get().getLastName());
		
		return "reset_password";
		}
		return "redirect:users";
	}
	
	public String resetUserPasswordAdmin(Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes) {
		Optional<User> u = userRepository.findById((Integer) session.getAttribute("id"));
		if(!u.equals(null) && u.get().getRole() == UserRole.ADMIN) {
			
			int id = Integer.parseInt(session.getAttribute("reset_password_user_id").toString());
			String password = params.get("password");
			
			Optional<User> user = userRepository.findById(id);
			if(!user.equals(null)) {
				User updatedUser = user.get();
	
				updatedUser.setPassword(bCryptPasswordEncoder.encode(password));
				userRepository.save(updatedUser);
				
				redirectAttributes.addFlashAttribute("success", updatedUser.getFirstName()+ " password changed!");
				return "redirect:/admin/users";
			}
			
			
			else {
				redirectAttributes.addFlashAttribute("error", "Password not changed!");
			}

		}
		else if(!u.equals(null) && u.get().getRole() == UserRole.STUDENT) {
			
			int id = Integer.parseInt(session.getAttribute("id").toString());
			String password = params.get("password");
			
			Optional<User> user = userRepository.findById(id);
			if(!user.equals(null)) {
				User updatedUser = user.get();
	
				updatedUser.setPassword(bCryptPasswordEncoder.encode(password));
				userRepository.save(updatedUser);
				
				redirectAttributes.addFlashAttribute("success", updatedUser.getFirstName()+ " password changed!");
				return "redirect:/student";
			}
			else {
				redirectAttributes.addFlashAttribute("error", "Password not changed!");
			}

		}
		
		else if(!u.equals(null) && u.get().getRole() == UserRole.STAFF) {
			
			int id = Integer.parseInt(session.getAttribute("id").toString());
			String password = params.get("password");
			
			Optional<User> user = userRepository.findById(id);
			if(!user.equals(null)) {
				User updatedUser = user.get();
	
				updatedUser.setPassword(bCryptPasswordEncoder.encode(password));
				userRepository.save(updatedUser);
				
				redirectAttributes.addFlashAttribute("success", updatedUser.getFirstName()+ " password changed!");
				return "redirect:/staff";
			}
			else {
				redirectAttributes.addFlashAttribute("error", "Password not changed!");
			}

		}
		return "redirect: reset_password";
	}
		
		
	
	
	
	public String deleteUser(int user_id, Model m, HttpSession session, RedirectAttributes redirectAttributes) {
		
		Optional<User> admin = userRepository.findById((Integer) session.getAttribute("id"));
		if(!admin.equals(null) && admin.get().getRole() == UserRole.ADMIN) {

			User user = userRepository.findById(user_id).get();
			if(user != null) {
				List<Query> queries = queryRepository.findAllByReceiver(user);

				if (queries != null) {
					queryRepository.deleteAll(queries);
				}

				queries = queryRepository.findAllBySender(user);
				if (queries != null) {
					queryRepository.deleteAll(queries);
				}

				List<Appointment> appointments = appointmentRepository.findAllBySetBy(user);

				if(appointments!=null)
					appointmentRepository.deleteAll(appointments);

				appointments = appointmentRepository.findAllBySetFor(user);

				if(appointments != null)
					appointmentRepository.deleteAll(appointments);

				List<DatabaseFile> files = databaseFileRepository.findAllByGeneratedBy(user);

				if(files != null)
					databaseFileRepository.deleteAll(files);



			}
			userRepository.deleteById(user_id);
			redirectAttributes.addFlashAttribute("success", "User deleted!");
		}
		else {
		redirectAttributes.addFlashAttribute("error", "User not deleted!");
		}
		return "redirect:/admin/users";
	}
	
}
