package com.application.model;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonAutoDetect
public class Service {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long serviceId;
	
	@JsonProperty
	@Column(name = "due_date", nullable= false)
	private Date due_date;
	@JsonProperty
	@Column(name = "completed", nullable= true)
	private Date completed;
	@JsonProperty
	@Column(name = "registration", nullable= false)
	private String registration;
	@JsonProperty
	@Column(name = "garageId", nullable= false)
	private Long garageId;
	@JsonProperty
	@Column(name = "total_amount", nullable= true)
	private float total_amount;
	@JsonProperty
	@OneToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "WorkDone", 
	joinColumns= @JoinColumn(name= "serviceId"),
	inverseJoinColumns= @JoinColumn(name= "partId"))
	private List<WorkDone> parts = new ArrayList<WorkDone>();
	
	public Service() {}

	public Service(String registration, Date due_date, Date completed, Long garageId) {
		super();
		this.due_date = due_date;
		this.completed = completed;
		this.registration = registration;
		this.garageId = garageId;
	}

	public Long getId() {
		return serviceId;
	}

	public void setId(Long id) {
		this.serviceId = id;
	}

	public Date getDue_date() {
		return due_date;
	}

	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}

	public Date getCompleted() {
		return completed;
	}

	public void setCompleted(Date completed) {
		this.completed = completed;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public Long getGarageId() {
		return garageId;
	}

	public void setGarageId(Long garageId) {
		this.garageId = garageId;
	}

	public float getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(float total_amount) {
		this.total_amount = total_amount;
	}
	
	public boolean addWork(WorkDone workDone) {
		try{
			this.parts.add(workDone);
			return true;
		}catch(Exception ex){
			System.out.println("Exception: Not Added");
			return false;
		}
	}
	
	public boolean removeWork(WorkDone workDone) {
		try {
			this.parts.remove(workDone);
			return true;
		}catch(Exception ex) {
			System.out.println("Exception: Not Removed");
			return false;
		}
	}

	@Override
	public String toString() {
		return "Service [serviceId=" + serviceId + ", due_date=" + due_date + ", completed=" + completed
				+ ", registration=" + registration + ", garageId=" + garageId + ", total_amount=" + total_amount
				+ ", parts=" + parts + "]";
	}

	
	
	

}
