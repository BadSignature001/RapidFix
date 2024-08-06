package com.spring.rapidfix.entities;


import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import jakarta.validation.constraints.NotEmpty;



@Entity
@Table(name = "users")
public class UserReg implements UserDetails{
	
	
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
	
	@OneToMany(mappedBy = "user")
    private List<UserToken> tokens;
	
	
	@OneToMany(mappedBy = "user")
    private List<OrderFuelForm> fuelOrder;
	
	
	
	
	
	
	public List<OrderFuelForm> getFuelOrder() {
		return fuelOrder;
	}
	public void setFuelOrder(List<OrderFuelForm> fuelOrder) {
		this.fuelOrder = fuelOrder;
	}
	public List<UserToken> getTokens() {
		return tokens;
	}
	public void setTokens(List<UserToken> tokens) {
		this.tokens = tokens;
	}
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
	
	
	public UserReg(Integer id, @NotEmpty String username, @NotEmpty String mobileNo, @NotEmpty String password,
			@NotEmpty String confirmPassword, List<UserToken> tokens, List<OrderFuelForm> fuelOrder) {
		super();
		this.id = id;
		this.username = username;
		this.mobileNo = mobileNo;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.tokens = tokens;
		this.fuelOrder = fuelOrder;
	}
	
	public UserReg() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public String toString() {
		return "UserReg [id=" + id + ", username=" + username + ", mobileNo=" + mobileNo + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + ", tokens=" + tokens + ", fuelOrder=" + fuelOrder + "]";
	}
	
	
	
	
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	

	
	

	
	

}

//s
