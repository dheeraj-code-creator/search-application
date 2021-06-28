package com.example.restspringbootexample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restspringbootexample.entitie.User;
import com.example.restspringbootexample.repository.UserRepository;
import com.example.restspringbootexample.service.UserService;

@RestController
@RequestMapping(value = "/userinfo")
public class UserController {
// autowired
	@Autowired
	private UserService userService;

	// feature branch
	@Autowired
	private UserRepository userRepository;

	// For all users
	@GetMapping(value = "/alluser", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getAllUser() {
		return userService.getListOfUser();
	}

	// For particular user
	@GetMapping(value = "/userinfo/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public User getUserById(@PathVariable("userId") String userId) {
		return userService.getUserById(userId);

	}

	// add users
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public User createUser(@RequestBody User users) {
		return userService.createNewUser(users);

	}

	// update users
	@PutMapping(value = "/update/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public User updateUser(@PathVariable(value = "userId") String userId, @RequestBody User users) {
		return userService.updateUserInfo(userId, users);

	}

	// delete users
	@DeleteMapping(value = "/delete/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteUser(@PathVariable(value = "userId") String userId) {
		userRepository.deleteById(userId);
		// return userService.deleteUserById(userId);

	}

}
