package com.academicsupportsystem.controllers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.academicsupportsystem.models.DatabaseFile;
import com.academicsupportsystem.services.DatabaseFileService;
import com.academicsupportsystem.services.ReportsService;
import com.lowagie.text.pdf.codec.Base64.OutputStream;

import net.sf.jasperreports.engine.JRException;

@Controller
public class ReportsController {

	@Autowired
	ReportsService reportsService;

	@Autowired
	DatabaseFileService databaseFileService;

	@RequestMapping(value="/admin/reports", method=RequestMethod.GET)
	public String getAdminReport(Model m, HttpSession session) {
		return reportsService.getAdminReports(m, session);
	}

	@RequestMapping(value="/staff/reports", method=RequestMethod.GET)
	public String getStudentReport(Model m, HttpSession session) {
		return reportsService.getStaffReports(m, session);
	}

	@RequestMapping(value="/reports/download/{id}", method=RequestMethod.GET)
	public void downloadReportFile(@PathVariable("id") String id, HttpServletResponse response, HttpSession session) throws IOException {

		reportsService.downloadReportFile(id, response, session);
	}

	@RequestMapping(value="/admin/reports/monthly", method=RequestMethod.POST)
	public String createAdminReportMonthly(@RequestParam Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes) throws JRException, FileNotFoundException, IOException {
		return reportsService.createAdminReportMonthly(params, m, session, redirectAttributes);
	}

	@RequestMapping(value="/admin/reports/yearly", method=RequestMethod.POST)
	public String createAdminReportYearly(@RequestParam Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes) throws JRException, FileNotFoundException, IOException {
		return reportsService.createAdminReportYearly(params, m, session, redirectAttributes);
	}
	
	@RequestMapping(value="/staff/reports/monthly", method=RequestMethod.POST)
	public String createStaffReportMonthly(@RequestParam Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes) throws JRException, FileNotFoundException, IOException {
		return reportsService.createStaffReportMonthly(params, m, session, redirectAttributes);
	}

	@RequestMapping(value="/staff/reports/yearly", method=RequestMethod.POST)
	public String createStaffReportYearly(@RequestParam Map<String, String> params, Model m, HttpSession session, RedirectAttributes redirectAttributes) throws JRException, FileNotFoundException, IOException {
		return reportsService.createStaffReportYearly(params, m, session, redirectAttributes);
	}
}
