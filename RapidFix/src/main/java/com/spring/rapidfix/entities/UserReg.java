package com.spring.rapidfix.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;



@Entity
@Table(name = "users")
public class UserReg {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id ;
	
	@NotEmpty
	private String username ;
	@NotEmpty
	private String mobileNo ;
	@NotEmpty
	private String password ;
	@NotEmpty
	@Transient
	private String confirmPassword ;
	
	
	
	
	
	
	
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
	
	
	
	
	public UserReg() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserReg(Integer id, @NotEmpty String username, @NotEmpty String mobileNo, @NotEmpty String password,
			@NotEmpty String confirmPassword) {
		super();
		this.id = id;
		this.username = username;
		this.mobileNo = mobileNo;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}
	
	
	
	
	
	@Override
	public String toString() {
		return "UserReg [id=" + id + ", username=" + username + ", mobileNo=" + mobileNo + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + "]";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	

}

// sahilbhukal
