package org.kaapi.app.controllers.elearning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.Comment;
import org.kaapi.app.entities.Video;
import org.kaapi.app.services.VideoService;
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
@RequestMapping("/api/elearning")
public class VideoController {
	
	@Autowired
	VideoService videoService;

	//list all video with offset and limit: param(offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/list", headers = "Accept=application/json")
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
		int count = videoService.countVideo();
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("TOTAL_RECORD", count);
		map.put("CURRENT_PAGE", p);
		map.put("TOTAL_PAGE", Math.ceil(count/(float)i));
		map.put("RES_DATA", video);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//list video by name or search video by name: param(videoName,offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/list/{name}", headers = "Accept=application/json")
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
		int count = videoService.countVideo(name);
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("TOTAL_RECORD", count);
		map.put("CURRENT_PAGE", p);
		map.put("TOTAL_PAGE", Math.ceil(count/(float)i));
		map.put("RES_DATA", video);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//list video by user id: param(userId, offset, limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/user", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoListByUserId(@RequestParam("uid") String userId, @RequestParam("page") String page, @RequestParam("item") String item) {
		Map<String, Object> map = new HashMap<String, Object>();
		int p,i,u;
		try{
			u = Integer.parseInt(userId);
			p = Integer.parseInt(page);
			i = Integer.parseInt(item);
		}catch(NumberFormatException e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		List<Video> video = videoService.listVideo(u, p, i);
		if (video.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		int count = videoService.countVideo(u);
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("TOTAL_RECORD", count);
		map.put("CURRENT_PAGE", p);
		map.put("TOTAL_PAGE", Math.ceil(count/(float)i));
		map.put("RES_DATA", video);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//list video by user id and video name or search user video: param(userId, videoName, offset, limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/user/{name}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoListByUserIdAndName(@PathVariable("name") String videoName, @RequestParam("uid") String userId, @RequestParam("page") String page, @RequestParam("item") String item) {
		Map<String, Object> map = new HashMap<String, Object>();
		int p,i,u;
		try{
			u = Integer.parseInt(userId);
			p = Integer.parseInt(page);
			i = Integer.parseInt(item);
		}catch(NumberFormatException e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		List<Video> video = videoService.listVideo(u, videoName, p, i);
		if (video.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		int count = videoService.countVideo(u, videoName);
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("TOTAL_RECORD", count);
		map.put("CURRENT_PAGE", p);
		map.put("TOTAL_PAGE", Math.ceil(count/(float)i));
		map.put("RES_DATA", video);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//list related video: param(categoryName, limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/related", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getRelatedVideo(@RequestParam("category") String categoryName, @RequestParam("item") String item) {
		Map<String, Object> map = new HashMap<String, Object>();
		int i;
		try{
			i = Integer.parseInt(item);
		}catch(NumberFormatException e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		List<Video> video = videoService.getRelateVideo(categoryName, i);
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
	
	//get video category: param(categoryId,offset,limit);
	@RequestMapping(method = RequestMethod.GET, value = "/video/category", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getCategoryVideo(@RequestParam("cid") String categoryId, @RequestParam("page") String page, @RequestParam("item") String item) {
		Map<String, Object> map = new HashMap<String, Object>();
		int c,i,p;
		try{
			c = Integer.parseInt(categoryId);
			p = Integer.parseInt(page);
			i = Integer.parseInt(item);
		}catch(NumberFormatException e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		List<Video> video = videoService.categoryVideo(c, p, i);
		if (video.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		int count = videoService.countCategoryVideo(c);
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("TOTAL_RECORD", count);
		map.put("CURRENT_PAGE", p);
		map.put("TOTAL_PAGE", Math.ceil(count/(float)i));
		map.put("RES_DATA", video);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//get comment on video: param(videoId,offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/comment", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoComment(@RequestParam("vid") String videoId, @RequestParam("page") String page, @RequestParam("item") String item) {
		Map<String, Object> map = new HashMap<String, Object>();
		int i,v,p;
		try{
			v = Integer.parseInt(videoId);
			p = Integer.parseInt(page);
			i = Integer.parseInt(item);
		}catch(NumberFormatException e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		List<Comment> comment = videoService.listComment(v, p, i);
		if (comment.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		int count = videoService.countComment(v);
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("TOTAL_RECORD", count);
		map.put("CURRENT_PAGE", p);
		map.put("TOTAL_PAGE", Math.ceil(count/(float)i));
		map.put("RES_DATA", comment);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//top vote video sort by postdate desc: param(limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/top_vote", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getTopVoteAndRecent(@RequestParam("item") String item) {
		Map<String, Object> map = new HashMap<String, Object>();
		int i;
		try{
			i = Integer.parseInt(item);
		}catch(NumberFormatException e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		List<Video> video = videoService.topVoteAndRecent(i);
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
	
	//get video: param(videoId, viewCount)
	@RequestMapping(method = RequestMethod.GET, value = "/video", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideo(@RequestParam("vid") String id, @RequestParam("view") String count) {
		Map<String, Object> map = new HashMap<String, Object>();
		int i;
		try{
			i = Integer.parseInt(id);
		}catch(NumberFormatException e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		System.out.println(count);
		boolean view = false;
		if(!count.equals("f") && !count.equals("t")){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT,t,f");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}else{
			view = (count.equals("t"))?true:false;
		}
		System.out.println(view);
		Video video = videoService.getVideo(i, view);
		if (video==null) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", video);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/video", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> deleteVideo(@RequestBody Video video) {
		Map<String, Object> map = new HashMap<String, Object>();
		int i;
		try{
			i = Integer.parseInt("0" + video.getVideoId());
		}catch(NumberFormatException e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT" + video.getVideoId());
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		if (videoService.delete(i)) {
			map.put("STATUS", true);
			map.put("MESSAGE", "RECORD HAS BEEN DELETED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}else{
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD HAS NOT BEEN DELETED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
	}
	
	//count all videos
	@RequestMapping(method = RequestMethod.GET, value = "/countVideo", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> countVideo() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("RES_DATA", videoService.countVideo());
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
}
