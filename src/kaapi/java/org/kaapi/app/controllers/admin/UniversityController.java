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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/university")
public class UniversityController {
	
	@Autowired
	@Qualifier("universityServiceImpl")
	UniversityService universityService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> findAllUniversityByName(Pagination pagination, @RequestParam( value = "keyword" , required = false , defaultValue="") String keyword){
		List<University> lstUniversity = universityService.findAllUniverstiyByName(pagination, keyword);
		System.out.println(keyword);
		Map<String , Object> map = new HashMap<String , Object>();
		if(lstUniversity == null){
			map.put("MESSAGE", "Not found!");
			return new ResponseEntity<Map<String,Object>>(map , HttpStatus.NOT_FOUND);
		}
		pagination.setTotalCount(universityService.countUniversity());
		pagination.setTotalPages(pagination.totalPages());
		map.put("RESP_DATA", lstUniversity);
		map.put("PAGINATION", pagination);
		return new ResponseEntity<Map<String,Object>>(map , HttpStatus.OK);
	}
	

}
