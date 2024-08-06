package com.spring.rapidfix.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.rapidfix.entities.CurrentLocation;

@Repository
public interface CurrentLocationRepository extends JpaRepository<CurrentLocation, Integer> {
    Optional<CurrentLocation> findByRiderName(String riderName);
    
    void deleteByRiderName(String riderName);
}

//s
