package org.kaapi.app.controllers.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.UserType;
import org.kaapi.app.forms.FrmAddUserType;
import org.kaapi.app.forms.FrmUpdateUserType;
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
	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listUserType(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "item", required = false, defaultValue = "10") int item) {
		Pagination pagination = new Pagination();
		pagination.setItem(item);
		pagination.setPage(page);
		pagination.setTotalCount(userTypeService.countUserType());
		pagination.setTotalPages(pagination.totalPages());
		
		List<UserType> userType = userTypeService.listUserType(pagination);
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (userType.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND!");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", userType);
		map.put("PAGINATION", pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	// List SearchUserType by Name
	@RequestMapping(value = "/search", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> searchUserType(
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "item", required = false, defaultValue = "10") int item) {
		Pagination pagination = new Pagination();
		pagination.setItem(item);
		pagination.setPage(page);
		pagination.setTotalCount(userTypeService.countSearchUserType(name));
		pagination.setTotalPages(pagination.totalPages());
		
		List<UserType> searchUserType = userTypeService.searchUserType(name, pagination);
		Map<String, Object> map = new HashMap<String, Object>();

		if (searchUserType == null) {
			map.put("MESSAGE", "RECORD NOT FOUND!");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("MESSAGE", "RECORD FOUND!");
		map.put("STATUS", true);
		map.put("RESP_DATA", searchUserType);
		map.put("PAGINATION", pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	// Get UserType By ID
	@RequestMapping(value = "/getUserType/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getUserTypeById(@PathVariable("id") String userTypeid) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		UserType userType = userTypeService.getUserType(userTypeid);

		if (userType !=null) {
			map.put("MESSAGE", "RECORD FOUND!");
			map.put("STATUS", true);
			map.put("RESP_DATA", userType);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("MESSAGE", "RECORD NOT FOUND!");
		map.put("STATUS", false);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	// Insert UserType
	@RequestMapping(method = RequestMethod.POST, value = "/insert", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> insertUserType(
			@RequestBody FrmAddUserType userType) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (userTypeService.insertUserType(userType)) {
			map.put("MESSAGE", "USERTYPE HAS BEEN CREATED");
			map.put("STATUS", true);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} else {
			map.put("MESSAGE", "USERTYPE HAS NOT BEEN CREATED");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
	}

	// Update UserType
	@RequestMapping(method = RequestMethod.PUT, value = "/update", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> updateUserType(
			@RequestBody FrmUpdateUserType userType) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (userTypeService.updateUserType(userType)) {
			map.put("MESSAGE", "USERTYPE HAS BEEN UPDATED");
			map.put("STATUS", true);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} else {
			map.put("MESSAGE", "USERTYPE HAS NOT BEEN UPDATED");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
	}

	// Delete UserType
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> deleteUserType(
			@PathVariable("id") String id) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (userTypeService.deleteUserType(id)) {
			map.put("MESSAGE", "USERTYPE HAS BEEN DELETED");
			map.put("STATUS", true);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} else {
			map.put("MESSAGE", "USERTYPE HAS NOT BEEN DELETED");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
	}

}
