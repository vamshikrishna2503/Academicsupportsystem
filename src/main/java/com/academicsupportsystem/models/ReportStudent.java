package com.academicsupportsystem.models;

public class ReportStudent {

	private String studentName;
	private int queries;
	private int id;
	
	
	public ReportStudent() {
		
	}
	
	public ReportStudent(int id, String studentName, int queries) {
		this.id = id;
		this.studentName = studentName;
		this.queries = queries;
	}
	
	public ReportStudent(String studentName, int queries) {
		this.studentName = studentName;
		this.queries = queries;
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
	public int getQueries() {
		return queries;
	}
	public void setQueries(int queries) {
		this.queries = queries;
	}
	
	
}
