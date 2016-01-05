package org.kaapi.app.controllers.elearning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Playlist;
import org.kaapi.app.entities.Video;
import org.kaapi.app.services.PlayListServics;
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
@RequestMapping("/api/elearning/playlist/")
public class PlayListControllers {
	
	@Autowired
	PlayListServics playlistservice;

	//
	/*actionaddvideoToplayistdetail ->well
	 * we want to add new playlist detail 
	 * we need playlist ID and video ID
	 */
	@RequestMapping(value="/addvideotoplaylist/pid-{playlistid}/vid-{videoid}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> addVideoToPlayList(@PathVariable("playlistid") String pid,
																	@PathVariable("videoid") String vid){
		Map<String, Object> map= new HashMap<String, Object>();
		try{		
			if(playlistservice.addVideoToPlst(pid, vid)){
				if(playlistservice.countvideos(pid) == 1){
					playlistservice.updateThumbnail(vid, pid);
				}
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
	
	
	/*action create playlist ->well
	 * we want to add new playlist to table playlist in database
	 * we need (playlistName, description, userId, thumbnailUrl, publicView, maincategory,bgImage,color)
	 *  and status will auto = true
	 */
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
	
	
	/*action delete playlist ->well
	 * we want to delete playlist
	 * we need only playlist ID
	 */
	@RequestMapping(value="/deleteplaylist-{playlistid}", method= RequestMethod.DELETE, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> deletePlayList(@PathVariable("playlistid") String pid){
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
	
	
	/*action delete video from playlist ->well
	 * 
	 * we want to delete playlistdetail
	 * we need playlist id and video id
	 */
	@RequestMapping(value="/deletevideofromplaylistdetail/pid-{playlistid}/vid-{videoid}", method= RequestMethod.DELETE, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> deleteViedoFromPlayListDetail(@PathVariable("playlistid") String pid,
																		@PathVariable("videoid") String vid){
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
	
	
	
	
	/*
	 * action get playlist ->well
	 * we want to list playlist
	 */
	@RequestMapping(value="/getplaylist/pid-{playlistid}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getPlayList(	@PathVariable("playlistid") String pid,
															@RequestParam(value ="page", required = false) int page,
															@RequestParam(value ="item" , required = false) int item){
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
	
	
	/*
	 * action get play list for update ->well
	 */
	@RequestMapping(value="/getplaylistforupdate/pid-{playlistid}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getPlayListForUpdate(@PathVariable("playlistid") String pid){
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
	
	
	
	/*action list play list ->well
	 * this method need user id and playlistname from session
	 * not sure about this 
	 */
	@RequestMapping(value="/listplayList/uid-{userid}/playname-{playlistname}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listPlayList(@PathVariable("userid") String uid,
															@PathVariable("playlistname") String name,
															@RequestParam("page") int page,
															@RequestParam("item") int item){
		
		
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			
			Playlist playlist =new Playlist();
			playlist.setUserId(uid);
			playlist.setPlaylistName(name);
			
			
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
	
	/*action list play list Name ->
	 * we want to list playlist name 
	 * we neen only (playlistName,userId)
	 */
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
	
	/*action update play list ->well
	 * we need (playlistName, description, userId, thumbnailUrl, publicView, maincategory,bgImage,color,status)
	 *  
	 */
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
