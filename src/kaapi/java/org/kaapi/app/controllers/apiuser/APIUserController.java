package org.kaapi.app.controllers.apiuser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.APIUser;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.exceptions.ResourceConflictException;
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
		Map<String , Object> map = new HashMap<String , Object>();
		if(apiUserService.isUsernameExist(user.getUsername())){
//			map.put("MESSAGE", "Username is already existed.");
//			return new ResponseEntity<Map<String,Object>>(map , HttpStatus.OK);
			throw new ResourceConflictException("Username is already existed.");
		}
		if(apiUserService.isEmailExist(user.getEmail())){
			map.put("MESSAGE", "Email is already existed.");
			return new ResponseEntity<Map<String,Object>>(map , HttpStatus.OK);
		}
		if(apiUserService.addUser(user) == false){
			map.put("MESSAGE", "Error! User was not registered successful!");
			return new ResponseEntity<Map<String,Object>>(map , HttpStatus.OK);
		}
		map.put("MESSAGE", "User was registered successfully");
		return new ResponseEntity<Map<String,Object>>(map , HttpStatus.OK);
	}
	
}
