package org.kaapi.app.controllers.elearning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Playlist;
import org.kaapi.app.entities.Video;
import org.kaapi.app.services.PlayListServices;
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
@RequestMapping("/api/playlist/")
public class PlayListController {
	
	@Autowired
	PlayListServices playlistservice;

	//actionaddvideoToplayist ->well
	@RequestMapping(value="/addvideotoplaylist/pid-{pid}/vid-{vid}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> addVideoToPlayList(@PathVariable("pid") int pid,
																	@PathVariable("vid") int vid){
		Map<String, Object> map= new HashMap<String, Object>();
		try{		
			if(playlistservice.addVideoToPlst(pid, vid)){
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
	
	//action create playlist ->well
/*	{
	      
	      "playlistName": "test add1",
	      "description": "CSS tutorial developed by HRD",
	      "userId": 1,
	      "thumbnailUrl": "default.png",
	      "publicView": true,
	        "maincategory": 1,
	        "bgimage": "aaaa",
	        "color":"red"
	}*/
	@RequestMapping(value="/createplaylist", method= RequestMethod.POST, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> createPlayList(@RequestBody Playlist playlist){
		Map<String, Object> map= new HashMap<String, Object>();
		try{	
		
			if(playlistservice.insert(playlist)){
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
	
	//action delete playlist ->well
	@RequestMapping(value="/deleteplaylist-{id}", method= RequestMethod.DELETE, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> deletePlayList(@PathVariable("id") int pid){
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			if(playlistservice.delete(pid)){
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
	
	//action delete video from playlist ->well
	@RequestMapping(value="/deletevideofromplaylist", method= RequestMethod.DELETE, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> deleteViedoFromPlayList(@RequestParam("pid") int pid,
																		@RequestParam("vid") int vid){
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			if(playlistservice.deleteVideoFromPlaylist(pid, vid)){
				if(playlistservice.countvideos(pid) == 0){
					playlistservice.updateThumbnailToDefault(pid);
				}
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
	
	
	
	//action get playlist ->well 888888
	@RequestMapping(value="/getplaylist/pid-{id}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getPlayList(@PathVariable("id") int pid,
															@RequestParam("page") int page,
															@RequestParam("item") int item){
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			int begin = (item * page) - item;
			Pagination pagin = new Pagination();
			pagin.setItem(item);
			pagin.setPage(begin);
			
			ArrayList<Video>  dto= playlistservice.listVideoInPlaylist(pid, pagin);
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
	
	//action get play list for update ->well
	@RequestMapping(value="/getplaylistforupdate-{pid}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getPlayListForUpdate(@PathVariable("pid") int pid){
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			Playlist playlist = playlistservice.getPlaylistForUpdate(pid);
			if(playlist != null){
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND!");
			}
			map.put("STATUS", true);
			map.put("MESSAGE", "RECORD FOUND");
			map.put("RES_DATA", playlist);
		}catch(Exception e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR OCCURRING!");
		}
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
	}
	
	
	//action list play list ->well
	/*
	 * this method need user id and playlistname from session
	 */
	@RequestMapping(value="/listplayList", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listPlayList(/*@RequestBody Playlist playlist,*/
															@RequestParam("page") int page,
															@RequestParam("item") int item){
		
		
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			
			//this will get from session
			Playlist playlist =new Playlist();
			playlist.setUserId(1);
			playlist.setPlaylistName("CSS");
			
			
			int begin = (item * page) - item;
			Pagination pagin = new Pagination();
			pagin.setItem(item);
			pagin.setPage(begin);
			
			ArrayList<Playlist>  dto= playlistservice.list(pagin, playlist);
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
	/*{
	      
	      "playlistName": "php",
	      "description": "CSS tutorial developed by HRD",
	      "userId": 1,
	      "thumbnailUrl": "default.png",
	      "publicView": true,
	        "maincategory": 1,
	        "bgimage": "aaaa",
	        "color":"red"
	}*/
	//action list play list Name ->
	@RequestMapping(value="/listPlayListName", method= RequestMethod.POST, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listPlayListName(@RequestBody Playlist playlist){
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			Playlist  dto= playlistservice.listplaylistname(playlist);
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
	/*{
	      "playlistId": 256,
	      "playlistName": "php update",
	      "description": "it is was update",
	      "userId": 1,
	      "thumbnailUrl": "default.png",
	      "publicView": true,
	       "maincategory": 1,
	       "bgImage": "aaaa",
	       "color":"update",
	        "status": true
  
	}*/
	//action update play list ->well
	@RequestMapping(value="/updatePlayList", method= RequestMethod.PUT, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> updatePlayList(@RequestBody Playlist playlist){
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			if(playlistservice.update(playlist)){
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
			

	

}
