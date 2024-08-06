package com.spring.rapidfix.entities;

import java.util.Arrays;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;


@Entity
@Table(name = "fuel_order")
public class OrderFuelForm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id ;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
	private UserReg user;
	
	@NotEmpty
	private String userName ;
	@NotEmpty
	private String mobileNo ;
	@NotEmpty
	private String vehicleNo ;
	@NotEmpty
	private String vehicleType ;
	@NotEmpty
	private String fuelType ;
	@NotEmpty
	private String liters ;
	@NotEmpty
	private String vehicleImage ;
	@NotEmpty
	private String manualAddress ;
	@NotEmpty
	private String autoAddress ;
	
	private Double latitude ;
	private Double longitude ;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getFuelType() {
		return fuelType;
	}
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}
	public String getLiters() {
		return liters;
	}
	public void setLiters(String liters) {
		this.liters = liters;
	}
	
	public String getVehicleImage() {
		return vehicleImage;
	}
	public void setVehicleImage(String vehicleImage) {
		this.vehicleImage = vehicleImage;
	}
	public String getManualAddress() {
		return manualAddress;
	}
	public void setManualAddress(String manualAddress) {
		this.manualAddress = manualAddress;
	}
	public String getAutoAddress() {
		return autoAddress;
	}
	public void setAutoAddress(String autoAddress) {
		this.autoAddress = autoAddress;
	}
	public UserReg getUser() {
		return user;
	}
	public void setUser(UserReg user) {
		this.user = user;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public OrderFuelForm(Integer id, UserReg user, @NotEmpty String userName, @NotEmpty String mobileNo,
			@NotEmpty String vehicleNo, @NotEmpty String vehicleType, @NotEmpty String fuelType,
			@NotEmpty String liters, @NotEmpty String vehicleImage, @NotEmpty String manualAddress,
			@NotEmpty String autoAddress, Double latitude, Double longitude) {
		super();
		this.id = id;
		this.user = user;
		this.userName = userName;
		this.mobileNo = mobileNo;
		this.vehicleNo = vehicleNo;
		this.vehicleType = vehicleType;
		this.fuelType = fuelType;
		this.liters = liters;
		this.vehicleImage = vehicleImage;
		this.manualAddress = manualAddress;
		this.autoAddress = autoAddress;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public OrderFuelForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "OrderFuelForm [id=" + id + ", user=" + user + ", userName=" + userName + ", mobileNo=" + mobileNo
				+ ", vehicleNo=" + vehicleNo + ", vehicleType=" + vehicleType + ", fuelType=" + fuelType + ", liters="
				+ liters + ", vehicleImage=" + vehicleImage + ", manualAddress=" + manualAddress + ", autoAddress="
				+ autoAddress + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	
	
	
	
	
	
	/*
	 LOGIC WRITEN FOR AUTOADDRESS IN JAVASCRIPT
	 
	 <!DOCTYPE html>
<html lang="en">
<head>
   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>Document</title>
</head>
<body>
 <button class="geo-btn">CLICK</button>
 <p class="showDetails">User Location Details</p>
 <p class="fullAddress">User Address</p>
 <p class="formattedAddress">User Address</p>
<script>
const showDetails = document.querySelector(".showDetails");
const fullAddress = document.querySelector(".fullAddress");
const formattedAddress = document.querySelector('.formattedAddress');

let apiEndpoint = "https://api.opencagedata.com/geocode/v1/json";
let apikey = "a83f041117f24e74b2e235ba14f797f7";

const getUserCurrentAddress = async (latitude, longitude) => {
 let query = `${latitude},${longitude}`;
 let apiUrl = `${apiEndpoint}?key=${apikey}&q=${query}&pretty=1`;

 try {
   const res = await fetch(apiUrl);
   const data = await res.json();
   const { city, state, postcode, country } = data.results[0].components;
   fullAddress.textContent = `User address: ${city}, ${postcode}, ${state}, ${country}`;
   formattedAddress.textContent = `User Full address: ${data.results[0].formatted}`;
 } catch (error) {
   console.log(error);
 }
};

document.querySelector(".geo-btn").addEventListener("click", () => {
 if (navigator.geolocation) {
   navigator.geolocation.getCurrentPosition(
     (position) => {
       const { latitude, longitude } = position.coords;
       showDetails.textContent = `the latitude ${latitude} & longitude ${longitude}`;
       getUserCurrentAddress(latitude, longitude);
     },
     (error) => {
       showDetails.textContent = error.message;
     }
   );
 }
});
</script>
</body>
</html>

	
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	

}

//s
