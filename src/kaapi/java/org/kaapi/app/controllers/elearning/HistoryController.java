package org.kaapi.app.controllers.elearning;

import java.util.HashMap;
import java.util.Map;

import org.kaapi.app.entities.History;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/history/")
public class HistoryController {
	
	@Autowired
	HistoryService historyservice;
	
		//action delete all history well
		@RequestMapping(value="/deleteallhistory/uid-{uid}", method= RequestMethod.DELETE, headers= "Accept=application/json")
		public ResponseEntity<Map<String, Object>> deleteAllHistory(@PathVariable("uid") int uid){
			Map<String, Object> map= new HashMap<String, Object>();
			try{
				if(historyservice.deleteAll(uid)){
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
		
		//action delete history ->well
		@RequestMapping(value="/deletehistory-{hid}", method= RequestMethod.DELETE, headers= "Accept=application/json")
		public ResponseEntity<Map<String, Object>> deleteHistory(@PathVariable("hid") int hid){
			Map<String, Object> map= new HashMap<String, Object>();
			try{
				if(historyservice.delete(hid)){
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
		
		//action list history ->well
		@RequestMapping(value="/listhistory/{uid}/{videoname}", method= RequestMethod.GET, headers= "Accept=application/json")
		public ResponseEntity<Map<String, Object>> listHistory(	@PathVariable("videoname") String videoname,
																@PathVariable("uid") int uid,
																@RequestParam("page") int page,
																@RequestParam("item") int item){
			Map<String, Object> map= new HashMap<String, Object>();
			try{
				int begin = (item * page) - item;
				Pagination pagin = new Pagination();
				pagin.setItem(item);
				pagin.setPage(begin);
				
				History dto= historyservice.list(videoname, uid, pagin);
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
		
		//action insert well
		@RequestMapping(value="/inserthistory/uid-{uid}/vid-{vid}", method= RequestMethod.POST, headers= "Accept=application/json")
		public ResponseEntity<Map<String, Object>> insertHistory(@PathVariable("uid") int uid, 
																	@PathVariable("vid") int vid){
		
			
			Map<String, Object> map= new HashMap<String, Object>();
			History dto = new History();
			dto.setUserId(uid);
			dto.setVideoId(vid);
			try{
				if(historyservice.insert(dto)){
					map.put("STATUS", true);
					map.put("MESSAGE", "INSERT SUCCESSFULLY");
				}else{
					map.put("STATUS", false);
					map.put("MESSAGE", "INSERT UNSUCCESSFULLY");
				}
			}catch(Exception e){
				map.put("STATUS", false);
				map.put("MESSAGE", "ERROR OCCURRING!");
			}
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);	
			
		}
		
		
		//action count user history
		@RequestMapping(value="/countstory/uid-{uid}/videoname-{name}", method= RequestMethod.GET, headers= "Accept=application/json")
		public ResponseEntity<Map<String, Object>> countUserHistory(@PathVariable("uid") int uid, 
																	@PathVariable("name") String name){
			
			Map<String, Object> map= new HashMap<String, Object>();
			int count =historyservice.count(name, uid);
			try{
				if(count>0){
					map.put("STATUS", true);
					map.put("MESSAGE", "RECORD FOUND");
					map.put("TOTAL", count);
				}else{
					map.put("STATUS", false);
					map.put("MESSAGE", "RECORD NOTFOUND");
				}
			}catch(Exception e){
				map.put("STATUS", false);
				map.put("MESSAGE", "ERROR OCCURRING!");
			}
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);	
			
		}
}
