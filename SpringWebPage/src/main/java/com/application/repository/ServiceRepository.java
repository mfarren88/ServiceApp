package com.application.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.application.model.Service;

public interface ServiceRepository extends CrudRepository<Service, Long> {

	List<Service> findByRegistration(String registration);
	List<Service> findByGarageId(Long id);
	//List<Service> findByDue_date(Date due_date);
}
