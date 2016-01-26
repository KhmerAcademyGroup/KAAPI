package org.kaapi.app.controllers.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.kaapi.app.entities.Log;
import org.kaapi.app.services.LogService;
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
@RequestMapping("/api/log")
public class LogController {
	
		@Autowired
		LogService service;
		
		@RequestMapping(value="/listcatebyuser/{userid}", method= RequestMethod.GET, headers= "Accept=application/json")
		public ResponseEntity<Map<String, Object>> getCateByUser(@PathVariable("userid") String userid){
						
			ArrayList<Log> logs = service.listCategoryInUser(userid);
			Map<String, Object> map = new HashMap<String, Object>();
			
			if(logs.isEmpty()){
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND!");
				return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			}
			
			map.put("STATUS", true);
			map.put("MESSAGE", "RECORD FOUND");
			map.put("RES_DATA", logs);
			
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);			
		}
		
		@RequestMapping(value="/listcategory", method = RequestMethod.GET, headers="Accept=application/json")
		public ResponseEntity<Map<String,Object>> getCategory(){
			
			ArrayList<Log> logs = service.listCategory();
			Map<String,Object> map = new HashMap<String, Object>();
			if(logs.isEmpty()){
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND!");
				return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			}
			map.put("STATUS", true);
			map.put("MESSAGE", "RECORD FOUND");
			map.put("RES_DATA", logs);
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		}
		
		@RequestMapping(value="/listdepartment/{univid}",method = RequestMethod.GET,headers="Accept=application/json")
		public ResponseEntity<Map<String,Object>> getDepartment(@PathVariable("univid")String universityid){
			
			ArrayList<Log> logs = service.listDeparmentByUniversity(universityid);
			Map<String,Object> map = new HashMap<String, Object>();
			if(logs.isEmpty()){
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND!");
				return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			}
			map.put("STATUS", true);
			map.put("MESSAGE", "RECORD FOUND");
			map.put("RES_DATA", logs);
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		}
		
		@RequestMapping(value="/listuniversity", method = RequestMethod.GET,headers = "Accept=application/json")
		public ResponseEntity<Map<String,Object>> getUniversity(){
			
			ArrayList<Log> logs = service.listUniversity();
			Map<String,Object> map = new HashMap<String, Object>();
			
			if(logs.isEmpty()){
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND!");
				return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			}
			map.put("STATUS", true);
			map.put("MESSAGE", "RECORD FOUND");
			map.put("RES_DATA", logs);
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		}		

		@RequestMapping(value="/listuserbydept/d/{deptid}/u/{univid}", method=RequestMethod.GET, headers = "Accept=application/json")
		public ResponseEntity<Map<String,Object>> getUserByDept(@PathVariable("deptid")String departmentid,@PathVariable("univid")String universityid){
			
			ArrayList<Log> logs = service.listUserInDepartmentAndUniversity(departmentid, universityid);
			Map<String,Object> map = new HashMap<String, Object>();
			
			if(logs.isEmpty()){
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND!");
				return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			}
			map.put("STATUS", true);
			map.put("MESSAGE","RECORD FOUND");
			map.put("RES_DATA", logs);			
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		}

		@RequestMapping(value="/listuserbycate/{cateid}", method=RequestMethod.GET, headers = "Accept=application/json")
		public ResponseEntity<Map<String,Object>> getUserByCate(@PathVariable("cateid")String categoryid){
			
			ArrayList<Log> logs = service.listUserInCategory(categoryid);
			Map<String,Object> map = new HashMap<String, Object>();
			
			if(logs.isEmpty()){
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND!");
				return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			}
			map.put("STATUS", true);
			map.put("MESSAGE", "RECORD FOUND");
			map.put("RES_DATA", logs);
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		}
		
		@RequestMapping(value="/stopwatch", method=RequestMethod.PUT, headers = "Accept=application/json")
		public ResponseEntity<Map<String,Object>> stopWatch(@RequestParam("userid") String userid,
				@RequestParam("logid") String logid){
				
			
			Map<String,Object> map = new HashMap<String, Object>();
			Log log = new Log();
			log.setUserId(userid);
			log.setLogId(logid);
			if(service.stopWatching(log)==false){
				map.put("STATUS", false);
				map.put("MESSAGE", "OPERATION FAIL!");
				return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			}
			map.put("STATUS", true);
			map.put("MESSAGE", "OPERATION SUCCESS!");
			
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		}		
}
