package com.application.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.model.Vehicle;
import com.application.repository.VehicleRepository;

@RestController
@RequestMapping("/api")
public class VehicleApiController {

	//TODO: Add Services
	@Autowired
	private VehicleRepository vehicleRepository;
	
	//private List<Vehicle> vehicles = new ArrayList<Vehicle>();
	
	@RequestMapping(value= "/vehicle", method= RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Vehicle>> getVehicles(){
		
	List<Vehicle> v = (List<Vehicle>) vehicleRepository.findAll();
	return new ResponseEntity<List<Vehicle>>(v, HttpStatus.OK);
	}
	
	@RequestMapping(value ="/vehicle/{reg}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Vehicle> getVehicle(@PathVariable("reg") String registration){
		Vehicle vehicle = vehicleRepository.findByRegistration(registration);
		
		if(vehicle != null) {
			return new ResponseEntity<Vehicle>(vehicle, HttpStatus.OK);
		}else
			return new ResponseEntity<Vehicle>(vehicle, HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value= "/vehicle", method= RequestMethod.POST, consumes= "application/json")
	public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle){
		
		if(vehicle !=null) {
			try {
				vehicleRepository.save(vehicle);
			}catch(Exception ex) {
				System.out.println("Not Saved Exception");
			}
		}else {
			return new ResponseEntity<Vehicle>(vehicle, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Vehicle>(vehicle, HttpStatus.OK);
	}
	
	@RequestMapping(value= "/vehicles/addlist", method= RequestMethod.POST, consumes= "application/json")
	public ResponseEntity<List<Vehicle>> createVehicles(@RequestBody List<Vehicle> vehicles){
		
		if(vehicles != null) {
			try {
				vehicleRepository.save(vehicles);
			}catch(Exception ex) {
				System.out.println("List Not Added Exception");
			}
		}else {
			return new ResponseEntity<List<Vehicle>>(vehicles, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<Vehicle>>(vehicles, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/vehicle/update/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Vehicle> updateVehicle(@PathVariable("id") String registration, @RequestBody Vehicle vehicle){
		Vehicle v = vehicleRepository.findOne(registration);
		if(v != null) {
			if(v.getRegistration().compareToIgnoreCase(registration) == 0) {
				
				if (vehicle.getCust_id() != null) v.setCust_id(vehicle.getCust_id());
				if (vehicle.getLast_mot()!= null) v.setLast_mot(vehicle.getLast_mot());
				if (vehicle.getLast_mot() != null) v.setLast_service(vehicle.getLast_service());
				
				vehicleRepository.save(v);
				return new ResponseEntity<Vehicle>(v, HttpStatus.OK);
				
			}else {
				return new ResponseEntity<Vehicle>(vehicle, HttpStatus.BAD_REQUEST);
				}
		}else {
			return new ResponseEntity<Vehicle>(vehicle, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value= "/vehicle/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Vehicle> deleteVehicle(@PathVariable("id") String registration){
		Vehicle v = vehicleRepository.findOne(registration);
		if(v != null) {
			vehicleRepository.delete(registration);
			return new ResponseEntity<Vehicle>(v, HttpStatus.OK);
		}else {
			return new ResponseEntity<Vehicle>(v, HttpStatus.NOT_FOUND); 
		}
	}
	@RequestMapping(value= "/vehicle/owner/{id}", method= RequestMethod.GET)
	public ResponseEntity<List<Vehicle>> getOwnerVehicle(@PathVariable("id") Long owner_id){
		
		List<Vehicle> vehicles= vehicleRepository.findByCustId(owner_id);
		if(vehicles != null) {
			return new ResponseEntity<List<Vehicle>>(vehicles, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<Vehicle>>(vehicles, HttpStatus.NOT_FOUND);
		}
	}
}