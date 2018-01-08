package com.application.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonAutoDetect
public class Vehicle {
	
	@Id
	@JsonProperty
	private String registration;
	@JsonProperty
	@Column(name= "VIN", nullable = false)
	private String vehicleIdNo;
	@JsonProperty
	@Column(name = "make", nullable = false)
	private String make;
	@JsonProperty
	@Column(name = "model", nullable= false)
	private String model;
	@JsonProperty
	@Column(name= "last_mot", nullable = false)
	private Date last_mot;
	@JsonProperty
	@Column(name = "last_service", nullable= false)
	private Date last_service;
	@JsonProperty
	@Column(name= "owner_id", nullable = true)
	private Long custId;
	
	public Vehicle() {}

	public Vehicle(String registration, String vehicleIdNo, String make, String model, Date last_mot, Date last_service,
			Long cust_id) {
		super();
		this.registration = registration;
		this.vehicleIdNo = vehicleIdNo;
		this.make = make;
		this.model = model;
		this.last_mot = last_mot;
		this.last_service = last_service;
		this.custId = cust_id;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getVehicleIdNo() {
		return vehicleIdNo;
	}

	public void setVehicleIdNo(String vehicleIdNo) {
		this.vehicleIdNo = vehicleIdNo;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Date getLast_mot() {
		return last_mot;
	}

	public void setLast_mot(Date last_mot) {
		this.last_mot = last_mot;
	}

	public Date getLast_service() {
		return last_service;
	}

	public void setLast_service(Date last_service) {
		this.last_service = last_service;
	}

	public Long getCust_id() {
		return custId;
	}

	public void setCust_id(Long cust_id) {
		this.custId = cust_id;
	}

	@Override
	public String toString() {
		return "Vehicle [registration=" + registration + ", VehicleIdNo=" + vehicleIdNo + ", make=" + make + ", model="
				+ model + ", last_mot=" + last_mot + ", last_service=" + last_service + ", cust_id=" + custId + "]";
	}

}
