package org.kaapi.app.controllers.department;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.Department;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.services.DepartmentService;
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
@RequestMapping("/api/department")
public class DepartmentController {
	
	@Autowired
	DepartmentService departmentService;
	
	//Create university
	@RequestMapping(method = RequestMethod.POST, value = "/insert", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> insertDepartment(@RequestBody Department department){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(departmentService.createDepartment(department)){
			map.put("MESSAGE", "DEPARTMENT HAS BEEN CREATED");
			map.put("STATUS", true);
			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		}else{
			map.put("MESSAGE", "DEPARTMENT HAS NOT BEEN CREATED");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		}
	}
	
	//Update Department
	@RequestMapping(method = RequestMethod.PUT, value = "/update" , headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> updateDepartment(@RequestBody Department department){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(departmentService.updateDepartment(department)){
			map.put("MESSAGE", "DEPARTMENT HAS BEEN UPDATED");
			map.put("STATUS", true);
			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		}else{
			map.put("MESSAGE", "DEPARTMENT HAS NOT BEEN CREATED");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		}
	}
	
	//Delete Department
	@RequestMapping(method = RequestMethod.DELETE, value="/delete/{id}", headers="Accept=application/json")
	public ResponseEntity<Map<String, Object>> deleteDepartment(@PathVariable("id") String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (departmentService.deleteDepartment(id)) {			
			map.put("MESSAGE", "DEPARTMENT HAS BEEN DELETED");
			map.put("STATUS", true);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}else{			
			map.put("MESSAGE", "DEPARTMENT HAS NOT BEEN DELETED");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
	}

	// List Department
	@RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listDepartment(
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="item", defaultValue="10") int item) {
		Pagination pagination= new Pagination();
		pagination.setPage(page);
		pagination.setItem(item);
		List<Department> listDepartment = departmentService
				.listDepartment(pagination, keyword);
		Map<String, Object> map = new HashMap<String, Object>();

		if (listDepartment == null) {
			map.put("MESSAGE", "RECORD NOT FOUND!");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map,
					HttpStatus.NOT_FOUND);
		}
		pagination.setTotalCount(departmentService.countDepartment());
		pagination.setTotalPages(pagination.totalPages());		
		map.put("MESSAGE", "RECORD FOUND!");
		map.put("STATUS", true);
		map.put("RESP_DATA", listDepartment);
		map.put("TOTAL_RECORD", pagination.getTotalCount());
		map.put("TOTAL_PAGE", pagination.getTotalPages());
		map.put("CURRENT_PAGE", pagination.getPage());
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	// Count all Department
	@RequestMapping(method = RequestMethod.GET, value = "/count", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> countDepartment() {
		Map<String, Object> map = new HashMap<String, Object>();

		int department = departmentService.countDepartment();

		if (department == 0) {
			map.put("MESSAGE", "RECORD NOT FOUND");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} else {
			map.put("MESSAGE", "RECORD FOUND");
			map.put("STATUS", true);
			map.put("RES_DATA", department);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
	}
}
