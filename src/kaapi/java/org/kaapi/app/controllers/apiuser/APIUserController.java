package org.kaapi.app.controllers.apiuser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.APIUser;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.exceptions.ResourceConflictException;
import org.kaapi.app.exceptions.ResourceNotFoundException;
import org.kaapi.app.services.APIUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mangofactory.swagger.annotations.ApiIgnore;

/*
 * This controller is used to manage all user in KhmerAcedemy WebService API.
 */

@RestController
@RequestMapping("api/apiuser")
@ApiIgnore
public class APIUserController {

	@Autowired
	@Qualifier("APIUserService")
	APIUserService apiUserService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> findAllAPIUserByUsername(Pagination pagination, @RequestParam( value = "username" , required = false , defaultValue="") String username){
		List<APIUser> users = apiUserService.findAllUserByUsername(pagination, username);
		Map<String , Object> map = new HashMap<String , Object>();
		if(users == null){
			map.put("MESSAGE", "Not found!");
			return new ResponseEntity<Map<String,Object>>(map , HttpStatus.NOT_FOUND);
		}
		pagination.setTotalCount(apiUserService.countAPIUser());
		pagination.setTotalPages(pagination.totalPages());
		map.put("RESP_DATA", users);
		map.put("PAGINATION", pagination);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		APIUser user = (APIUser)authentication.getPrincipal();
		System.out.print("____________adminID " +user.getId());
		System.out.print("____________adminID " +user.getUsername());
		
		return new ResponseEntity<Map<String,Object>>(map , HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> addUser(@RequestBody APIUser user){
		if(apiUserService.isUsernameExist(user.getUsername())){
			throw new ResourceConflictException("Username is already existed.");
		}
		if(apiUserService.isEmailExist(user.getEmail())){
			throw new ResourceConflictException("Email is already existed.");
		}
		if(apiUserService.addUser(user) == false){
			throw new ResourceNotFoundException("Email is already existed.");
		}
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("MESSAGE", "User was registered successfully");
		return new ResponseEntity<Map<String,Object>>(map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/count_req" , method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> countRequestedUser(){
		List<APIUser> users = apiUserService.listRequestedUser();
		if(users == null){
			throw new ResourceConflictException("Not found!");
		}
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("RESP_DATA", users);
		return new ResponseEntity<Map<String,Object>>(map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/requestedUser/{id}" , method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> findRequestedUserById(@PathVariable("id") int id){
		APIUser user = apiUserService.findUserReqestedByID(id);
		if(user == null){
			throw new ResourceNotFoundException("Not found!");
		}
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("RESP_DATA", user);
		return new ResponseEntity<Map<String,Object>>(map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/acceptRequest/{userID}" , method  = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> acceptRequest(@PathVariable("userID") int userID ){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		APIUser user = (APIUser)authentication.getPrincipal();
		System.out.print("adminID " +user.getId());
		Map<String , Object> map = new HashMap<String , Object>();
		if(apiUserService.acceptRequest(userID, user.getId())){
			map.put("MESSAGE", "Success");
			return new ResponseEntity<Map<String,Object>> (map , HttpStatus.OK);
		}
		map.put("MESSAGE", "Unsuccess");
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/rejectRequest/{userID}" , method = RequestMethod.POST)
	public ResponseEntity<Map<String , Object>> rejectRequest(@PathVariable("userID") int userID){
		Map<String , Object> map = new HashMap<String , Object>();
		if(apiUserService.rejectRequest(userID)){
			map.put("MESSAGE", "Success");
			return new ResponseEntity<Map<String,Object>> (map , HttpStatus.OK);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.NOT_FOUND);
	}
	
	public int getUserID(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		APIUser user = (APIUser)authentication.getPrincipal();
		System.out.print("____________adminID " +user.getId());
		System.out.print("____________adminID " +user.getUsername());
		return user.getId();
	}
	
	
}
