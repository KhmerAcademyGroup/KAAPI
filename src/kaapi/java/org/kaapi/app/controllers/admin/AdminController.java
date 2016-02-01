package org.kaapi.app.controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.User;
import org.kaapi.app.entities.Video;
import org.kaapi.app.services.UserService;
import org.kaapi.app.services.VideosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {
	
	@Autowired
	VideosService videoService;
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/dashboard", method= RequestMethod.GET, headers="Accept=application/json")
	public ResponseEntity<Map<String, Object>> dashbord(){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			map.put("STATUS", true);
			map.put("MESSAGE", "COUNT STATISTIC");			
			map.put("countvideos", videoService.countVideo());
			map.put("countusers", videoService.countUser());
			map.put("countplaylists", videoService.countPlaylist());
			map.put("countforum", videoService.countForum());
			Pagination pagination = new Pagination();
			pagination.setPage(1);
			pagination.setItem(5);
			List<Video> video=  videoService.listVideo(pagination);
			map.put("listvideo", video);
			List<User> user= userService.listUser(pagination);
			map.put("listuser", user);
			
		}catch(Exception e){
			e.printStackTrace();
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR OCCURING");
		}
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		
	}
	
	
	
}
