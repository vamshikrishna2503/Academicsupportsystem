package com.academicsupportsystem.services;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.academicsupportsystem.repositories.DatabaseFileRepository;
import com.academicsupportsystem.repositories.QueryRepository;
import com.academicsupportsystem.repositories.UserRepository;
import com.ibm.icu.util.Calendar;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import com.academicsupportsystem.models.*;

@Service
public class ReportsService {

	@Autowired
	DatabaseFileService databaseFileService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	QueryRepository queryRepository;

	@Autowired
	DatabaseFileRepository databaseFileRepository;


	DateFormat formatter = new SimpleDateFormat("ddMMyyyy-hhmmss", Locale.ENGLISH);

	public String getAdminReports(Model m, HttpSession session) {
		User user = userRepository.findById(Integer.parseInt(session.getAttribute("id").toString())).get();
		List<DatabaseFile> reports = (List<DatabaseFile>) databaseFileRepository.findAll().stream().filter(file->file.getGeneratedBy().getId() == user.getId()).collect(Collectors.toList());

		if(user != null && user.getRole().equals(UserRole.ADMIN)) {


			m.addAttribute("id", user.getId());
			m.addAttribute("firstName", user.getFirstName());
			m.addAttribute("lastName", user.getLastName());
			m.addAttribute("reports", reports);

			return "report_admin";
		}


		return "redirect";
	}

	public String getStaffReports(Model m, HttpSession session) {
		User user = userRepository.findById(Integer.parseInt(session.getAttribute("id").toString())).get();
		List<DatabaseFile> reports = (List<DatabaseFile>) databaseFileRepository.findAll().stream().filter(file->file.getGeneratedBy().getId() == user.getId()).collect(Collectors.toList());

		if(user != null && user.getRole().equals(UserRole.STAFF)) {

			m.addAttribute("id", user.getId());
			m.addAttribute("firstName", user.getFirstName());
			m.addAttribute("lastName", user.getLastName());
			m.addAttribute("reports", reports);

			return "report_staff";
		}


		return "redirect";
	}


	public String createAdminReportMonthly(Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes) throws JRException, FileNotFoundException, IOException {

		Optional<User> user = userRepository.findById((Integer) session.getAttribute("id"));

		if(!user.equals(null) && user.get().getRole().equals(UserRole.ADMIN)) {


			int month = Integer.parseInt(params.get("month"));
			List<Report> reportList = new ArrayList<>();
			List<Query> queries = (List<Query>) queryRepository.findAll();
			Calendar calendar = Calendar.getInstance();
			for(Query query: queries) {

				calendar.setTime(query.getDate());
				int query_month = calendar.get(Calendar.MONTH);

				if(month == query_month) {


					String studentName = query.getSender().getFirstName()+" "+query.getSender().getLastName();
					String staffName = query.getReceiver().getFirstName()+" "+query.getReceiver().getLastName();
					String reply = query.getReply() == null ? "FALSE" : "TRUE"; 

					Report r = new Report(query.getId(), studentName, staffName, formatter.format(query.getDate()), reply);
					reportList.add(r);
				}

			}

			String createdBy = user.get().getFirstName()+" "+user.get().getLastName();


			if(!reportList.isEmpty())
			{
				User u = user.get();
				createPdfReportAdmin(reportList, createdBy, "monthly", u);
				redirectAttributes.addFlashAttribute("success", "Report generated successfully!");
			}else {
				redirectAttributes.addFlashAttribute("error", "There was an error generating the report!");
			}

		}

		return "redirect:/admin/reports";
	}

