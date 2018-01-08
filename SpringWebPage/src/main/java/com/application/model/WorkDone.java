package com.application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonAutoDetect
public class WorkDone {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	@JsonProperty
	@Column(name = "serviceId", nullable= false)
	private Long serviceId;
	@JsonProperty
	@Column(name = "partId", nullable= false)
	private Long partId;
	@JsonProperty
	@Column(name = "notes", nullable= true)
	private String notes;
	@JsonProperty
	@Column(name = "advisory", nullable= true)
	private String advisory;
	@JsonProperty
	@Column(name = "completed", nullable= false)
	private Boolean completed;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name= "serviceId", referencedColumnName= "serviceId")
	private Service service;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name= "partId", referencedColumnName= "partId")
	private Part part;
	
	public WorkDone() {}

	public WorkDone(Long serviceId, Long partId, String notes, String advisory, Boolean completed) {
		super();
		this.serviceId = serviceId;
		this.partId = partId;
		this.notes = notes;
		this.advisory = advisory;
		this.completed= completed;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getAdvisory() {
		return advisory;
	}

	public void setAdvisory(String advisory) {
		this.advisory = advisory;
	}

	@Override
	public String toString() {
		return "WorkDone [serviceId=" + serviceId + ", partId=" + partId + ", notes=" + notes + ", advisory=" + advisory
				+ "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
