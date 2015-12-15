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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * This controller is used to manage all user in KhmerAcedemy WebService API.
 */

@RestController
@RequestMapping("api/apiuser")
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
	
}
