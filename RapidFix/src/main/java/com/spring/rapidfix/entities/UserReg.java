package com.spring.rapidfix.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;



@Entity
@Table(name = "users")
public class UserReg {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id ;
	
	@NotBlank
	private String username ;
	@NotBlank
	private String mobileNo ;
	@NotBlank
	private String password ;
	@NotBlank
	@Transient
	private String confirmPassword ;
	
	
	
	
	// Getter and Setters
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
	
	
	// Constructor
	
	public UserReg() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserReg(Integer id, @NotBlank String username, @NotBlank String mobileNo, @NotBlank String password,
			@NotBlank String confirmPassword) {
		super();
		this.id = id;
		this.username = username;
		this.mobileNo = mobileNo;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}
	
	
	
	//to String
	
	@Override
	public String toString() {
		return "UserReg [id=" + id + ", username=" + username + ", mobileNo=" + mobileNo + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + "]";
	}
	
	
	
	
	
	

}
