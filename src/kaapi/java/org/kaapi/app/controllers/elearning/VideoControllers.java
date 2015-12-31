package org.kaapi.app.controllers.elearning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Video;
import org.kaapi.app.services.VideosService;
import org.kaapi.app.utilities.Encryption;
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
public class VideoControllers {

	@Autowired VideosService videoService;

	//Get video: param(videoId, viewCount)
	@RequestMapping(method = RequestMethod.GET, value = "/video/v/{id}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideo(@PathVariable("id") String id, @RequestParam("view") String count) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean view = false;
		if(!count.equals("f") && !count.equals("t")){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}else{
			view = (count.equals("t"))?true:false;
		}
		System.out.println(view);
		Video video = videoService.getVideo(id, view);
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
	
	//Get video: param(videoId, viewCount)
	@RequestMapping(method = RequestMethod.PATCH, value = "/video/enable/v/{id}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> toggleVideo(@PathVariable("id") String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(videoService.toggleVideo(id)) {
			map.put("STATUS", true);
			map.put("MESSAGE", "OPERATION SUCCESS!");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}else{
			map.put("STATUS", false);
			map.put("MESSAGE", "OPERATION FAIL");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		
	}
		
	//Insert Video
	@RequestMapping(method = RequestMethod.POST, value = "/video", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> insertVideo(@RequestBody Video video) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(video.getVideoName().equals("") || video.getVideoName().equals(null)){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT VIDEO NAME");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		if(video.getYoutubeUrl().equals("") || video.getYoutubeUrl().equals(null)){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT YOUTUBE URL");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		if(video.getUserId()<=0){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT USER ID");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		boolean help = true;
		int vid = videoService.insert(video);
		String id = new Integer(vid).toString();
		int []catid = video.getCategoryId();
		for(int i=0; i<catid.length; i++){
			int cid = Integer.parseInt("0" + catid[i]);
			if(!videoService.insertVideoToCategory(Encryption.encode(id), cid)){
				videoService.delete(Encryption.encode(id));
				help=false;
				break;
			}
		}
		if (vid>0 && help) {
			map.put("STATUS", true);
			map.put("MESSAGE", "RECORD HAS BEEN INSERTED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}else{
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD HAS NOT BEEN INSERTED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		
	}
	
	//Update Video
	@RequestMapping(method = RequestMethod.PUT, value = "/video", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> updateVideo(@RequestBody Video video) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(video.getVideoName().equals("") || video.getVideoName().equals(null)){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT VIDEO NAME");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		if(video.getYoutubeUrl().equals("") || video.getYoutubeUrl().equals(null)){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT YOUTUBE URL");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		boolean help = true;
		boolean update = videoService.update(video);
		if(update) videoService.removeVideoFromCategory(video.getVideoId());
		int []catid = video.getCategoryId();
		for(int i=0; i<catid.length; i++){
			if(!videoService.insertVideoToCategory(video.getVideoId(), catid[i])){
				videoService.delete(video.getVideoId());
				help=false;
				break;
			}
		}
		if (update && help) {
			map.put("STATUS", true);
			map.put("MESSAGE", "RECORD HAS BEEN UPDATED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}else{
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD HAS NOT BEEN UPDATED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		
	}
	
	//Delete video
	@RequestMapping(method = RequestMethod.DELETE, value = "/video", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> deleteVideo(@RequestBody Video video) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (videoService.delete(video.getVideoId())) {
			videoService.removeVideoFromCategory(video.getVideoId());
			map.put("STATUS", true);
			map.put("MESSAGE", "RECORD HAS BEEN DELETED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}else{
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD HAS NOT BEEN DELETED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
	}
	
	//list all video with offset and limit: param(offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/list/all", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoList(Pagination page) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Video> video = videoService.listVideo(page);
		if (video.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		page.setTotalCount(videoService.countVideo());
		page.setTotalPages(page.totalPages());
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", video);
		map.put("PAGINATION", page);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//List all video with status(enable or disable) and offset and limit: param(status, offset, limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/list", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoListWithStatus(Pagination page , @RequestParam("status") String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean help = false;
		if(!status.equals("f") && !status.equals("t")){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}else{
			help = (status.equals("t"))?true:false;
		}
		List<Video> video = videoService.listVideo(help, page);
		if (video.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		page.setTotalCount(videoService.countVideo(help));
		page.setTotalPages(page.totalPages());
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", video);
		map.put("PAGINATION", page);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//list video by name or search video by name: param(videoName,offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/list/all/{name}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoListByName(Pagination page, @PathVariable("name") String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Video> video = videoService.listVideo(name, page);
		if (video.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		page.setTotalCount(videoService.countVideo(name));
		page.setTotalPages(page.totalPages());
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", video);
		map.put("PAGINATION", page);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//list video by name with enable or disable or search video by name: param(videoName,status,offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/list/{name}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoListByNameWithStatus(Pagination page,@PathVariable("name") String name, @RequestParam("status") String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean help = false;
		if(!status.equals("f") && !status.equals("t")){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}else{
			help = (status.equals("t"))?true:false;
		}
		List<Video> video = videoService.listVideo(name, help, page);
		if (video.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		page.setTotalCount(videoService.countVideo(name, help));
		page.setTotalPages(page.totalPages());
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", video);
		map.put("PAGINATION", page);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//list video by user id: param(userId, pagination)
	@RequestMapping(method = RequestMethod.GET, value = "/video/user/all/u/{id}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoListByUserId(Pagination page, @PathVariable("id") String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Video> video = videoService.listVideoUser(userId, page);
		if (video.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		page.setTotalCount(videoService.countVideoUser(userId));
		page.setTotalPages(page.totalPages());
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", video);
		map.put("PAGINATION", page);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//list video by user id: param(userId, status, pagination)
	@RequestMapping(method = RequestMethod.GET, value = "/video/user/u/{id}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoListByUserIdWithStatus(Pagination page, @PathVariable("id") String userId, @RequestParam("status") String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean help = false;
		if(!status.equals("f") && !status.equals("t")){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}else{
			help = (status.equals("t"))?true:false;
		}
		List<Video> video = videoService.listVideoUser(userId, help, page);
		if (video.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		page.setTotalCount(videoService.countVideoUser(userId, help));
		page.setTotalPages(page.totalPages());
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", video);
		map.put("PAGINATION", page);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//list video by user id and video name or search user video: param(userId, videoName, pagination)
	@RequestMapping(method = RequestMethod.GET, value = "/video/user/all/u/{id}/name/{name}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoListByUserIdAndName(Pagination page, @PathVariable("name") String videoName, @PathVariable("id") String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Video> video = videoService.listVideo(userId, videoName, page);
		if (video.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		page.setTotalCount(videoService.countVideo(userId, videoName));
		page.setTotalPages(page.totalPages());
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", video);
		map.put("PAGINATION", page);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//list video by user id and video name or search user video: param(userId, videoName, status, pagination)
	@RequestMapping(method = RequestMethod.GET, value = "/video/user/u/{id}/name/{name}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoListByUserIdAndNameWithStatus(Pagination page, @PathVariable("name") String videoName, @RequestParam("status") String status, @PathVariable("id") String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean help = false;
		if(!status.equals("f") && !status.equals("t")){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}else{
			help = (status.equals("t"))?true:false;
		}
		List<Video> video = videoService.listVideo(userId, videoName, help, page);
		if (video.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		page.setTotalCount(videoService.countVideo(userId, videoName, help));
		page.setTotalPages(page.totalPages());
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", video);
		map.put("PAGINATION", page);
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
	@RequestMapping(method = RequestMethod.GET, value = "/video/category/c/{id}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getCategoryVideo(Pagination page, @PathVariable("id") String categoryId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Video> video = videoService.categoryVideo(categoryId, page);
		if (video.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		page.setTotalCount(videoService.countCategoryVideo(categoryId));
		page.setTotalPages(page.totalPages());
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", video);
		map.put("PAGINATION", page);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//top vote video sort by postdate desc: param(limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/top_vote_recent", headers = "Accept=application/json")
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
	
	//top vote : param(limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/top_vote", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getTopVote(@RequestParam("item") String item) {
		Map<String, Object> map = new HashMap<String, Object>();
		int i;
		try{
			i = Integer.parseInt(item);
		}catch(NumberFormatException e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		if(i<0){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		List<Video> video = videoService.topVote(i);
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
	
	//recent video : param(limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/recent", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getRecent(@RequestParam("item") String item) {
		Map<String, Object> map = new HashMap<String, Object>();
		int i;
		try{
			i = Integer.parseInt(item);
		}catch(NumberFormatException e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		if(i<0){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		List<Video> video = videoService.recentVideo(i);
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
	
	//count all videos
	@RequestMapping(method = RequestMethod.GET, value = "/video/count", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> countVideo() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("RES_DATA", videoService.countVideo());
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
}
