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
@Table(name = "riders")
public class RidersReg implements UserDetails{
	
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
	
	
	@Transient
	private String confirmOtp ;
	
	private String jobId ;
	
	
	@OneToMany(mappedBy = "rider")
    private List<RidersToken> tokens;
	
	
	

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

	public String getConfirmOtp() {
		return confirmOtp;
	}

	public void setConfirmOtp(String confirmOtp) {
		this.confirmOtp = confirmOtp;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public List<RidersToken> getTokens() {
		return tokens;
	}

	public void setTokens(List<RidersToken> tokens) {
		this.tokens = tokens;
	}

	public RidersReg(Integer id, @NotEmpty String username, @NotEmpty String mobileNo, @NotEmpty String password,
			@NotEmpty String confirmPassword, String confirmOtp, String jobId, List<RidersToken> tokens) {
		super();
		this.id = id;
		this.username = username;
		this.mobileNo = mobileNo;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.confirmOtp = confirmOtp;
		this.jobId = jobId;
		this.tokens = tokens;
	}
	
	

	public RidersReg() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public String toString() {
		return "RidersReg [id=" + id + ", username=" + username + ", mobileNo=" + mobileNo + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + ", confirmOtp=" + confirmOtp + ", jobId=" + jobId
				+ ", tokens=" + tokens + "]";
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