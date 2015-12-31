package org.kaapi.app.controllers.elearning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.Comment;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.services.CommentService;
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
public class CommentController {
	
	@Autowired CommentService commentService;

	//get comment
	@RequestMapping(method = RequestMethod.GET, value = "/comment", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getComment(@RequestParam("commentId") String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Comment comment = commentService.getComment(id);
		if (comment==null) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", comment);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//Insert Comment
	@RequestMapping(method = RequestMethod.POST, value = "/comment", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> insertComment(@RequestBody Comment comment) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (commentService.insert(comment)) {
			map.put("STATUS", true);
			map.put("MESSAGE", "RECORD HAS BEEN INSERTED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}else{
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD HAS NOT BEEN INSERTED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		
	}
	
	//Reply Comment
	@RequestMapping(method = RequestMethod.POST, value = "/comment/reply", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> replyComment(@RequestBody Comment comment) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (commentService.reply(comment)) {
			map.put("STATUS", true);
			map.put("MESSAGE", "RECORD HAS BEEN REPLYED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}else{
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD HAS NOT BEEN REPLYED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		
	}
	
	//Update Comment
	@RequestMapping(method = RequestMethod.PUT, value = "/comment", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> updateComment(@RequestBody Comment comment) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (commentService.update(comment)) {
			map.put("STATUS", true);
			map.put("MESSAGE", "RECORD HAS BEEN UPDATED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}else{
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD HAS NOT BEEN UPDATED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		
	}
	
	//Delete Comment
	@RequestMapping(method = RequestMethod.DELETE, value = "/comment", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> deleteComment(@RequestBody Comment comment) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (commentService.delete(comment.getCommentId())) {
			map.put("STATUS", true);
			map.put("MESSAGE", "RECORD HAS BEEN DELETED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}else{
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD HAS NOT BEEN DELETED");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		
	}
	
	//get comment on video: param(videoId,offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/comment/video/{videoId}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoComment(Pagination page, @PathVariable("videoId") String videoId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Comment> comment = commentService.listCommentOnVideo(videoId, page);
		if (comment.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		page.setTotalCount(commentService.countCommentOnVideo(videoId));
		page.setTotalPages(page.totalPages());
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", comment);
		map.put("PAGINATION", page);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//Get List comment: param(offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/comment/list", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getListComment(Pagination page) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Comment> comment = commentService.listComment(page);
		if (comment.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		page.setTotalCount(commentService.countComment());
		page.setTotalPages(page.totalPages());
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", comment);
		map.put("PAGINATION", page);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//Get List comment with comment text: param(commentText,offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/comment/list/{commentText}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getListCommentWithCommentText(Pagination page, @PathVariable("commentText") String commentText) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Comment> comment = commentService.listComment(commentText, page);
		if (comment.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		page.setTotalCount(commentService.countComment(commentText));
		page.setTotalPages(page.totalPages());
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", comment);
		map.put("PAGINATION", page);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//Get List super comment with comment text: param(offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/comment/super", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getListSuperComment(Pagination page) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Comment> comment = commentService.listSuperComment(page);
		if (comment.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		page.setTotalCount(commentService.countSuperComment());
		page.setTotalPages(page.totalPages());
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", comment);
		map.put("PAGINATION", page);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//Get List comment with comment text: param(commentText,offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/comment/reply/list", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getListReplyComment(Pagination page, @RequestParam("videoId") String videoId, @RequestParam("replyId") String replyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Comment> comment = commentService.listReplyComment(videoId, replyId, page);
		if (comment.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		page.setTotalCount(commentService.countReplyComment(videoId, replyId));
		page.setTotalPages(page.totalPages());
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", comment);
		map.put("PAGINATION", page);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
		
}
