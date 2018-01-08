package com.application.model;

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
public class Part {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long partID;
	
	@JsonProperty
	@Column(name = "name", nullable= false)
	private String name;
	@JsonProperty
	@Column(name = "type", nullable= false)
	private String type;
	@JsonProperty
	@Column(name = "desctiption", nullable= false)
	private String description;
	@JsonProperty
	@Column(name = "retailPrice", nullable= false)
	private float retailPrice;
	@JsonProperty
	@Column(name = "costPrice", nullable= false)
	private float costPrice;
	
	@JsonProperty
	@OneToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "WorkDone", 
	joinColumns= @JoinColumn(name= "partId"),
	inverseJoinColumns= @JoinColumn(name= "serviceId"))
	private List<WorkDone> services = new ArrayList<WorkDone>();
	
	public Part() {}

	public Part(String name, String type, String description, float retailPrice, float costPrice) {
		super();
		this.name = name;
		this.type = type;
		this.description = description;
		this.retailPrice = retailPrice;
		this.costPrice = costPrice;
	}

	public Long getPartID() {
		return partID;
	}

	public void setPartID(Long partID) {
		this.partID = partID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(float retailPrice) {
		this.retailPrice = retailPrice;
	}

	public float getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(float costPrice) {
		this.costPrice = costPrice;
	}
	
	public boolean addWork(WorkDone workDone) {
		try{
			this.services.add(workDone);
			return true;
		}catch(Exception ex){
			System.out.println("Exception: Not Added");
			return false;
		}
	}
	
	public boolean removeWork(WorkDone workDone) {
		try {
			this.services.remove(workDone);
			return true;
		}catch(Exception ex) {
			System.out.println("Exception: Not Removed");
			return false;
		}
	}

	@Override
	public String toString() {
		return "Part [partID=" + partID + ", name=" + name + ", type=" + type + ", description=" + description
				+ ", retailPrice=" + retailPrice + ", costPrice=" + costPrice + ", services=" + services + "]";
	}	
	
}
