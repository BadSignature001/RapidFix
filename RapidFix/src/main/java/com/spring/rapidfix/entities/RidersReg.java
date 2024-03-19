package com.spring.rapidfix.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "riders")
public class RidersReg {
	
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
	private String confirmPassword ;
	
	
	
	

}
