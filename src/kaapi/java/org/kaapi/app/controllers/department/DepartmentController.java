package org.kaapi.app.controllers.department;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kaapi.app.entities.Department;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.services.DepartmentService;
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
@RequestMapping("/api/department")
public class DepartmentController {
	
	@Autowired
	@Qualifier("DepartmentServiceImpl")
	DepartmentService departmentService;
	
	//Create university
	@RequestMapping(method = RequestMethod.POST, value = "/insertDepartment", headers = "Accept=application/json")
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
	@RequestMapping(method = RequestMethod.PUT, value = "/updateDepartment" , headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> updateUniversity(@RequestBody Department department){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(departmentService.updateDepartment(department)){
			map.put("MESSAGE", "DEPARTMENT HAS BEEN CREATED");
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
	public ResponseEntity<Map<String, Object>> deleteUniversity(@PathVariable("id") int id) {
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
	
	//List Department
	@RequestMapping(method = RequestMethod.GET, value="/listDepartment", headers = "Accept=application/json")
	public ResponseEntity<Map<String,Object>> listDepartment(@RequestParam("currentPage") int currentPage, @RequestParam("perPage") int perPage, @RequestParam("totalCount") int totalCount, @RequestParam("totalPages") int totalPages, @RequestParam("keyword") String keyword){
		Map<String, Object> map = new HashMap<String, Object>();
		
		Pagination pagination = new Pagination();
		pagination.setCurrentPage(currentPage);
		pagination.setPerPage(perPage);
		pagination.setTotalCount(totalCount);
		pagination.setTotalPages(totalPages);
		
		List<Department> departmentList = departmentService.listDepartment(pagination, keyword);
		
		if(departmentList == null){
			map.put("MESSAGE", "RECORD NOT FOUND");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String,Object>>(map , HttpStatus.NOT_FOUND);
		}else{
			map.put("MESSAGE","RECORD FOUND");
			map.put("STATUS", true);
			map.put("REST_DATA", departmentList);
			map.put("PAGINATION", pagination);
			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		}
		
	}

}
