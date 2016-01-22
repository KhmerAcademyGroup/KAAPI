package org.kaapi.app.controllers.precourse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.PreCourse;
import org.kaapi.app.forms.FrmAddPreCourse;
import org.kaapi.app.forms.FrmEditPreCourse;
import org.kaapi.app.forms.FrmUpdatePreCourse;
import org.kaapi.app.services.PreCourseService;
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
@RequestMapping("/api/precourse")
public class PrecourseController {
	
	@Autowired
	PreCourseService service;
	
	@RequestMapping(value="/addprecourse", method= RequestMethod.POST, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> addPreCourse(@RequestBody FrmAddPreCourse preCourse){
		
			
		boolean status = service.addPreCourse(preCourse);
		Map<String,Object> map = new HashMap<String, Object>();
			
		if(!status){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT PRECOURSE");
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		}
		
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD HAS BEEN INSERTED");		
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);			
	}
	
	@RequestMapping(value="/deleteprecourse/{id}", method= RequestMethod.DELETE, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> deletePreCourse(@PathVariable("id")String id){
				
		boolean status = service.deletePreCourse(id);
		Map<String,Object> map = new HashMap<String, Object>();
			
		if(!status){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR DELETE PRECAUSE");
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		}
		
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD HAS BEEN DELETED");		
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	

	@RequestMapping(value="/getprecourse/{id}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getPreCourse(@PathVariable("id")String id){
				
		PreCourse preCourse = service.getPreCourse(id);
		Map<String,Object> map = new HashMap<String, Object>();
			
		if(preCourse==null){
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		}
		
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");		
		map.put("RES_DATA", preCourse);
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	
	@RequestMapping(value="/listprecourse", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listPreCourse(
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="item", required=false, defaultValue="10") int item){
				
		Pagination pagination = new Pagination();
		pagination.setItem(item);
		pagination.setPage(page);
		pagination.setTotalCount(service.countPreCourse());
		pagination.setTotalPages(pagination.totalPages());
		
		ArrayList<PreCourse> preCourses = service.getAllPreCourses(pagination);
		Map<String,Object> map = new HashMap<String, Object>();
			
		if(preCourses.isEmpty()){
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		}
		
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("PAGINATION", pagination);
		map.put("RES_DATA", preCourses);
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	
	@RequestMapping(value="/checkprecourses/{uid}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> checkPreCourses(@PathVariable("uid")String uid){
				
		boolean status = service.checkPrecourseStudent(uid);
		Map<String,Object> map = new HashMap<String, Object>();
			
		if(!status){
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		}
		
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getprecoursestudent/{uid}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getPreCourseStudent(@PathVariable("uid")String uid){
				
		PreCourse preCourse = service.getPreCourseStudent(uid);
		Map<String,Object> map = new HashMap<String, Object>();
			
		if(preCourse==null){
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		}
		
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");		
		map.put("RES_DATA", preCourse);
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateprecourse", method= RequestMethod.PUT, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> updatePreCourse(@RequestBody FrmUpdatePreCourse preCourse){
				
		boolean status = service.updatePreCourse(preCourse);
		Map<String,Object> map = new HashMap<String, Object>();
			
		if(!status){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR UPDATE PRECAUSE");
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		}
		
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD HAS BEEN UPDATED");		
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}

}
