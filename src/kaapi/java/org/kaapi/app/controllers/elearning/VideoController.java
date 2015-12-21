package org.kaapi.app.controllers.elearning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.Video;
import org.kaapi.app.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/elearning")
public class VideoController {
	
	@Autowired
	VideoService videoService;

	@RequestMapping(method = RequestMethod.GET, value = "/video", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoList(@RequestParam("page") String page, @RequestParam("item") String item) {
		Map<String, Object> map = new HashMap<String, Object>();
		int p,i;
		try{
			p = Integer.parseInt(page);
			i = Integer.parseInt(item);
		}catch(NumberFormatException e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		List<Video> video = videoService.listVideo(p, i);
		if (video.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("TOTAL_RECORD", videoService.countVideo());
		map.put("RES_DATA", video);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/video/{name}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoListByName(@PathVariable("name") String name, @RequestParam("page") String page, @RequestParam("item") String item) {
		Map<String, Object> map = new HashMap<String, Object>();
		int p,i;
		try{
			p = Integer.parseInt(page);
			i = Integer.parseInt(item);
		}catch(NumberFormatException e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		List<Video> video = videoService.listVideo(name, p, i);
		if (video.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("TOTAL_RECORD", videoService.countVideo(name));
		map.put("RES_DATA", video);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/countVideo", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> countVideo() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("RES_DATA", videoService.countVideo());
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
}
