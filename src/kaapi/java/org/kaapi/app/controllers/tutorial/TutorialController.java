package org.kaapi.app.controllers.tutorial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.kaapi.app.entities.Category;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Tutorial;
import org.kaapi.app.forms.FrmTutorial;
import org.kaapi.app.forms.FrmUpdateTutorial;
import org.kaapi.app.services.TutorialService;
import org.kaapi.app.utilities.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tutorial/")
public class TutorialController {
	
	@Autowired
	TutorialService service;
	
	@RequestMapping(value="/list/{userid}" ,method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getListTutorial(@PathVariable("userid") String userid 
			, @RequestParam(value = "page", required = false , defaultValue="1") int page 
			, @RequestParam(value="item" , required = false , defaultValue="20") int item){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			ArrayList<Tutorial> tutorial = service.lists(userid, pagination);
			if(tutorial.isEmpty()){
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND!");
			}
			pagination.setTotalCount(service.countByUser(userid));
			pagination.setTotalPages(pagination.totalPages());
			map.put("PAGINATION", pagination);
			map.put("STATUS", true);
			map.put("MESSAGE", "RECORD FOUND");
			map.put("RES_DATA", tutorial);
		}catch(Exception e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR OCCURRING!");
		}
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/listtitle/{categoryid}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getListTitle(@PathVariable("categoryid") String categoryId){
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			ArrayList<Tutorial> tutorial = service.list(categoryId);
			if(tutorial.isEmpty()){
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND!");
			}
			map.put("STATUS", true);
			map.put("MESSAGE", "RECORD FOUND");
			map.put("RES_DATA", tutorial);
		}catch(Exception e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR OCCURRING!");
		}
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getdefault/{categoryid}", method= RequestMethod.GET, headers="Accept=application/json")
	public ResponseEntity<Map<String, Object>> getDetailDefault(@PathVariable("categoryid") String categoryId){
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			Tutorial dto= new Tutorial();			
			dto = service.getFirstDetail(categoryId);
			if(dto != null){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", dto);
			}else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND!");
			}
		}catch(Exception e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR OCCURRING!");
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{tutorialid}", method= RequestMethod.GET, headers="Accept=application/json")
	public ResponseEntity<Map<String, Object>> getDetail(@PathVariable("tutorialid") String tutorialId){
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			Tutorial tutorial= new Tutorial();
			tutorial = service.get(tutorialId);
			if(tutorial!=null){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", tutorial);
			}else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND!");
			}
		}catch(Exception e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR OCCURRING!");
		}
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);		
	}
	
	@RequestMapping(method= RequestMethod.POST, headers="Accept=application/json")
	public ResponseEntity<Map<String, Object>> add(@RequestBody FrmTutorial tutorial){
		Map<String, Object> map= new HashMap<String, Object>();
		try{		
			if(service.insert(tutorial)){
				map.put("STATUS", true);
				map.put("MESSAGE", "INSERT SUCCESSFULLY");
			}else{
				map.put("STATUS", false);
				map.put("MESSAGE", "INSERT UNSUCCESSFULLY");
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR OCCURRING!");
		}
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);	
	}
	
	@RequestMapping(method= RequestMethod.PUT, headers="Accept=application/json")
	public ResponseEntity<Map<String, Object>> update(@RequestBody FrmUpdateTutorial tutorial){
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			if(service.update(tutorial)){
				map.put("STATUS", true);
				map.put("MESSAGE", "UPDATE SUCCESSFULLY");
			}else{
				map.put("STATUS", false);
				map.put("MESSAGE", "UPDATE UNSUCCESSFULLY");
			}
		}catch(Exception e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR OCCURRING!");
		}
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);	
	}
	
	@RequestMapping(value="/{tutorialid}", method= RequestMethod.DELETE, headers="Accept=application/json")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable("tutorialid") String tutorialId){
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			if(service.delete(tutorialId)){
				map.put("STATUS", true);
				map.put("MESSAGE", "DELETE SUCCESSFULLY");
			}else{
				map.put("STATUS", false);
				map.put("MESSAGE", "DELETE UNSUCCESSFULLY");
			}
		}catch(Exception e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR OCCURRING!");
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);	
	}
	
	@RequestMapping(value="listcategory", method= RequestMethod.GET, headers="Accept=application/json")
	public ResponseEntity<Map<String, Object>> listCategory(){
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			ArrayList<Category> category = service.listCategories();
			if(category.isEmpty()){
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND!");
			}
			map.put("STATUS", true);
			map.put("MESSAGE", "RECORD FOUND");
			map.put("RES_DATA", category);
			
		}catch(Exception e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR OCCURRING!");
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);	
	}
	
}
