package org.kaapi.app.controllers.elearning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.History;
import org.kaapi.app.entities.Log;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Playlist;
import org.kaapi.app.entities.Video;
import org.kaapi.app.services.CategoryService;
import org.kaapi.app.services.CommentService;
import org.kaapi.app.services.HistoryService;
import org.kaapi.app.services.LogService;
import org.kaapi.app.services.PlayListServics;
import org.kaapi.app.services.VideosService;
import org.kaapi.app.services.VoteService;
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
public class VideoController {

	@Autowired VideosService videoService;
	@Autowired PlayListServics playlistService;
	@Autowired CommentService commentService;
	@Autowired VoteService voteService;
	@Autowired LogService logService;
	@Autowired CategoryService categoryService;
	@Autowired HistoryService historyService;

	//Get video: param(videoId, viewCount)
	@RequestMapping(method = RequestMethod.GET, value = "/video/v/{id}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideo(
			@PathVariable("id") String id, 
			@RequestParam(value="view", required = false, defaultValue = "true") boolean view) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Video video = videoService.getVideo(id, view);
			if (video==null) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", video);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//Toggle video: param(videoId, viewCount)
	@RequestMapping(method = RequestMethod.PUT, value = "/video/enable/v/{id}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> toggleVideo(@PathVariable("id") String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(videoService.toggleVideo(id)) {
				map.put("STATUS", true);
				map.put("MESSAGE", "OPERATION SUCCESS!");
			}else{
				map.put("STATUS", false);
				map.put("MESSAGE", "OPERATION FAIL");
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);	
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
		boolean help = true;
		int vid = videoService.insert(video);
		String id = new Integer(vid).toString();
		String []catid = video.getCategoryId();
		for(int i=0; i<catid.length; i++){
			if(!videoService.insertVideoToCategory(Encryption.encode(id), catid[i])){
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
		String []catid = video.getCategoryId();
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
	@RequestMapping(method = RequestMethod.DELETE, value = "/video/{id}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> deleteVideo(@PathVariable("id") String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(id);
		if (videoService.delete(id)) {
			videoService.removeVideoFromCategory(id);
			map.put("STATUS", true);
			map.put("MESSAGE", "RECORD HAS BEEN DELETED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}else{
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD HAS NOT BEEN DELETED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
	}
	
	//list all video with offset and limit: param(pagination)
	@RequestMapping(method = RequestMethod.GET, value = "/video/list/all", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoList(
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="item", required=false, defaultValue="10") int item) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(videoService.countVideo());
			pagination.setTotalPages(pagination.totalPages());
			List<Video> video = videoService.listVideo(pagination);
			if (video.isEmpty()) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", video);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//List all video with status(enable or disable) and offset and limit: param(status, offset, limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/list", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoListWithStatus(
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="item", required=false, defaultValue="10") int item,
			@RequestParam(value="status", required=false, defaultValue="true") boolean status) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(videoService.countVideo(status));
			pagination.setTotalPages(pagination.totalPages());
			List<Video> video = videoService.listVideo(status, pagination);
			if (video.isEmpty()) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", video);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//list video by name or search video by name: param(videoName,offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/list/all/{name}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoListByName(
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="item", required=false, defaultValue="10") int item,
			@PathVariable("name") String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(videoService.countVideo(name));
			pagination.setTotalPages(pagination.totalPages());
			List<Video> video = videoService.listVideo(name, pagination);
			if (video.isEmpty()) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", video);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//list video by name with enable or disable or search video by name: param(videoName,status,offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/list/{name}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoListByNameWithStatus(
			@PathVariable("name") String name,
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="item", required=false, defaultValue="10") int item,
			@RequestParam(value="status", required=false, defaultValue="true") boolean status) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(videoService.countVideo(name, status));
			pagination.setTotalPages(pagination.totalPages());
			List<Video> video = videoService.listVideo(name, status, pagination);
			if (video.isEmpty()) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", video);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//list video by user id: param(userId, pagination)
	@RequestMapping(method = RequestMethod.GET, value = "/video/user/all/u/{id}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoListByUserId(
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="item", required=false, defaultValue="10") int item,
			@PathVariable("id") String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(videoService.countVideoUser(userId));
			pagination.setTotalPages(pagination.totalPages());
			List<Video> video = videoService.listVideoUser(userId, pagination);
			if (video.isEmpty()) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", video);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//list video by user id: param(userId, status, pagination)
	@RequestMapping(method = RequestMethod.GET, value = "/video/user/u/{id}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoListByUserIdWithStatus(
			@PathVariable("id") String userId,
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="item", required=false, defaultValue="10") int item,
			@RequestParam(value="status", required=false, defaultValue="true") boolean status) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(videoService.countVideoUser(userId, status));
			pagination.setTotalPages(pagination.totalPages());
			List<Video> video = videoService.listVideoUser(userId, status, pagination);
			if (video.isEmpty()) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", video);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//list video by user id and video name or search user video: param(userId, videoName, pagination)
	@RequestMapping(method = RequestMethod.GET, value = "/video/user/all/u/{id}/name/{name}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoListByUserIdAndName(
			@PathVariable("id") String userId,
			@PathVariable("name") String videoName,
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="item", required=false, defaultValue="10") int item) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(videoService.countVideo(userId, videoName));
			pagination.setTotalPages(pagination.totalPages());
			List<Video> video = videoService.listVideo(userId, videoName, pagination);
			if (video.isEmpty()) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", video);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//list video by user id and video name or search user video: param(userId, videoName, status, pagination)
	@RequestMapping(method = RequestMethod.GET, value = "/video/user/u/{id}/name/{name}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoListByUserIdAndNameWithStatus(
			@PathVariable("id") String userId,
			@PathVariable("name") String videoName,
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="item", required=false, defaultValue="10") int item,
			@RequestParam(value="status", required=false, defaultValue="true") boolean status) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(videoService.countVideo(userId, videoName, status));
			pagination.setTotalPages(pagination.totalPages());
			List<Video> video = videoService.listVideo(userId, videoName, status, pagination);
			if (video.isEmpty()) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", video);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//list related video: param(categoryName, limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/related", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getRelatedVideo(
			@RequestParam("category") String categoryName,
			@RequestParam(value="item", required=false, defaultValue="10") int item) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			List<Video> video = videoService.getRelateVideo(categoryName, item);
			if (video.isEmpty()) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", video);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//get video category: param(categoryId,pagination);
	@RequestMapping(method = RequestMethod.GET, value = "/video/category/all/c/{id}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getCategoryVideo(
			@PathVariable("id") String categoryId,
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="item", required=false, defaultValue="10") int item) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(videoService.countCategoryVideo(categoryId));
			pagination.setTotalPages(pagination.totalPages());
			List<Video> video = videoService.categoryVideo(categoryId, pagination);
			if (video.isEmpty()) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", video);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//get video category: param(categoryId,pagination);
	@RequestMapping(method = RequestMethod.GET, value = "/video/category/c/{id}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getCategoryVideoWithStatus(
			@PathVariable("id") String categoryId,
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="item", required=false, defaultValue="10") int item,
			@RequestParam(value="status", required=false, defaultValue="true") boolean status) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(videoService.countCategoryVideo(categoryId, status));
			pagination.setTotalPages(pagination.totalPages());
			List<Video> video = videoService.categoryVideo(categoryId, status, pagination);
			if (video.isEmpty()) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", video);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//top vote video sort by postdate desc: param(limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/top_vote_recent", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getTopVoteAndRecent(
			@RequestParam(value="item", required=false, defaultValue="10") int item) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			List<Video> video = videoService.topVoteAndRecent(item);
			if (video.isEmpty()) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", video);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//top vote : param(limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/top_vote", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getTopVote(
			@RequestParam(value="item", required=false, defaultValue="10") int item) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			List<Video> video = videoService.topVote(item);
			if (video.isEmpty()) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", video);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//recent video : param(limit)
	@RequestMapping(method = RequestMethod.GET, value = "/video/recent", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getRecent(
			@RequestParam(value="item", required=false, defaultValue="10") int item) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			List<Video> video = videoService.recentVideo(item);
			if (video.isEmpty()) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", video);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//count all videos
	@RequestMapping(method = RequestMethod.GET, value = "/video/count", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> countVideo() {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			map.put("RES_DATA", videoService.countVideo());
			map.put("MESSAGE", "OPERATION SUCCESS");
			map.put("STATUS", true);
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/video/listMainCategoryAndPlaylist", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listMainCategoryAndPlaylist() {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			List<Playlist> playlists = videoService.listPlaylist();
			List<Playlist> mainCategory = videoService.listMainCategory();
			map.put("STATUS", true);
			map.put("MESSAGE", "OPERATION SUCCESS");
			map.put("PLAYLIST", playlists);
			map.put("MAINCATEGORY", mainCategory);
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/video/playvideo", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> playVideo(
			@RequestParam(value="playlist", required=false) String pid) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			List<Playlist> playlists = videoService.listPlaylist();
			List<Playlist> mainCategory = videoService.listMainCategory();
			map.put("PLAYLIST_SIDEBAR", playlists);
			map.put("MAINCATEGORY", mainCategory);
			
			if(pid!=null){
				List<Video> playlistVideo= playlistService.listVideo(pid);
				map.put("PLAYLIST", playlistVideo);
			}else{
				map.put("PLAYLIST", null);
			}
			map.put("STATUS", true);
			map.put("MESSAGE", "OPERATION SUCCESS");
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/video/getplayvideo", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getPlayVideo(
			@RequestParam(value="v") String vid,
			@RequestParam(value="user", required=false) String uid) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Video video = videoService.getVideo(vid, true);
			if(video!=null){
				if(video.getCategoryName()!=null){
					List<Video> relateVideo = videoService.getRelateVideo(video.getCategoryName(), 10);
					map.put("RELATEVIDEO", relateVideo);
				}
				
				Log log = new Log();
				log.setUserId(uid);
				log.setVideoId(vid);
				int logid = logService.insert(log);
				History history = new History();
				history.setUserId(uid);
				history.setVideoId(vid);
				historyService.insert(history);
				map.put("LOGID", logid);
				map.put("VIDEO", video);
				map.put("STATUS", true);
				map.put("MESSAGE", "VIDEO FOUND");
			}else{
				map.put("MESSAGE", "VIDEO NOT FOUND");
				map.put("STATUS", false);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/mainpage/countdata", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getCountMainPage() {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			
			int users = videoService.countUser();
			int videos = videoService.countVideo();
			int categories = categoryService.countCategory();
			int courses = videoService.countCourse();
			
			map.put("STATUS", true);
			map.put("MESSAGE", "OPERATION SUCCESS");
			map.put("COUNTUSER", users);
			map.put("COUNTVIDEO", videos);
			map.put("COUNTCATEGORY", categories);
			map.put("COUNTCOURSE", courses);
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
}
