package com.spring.rapidfix.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.rapidfix.entities.RidersReg;

@Repository
public interface RidersRepository extends JpaRepository<RidersReg , Integer>{
	
	public Optional<RidersReg> findByUsername(String username);
	
	@Query("SELECT r FROM RidersReg r WHERE r.mobileNo = :mobileNo")
	public Optional<RidersReg> findByMobileNumber(@Param("mobileNo") String mobileNo);
	
	

}

//s