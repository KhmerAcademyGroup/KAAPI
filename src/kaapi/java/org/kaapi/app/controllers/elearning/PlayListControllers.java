package org.kaapi.app.controllers.elearning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Playlist;
import org.kaapi.app.entities.PlaylistDetail;
import org.kaapi.app.entities.Video;
import org.kaapi.app.forms.FrmCreatePlaylist;
import org.kaapi.app.forms.FrmUpdatePlaylist;
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
	
	/*
	 * action get listallplaylist
	 * we want to listallplaylist
	 */
	@RequestMapping(value="/userplaylistall/{uid}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> UserPlayListAll(
												@PathVariable("uid") String uid
												){
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			ArrayList<Playlist>  dto= playlistservice.UserPlayList(uid);
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
	 * action get listallplaylist
	 * we want to listallplaylist
	 */
	@RequestMapping(value="/userplaylist/{uid}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> UserPlayList(
												@PathVariable("uid") String uid,
												@RequestParam(value ="page", required = false, defaultValue = "1") int page,
												@RequestParam(value ="item" , required = false , defaultValue = "10") int item){
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			Pagination pagin = new Pagination();
			pagin.setItem(item);
			pagin.setPage(page);
			pagin.setTotalCount(playlistservice.countUserPlaylist(uid));
			pagin.setTotalPages(pagin.totalPages());
			
			ArrayList<Playlist>  dto= playlistservice.UserPlayList(uid, pagin);
			if(!dto.isEmpty()){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", dto);
				map.put("PAGINATION", pagin);
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
	
	
	/*getplaylist by id
	 */
	@RequestMapping(value="/getplaylistbyplaylistid/{playlistid}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> GetPlayListByPlayListId(@PathVariable("playlistid") String pid){
		Map<String, Object> map= new HashMap<String, Object>();
		
		try{
			Playlist  dto= playlistservice.get(pid);
			if(dto !=null){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("USERPLAYLIST", dto);
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

	/*listuserplaylist
	 */
	@RequestMapping(value="/listuserplaylist/{userid}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listUserPlayList(@PathVariable("userid") String uid){
		Map<String, Object> map= new HashMap<String, Object>();
		
		try{
			ArrayList<Playlist>  dto= playlistservice.listUserPlayList(uid);
			ArrayList<PlaylistDetail> playDetail = playlistservice.listplaylistdetail(uid); 
			if(!dto.isEmpty()){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("USERPLAYLIST", dto);
				map.put("PLAYLISTDETAIL", playDetail);
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
	 * list playlist by maincategory
	 * we want to list all the course base on maincategory
	 */
	@RequestMapping(value="/listplaylistbymaincategory/{categoryid}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listPlaylistByMainCategory(@PathVariable("categoryid") String cid){
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			ArrayList<Playlist> dto= playlistservice.listPlayListByMainCategory(cid);
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
	 * search category
	 * we want to search the course in play
	 */
	@RequestMapping(value="/searchplaylist/{searchkey}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> test(@PathVariable("searchkey") String key){
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			ArrayList<Playlist> dto= playlistservice.searchPlayList(key);
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
	 * action get List all playlist for elearning home page
	 * 
	 */
	@RequestMapping(value="/listmianplaylist", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listMainPlayList(){
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			
			ArrayList<Playlist> maincategory= playlistservice.litsMainElearning();
			ArrayList<Playlist> playlist= playlistservice.listMainPlaylist();
			
		
			if(!maincategory.isEmpty()){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("MAINCATEGORY", maincategory);
				map.put("PLAYLIST", playlist);
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
	 * action get listallplaylist
	 * we want to listallplaylist
	 */
	@RequestMapping(value="/listallplaylist", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listAllPlayList(@RequestParam(value ="page", required = false, defaultValue = "1") int page,
																@RequestParam(value ="item" , required = false , defaultValue = "10") int item){
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			Pagination pagin = new Pagination();
			pagin.setItem(item);
			pagin.setPage(page);
			pagin.setTotalCount(playlistservice.countPlayList());
			pagin.setTotalPages(pagin.totalPages());
			
			ArrayList<Playlist>  dto= playlistservice.listAllPlaylist(pagin);
			if(!dto.isEmpty()){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", dto);
				map.put("PAGINATION", pagin);
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
	

	//
	/*actionaddvideoToplayistdetail ->well
	 * we want to add new playlist detail 
	 * we need playlist ID and video ID
	 */
	@RequestMapping(value="/addvideotoplaylistDetail/{playlistid}/{videoid}", method= RequestMethod.POST, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> addVideoToPlayListDetail(@PathVariable("playlistid") String pid,
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
	public ResponseEntity<Map<String, Object>> createPlayList(@RequestBody FrmCreatePlaylist playlist){
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
	@RequestMapping(value="/deleteplaylist/{playlistid}", method= RequestMethod.DELETE, headers= "Accept=application/json")
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
	@RequestMapping(value="/deletevideofromplaylistdetail/{playlistid}/{videoid}", method= RequestMethod.DELETE, headers= "Accept=application/json")
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
	 * change from getplaylist to listVideoInPlaylist
	 */
	@RequestMapping(value="/listVideoInPlaylist/{playlistid}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listVideoInPlaylist(	@PathVariable("playlistid") String pid,
															@RequestParam(value ="page", required = false, defaultValue = "1") int page,
															@RequestParam(value ="item" , required = false , defaultValue = "10") int item){
		
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			
			Pagination pagin = new Pagination();
			pagin.setItem(item);
			pagin.setPage(page);
			pagin.setTotalCount(playlistservice.countvideos(pid));
			pagin.setTotalPages(pagin.totalPages());
			
			ArrayList<Video>  dto= playlistservice.listVideoInPlaylist(pid, pagin);
			if(!dto.isEmpty()){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", dto);
				map.put("PAGINATION", pagin);
				
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
	 * action get playlist ->well
	 * we want to list playlist
	 * change from getplaylist to listVideoInPlaylist
	 */
	@RequestMapping(value="/listAllVideoInPlaylist/{playlistid}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listAllVideoInPlaylist(	@PathVariable("playlistid") String pid){
		
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			
			
			ArrayList<Video>  dto= playlistservice.listVideoInPlaylist(pid);
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
	 * action get play list for update ->well
	 */
	@RequestMapping(value="/getplaylistforupdate/{playlistid}", method= RequestMethod.GET, headers= "Accept=application/json")
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
	@RequestMapping(value="/listplayList/{userid}/{playlistname}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listPlayList(@PathVariable("userid") String uid,
															@PathVariable("playlistname") String name,
															@RequestParam(value="page", required = false, defaultValue = "1") int page,
															@RequestParam(value="item", required = false, defaultValue = "10") int item){
		
		
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			
			Playlist playlist =new Playlist();
			playlist.setUserId(uid);
			playlist.setPlaylistName(name);
			
			
		
			Pagination pagin = new Pagination();
			pagin.setItem(item);
			pagin.setPage(page);
			pagin.setTotalCount(playlistservice.countUserPlaylist(uid, name));
			pagin.setTotalPages(pagin.totalPages());
			
			ArrayList<Playlist>  dto= playlistservice.list(pagin, playlist);
			if(!dto.isEmpty()){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", dto);
				map.put("PAGINATION", pagin);
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
	@RequestMapping(value="/listPlayStatusByListName/{playlistname}/{userid}", method= RequestMethod.GET, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listPlayStatusByListName(@PathVariable("playlistname") String listName,
																@PathVariable("userid") String uid){
		Map<String, Object> map= new HashMap<String, Object>();
		Playlist playlist =new Playlist();
		playlist.setPlaylistName(listName);
		playlist.setUserId(uid);
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
	 * we need (playlistid,playlistName, description, userId, thumbnailUrl, publicView, maincategory,bgImage,color,status)
	 *  
	 */
	@RequestMapping(value="/updatePlayList", method= RequestMethod.PUT, headers= "Accept=application/json")
	public ResponseEntity<Map<String, Object>> updatePlayList(@RequestBody FrmUpdatePlaylist playlist){
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
