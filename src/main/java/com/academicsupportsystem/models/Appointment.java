package com.academicsupportsystem.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="appointment")
public class Appointment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false, unique=true)
	private int id;
	
	@Column(nullable = false)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy, timezone = UTC")
	private Date date;
	
	@Column(nullable=false)
	private boolean isConfirmed = false;
	
	@Column(nullable = false)
	private String purpose;
	
	@Column(nullable = true)
	private String description;
	
	@Column(nullable = false)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy, timezone = UTC")
	private Date createdOn;

	@ManyToOne
	@JoinColumn(name = "setBy")
	private User setBy;
	
	@ManyToOne
	@JoinColumn(name = "setFor")
	private User setFor;

	
	public Appointment() {
		
	}
	
	public Appointment(Date date, String purpose, String description, User setBy, User setFor) {
		
		this.date = date;
		
		this.purpose = purpose;
		this.description = description;
		this.createdOn = new Date();
		this.setBy = setBy;
		this.setFor = setFor;
	}
	public Appointment(int id, Date date, boolean isConfirmed, String purpose, String description, Date createdOn,
			User setBy, User setFor) {
		super();
		this.id = id;
		this.date = date;
		this.isConfirmed = isConfirmed;
		this.purpose = purpose;
		this.description = description;
		this.createdOn = createdOn;
		this.setBy = setBy;
		this.setFor = setFor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public User getSetBy() {
		return setBy;
	}

	public void setSetBy(User setBy) {
		this.setBy = setBy;
	}

	public User getSetFor() {
		return setFor;
	}

	public void setSetFor(User setFor) {
		this.setFor = setFor;
	}
	
	
}
