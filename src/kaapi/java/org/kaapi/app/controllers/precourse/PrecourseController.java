package org.kaapi.app.controllers.precourse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.kaapi.app.entities.PreCourse;
import org.kaapi.app.services.PreCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/precourse")
public class PrecourseController {
	
	@Autowired
	PreCourseService service;
	
	@RequestMapping(value="/addprecourse", method= RequestMethod.POST, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> addPreCourse(@RequestBody PreCourse preCourse){
		
			
		boolean status = service.addPreCourse(preCourse);
		Map<String,Object> map = new HashMap<String, Object>();
			
		if(!status){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT PRECAUSE");
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		}
		
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD HAS BEEN INSERTED");		
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		
//		ArrayList<Log> logs = service.listCategoryInUser(userid);
//		Map<String, Object> map = new HashMap<String, Object>();
//		
//		if(logs.isEmpty()){
//			map.put("STATUS", false);
//			map.put("MESSAGE", "RECORD NOT FOUND");
//			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
//		}
//		
//		map.put("STATUS", true);
//		map.put("MESSAGE", "RECORD FOUND");
//		map.put("RES_DATA", logs);
//		
//		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);			
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
	
	@RequestMapping(value="/editprecourse", method= RequestMethod.PUT, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> editPreCourse(@RequestBody PreCourse preCourse){
				
		boolean status = service.editPreCourse(preCourse);
		Map<String,Object> map = new HashMap<String, Object>();
			
		if(!status){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR EDIT PRECAUSE");
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		}
		
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD HAS BEEN EDITED");		
		
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
	public ResponseEntity<Map<String, Object>> listPreCourse(){
				
		ArrayList<PreCourse> preCourses = service.getAllPreCourses();
		Map<String,Object> map = new HashMap<String, Object>();
			
		if(preCourses.isEmpty()){
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		}
		
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");		
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
	public ResponseEntity<Map<String, Object>> updatePreCourse(@RequestBody PreCourse preCourse){
				
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
