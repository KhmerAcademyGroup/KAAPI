package org.kaapi.app.controllers.elearning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.Comment;
import org.kaapi.app.services.CommentService;
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
public class CommentController {
	
	@Autowired CommentService commentService;

	//get comment on video: param(videoId,offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/comment/video/{videoId}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoComment(@PathVariable("videoId") String videoId, @RequestParam("page") String page, @RequestParam("item") String item) {
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
		if(v<=0 || p<=0 || i<=0){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		List<Comment> comment = commentService.listComment(v, p, i);
		if (comment.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		int count = commentService.countComment(v);
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("TOTAL_RECORD", count);
		map.put("CURRENT_PAGE", p);
		map.put("TOTAL_PAGE", Math.ceil(count/(float)i));
		map.put("RES_DATA", comment);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//Get List comment: param(offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/comment", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getListComment(@RequestParam("page") String page, @RequestParam("item") String item) {
		Map<String, Object> map = new HashMap<String, Object>();
		int i,p;
		try{
			p = Integer.parseInt(page);
			i = Integer.parseInt(item);
		}catch(NumberFormatException e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		if(p<=0 || i<=0){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		List<Comment> comment = commentService.listComment(p, i);
		if (comment.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		int count = commentService.countComment();
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("TOTAL_RECORD", count);
		map.put("CURRENT_PAGE", p);
		map.put("TOTAL_PAGE", Math.ceil(count/(float)i));
		map.put("RES_DATA", comment);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//Get List comment with comment text: param(commentText,offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/comment/{commentText}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getListCommentWithCommentText(@PathVariable("commentText") String commentText, @RequestParam("page") String page, @RequestParam("item") String item) {
		Map<String, Object> map = new HashMap<String, Object>();
		int i,p;
		try{
			p = Integer.parseInt(page);
			i = Integer.parseInt(item);
		}catch(NumberFormatException e){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		if(p<=0 || i<=0){
			map.put("STATUS", false);
			map.put("MESSAGE", "ERROR INPUT");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		List<Comment> comment = commentService.listComment(commentText, p, i);
		if (comment.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		int count = commentService.countComment(commentText);
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("TOTAL_RECORD", count);
		map.put("CURRENT_PAGE", p);
		map.put("TOTAL_PAGE", Math.ceil(count/(float)i));
		map.put("RES_DATA", comment);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
		
}
