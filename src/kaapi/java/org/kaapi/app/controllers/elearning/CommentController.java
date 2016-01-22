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
	
	//get comment on video: param(videoId,offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/comment/reply/video/v/{videoId}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getCommentOnVideo(
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="item", required=false, defaultValue="10") int item,
			@PathVariable("videoId") String videoId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(commentService.countSuperCommentOnVideo(videoId));
			pagination.setTotalPages(pagination.totalPages());
			List<Comment> comment = commentService.listSuperCommentOnVideo(videoId, pagination);
			List<Comment> replycomment = commentService.listReplyCommentOnVideo(videoId);
			if (comment.isEmpty()) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("COMMENT", comment);
				map.put("REPLY", replycomment);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	//get comment
	@RequestMapping(method = RequestMethod.GET, value = "/comment/c/{id}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getComment(@PathVariable("id") String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Comment comment = commentService.getComment(id);
			if (comment==null) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", comment);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//Insert Comment
	@RequestMapping(method = RequestMethod.POST, value = "/comment", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> insertComment(@RequestBody Comment comment) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if (commentService.insert(comment)) {
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD HAS BEEN INSERTED");
			}else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD HAS NOT BEEN INSERTED");
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//Reply Comment
	@RequestMapping(method = RequestMethod.POST, value = "/comment/reply", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> replyComment(@RequestBody Comment comment) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if (commentService.reply(comment)) {
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD HAS BEEN REPLYED");
			}else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD HAS NOT BEEN REPLYED");
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//Update Comment
	@RequestMapping(method = RequestMethod.PUT, value = "/comment", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> updateComment(@RequestBody Comment comment) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if (commentService.update(comment)) {
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD HAS BEEN UPDATED");
			}else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD HAS NOT BEEN UPDATED");
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//Delete Comment
	@RequestMapping(method = RequestMethod.DELETE, value = "/comment/{id}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable("id") String commentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if (commentService.delete(commentId)) {
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD HAS BEEN DELETED");
			}else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD HAS NOT BEEN DELETED");
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//get comment on video: param(videoId,offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/comment/video/v/{videoId}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVideoComment(
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="item", required=false, defaultValue="10") int item,
			@PathVariable("videoId") String videoId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(commentService.countCommentOnVideo(videoId));
			pagination.setTotalPages(pagination.totalPages());
			List<Comment> comment = commentService.listCommentOnVideo(videoId, pagination);
			if (comment.isEmpty()) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", comment);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//Get List comment: param(offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/comment/list", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getListComment(
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="item", required=false, defaultValue="10") int item) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(commentService.countComment());
			pagination.setTotalPages(pagination.totalPages());
			List<Comment> comment = commentService.listComment(pagination);
			if (comment.isEmpty()) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", comment);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//Get List comment with comment text: param(commentText,offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/comment/list/{commentText}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getListCommentWithCommentText(
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="item", required=false, defaultValue="10") int item,
			@PathVariable("commentText") String commentText) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(commentService.countComment(commentText));
			pagination.setTotalPages(pagination.totalPages());
			List<Comment> comment = commentService.listComment(commentText, pagination);
			if (comment.isEmpty()) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", comment);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//Get List super comment with comment text: param(offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/comment/super", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getListSuperComment(
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="item", required=false, defaultValue="10") int item) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(commentService.countSuperComment());
			pagination.setTotalPages(pagination.totalPages());
			List<Comment> comment = commentService.listSuperComment(pagination);
			if (comment.isEmpty()) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", comment);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//Get List comment with comment text: param(commentText,offset,limit)
	@RequestMapping(method = RequestMethod.GET, value = "/comment/reply/list/v/{videoId}/r/{replyId}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getListReplyComment(
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="item", required=false, defaultValue="10") int item,
			@PathVariable("videoId") String videoId,
			@PathVariable("replyId") String replyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(commentService.countReplyComment(videoId, replyId));
			pagination.setTotalPages(pagination.totalPages());
			List<Comment> comment = commentService.listReplyComment(videoId, replyId, pagination);
			if (comment.isEmpty()) {
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD NOT FOUND");
			}else{
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD FOUND");
				map.put("RES_DATA", comment);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
		
}