	public String createAdminReportYearly(Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes) throws JRException, FileNotFoundException, IOException {

		Optional<User> user = userRepository.findById((Integer) session.getAttribute("id"));
		if(!user.equals(null) && user.get().getRole().equals(UserRole.ADMIN)) {


			int year = Integer.parseInt(params.get("year"));
			int semester = Integer.parseInt(params.get("semester"));
			String department = params.get("department");
			List<Report> reportList = new ArrayList<>();
			List<Query> queries = (List<Query>) queryRepository.findAll();
			Calendar calendar = Calendar.getInstance();
			for(Query query: queries) {


				calendar.setTime(query.getDate());
				int query_year = calendar.get(Calendar.YEAR);

				if(year == query_year) {


					String studentName = query.getSender().getFirstName()+" "+query.getSender().getLastName();
					String staffName = query.getReceiver().getFirstName()+" "+query.getReceiver().getLastName();
					String reply = query.getReply() == null ? "FALSE" : "TRUE"; 

					Report r = new Report(query.getId(), studentName, staffName, formatter.format(query.getDate()), reply);
					reportList.add(r);
				}

			}

			String createdBy = user.get().getFirstName()+" "+user.get().getLastName();

			if(!reportList.isEmpty())
			{
				User u = user.get();
				createPdfReportAdmin(reportList, createdBy, "yearly", u);
				redirectAttributes.addFlashAttribute("success", "Report generated successfully!");
			}else {
				redirectAttributes.addFlashAttribute("error", "There was an error generating the report!");
			}

		}

		return "redirect:/admin/reports";
	}

	public String createStaffReportYearly(Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes) throws JRException, FileNotFoundException, IOException {

		User user = userRepository.findById((Integer) session.getAttribute("id")).get();
		if(!user.equals(null) && user.getRole().equals(UserRole.STAFF)) {

			int year = Integer.parseInt(params.get("year"));
			int semester = Integer.parseInt(params.get("semester"));
			String department = params.get("department");
			List<Report> reportList = new ArrayList<>();
			List<Query> queries = (List<Query>) queryRepository.findAll();
			Calendar calendar = Calendar.getInstance();
			for(Query query: queries) {

				calendar.setTime(query.getDate());
				int query_year = calendar.get(Calendar.YEAR);


				if(query_year == year && query.getReceiver().equals(user) && query.getSender().getSemester() == semester && query.getSender().getDepartment().equals(department)) {


					String studentName = query.getSender().getFirstName()+" "+query.getSender().getLastName();
					String staffName = query.getReceiver().getFirstName()+" "+query.getReceiver().getLastName();
					String reply = query.getReply() == null ? "FALSE" : "TRUE";

					Report r = new Report(query.getId(), studentName, staffName, formatter.format(query.getDate()), reply);
					reportList.add(r);
				}

			}

			List<ReportStudent> reportData = new ArrayList<>();
			for(Report r: reportList) {

				String name = r.getStudentName();
				int noOfQueries = 0;

				if(reportData.stream().filter(report->report.getStudentName().equals(name)).findFirst().isEmpty()) {
					for(Report rep: reportList)
					{
						if(rep.getStudentName().equals(name))
							noOfQueries++;

					}

					ReportStudent reportStudent = new ReportStudent(name, noOfQueries);
					reportData.add(reportStudent);
				}
			}

			String createdBy = user.getFirstName()+" "+user.getLastName();


			if(!reportList.isEmpty())
			{

				createPdfReportStaff(reportData, createdBy, "yearly", user);
				redirectAttributes.addFlashAttribute("success", "Report generated successfully!");
			}else {
				redirectAttributes.addFlashAttribute("error", "There was an error generating the report!");
			}

		}

		return "redirect:/staff/reports";
	}

