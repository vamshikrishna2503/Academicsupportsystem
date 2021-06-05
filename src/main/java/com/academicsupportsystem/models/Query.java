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
@Table(name = "query")
public class Query implements Comparable<Query>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private int id;

	@Column(nullable = false)
	private String message;

	@Column(nullable = true)
	private String reply;

	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy, timezone = UTC")
	private Date date;

	@ManyToOne
	@JoinColumn(name = "user_id_sender")
	private User sender;

	@ManyToOne
	@JoinColumn(name = "user_id_receiver")
	private User receiver;

	public Query() {
		
	}
	
	public Query(String messageBody, User sender, User reciever) {
		
		this.message = messageBody;
		this.date = new Date();
		this.sender = sender;
		this.receiver = reciever;
	}

	public Query(int id, String message, String reply, Date date, User sender, User receiver) {
		super();
		this.id = id;
		this.message = message;
		this.reply = reply;
		this.date = date;
		this.sender = sender;
		this.receiver = receiver;
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public void setMessage(String messageBody) {
		this.message = messageBody;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	@Override
	public int compareTo(Query o) {
		if(o == null || this == null) {
			return -1;
		}else if(this.equals(o))
		{
			return 0;
		}
		
		return 1;
	}

}
