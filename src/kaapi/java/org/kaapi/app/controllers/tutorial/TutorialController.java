package org.kaapi.app.controllers.tutorial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.kaapi.app.entities.Tutorial;
import org.kaapi.app.services.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tutorial/")
public class TutorialController {
	
	@Autowired
	TutorialService service;
	
	@RequestMapping(value="/listtutorial", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getListTutorial(@RequestParam("page") int offset, @RequestParam("perPage") int limit){
		int userid = 1;
		ArrayList<Tutorial> tutorial = service.lists(userid, offset, limit);
		Map<String, Object> map = new HashMap<String, Object>();
		if(tutorial.isEmpty()){
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND!");
		}
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", tutorial);
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/listtitle", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getListTitle(@RequestParam("categoryid") int categoryId){
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
	
}
