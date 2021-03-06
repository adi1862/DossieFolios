package com.dossiefolios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dossiefolios.portfolio.dao.UserDAO;
import com.dossiefolios.portfolio.domain.User;

@RestController
@RequestMapping("/api/board")
@CrossOrigin
public class UserController {
	
	@Autowired
	UserDAO userDAO;
	
	/*Save an User to DB*/
	@PostMapping ("/users")
	public User createUser(@Validated @RequestBody User user ) {
		return userDAO.save(user);
	}
	/*get all users*/
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userDAO.findAll();
	}
	
	/*get user by userid*/
	public ResponseEntity<User> getUserById(@PathVariable(value="id") Long userid) {
		
		User user = userDAO.findOne(userid);
		
		if(user==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(user);
		
	}
	/*Update an user by userid*/
	@PutMapping("/users/{id}")
	public ResponseEntity<User> UpdateUser(@PathVariable (value="id") Long userid,@Validated @RequestBody User userDetails){
		User user = userDAO.findOne(userid);
		if(user==null) {
			return ResponseEntity.notFound().build();
		}
		user.setUsername(userDetails.getUsername());
		user.setPassword(user.getPassword());
		user.setPhone(userDetails.getPhone());
		user.setIsadmin(userDetails.isIsadmin());
		
		User updateUser = userDAO.save(user);
		return ResponseEntity.ok().body(updateUser);
		
	}
	
	/*Delete an user*/
	@DeleteMapping("users/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable(value="id") Long userid) {
		User user = userDAO.findOne(userid);
		
		if(user==null) {
			return ResponseEntity.notFound().build();
		}
		userDAO.delete(user);
		return ResponseEntity.ok().build();
	}
	
	
 }
