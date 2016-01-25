package org.kaapi.app.controllers.elearning;

import java.util.ArrayList;
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
@RequestMapping("/api/elearning/history/")
public class HistorysController {
	
	@Autowired
	HistoryService historyservice;
	
	/*action list user history ->well
	 * we want to list history base on user id
	 * and video name
	 */
	@RequestMapping(value="/listuserhistory/{userid}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listUserHistory(	
															@PathVariable("userid") String uid,
															@RequestParam(value="page", required = false, defaultValue = "1") int page,
															@RequestParam(value="item", required = false, defaultValue = "10") int item){
	
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			int begin = (item * page) - item;
			Pagination pagin = new Pagination();
			pagin.setItem(item);
			pagin.setPage(begin);
			
			ArrayList<History> dto= historyservice.userHistory(uid, pagin);
				
			if(!dto.isEmpty()){
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
	
	/*
	 * action get listall history
	 * we want to 
	 */
	@RequestMapping(value="/listallhistory", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listallhistory(@RequestParam(value ="page" , required = false, defaultValue = "1") int page,
															@RequestParam(value ="item" , required = false, defaultValue = "10") int item){
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			int begin = (item * page) - item;
			Pagination pagin = new Pagination();
			pagin.setItem(item);
			pagin.setPage(begin);
			

			ArrayList<History>  dto= historyservice.listAllHistory(pagin);
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
	
	
		/*action delete all history well
		 * we want to delete all history in tblhistory
		 * base on user ID
		 */
		@RequestMapping(value="/deleteallhistory/{userid}", method= RequestMethod.DELETE, headers= "Accept=application/json")
		public ResponseEntity<Map<String, Object>> deleteAllHistory(@PathVariable("userid") String uid){
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
		
		
		/*action delete history ->well
		 * we want to delete history base on history ID
		 */
		@RequestMapping(value="/deletehistory/{historyid}", method= RequestMethod.DELETE, headers= "Accept=application/json")
		public ResponseEntity<Map<String, Object>> deleteHistory(@PathVariable("historyid") String hid){
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
		
		
		/*action list history ->well
		 * we want to list history base on user id
		 * and video name
		 */
		@RequestMapping(value="/searchhistory/{userid}/{videoname}", method= RequestMethod.GET, headers= "Accept=application/json")
		public ResponseEntity<Map<String, Object>> searchHistory(	@PathVariable("videoname") String videoname,
																@PathVariable("userid") String uid,
																@RequestParam(value="page" , required = false, defaultValue = "1") int page,
																@RequestParam(value="item" , required = false, defaultValue = "10") int item){
			
			Map<String, Object> map= new HashMap<String, Object>();
			try{
				int begin = (item * page) - item;
				Pagination pagin = new Pagination();
				pagin.setItem(item);
				pagin.setPage(begin);
				
				ArrayList<History> dto= historyservice.list(videoname, uid, pagin);
				int count = historyservice.count(videoname, uid);
				if(dto != null){
					map.put("STATUS", true);
					map.put("MESSAGE", "RECORD FOUND");
					map.put("TOTAL", count);
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
		@RequestMapping(value="/inserthistory/{userid}/{videoid}", method= RequestMethod.POST, headers= "Accept=application/json")
		public ResponseEntity<Map<String, Object>> insertHistory(@PathVariable("userid") String uid, 
																	@PathVariable("videoid") String vid){
		
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
		@RequestMapping(value="/counthistory/{userid}/{videoname}", method= RequestMethod.GET, headers= "Accept=application/json")
		public ResponseEntity<Map<String, Object>> countHistory(@PathVariable("userid") String uid, 
																	@PathVariable("videoname") String name){
			
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
		//action count user history
		@RequestMapping(value="/countuserhistory/{userid}", method= RequestMethod.GET, headers= "Accept=application/json")
		public ResponseEntity<Map<String, Object>> countUserHistory(@PathVariable("userid") String uid){
			
			Map<String, Object> map= new HashMap<String, Object>();
			int count =historyservice.userHistoryCount(uid);
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
