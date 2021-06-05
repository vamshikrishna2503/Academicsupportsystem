package com.academicsupportsystem.models;

import java.util.Date;

public class Report {

	private int id;
	private String studentName;
	private String staffName;
	private String date;
	private String reply;
	
	public Report() {
		
	}
	
	public Report(int id, String studentName, String staffName, String date, String reply) {
		super();
		this.id = id;
		this.studentName = studentName;
		this.staffName = staffName;
		this.date = date;
		this.reply = reply;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	
	
}
