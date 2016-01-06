package org.kaapi.app.controllers.usertype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kaapi.app.entities.UserType;
import org.kaapi.app.services.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usertype")
public class UserTypeController {

	@Autowired
	UserTypeService userTypeService;
	
	// List UserType
	@RequestMapping(value="/listUserType", method = RequestMethod.GET, headers="Accept=application/json")
	public ResponseEntity<Map<String,Object>> listUserType(){
		
		List<UserType> userType = userTypeService.listUserType();
		Map<String,Object> map = new HashMap<String, Object>();
		if(userType.isEmpty()){
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND!");
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		}
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", userType);
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	
	//Count UserType
	@RequestMapping(value="/count", method=RequestMethod.GET, headers="Accept=appication/json")
	public ResponseEntity<Map<String,Object>> countUserType(){
		Map<String, Object> map = new HashMap<String, Object>();
		
		int count = userTypeService.countUserType();

		if (count == 0) {
			map.put("MESSAGE", "RECORD NOT FOUND");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} else {
			map.put("MESSAGE", "RECORD FOUND");
			map.put("STATUS", true);
			map.put("RES_DATA", count);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
	}
	
	//List SearchUserType by Name
	public ResponseEntity<Map<String, Object>> searchUserType(
			@RequestParam(value = "name", required = false, defaultValue = "") String name) {

		List<UserType> searchUserType = userTypeService.searchUserType(name);
		Map<String, Object> map = new HashMap<String, Object>();

		if (searchUserType == null) {
			map.put("MESSAGE", "RECORD NOT FOUND!");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map,
					HttpStatus.NOT_FOUND);
		}	
		map.put("MESSAGE", "RECORD FOUND!");
		map.put("STATUS", true);
		map.put("RESP_DATA", searchUserType);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//Count Search UserType By Name
	@RequestMapping(value="/countSearchUserType", method=RequestMethod.GET, headers="Accept=appication/json")
	public ResponseEntity<Map<String,Object>> countSearchUserType(@RequestParam(value = "name", required = false, defaultValue = "") String name){
		Map<String, Object> map = new HashMap<String, Object>();
		
		int countSearchUserType = userTypeService.countUserType();

		if (countSearchUserType == 0) {
			map.put("MESSAGE", "RECORD NOT FOUND");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} else {
			map.put("MESSAGE", "RECORD FOUND");
			map.put("STATUS", true);
			map.put("RES_DATA", countSearchUserType);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
	}
	
	//Get UserType By ID
	@RequestMapping(value="/getUserType/{id}",method = RequestMethod.GET,headers="Accept=application/json")
	public ResponseEntity<Map<String,Object>> getDepartment(@PathVariable("id") String id){
		
		Map<String,Object> map = new HashMap<String, Object>();
		UserType userType = userTypeService.getUserType(id);		
		
		if (userType == null) {
			map.put("MESSAGE", "RECORD NOT FOUND!");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map,
					HttpStatus.NOT_FOUND);
		}	
		map.put("MESSAGE", "RECORD FOUND!");
		map.put("STATUS", true);
		map.put("RESP_DATA", userType);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//Insert UserType
	@RequestMapping(method = RequestMethod.POST, value = "/insert", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> insertDepartment(@RequestBody UserType userType){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(userTypeService.insertUserType(userType)){
			map.put("MESSAGE", "USERTYPE HAS BEEN CREATED");
			map.put("STATUS", true);
			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		}else{
			map.put("MESSAGE", "USERTYPE HAS NOT BEEN CREATED");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		}
	}
	
	//Update UserType
	@RequestMapping(method = RequestMethod.PUT, value = "/update" , headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> updateDepartment(@RequestBody UserType userType){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(userTypeService.updateUserType(userType)){
			map.put("MESSAGE", "USERTYPE HAS BEEN UPDATED");
			map.put("STATUS", true);
			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		}else{
			map.put("MESSAGE", "USERTYPE HAS NOT BEEN CREATED");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		}
	}
	
	//Delete UserType
	@RequestMapping(method = RequestMethod.DELETE, value="/delete/{id}", headers="Accept=application/json")
	public ResponseEntity<Map<String, Object>> deleteDepartment(@PathVariable("id") String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (userTypeService.deleteUserType(id)) {			
			map.put("MESSAGE", "USERTYPE HAS BEEN DELETED");
			map.put("STATUS", true);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}else{			
			map.put("MESSAGE", "USERTYPE HAS NOT BEEN DELETED");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
	}


}
