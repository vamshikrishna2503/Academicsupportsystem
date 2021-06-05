package com.academicsupportsystem.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.academicsupportsystem.models.Query;
import com.academicsupportsystem.models.User;
import com.academicsupportsystem.models.UserRole;
import com.academicsupportsystem.repositories.QueryRepository;
import com.academicsupportsystem.repositories.UserRepository;

@Service
public class QueryService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	QueryRepository queryRepository;

	@Autowired
	JavaMailSender javaMailSender;

	public String getStaffMessage(Model m, HttpSession session) {
		User user = userRepository.findById(Integer.parseInt(session.getAttribute("id").toString())).get();
		List<User> students = userRepository.findByRole(UserRole.STUDENT);  

		if(user != null && user.getRole().equals(UserRole.STAFF)) {

			m.addAttribute("id", user.getId());
			m.addAttribute("firstName", user.getFirstName());
			m.addAttribute("lastName", user.getLastName());
			m.addAttribute("students", students);
			return "message_staff";
		}

		return "redirect";
	}

	public String getStudentQueries(Model m, HttpSession session) {

		User user = userRepository.findById(Integer.parseInt(session.getAttribute("id").toString())).get();
		List<User> staffMembers = userRepository.findByRole(UserRole.STAFF);  

		if(user != null && user.getRole().equals(UserRole.STUDENT)) {
			List<Query> queriesList = new ArrayList<>();
			queryRepository.findAll().forEach(q -> { if(q.getSender().equals(user)) {
				queriesList.add(q);
			}});


			m.addAttribute("id", user.getId());
			m.addAttribute("firstName", user.getFirstName());
			m.addAttribute("lastName", user.getLastName());

			m.addAttribute("queries", queriesList);
			m.addAttribute("staffMembers", staffMembers);

			return "query_student";

		}

		else {
			return "redirect";
		}

	}

	public String getReplyQuery(int id, Model m, HttpSession session) {
		User user = userRepository.findById(Integer.parseInt(session.getAttribute("id").toString())).get();

		if(user!=null && user.getRole().equals(UserRole.STAFF)) {

			String message = queryRepository.findById(id).get().getMessage();

			m.addAttribute("id", user.getId());
			m.addAttribute("firstName", user.getFirstName());
			m.addAttribute("lastName", user.getLastName());

			m.addAttribute("query_id", id);
			m.addAttribute("query_message", message);

			return "reply_query";
		}

		return "redirect";
	}

	public String replyQuery(Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes) {
		User user = userRepository.findById(Integer.parseInt(session.getAttribute("id").toString())).get();

		if(user!=null && user.getRole().equals(UserRole.STAFF)) {

			String reply = params.get("reply");
			int id = Integer.parseInt(params.get("id"));

			Query query = queryRepository.findById(id).get();

			if(query != null ) {

				query.setReply(reply);
				queryRepository.save(query);

				String body = "Your query:\n"+query.getMessage()+".\nHas been replied by the department, you can check the reply now!";
				String subject = "Academic Support System";
				String email = query.getSender().getEmail();

				try {
					SimpleMailMessage msg = new SimpleMailMessage();
					msg.setTo(email);

					msg.setSubject(subject);
					msg.setText(body);
					javaMailSender.send(msg);

				}catch(Exception e) {
					System.out.println(e.getMessage());
				}

				redirectAttributes.addFlashAttribute("success", "Your reply is sent to the student successfully!");


				return "redirect:/staff/queries";
			}
		}

		redirectAttributes.addFlashAttribute("error", "There was a problem sending your reply!");
		return "redirect";


	}
	public String getStaffQueries(Model m, HttpSession session) {

		User user = userRepository.findById(Integer.parseInt(session.getAttribute("id").toString())).get();

		if(user != null && user.getRole().equals(UserRole.STAFF)) {
			List<Query> queriesList = new ArrayList<>();

			queryRepository.findAll().forEach(q -> { if(q.getReceiver().equals(user)) {
				queriesList.add(q);
			}});

			Collections.sort(queriesList, Comparator.nullsLast(Comparator.naturalOrder()));

			m.addAttribute("id", user.getId());
			m.addAttribute("firstName", user.getFirstName());
			m.addAttribute("lastName", user.getLastName());

			m.addAttribute("queries", queriesList);

			return "query_staff";

		}

		else {
			return "redirect";
		}

	}

	public String addQuery(Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes) {

		Optional<User> user = userRepository.findById((Integer) session.getAttribute("id"));
		if(!user.equals(null) && user.get().getRole().equals(UserRole.STUDENT)) {

			User sender = user.get();
			User reciever = userRepository.findById(Integer.parseInt(params.get("staffMember"))).get();
			String queryMessage = params.get("query");

			Query query = new Query(queryMessage, sender, reciever);
			query = queryRepository.save(query);

			if(query != null) {
				redirectAttributes.addFlashAttribute("success", "Query sent successfully!");
			}else {
				redirectAttributes.addFlashAttribute("error", "There was an error sending the query!");
			}
		}


		return "redirect:queries";
	}

	public String sendMessage(Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes) {

		Optional<User> user = userRepository.findById((Integer) session.getAttribute("id"));
		if(!user.equals(null) && user.get().getRole().equals(UserRole.STAFF)) {


			User recipient = userRepository.findById(Integer.parseInt(params.get("student"))).get();
			String subject = params.get("subject");
			String message = params.get("message");
			try {
				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setTo(recipient.getEmail());

				msg.setSubject(subject);
				msg.setText(message);
				javaMailSender.send(msg);

			}catch(Exception e) {
				System.out.println(e.getMessage());
			}


			redirectAttributes.addFlashAttribute("success", "Message sent successfully!");

			return "redirect:/staff/message";
		}

		redirectAttributes.addFlashAttribute("error", "There was an error sending the message!");


		return "redirect:/staff/message";
	}
}
