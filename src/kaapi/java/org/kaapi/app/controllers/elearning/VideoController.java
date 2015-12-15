package org.kaapi.app.controllers.elearning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.Video;
import org.kaapi.app.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/elearning/videos")
public class VideoController {
	
	@Autowired
	VideoService videoService;

	@RequestMapping(method = RequestMethod.GET, value = "/listVideo", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getArticleList(@RequestParam("page") int page, @RequestParam("perPage") int perPage) {
		List<Video> video = videoService.listVideo(page, perPage);
		Map<String, Object> map = new HashMap<String, Object>();
		if (video.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", video);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
}
