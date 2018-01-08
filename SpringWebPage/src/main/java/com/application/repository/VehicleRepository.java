package com.application.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.application.model.Vehicle;

public interface VehicleRepository extends CrudRepository<Vehicle, String>{

	Vehicle findByRegistration(String registration); 
	List<Vehicle> findByCustId(Long id);
}
