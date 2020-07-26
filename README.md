# search-application

<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-hateoas</artifactId>
		</dependency>
    
    
    
    package com.springboot.rest.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rest.controller.assembler.UserControllerResourceAssembler;
import com.springboot.rest.dto.UserDto;
import com.springboot.rest.entity.User;
import com.springboot.rest.service.ConverterService;
import com.springboot.rest.service.UserService;

@RestController
@RequestMapping(value = "/userinfo")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ConverterService converterService;
	
	@Autowired
	private UserControllerResourceAssembler userControllerResourceAssembler;
	
	// PagedResourcesAssembler easily convert {@link Page} instances into {@link PagedResources}.
	@GetMapping(value = "/alluser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<UserDto>> getUserinfo(@PageableDefault(page = 0, size = 4) Pageable pageRequest,
			                       PagedResourcesAssembler<UserDto> pagedResourcesAssembler) {
		Page<User> userPage = userService.getAllUserInfo(pageRequest);
		List<User> userResultList = userPage.getContent();
		List<UserDto> UserDtoList =	userResultList.stream().map(converterService::convertToDto).collect(Collectors.toList());
		Page<UserDto> userDtoPage = new PageImpl<>(UserDtoList, pageRequest,userPage.getTotalElements());
		String userInfoPath = linkTo(methodOn(UserController.class).getUserinfo(pageRequest, pagedResourcesAssembler)).toString();
		String relativeUserInfoPath = userInfoPath.substring(userInfoPath.indexOf("/userinfo")).trim();
		//Resource will wrap a domain object and adding links to it.
		PagedResources<Resource> resource = pagedResourcesAssembler.toResource(userDtoPage, userControllerResourceAssembler, new Link(relativeUserInfoPath));
		return new ResponseEntity(resource, HttpStatus.OK);
	}
	
	@GetMapping(value = "/alluser/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDto getUserById(@PathVariable("userId") String userId) {
		return userService.getUserByUserId(userId);
	}
	
}





package com.springboot.rest.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.rest.dto.UserDto;
import com.springboot.rest.entity.User;
import com.springboot.rest.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ConverterService converterService;

	public Page<User> getAllUserInfo(Pageable pageRequest) {
		List<User> userList = Arrays.asList(
				new User("111", "First Demo"),
				new User("222", "Second Demo"),
				new User("333", "third Demo"),
				new User("444", "Second Demo"),
				new User("555", "fourth Demo"),
				new User("666", "fifte Demo"),
				new User("77", "sixth Demo"),
				new User("888", "Seventh Demo"));
		userRepository.saveAll(userList);
		return userRepository.findAll(pageRequest);
	}
	
	 public UserDto getUserByUserId(String userId) { 
		  User userObj = userRepository.findById(userId).orElse(null);
		  return converterService.convertToDto(userObj);
	  }
}



package com.springboot.rest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.rest.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	public User findByUserId(String userId);
	public Page<User> findAll(Pageable pageRequest);

}



package com.springboot.rest.controller.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.springboot.rest.controller.UserController;
import com.springboot.rest.dto.UserDto;

//Base class to implement {@link ResourceAssembler}s. Will automate {@link ResourceSupport} instance creation and make sure a self-link is always added.
// This class is to make sure the self link is always added.
@Component
public class UserControllerResourceAssembler extends ResourceAssemblerSupport<UserDto, Resource>{

	public UserControllerResourceAssembler() {
		super(UserController.class, Resource.class);
	}

	//for object
	@Override
	public Resource toResource(UserDto entity) {
		String userInfoPath = linkTo(methodOn(UserController.class).getUserById(entity.getUserId())).toString();
		String relativeUserInfoPath = userInfoPath.substring(userInfoPath.indexOf("/userinfo")).trim();
		return new Resource<>(entity, new Link(relativeUserInfoPath));
	}
	
	// for list
	@Override
	public List<Resource> toResources(Iterable<? extends UserDto> entities) {
		final List<Resource> resources = new ArrayList<>();
		for (UserDto entity : entities) {
			resources.add(toResource(entity));
		}
		return resources;
	}

}


changes
==========
pom.xml added dependency
in the response first show relativeUserInfo then comment it, and show userInfoPath.
