package com.spring.rapidfix.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "riders_current_location")
public class CurrentLocation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String riderName ;

	private String petrolpumpName;

	private String ltrCost;

	private String updatedLocation;
	
	private double latitude ;
	
	private double longitude ;
	


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRiderName() {
		return riderName;
	}

	public void setRiderName(String riderName) {
		this.riderName = riderName;
	}

	public String getPetrolpumpName() {
		return petrolpumpName;
	}

	public void setPetrolpumpName(String petrolpumpName) {
		this.petrolpumpName = petrolpumpName;
	}

	public String getLtrCost() {
		return ltrCost;
	}

	public void setLtrCost(String ltrCost) {
		this.ltrCost = ltrCost;
	}

	public String getUpdatedLocation() {
		return updatedLocation;
	}

	public void setUpdatedLocation(String updatedLocation) {
		this.updatedLocation = updatedLocation;
	}
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public CurrentLocation(Integer id, String riderName, String petrolpumpName, String ltrCost, String updatedLocation,
			double latitude, double longitude) {
		super();
		this.id = id;
		this.riderName = riderName;
		this.petrolpumpName = petrolpumpName;
		this.ltrCost = ltrCost;
		this.updatedLocation = updatedLocation;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public CurrentLocation() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CurrentLocation [id=" + id + ", riderName=" + riderName + ", petrolpumpName=" + petrolpumpName
				+ ", ltrCost=" + ltrCost + ", updatedLocation=" + updatedLocation + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
	}

	
	
	

	
	
	

}

//s
