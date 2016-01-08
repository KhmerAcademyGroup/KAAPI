package org.kaapi.app.controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.University;
import org.kaapi.app.forms.FrmAddUniversity;
import org.kaapi.app.forms.FrmUpdateUniversity;
import org.kaapi.app.services.UniversityService;
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
@RequestMapping("/api/university")
public class UniversityController {

	@Autowired
	UniversityService universityService;

	// GetListAllUniversityByName
	@RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> findAllUniversityByName(
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="item", defaultValue="10") int item) {
		Pagination pagination= new Pagination();
		pagination.setPage(page);
		pagination.setItem(item);
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
		map.put("TOTAL_RECORD", pagination.getTotalCount());
		map.put("TOTAL_PAGE", pagination.getTotalPages());
		map.put("CURRENT_PAGE", pagination.getPage());
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	// Create university
	@RequestMapping(method = RequestMethod.POST, value = "/insert", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> insertUniversity(
			@RequestBody FrmAddUniversity university) {
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
	@RequestMapping(method = RequestMethod.PUT, value = "/update", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> updateUniversity(
			@RequestBody FrmUpdateUniversity university) {
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
	@RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> deleteUniversity(
			@PathVariable("id") String universityId) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (universityService.deleteUniversityById(universityId)) {
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
	@RequestMapping(method = RequestMethod.GET, value = "/list/{id}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> findUniversityById(
			@PathVariable("id") String universityId) {

		Map<String, Object> map = new HashMap<String, Object>();
		String university = universityService.findUniversityById(universityId);
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
	@RequestMapping(method = RequestMethod.GET, value = "/count", headers = "Accept=application/json")
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
