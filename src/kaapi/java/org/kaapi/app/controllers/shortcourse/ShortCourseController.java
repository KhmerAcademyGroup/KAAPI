package org.kaapi.app.controllers.shortcourse;

import java.util.HashMap;
import java.util.Map;

import org.kaapi.app.entities.shortcourse.FrmShortCourse;
import org.kaapi.app.services.shortcourse.ShortCourseService;
import org.kaapi.app.services.shortcourse.StudentService;
import org.kaapi.app.utilities.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shortcourse")
public class ShortCourseController {
	
	@Autowired
	ShortCourseService shortCourseService;
	
	@Autowired
	StudentService studentService;
	
	@RequestMapping(value="/", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getShortCourse(){
		
		Map<String,Object> map = new HashMap<String, Object>();
		System.out.println("TEst");
		map.put("STATUS", true);
		map.put("RESPONSE_DATA", shortCourseService.getRegisteredStudents());
		map.put("MESSAGE", "RECORD HAS BEEN FOUND");		
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);				
	}	
	
	@RequestMapping(value="/add", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> registerShortCourse(@RequestBody FrmShortCourse shortCourse){

		Map<String,Object> map = new HashMap<String, Object>();
		
		int kaUid = Integer.parseInt(new Encryption().decode(shortCourse.getKauserId()));
		
		shortCourse.setKaUserId(kaUid);
		shortCourse.getFrmStudent().setKaUserId(kaUid);
		int studentId = studentService.insertStudent(shortCourse.getFrmStudent());
		shortCourse.getFrmStudent().setId(studentId);
		
		if(shortCourseService.registerShortCourse(shortCourse)){
			map.put("STATUS", true);
			map.put("MESSAGE", "Register successfully!");
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		}
		map.put("STATUS", true);
		map.put("MESSAGE", "Fail to register! Please try again.");		
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);				
	}
	
	@RequestMapping(value="/getcourseinfo", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getCourseInfo(){
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("STATUS", true);
		map.put("UNIVERSITY", shortCourseService.getUniversities());
		map.put("COURSE", shortCourseService.getCourses());
		map.put("SHIFT", shortCourseService.getShifts());
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);				
	}	
	
	@RequestMapping(value="/getuniversities", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getUniversity(){
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("STATUS", true);
		map.put("UNIVERSITY", shortCourseService.getUniversities());
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);				
	}	
	@RequestMapping(value="/getcourses", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getCourse(){
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("STATUS", true);
		map.put("UNIVERSITY", shortCourseService.getUniversities());
		
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);				
	}	
	@RequestMapping(value="/getshifts", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getShift(){
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("STATUS", true);
		map.put("SHIFT", shortCourseService.getShifts());
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);				
	}	
	
	@RequestMapping(value="/mycourse/{userid}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> myCourses(@PathVariable("userid") String id){
		System.out.println(id);
		int did = Integer.parseInt(new Encryption().decode(id));
		System.out.println(did);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("STATUS", true);
		map.put("MYCOURSES", shortCourseService.getMyRegisteredCourses(did));
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);				
	}	
	
	@RequestMapping(value="/mycourse/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> deleteCourse(@PathVariable("id") int id){
		Map<String,Object> map = new HashMap<String, Object>();
		boolean status = shortCourseService.deleteShortCourse(id);
		
		map.put("STATUS", status);
		if(status)
			map.put("MESSAGE", "REMOVE SUCCESS!");
		else
			map.put("MESSAGE", "REMOVE FAILD!");
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);				
	}	
	
	@RequestMapping(value="/edit/{id}")
	public ResponseEntity<Map<String, Object>> editCourse(@PathVariable("id") int id){
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("STATUS", true);
		map.put("COURSE", shortCourseService.getCourse(id));
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);				
	}	
	
	
	@RequestMapping(value="/add1", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> addStudentRegister(@RequestBody FrmShortCourse shortCourse){
		Map<String,Object> map = new HashMap<String, Object>();
		
		shortCourse.setKaUserId(Integer.parseInt(new Encryption().decode(shortCourse.getKauserId())));
		
		if(shortCourseService.checkExistCourse(shortCourse.getCourseId(), shortCourse.getKaUserId(), shortCourse.getGeneration())){
			map.put("MESSAGE", "You have already choose this course! Please, Choose another course.");
		}else if(shortCourseService.checkExistShift(shortCourse.getShiftId(), shortCourse.getKaUserId(), shortCourse.getGeneration())){
			map.put("MESSAGE", "You already choose this time to study another course! Please choose another time.");
		}else{
			if(shortCourseService.addShortCourse(shortCourse)){
				map.put("STATUS", true);
			}else{
				map.put("STATUS", false);
			}	
		}
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);				
	}	
	
	
	@RequestMapping(value="/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> updateCourse(@RequestBody FrmShortCourse shortCourse){
		Map<String,Object> map = new HashMap<String, Object>();
		
		System.out.println("Course Id : "+shortCourse.getId());
		
		shortCourse.setKaUserId(Integer.parseInt(new Encryption().decode(shortCourse.getKauserId())));
		
		if(shortCourseService.checkExistShift(shortCourse.getShiftId(), shortCourse.getKaUserId(), shortCourse.getGeneration())){
			map.put("MESSAGE", "You already choose this time to study another course! Please choose another time.");
		}else{
			if(shortCourseService.updateShortCourse(shortCourse)){
				map.put("STATUS", true);
			}else{
				map.put("STATUS", false);
			}	
		}
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);				
	}	
	
	@RequestMapping(value="/isstudentexist/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> checkStudent(@PathVariable("id") String sid){
		
		int id = Integer.parseInt(new Encryption().decode(sid));
		System.out.println("ID : " + id);
		Map<String,Object> map = new HashMap<String, Object>();
		
		boolean exist = studentService.isExist(id);
		System.out.println("Student Exist : " + exist);
		map.put("STATUS", exist);		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);				
	}	
}
