package com.spring.rapidfix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.rapidfix.entities.OrderFuelForm;


@Repository
public interface OrderFuelRepository extends JpaRepository<OrderFuelForm , Integer>{

}

//s