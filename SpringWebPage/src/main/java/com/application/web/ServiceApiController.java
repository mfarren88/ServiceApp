package com.application.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.application.model.Service;
import com.application.repository.ServiceRepository;

@RestController
@RequestMapping("/api")
public class ServiceApiController {

	//Temp storage
	//private List<Service> services = new ArrayList<Service>();
	//Temp id
	//private int count = 0;
	//TODO: Add ServiceRepo
	@Autowired
	private ServiceRepository serviceRepository;
	
	@RequestMapping(value="/service", method= RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Service>> getServices(){
		//Find All Service Objects
		List<Service> s = (List<Service>) serviceRepository.findAll();
		
		return new ResponseEntity<List<Service>>(s, HttpStatus.OK);
	}
	
	@RequestMapping(value="service/{id}", method= RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Service> getService(@PathVariable("id") Long id){
		//Find Service With ID
		if(id != null) {
			//Should be unreachable
			System.out.println("Invalid Request id: " + id);
			return new ResponseEntity<Service>(new Service(), HttpStatus.NO_CONTENT);
		}
		Service service = serviceRepository.findOne(id);
		
		if(service != null) {
			return new ResponseEntity<Service>(service, HttpStatus.OK);
		}
			return new ResponseEntity<Service>(new Service(), HttpStatus.NOT_FOUND);	
	}
	
	@RequestMapping(value= "/service/garages/{id}", method= RequestMethod.GET)
	public ResponseEntity<List<Service>> getServiceGarage(@PathVariable("id") long id){
		
		List<Service> garageServices = serviceRepository.findByGarageId(id);
		
		if(garageServices != null) {
			return new ResponseEntity<List<Service>>(garageServices, HttpStatus.OK);
		}else
			return new ResponseEntity<List<Service>>(garageServices, HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="service/vehicle/{registration}")
	@ResponseBody
	public ResponseEntity<List<Service>> getServiceVehicle(@PathVariable String reg){
		//Find services for registration
		//String reg = request.getParameter("registration");
		
		if(reg != null) {
			List<Service> service  = serviceRepository.findByRegistration(reg);	
			
			if(service != null) {
				return new ResponseEntity<List<Service>>(service, HttpStatus.OK);
			}else 
				return new ResponseEntity<List<Service>>(new ArrayList<Service>(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Service>>(new ArrayList<Service>(), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value= "/service", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Service> createService(@RequestBody Service service){
		//Try to add service to DB
		if(service!= null) {
			try {
				serviceRepository.save(service);
			}catch(Exception ex) {
				System.out.println("Not Saved");
				return new ResponseEntity<Service>(service, HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<Service>(service, HttpStatus.CREATED);
		}
		return new ResponseEntity<Service>(service, HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value= "service/addlist", method= RequestMethod.POST, consumes="application/json")
	public ResponseEntity<List<Service>> createServices(@RequestBody List<Service> services){
		
		if(services != null) {
			try {
				serviceRepository.save(services);
			}catch(Exception ex) {
				System.out.println("Unable to add List");
				return new ResponseEntity<List<Service>>(services, HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<List<Service>>(services, HttpStatus.CREATED);
		}
		return new ResponseEntity<List<Service>>(services, HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value= "/service/update/{id}")
	@ResponseBody
	public ResponseEntity<Service> updateService(@PathVariable("id") long id, @RequestBody Service service){
		
		Service oldService = serviceRepository.findOne(id);
		if(oldService != null) {
			if(oldService.getId() == service.getId()) {
				serviceRepository.save(service);
				return new ResponseEntity<Service>(service, HttpStatus.OK);
			}else {
				return new ResponseEntity<Service>(oldService, HttpStatus.CONFLICT);
			}
		}else
			return new ResponseEntity<Service> (service, HttpStatus.NOT_FOUND);
	}
	@RequestMapping(value="service/update/{registration}")
	@ResponseBody
	public ResponseEntity<Service> updateServiceRegistration (HttpServletRequest request, @RequestBody Service service){
		
		String reg = request.getParameter("registration");
		List<Service> vehicleServices = serviceRepository.findByRegistration(reg);
		
		if(vehicleServices != null) {
			for(Service s : vehicleServices) {
				if(s.getRegistration() == service.getRegistration() && s.getDue_date() == service.getDue_date()) {
					serviceRepository.save(service);
					return new ResponseEntity<Service> (service, HttpStatus.OK);
				}
			}
			return new ResponseEntity<Service>(service, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Service> (service, HttpStatus.BAD_REQUEST);	
	}
	
	@RequestMapping(value= "/service/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Service> deleteService(@PathVariable("id") Long id){
		
		if(id != null) {
			Service service = serviceRepository.findOne(id);
			
			if(service != null) {
				serviceRepository.delete(id);
				return new ResponseEntity<Service> (service, HttpStatus.OK);
			}else
				return new ResponseEntity<Service>(new Service(), HttpStatus.NOT_FOUND);	
		}
		return new ResponseEntity<Service>( new Service(), HttpStatus.BAD_REQUEST);
	}
	@RequestMapping(value="/service/delete/registration/{registration}")
	public ResponseEntity<Service> deleteServiceByRegistration(HttpServletRequest request){
		
		String reg = request.getParameter("registration");
		String due_date = request.getParameter("due_date");
		
		java.util.Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(due_date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Service>(new Service(), HttpStatus.BAD_REQUEST);
		}
		//Date sqlDate = new Date(date);
		
		
		if(reg == null || due_date == null) {
			return new ResponseEntity<Service>(new Service(), HttpStatus.BAD_REQUEST);
		}
		
		List<Service> services = serviceRepository.findByRegistration(reg);
		if(services != null) {
			for(Service s : services) {
				if(s.getDue_date() == date) {
					serviceRepository.delete(s);
					return new ResponseEntity<Service>(s, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Service>(new Service(), HttpStatus.NOT_FOUND);
	}
}
