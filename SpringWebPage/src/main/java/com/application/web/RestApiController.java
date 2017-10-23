package com.application.web;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.application.model.User;
import com.application.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class RestApiController {

	//TODO: Add Services
	//private List<User> users = new ArrayList<User>();
	//private int count = 0;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<User>> getUsers() {
		//TODO: find all user objects
		//URL: http://localhost:8080/api/users
		
		//List<User> u = users;
		List<User> users = (List<User>) userRepository.findAll();
		
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@GetMapping(value = "/users/{id}")
	@ResponseBody
	public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
		//TODO: find user with id
		//URL: http://localhost:8080/api/users/1
		User user = userRepository.findOne(id);
		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}else {
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		}	
	}
	
	@RequestMapping(value = "/users/email/{email}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<User> getUserEmail(@PathVariable("email") String email){
		List<User> users = (List<User>) userRepository.findAll();
		//System.out.println(email);
		for(User u: users) {
			//System.out.println(u.getEmail());
			if(u.getEmail().contains(email)) {
				return new ResponseEntity<User> (u, HttpStatus.OK);
			}
		}
		return new ResponseEntity<User>(new User(), HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<User> createUser(@RequestBody User user) {

		try {
			user.setName();
			userRepository.save(user);
		}catch(Exception ex) {
			System.out.println("Not Saved");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users/addlist", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<List<User>>createUsers(@RequestBody List<User> users){
		
		if (users != null) {
			for(User u : users) {
				u.setName();
				userRepository.save(u);
			}
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		}else {
			System.out.println("Users not added");
			return new ResponseEntity<List<User>>(users, HttpStatus.I_AM_A_TEAPOT);
		}
	}
	@RequestMapping(value = "users/update/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user){
		//TODO: find user with id
		//URL: http://localhost:8080/api/users/update/1
		User u = userRepository.findOne(id);
		if(u == null) {
			System.out.println("User not Found");
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		}
		user.setId(u.getId());
		user.setName();
		userRepository.save(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "users/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") long id){
		
		User u = userRepository.findOne(id);
		if(u == null) {
			return new ResponseEntity<User>(u, HttpStatus.NOT_FOUND);
		}else {
			userRepository.delete(id);
		}
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}

}
