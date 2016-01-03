package org.kaapi.app.controllers.forum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.ForumComment;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.services.ForumCommentService;
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
@RequestMapping("/api/forum/comment")
public class ForumCommentController {
	
	@Autowired
	ForumCommentService forumCommentService;
	
	@RequestMapping(value="/addanswer" , method=RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<Map<String,Object>> addAnswer(@RequestBody ForumComment forumComment){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			forumComment.setPostDate(new java.sql.Date(new java.util.Date().getTime()));
			forumComment.setCategoryId(null);
			forumComment.setSelected(true);
			if(forumCommentService.insertAnswer(forumComment)){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD HAS BEEN INSERTED");
			}
			else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD HAS NOT BEEN INSERTED");
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/addquestion" , method=RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<Map<String,Object>> addQuestion(@RequestBody ForumComment forumComment){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			forumComment.setPostDate(new java.sql.Date(new java.util.Date().getTime()));
			forumComment.setSelected(true);
			if(forumCommentService.insetQuestion(forumComment)){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD HAS BEEN INSERTED");
			}
			else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD HAS NOT BEEN INSERTED");
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/tags" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> getTags(){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			String tags[] = forumCommentService.getAllTags();
			map.put("MESSAGE", "RECORD FOUND");
			map.put("STATUS", true);
			map.put("TAGS",tags);
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/getFooter" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> getFooterCountCommentCountQuestionGetTags(){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			String tags[] = forumCommentService.getAllTags();
			map.put("MESSAGE", "RECORD FOUND");
			map.put("STATUS", true);
			map.put("TAGS",tags);
			map.put("TOTAL_COMMENT",forumCommentService.countComment());
			map.put("TOTAL_QUESTION",forumCommentService.countQuestion());
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}

	@RequestMapping(value="/getquestion/{qid}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> getQuestionByIdListAnswerByQuestionId(
			  @PathVariable("qid") int qid 
			, @RequestParam(value = "page", required = false , defaultValue="1") int page 
			, @RequestParam(value="item" , required = false , defaultValue="20") int item  ){
		Map<String , Object> map = new HashMap<String , Object>();
		Pagination pagination = new Pagination();
		pagination.setItem(item);
		pagination.setPage(page);
		pagination.setTotalCount(forumCommentService.countAnswerByQuestionId(qid));
		pagination.setTotalPages(pagination.totalPages());
		try{
			ForumComment question = forumCommentService.getQuestionById(qid);
			if(question == null){
				map.put("MESSAGE", "RECORD NOT FOUND");
				map.put("STATUS", false);
			}else{
				map.put("MESSAGE", "RECORD FOUND");
				map.put("STATUS", true);
				map.put("QUESTION", question);
				map.put("LIST_ANSWER", forumCommentService.listAnswerByQuestionId(qid, pagination));
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value="/listquestion" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> listQuestion(
			  @RequestParam(value = "page", required = false , defaultValue="1") int page 
			, @RequestParam(value="item" , required = false , defaultValue="20") int item  ){
		Map<String , Object> map = new HashMap<String , Object>();
		Pagination pagination = new Pagination();
		pagination.setItem(item);
		pagination.setPage(page);
		pagination.setTotalCount(forumCommentService.countQuestion());
		pagination.setTotalPages(pagination.totalPages());
		try{
			List<ForumComment> question = forumCommentService.listAllQuestion( pagination);
			if(question == null){
				map.put("MESSAGE", "RECORD NOT FOUND");
				map.put("STATUS", false);
			}else{
				map.put("MESSAGE", "RECORD FOUND");
				map.put("STATUS", true);
				map.put("RES_DATA", question);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/listquestion/u/{uid}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> listQuestionByUserId(
			  @PathVariable("uid") int userid,
			  @RequestParam(value = "page", required = false , defaultValue="1") int page ,
			  @RequestParam(value="item" , required = false , defaultValue="20") int item  ){
		Map<String , Object> map = new HashMap<String , Object>();
		Pagination pagination = new Pagination();
		pagination.setItem(item);
		pagination.setPage(page);
		pagination.setTotalCount(forumCommentService.countQuestionByUserid(userid));
		pagination.setTotalPages(pagination.totalPages());
		try{
			List<ForumComment> question = forumCommentService.listQuestionByUserid(userid, pagination);
			if(question == null){
				map.put("MESSAGE", "RECORD NOT FOUND");
				map.put("STATUS", false);
			}else{
				map.put("MESSAGE", "RECORD FOUND");
				map.put("STATUS", true);
				map.put("RES_DATA", question);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}

	@RequestMapping(value="/listquestion/c/{cid}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> listQuestionByCategoryId(
			  @PathVariable("cid") int cateid,
			  @RequestParam(value = "page", required = false , defaultValue="1") int page ,
			  @RequestParam(value="item" , required = false , defaultValue="20") int item  ){
		Map<String , Object> map = new HashMap<String , Object>();
		Pagination pagination = new Pagination();
		pagination.setItem(item);
		pagination.setPage(page);
		pagination.setTotalCount(forumCommentService.countQuestionByCategoryId(cateid));
		pagination.setTotalPages(pagination.totalPages());
		try{
			List<ForumComment> question = forumCommentService.listQuestionByCategoryId(cateid, pagination);
			if(question == null){
				map.put("MESSAGE", "RECORD NOT FOUND");
				map.put("STATUS", false);
			}else{
				map.put("MESSAGE", "RECORD FOUND");
				map.put("STATUS", true);
				map.put("RES_DATA", question);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/listquestion/search" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> searchQuestionByTitle(
			  @RequestParam(value = "keyword", required = false , defaultValue="") String keyword ,
			  @RequestParam(value = "page", required = false , defaultValue="1") int page ,
			  @RequestParam(value="item" , required = false , defaultValue="20") int item  ){
		Map<String , Object> map = new HashMap<String , Object>();
		Pagination pagination = new Pagination();
		pagination.setItem(item);
		pagination.setPage(page);
		pagination.setTotalCount(forumCommentService.countQuestionByTitle(keyword));
		pagination.setTotalPages(pagination.totalPages());
		try{
			List<ForumComment> question = forumCommentService.listQuestionByTitle(keyword, pagination);
			if(question == null){
				map.put("MESSAGE", "RECORD NOT FOUND");
				map.put("STATUS", false);
			}else{
				map.put("MESSAGE", "RECORD FOUND");
				map.put("STATUS", true);
				map.put("RES_DATA", question);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
}
