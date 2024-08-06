package com.spring.rapidfix.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.rapidfix.entities.UserReg;

@Repository
public interface UserRepository extends JpaRepository<UserReg,Integer> {
	
	public Optional<UserReg> findByUsername(String username);
	
	 List<UserReg> findByUsernameContaining(String username);
	
	
	@Query("SELECT u FROM UserReg u WHERE u.mobileNo = :mobileNo")
	public Optional<UserReg> findByMobileNumber(@Param("mobileNo") String mobileNo);
	

}
//s