package com.application.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.application.model.Part;

public interface PartRepository extends CrudRepository<Part, Long>{
	
	
	Part findByName(String name);

	List<Part> findByType(String type);
}
