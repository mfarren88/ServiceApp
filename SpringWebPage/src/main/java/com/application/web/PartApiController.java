package com.application.web;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.application.model.Part;
import com.application.repository.PartRepository;

@RestController
@RequestMapping("/api")
public class PartApiController {

	@Autowired
	private PartRepository partRepo;
	
	@RequestMapping(value="/part", method= RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Part>> getParts(){
		
		List<Part> parts = (List<Part>) partRepo.findAll();
		
		return new ResponseEntity<List<Part>>(parts, HttpStatus.OK);
	}
	@RequestMapping(value="/part/{id}", method= RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Part> getPart(@PathVariable("id") Long id){
		
		Part part = partRepo.findOne(id);
		
		if(part != null) {
			
			return new ResponseEntity<Part>(part, HttpStatus.OK);			
		}else 
			
			return new ResponseEntity<Part>(part, HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value= "/part/name/{name}", method= RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Part> getPartName(@PathVariable String name){
		
		if(name != null) {
			Part part = partRepo.findByName(name);
			
			if(part != null) {
				return new ResponseEntity<Part>(part, HttpStatus.OK);
			}	
			return new ResponseEntity<Part>(new Part(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Part>(new Part(), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value="/part/type/{type}")
	@ResponseBody
	public ResponseEntity<List<Part>> getPartTypes(@PathVariable String type){
		
		//String type = request.getParameter("type");
		if(type != null) {
			List<Part> parts = (List<Part>) partRepo.findByType(type);
			if(parts != null) {
				return new ResponseEntity<List<Part>>(parts, HttpStatus.OK);
			}
			return new ResponseEntity<List<Part>>(new ArrayList<Part>(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Part>>(new ArrayList<Part>(), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value= "/part/create", method= RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public ResponseEntity<Part> createPart(@RequestBody Part part){
		
		if(part != null) {
			try {
				partRepo.save(part);
			}catch(Exception ex) {
				System.out.println("Not Saved: " + part.toString());
				return new ResponseEntity<Part>(part, HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<Part>(part, HttpStatus.CREATED);
		}
		return new ResponseEntity<Part>(part, HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value= "/part/addlist", method= RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public ResponseEntity<List<Part>> createParts(@RequestBody List<Part> parts){
		
		if(parts != null) {
			try {
				partRepo.save(parts);
			}catch(Exception ex) {
				System.out.println("List Not Saved: ");
				return new ResponseEntity<List<Part>>(parts, HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<List<Part>>(parts, HttpStatus.CREATED);
		}
		return new ResponseEntity<List<Part>>(parts, HttpStatus.NO_CONTENT);
	}
	@RequestMapping(value= "/part/update/{id}")
	@ResponseBody
	public ResponseEntity<Part> updatePart(@PathVariable("id") Long id, @RequestBody Part part){
		
		Part oldPart = partRepo.findOne(id);
		if(oldPart != null) {
			if(oldPart.getPartID() == part.getPartID()) {
				try {
					partRepo.save(part);
				}catch(Exception ex) {
					System.out.println("Not Saved: " + part.toString());
					return new ResponseEntity<Part>(part, HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<Part>(part, HttpStatus.OK);
			}
			return new ResponseEntity<Part>(part, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Part>(part, HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<Part> deletePart(@PathVariable("id") Long id){
		
		Part part = partRepo.findOne(id);
		
		if(part != null) {
			try {
				partRepo.delete(part);
			}catch(Exception ex) {
				System.out.println("Not Deleted: " + part.toString());
				return new ResponseEntity<Part>(new Part(), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<Part>(part, HttpStatus.OK);
		}
		return new ResponseEntity<Part>(new Part(), HttpStatus.NOT_FOUND);
	}
}
