package org.kaapi.app.controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.University;
import org.kaapi.app.services.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/university")
public class UniversityController {

	@Autowired
	UniversityService universityService;

	// GetListAllUniversityByName
	@RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> findAllUniversityByName(
			Pagination pagination,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {

		List<University> lstUniversity = universityService
				.findAllUniverstiyByName(pagination, keyword);
		Map<String, Object> map = new HashMap<String, Object>();

		if (lstUniversity == null) {
			map.put("MESSAGE", "RECORD NOT FOUND!");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map,
					HttpStatus.NOT_FOUND);
		}
		pagination.setTotalCount(universityService.countUniversity());
		pagination.setTotalPages(pagination.totalPages());
		map.put("MESSAGE", "RECORD FOUND!");
		map.put("STATUS", true);
		map.put("RESP_DATA", lstUniversity);
		map.put("PAGINATION", pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	// Create university
	@RequestMapping(method = RequestMethod.POST, value = "/insertUniversity", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> insertUniversity(
			@RequestBody University university) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (universityService.createUniverstiy(university)) {
			map.put("MESSAGE", "UNIVERSITY HAS BEEN CREATED");
			map.put("STATUS", true);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} else {
			map.put("MESSAGE", "UNIVERSITY HAS NOT BEEN CREATED");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
	}

	// Update University
	@RequestMapping(method = RequestMethod.PUT, value = "/updateUnversity", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> updateUniversity(
			@RequestBody University university) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (universityService.updateUniversityById(university)) {
			map.put("MESSAGE", "UNIVERSITY HAS BEEN UPDATED");
			map.put("STATUS", true);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} else {
			map.put("MESSAGE", "UNIVERSITY HAS NOT BEEN  UPDATED");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
	}

	// Delete University
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteUniversity/{id}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> deleteUniversity(
			@PathVariable("id") int id) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (universityService.deleteUniversityById(id)) {
			map.put("MESSAGE", "UNIVERSITY HAS BEEN DELETED");
			map.put("STATUS", true);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} else {
			map.put("MESSAGE", "UNIVERSITY HAS NOT BEEN DELETED");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
	}

	// ListFindUniversityByID
	@RequestMapping(method = RequestMethod.GET, value = "/listUniversity/{id}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> findUniversityById(
			@PathVariable("id") int id) {

		Map<String, Object> map = new HashMap<String, Object>();
		String university = universityService.findUniversityById(id);
		System.out.println(university);
		if (university == null) {
			map.put("MESSAGE", "RECORD  NOT FOUND");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("MESSAGE", "RECORD FOUND");
		map.put("STATUS", true);
		map.put("REST_DATA", university);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	// Count all University
	@RequestMapping(method = RequestMethod.GET, value = "/countUniversity", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> countUniversity() {
		Map<String, Object> map = new HashMap<String, Object>();

		int university = universityService.countUniversity();

		if (university == 0) {
			map.put("MESSAGE", "RECORD NOT FOUND");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} else {
			map.put("MESSAGE", "RECORD FOUND");
			map.put("STATUS", true);
			map.put("RES_DATA", university);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
	}
}
