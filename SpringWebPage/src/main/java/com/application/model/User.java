package com.application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonAutoDetect
@JsonIgnoreProperties({"name"})
public class User {
	
	@Id
	@JsonProperty
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonProperty
	@Column(name = "fname", nullable= false)
	private String fname;
	@JsonProperty
	@Column(name = "lname", nullable= false)
	private String lname;
	@JsonProperty
	@Column(name = "email", nullable= false, unique = true)
	private String email;
	@JsonProperty
	@Column(name = "fullname", nullable= false)
	private String fullname;
	
	public User() {}
	
	public User(String fname, String lname, String email) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.fullname = fname + " " + lname;
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return fullname;
	}

	public void setName() {
		this.fullname = this.fname + " " + this.lname;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fname=" + fname + ", lname=" + lname + ", email=" + email + ", name=" + fullname + "]";
	}

	
	
	
}
