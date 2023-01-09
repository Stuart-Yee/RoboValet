package com.robovalet.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="conversations")
public class Conversation {
	/*
	 *  The Conversation object represents a "bot" that the customer
	 *  interacts with. The purpose of the Conversation is to 
	 *  manage the state of a customer's interaction via SMS.
	 *  
	 *  Attributes represent information from the customer that the 
	 *  Conversation "cares" about and what questions have been 
	 *  sent to the customer.
	 */
	
	public enum Stage {
		NEW,
		ASKEDSTAYVERIFICATION,
		STAYVERIFIED,
		ASKEDREADYVERIFICATION,
		READYVERIFIED,
		CLOSED
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(updatable=false)
	private Date createdAt;
	
	private Date updatedAt;
	
	private Date closedAt;
	
	private String SMS;
	
	private Boolean multipleStaysPossible;
	
	private Stage stage;
	
	@Column(length=10000)
	private String chatLog;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="stay_id")
	private Stay stay;
	
	public Conversation() {}
	
	public Conversation(String sms, Stage stage) {
		this.SMS = sms;
		this.stage = stage;
		this.chatLog = "";
	}
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getClosedAt() {
		return closedAt;
	}

	public void setClosedAt(Date closedAt) {
		this.closedAt = closedAt;
	}

	public String getSMS() {
		return SMS;
	}

	public void setSMS(String sMS) {
		SMS = sMS;
	}

	public Boolean getMultipleStaysPossible() {
		return multipleStaysPossible;
	}

	public void setMultipleStaysPossible(Boolean multipleStaysPossible) {
		this.multipleStaysPossible = multipleStaysPossible;
	}

	public String getChatLog() {
		return chatLog;
	}

	public void setChatLog(String chatLog) {
		this.chatLog = chatLog;
	}

	public Stay getStay() {
		return stay;
	}

	public void setStay(Stay stay) {
		this.stay = stay;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	
	

}
