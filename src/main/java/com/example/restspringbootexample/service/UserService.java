package com.example.restspringbootexample.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restspringbootexample.entitie.User;
import com.example.restspringbootexample.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> getListOfUser() {
		List<User> listUser = new ArrayList<>();
		User obj = new User("Dheeraj", "101");
		User obj1 = new User("Surbhi", "102");
		listUser.add(obj);
		listUser.add(obj1);
		userRepository.saveAll(listUser);
		return userRepository.findAll();
	}

	public User getUserById(String userId) {
		return userRepository.findById(userId).orElse(null);
	}

	public User createNewUser(User users) {
		User objUser = new User();
		objUser.setUserId(users.getUserId());
		objUser.setUserName(users.getUserName());
		return userRepository.save(objUser);
	}

	public User updateUserInfo(String userId, User users) {
		User existingUserId = userRepository.findById(userId).orElse(null);
		existingUserId.setUserId(users.getUserId());
		existingUserId.setUserName(users.getUserName());
		return userRepository.save(existingUserId);
	}

}
