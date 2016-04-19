package org.kaapi.app.controllers.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.kaapi.app.entities.CourseVideoManagement;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Playlist;
import org.kaapi.app.forms.FrmUpdatePlaylist;
import org.kaapi.app.services.CourseManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/admin/courses")
public class CourseManagementController {
	
	@Autowired
	CourseManagementService courseService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/{mainCategoryId}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> courses(
			@PathVariable("mainCategoryId") String mainCategoryId,
			@RequestParam(value ="playlistName", required = false, defaultValue = "") String playlistName,
			@RequestParam(value ="page", required = false, defaultValue = "1") int page,
			@RequestParam(value ="item" , required = false , defaultValue = "10") int item){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagin = new Pagination();
			pagin.setItem(item);
			pagin.setPage(page);
			pagin.setTotalCount(courseService.countCourse(playlistName,mainCategoryId));
			pagin.setTotalPages(pagin.totalPages());
			ArrayList<Playlist>  arr = courseService.listCourses(playlistName , mainCategoryId, pagin);
			if(!arr.isEmpty()){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", arr);
				map.put("PAGINATION", pagin);
			}else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND!");
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);	
	}
	
	@RequestMapping( method = RequestMethod.GET, value = "/listvideos/{curseId}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listVideosInCourse(
			@PathVariable("curseId") String curseId,
			@RequestParam(value ="page", required = false, defaultValue = "1") int page,
			@RequestParam(value ="item" , required = false , defaultValue = "10") int item,
			@RequestParam(value ="videoTitle" , required = false , defaultValue = "") String videoTitle){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagin = new Pagination();
			pagin.setItem(item);
			pagin.setPage(page);
			pagin.setTotalCount(courseService.countVideosInCourse(curseId,videoTitle));
			pagin.setTotalPages(pagin.totalPages());
			ArrayList<CourseVideoManagement>  arr = courseService.listVideosInCourse(curseId, pagin,videoTitle);
			if(!arr.isEmpty()){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", arr);
				map.put("PAGINATION", pagin);
			}else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND!");
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);	
	}
	
	@RequestMapping( method = RequestMethod.GET, value = "/course/{curseId}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getCourse(@PathVariable("curseId") String curseId){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", courseService.getCourse(curseId));
		}catch(Exception e){
			e.printStackTrace();
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);	
	}
	
	@RequestMapping( method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> updateCourse(@RequestBody FrmUpdatePlaylist p){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(courseService.updateCourse(p)){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD HAS BEEN UPDATED");
			}
			else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD HAS NOT BEEN UPDATED");
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);	
	}
	
	@RequestMapping(value="/update_status/{courseid}/{value}", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> updateCourseStatus(@PathVariable("courseid") String courseId,@PathVariable("value") boolean value){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(courseService.updateStatus(courseId, value)){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD HAS BEEN UPDATED");
			}
			else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD HAS NOT BEEN UPDATED");
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);	
	}
	
	@RequestMapping( method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> addCourse(@RequestBody FrmUpdatePlaylist p){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(courseService.addCourse(p)){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD HAS BEEN ADDED");
			}
			else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD HAS NOT BEEN ADDED");
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);	
	}

}