	public String createStaffReportMonthly(Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes) throws JRException, FileNotFoundException, IOException {

		User user = userRepository.findById((Integer) session.getAttribute("id")).get();
		if(!user.equals(null) && user.getRole().equals(UserRole.STAFF)) {


			int month = Integer.parseInt(params.get("month"));
			int semester = Integer.parseInt(params.get("semester"));
			String department = params.get("department");
			List<Report> reportList = new ArrayList<>();
			List<Query> queries = (List<Query>) queryRepository.findAll();
			Calendar calendar = Calendar.getInstance();
			for(Query query: queries) {

				calendar.setTime(query.getDate());
				int query_month = calendar.get(Calendar.MONTH);


				if(month == query_month && query.getReceiver().equals(user) && query.getSender().getSemester() == semester && query.getSender().getDepartment().equals(department)) {


					String studentName = query.getSender().getFirstName()+" "+query.getSender().getLastName();
					String staffName = query.getReceiver().getFirstName()+" "+query.getReceiver().getLastName();
					String reply = query.getReply() == null ? "FALSE" : "TRUE"; 

					Report r = new Report(query.getId(), studentName, staffName, formatter.format(query.getDate()), reply);
					reportList.add(r);
				}

			}

			List<ReportStudent> reportData = new ArrayList<>();
			for(Report r: reportList) {

				String name = r.getStudentName();
				int noOfQueries = 0;

				if(reportData.stream().filter(report->report.getStudentName().equals(name)).findFirst().isEmpty()) {
					for(Report rep: reportList)
					{
						if(rep.getStudentName().equals(name))
							noOfQueries++;

					}

					ReportStudent reportStudent = new ReportStudent(name, noOfQueries);
					reportData.add(reportStudent);
				}
			}

			String createdBy = user.getFirstName()+" "+user.getLastName();



			if(!reportList.isEmpty())
			{

				createPdfReportStaff(reportData, createdBy, "monthly", user);
				redirectAttributes.addFlashAttribute("success", "Report generated successfully!");
			}else {
				redirectAttributes.addFlashAttribute("error", "There was an error generating the report!");
			}

		}

		return "redirect:/staff/reports";
	}

	public void downloadReportFile(String id, HttpServletResponse response, HttpSession session) throws IOException {
		DatabaseFile dbFile = databaseFileService.getFile(id);
		FileOutputStream out = new FileOutputStream(System.getProperty("user.dir")+"report.pdf");
		out.write(dbFile.getData());
		out.close();

		File file = new File(System.getProperty("user.dir")+"report.pdf");

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment;filename=" + dbFile.getFileName());
		BufferedInputStream inStrem = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());

		byte[] buffer = new byte[1024];
		int bytesRead = 0;
		while ((bytesRead = inStrem.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		outStream.flush();
		inStrem.close();
	}

	private void createPdfReportAdmin(final List<Report> reportList, String createdBy, String type, User user) throws JRException, FileNotFoundException, IOException {

		final InputStream stream = this.getClass().getResourceAsStream("/report_queries_student.jrxml");


		final JasperReport report = JasperCompileManager.compileReport(stream);


		final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(reportList);


		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("createdBy", createdBy);


		final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);


		final String filePath = System.getProperty("user.dir") + "\\reports\\admin\\";
		final String fileName = "QueryReport-"+type+"-"+formatter.format(new Date())+".pdf";

		JasperExportManager.exportReportToPdfFile(print, filePath + fileName);

		MultipartFile file = new MockMultipartFile(fileName, new FileInputStream(new File(filePath+fileName)));
		databaseFileService.storeFile(file, fileName, ".pdf", user);

		File f = new File(filePath+fileName);
		f.delete();
	}

	private void createPdfReportStaff(final List<ReportStudent> reportList, String createdBy, String type, User user) throws JRException, FileNotFoundException, IOException {

		final InputStream stream = this.getClass().getResourceAsStream("/report_bar_chat1.jrxml");


		final JasperReport report = JasperCompileManager.compileReport(stream);


		final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(reportList);


		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("createdBy", createdBy);


		final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);
	
		final String filePath = System.getProperty("user.dir") + "\\reports\\staff\\";
		final String fileName = "QueryReport-Staff-"+type+"-"+formatter.format(new Date())+".pdf";

		JasperExportManager.exportReportToPdfFile(print, filePath + fileName);

		MultipartFile file = new MockMultipartFile(fileName, new FileInputStream(new File(filePath+fileName)));
		databaseFileService.storeFile(file, fileName, ".pdf", user);

		File f = new File(filePath+fileName);
		f.delete();
	}
}
