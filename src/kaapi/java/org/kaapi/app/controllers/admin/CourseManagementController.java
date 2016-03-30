package org.kaapi.app.controllers.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.kaapi.app.entities.CourseVideoManagement;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Playlist;
import org.kaapi.app.services.CourseManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
			@RequestParam(value ="page", required = false, defaultValue = "1") int page,
			@RequestParam(value ="item" , required = false , defaultValue = "10") int item){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagin = new Pagination();
			pagin.setItem(item);
			pagin.setPage(page);
			pagin.setTotalCount(courseService.countCourse(mainCategoryId));
			pagin.setTotalPages(pagin.totalPages());
			ArrayList<Playlist>  arr = courseService.listCourses(mainCategoryId, pagin);
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
	
	@RequestMapping( method = RequestMethod.GET, value = "/videos/{curseId}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> videoCourses(
			@PathVariable("curseId") String curseId,
			@RequestParam(value ="page", required = false, defaultValue = "1") int page,
			@RequestParam(value ="item" , required = false , defaultValue = "10") int item){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagin = new Pagination();
			pagin.setItem(item);
			pagin.setPage(page);
			ArrayList<CourseVideoManagement>  arr = courseService.listVideosInCourse(curseId, pagin);
			if(!arr.isEmpty()){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", arr);
			}else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND!");
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);	
	}

}
