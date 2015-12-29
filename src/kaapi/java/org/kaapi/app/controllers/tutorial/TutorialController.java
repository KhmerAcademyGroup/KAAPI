package org.kaapi.app.controllers.tutorial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Tutorial;
import org.kaapi.app.services.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tutorial/")
public class TutorialController {
	
	@Autowired
	TutorialService service;
	
	@RequestMapping(value="/list/{userid}" ,method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getListTutorial(Pagination pagination, @PathVariable("userid") int userid){
		ArrayList<Tutorial> tutorial = service.lists(userid, pagination);
		Map<String, Object> map = new HashMap<String, Object>();
		if(tutorial.isEmpty()){
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND!");
		}
		pagination.setTotalCount(service.countTutorials());
		pagination.setTotalPages(pagination.totalPages());
		map.put("PAGINATION", pagination);
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", tutorial);
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/listtitle/{categoryid}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getListTitle(@PathVariable("categoryid") int categoryId){
		ArrayList<Tutorial> tutorial = service.list(categoryId);
		Map<String, Object> map= new HashMap<String, Object>();
		if(tutorial.isEmpty()){
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND!");
		}
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", tutorial);
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getDefault/{categoryid}", method= RequestMethod.GET, headers="Accept=application/json")
	public ResponseEntity<Map<String, Object>> getDetailDefault(@PathVariable("categoryid") int categoryid){
		
		Tutorial dto= new Tutorial();
		Map<String, Object> map= new HashMap<String, Object>();
		dto = service.getFirstDetail(categoryid);
		if(dto != null){
			map.put("STATUS", true);
			map.put("MESSAGE", "RECORD FOUND");
			map.put("RES_DATA", dto);
		}else{
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND!");
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//@RequestMapping(value="/{tutorialid}", method= RequestMethod.GET, headers="Accept=application/json")
	
	
	
	
	
}
