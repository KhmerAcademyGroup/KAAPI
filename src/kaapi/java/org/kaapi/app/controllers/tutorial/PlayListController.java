/*package org.kaapi.app.controllers.tutorial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Playlist;
import org.kaapi.app.services.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/playlist/")
public class PlayListController {
	
	@Autowired
	PlayListService playlistservice;
	Playlist playlistdto;
	
	
	//actionaddvideoToplayist
	@RequestMapping(value="/test", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> test(){
		System.out.println("====================================");
		
		
		//boolean playlist =playlistservice.addVideoToPlst(1, 111);
		//System.out.println("result"+a);
		
		
		//ArrayList<Playlist> playlist =playlistservice.recommendPlaylist();
		
		//int playlist =playlistservice.countUserPlaylist("ios", 1);
		
		Playlist playlistdto = new Playlist();
		playlistdto.setPlaylistName("chhoin");
		playlistdto.setDescription("this is chhoin");
		playlistdto.setUserId(1);
		playlistdto.setThumbnailUrl("/chhoin/");
		playlistdto.setPublicView(true);
		playlistdto.setMaincategory(1);
		playlistdto.setBgImage("aaaa");
		playlistdto.setColor("bbbbb");
		
			boolean playlist =playlistservice.insert(playlistdto);

		//Playlist playlist = playlistservice.get(1);
		
		
		String playlist = playlistservice.getPlaylistName(5);
		
		
		String tutorial = "sfsfsdsd";
		Map<String, Object> map = new HashMap<String, Object>();
		if(tutorial.isEmpty()){
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND!");
		}
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", playlist);
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
	}
	
	//actionaddvideoToplayist
	@RequestMapping(value="/1", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> addVideoToPlayList(){
		
		System.out.println("good restcontroller");
		String tutorial = "sfsfsdsd";
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
	
	//action create playlist
	@RequestMapping(value="/2", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> createPlayList(){
		System.out.println("good restcontroller");
		String tutorial = "sfsfsdsd";
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
	
	//action delete playlist
		@RequestMapping(value="/3", method= RequestMethod.GET, headers= "Accept=application/json")
		public ResponseEntity<Map<String, Object>> deletePlayList(){
			System.out.println("good restcontroller");
			String tutorial = "sfsfsdsd";
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
		
		//action delete video from playlist
		@RequestMapping(value="/4", method= RequestMethod.GET, headers= "Accept=application/json")
		public ResponseEntity<Map<String, Object>> deleteViedoFromPlayList(){
			System.out.println("good restcontroller");
			String tutorial = "sfsfsdsd";
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
		
		//action get playlist
		@RequestMapping(value="/5", method= RequestMethod.GET, headers= "Accept=application/json")
		public ResponseEntity<Map<String, Object>> getPlayList(){
			System.out.println("good restcontroller");
			String tutorial = "sfsfsdsd";
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
		
		
		//action get play list for update
		@RequestMapping(value="/6", method= RequestMethod.GET, headers= "Accept=application/json")
		public ResponseEntity<Map<String, Object>> getPlayListForUpdate(){
			System.out.println("good restcontroller");
			String tutorial = "sfsfsdsd";
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
		//action list play list
		@RequestMapping(value="/listplayList", method= RequestMethod.GET, headers= "Accept=application/json")
		public ResponseEntity<Map<String, Object>> listPlayList(@RequestParam("limit") int limit , @RequestParam("offset") int offset){
			int begin = (limit * offset) - limit;
			//set pagination
			Pagination pagin = new Pagination();
//			pagin.setPerPage(limit);
//			pagin.setCurrentPage(begin);
			
			ArrayList<Playlist> playlist = (ArrayList<Playlist>) playlistservice.list(pagin, playlistdto);
			Map<String, Object> map = new HashMap<String, Object>();
		
			
			if(playlist.isEmpty()){
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND!");
			}
			map.put("STATUS", true);
			map.put("MESSAGE", "RECORD FOUND");
			map.put("RES_DATA", playlist);
			
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			
		}
		
		//action list play list Name
		@RequestMapping(value="/8", method= RequestMethod.GET, headers= "Accept=application/json")
		public ResponseEntity<Map<String, Object>> listPlayListName(){
			System.out.println("good restcontroller");
			String tutorial = "sfsfsdsd";
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
		
		//action update play list
		@RequestMapping(value="/9", method= RequestMethod.GET, headers= "Accept=application/json")
		public ResponseEntity<Map<String, Object>> updatePlayList(){
			System.out.println("good restcontroller");
			String tutorial = "sfsfsdsd";
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

}

*/