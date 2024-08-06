package com.spring.rapidfix.repository;


import com.spring.rapidfix.entities.UserToken;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserTokenRepository extends JpaRepository<UserToken, Integer> {

	@Query("""
			select t from UserToken t inner join UserReg u on t.user.id = u.id
			where t.user.id = :userId and t.loggedOut = false
			""")
	List<UserToken> findAllTokensByUser(Integer userId);
	
	Optional<UserToken> findByToken(String token);

}

//s