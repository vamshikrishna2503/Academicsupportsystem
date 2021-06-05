package com.academicsupportsystem.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name= "user")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name= "user_id", nullable = false, unique = true)
	private int id;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private boolean isActive = false;

	@Column(nullable = false)
	private String department;

	@Column(nullable = false)
	private int semester = -1;

	@Column(nullable=true)
	private String secondaryEmail;

	@Column(nullable = true)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy, timezone = UTC")
	private Date createdOn;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
	Set<Query> sentMessages;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "receiver")
	Set<Query> receivedMessages;
	
	public User() {
		
	}

	public User(int id, String email,  String password, UserRole role, String firstName, String lastName, boolean isActive) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.role = role;
		this.isActive = isActive;
		this.createdOn = new Date();
	}

	public User(String email,  String password, UserRole role, String firstName, String lastName, String department, String secondaryEmail) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.role = role;
		this.isActive = true;
		this.department = department;
		this.createdOn = new Date();
		this.secondaryEmail = secondaryEmail;
	}

	public User(String email,  String password, UserRole role, String firstName, String lastName, String department, int semester, String secondaryEmail) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.role = role;
		this.isActive = true;
		this.department = department;
		this.semester = semester;
		this.createdOn = new Date();
		this.secondaryEmail = secondaryEmail;
	}
	public User(int id, String email, UserRole role, String firstName, String lastName, String password, Date createdOn) {
		super();
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.role = role;
		this.createdOn = createdOn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isActive() {
		return this.isActive;
	}
	public void isActive(boolean isActive) {
		this.isActive  = isActive;
	}
	
	public UserRole getRole() {
		return this.role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Set<Query> getSentMessages() {
		return sentMessages;
	}
	public void setSentMessages(Set<Query> sentMessages) {
		this.sentMessages = sentMessages;
	}


	public Set<Query> getReceivedMessages() {
		return receivedMessages;
	}


	public void setReceivedMessages(Set<Query> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public String getSecondaryEmail(){
		return this.secondaryEmail;
	}
	public void setSecondaryEmail(String email){
		this.secondaryEmail = email;
	}
}
