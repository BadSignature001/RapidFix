package com.spring.rapidfix.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.rapidfix.entities.RidersToken;



public interface RidersTokenRepository extends JpaRepository<RidersToken, Integer> {

	@Query("""
			select t from RidersToken t inner join RidersReg r on t.rider.id = r.id
			where t.rider.id = :riderId and t.loggedOut = false
			""")
	List<RidersToken> findAllTokensByUser(Integer riderId);
	
	Optional<RidersToken> findByToken(String token);

}

//s