package org.kaapi.app.controllers.forum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.ForumComment;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.forms.FrmAddAnswer;
import org.kaapi.app.forms.FrmAddQuestion;
import org.kaapi.app.forms.FrmUpdateAnswer;
import org.kaapi.app.forms.FrmUpdateQuestion;
import org.kaapi.app.services.ForumCategoryService;
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
	
	@Autowired
	ForumCategoryService forumCategoryService;
	
	@RequestMapping(value="/addanswer" , method=RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<Map<String,Object>> addAnswer(@RequestBody FrmAddAnswer addAnswer){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			if(forumCommentService.insertAnswer(addAnswer)){
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
	
	@RequestMapping(value="/updateanswer" , method=RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseEntity<Map<String,Object>> updateAnswer(@RequestBody FrmUpdateAnswer answer){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			if(forumCommentService.updateAnswer(answer)){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD HAS BEEN UPDATED");
			}
			else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD HAS NOT BEEN UPDATED");
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/deleteanswer/{answerid}" , method=RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseEntity<Map<String,Object>> deleteAnswer(@PathVariable("answerid") String answerId){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			if(forumCommentService.deleteAnswer(answerId)){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD HAS BEEN DELETED");
			}
			else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD HAS NOT BEEN DELETED");
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/addquestion" , method=RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<Map<String,Object>> addQuestion(@RequestBody FrmAddQuestion addQuestion){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			if(forumCommentService.insetQuestion(addQuestion)){
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
	
	@RequestMapping(value="/updatequestion" , method=RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseEntity<Map<String,Object>> updateQuestion(@RequestBody FrmUpdateQuestion question){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			if(forumCommentService.updateQuestion(question)){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD HAS BEEN UPDATED");
			}
			else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD HAS NOT BEEN UPDATED");
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/deletequestion/{questionid}" , method=RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseEntity<Map<String,Object>> deleteQuestion(@PathVariable("questionid") String questionid){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			if(forumCommentService.deleteQuestion(questionid)){
				map.put("STATUS", true);
				map.put("MESSAGE", "RECORD HAS BEEN DELETED");
			}
			else{
				map.put("STATUS", false);
				map.put("MESSAGE", "RECORD HAS NOT BEEN DELETED");
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
	
	@RequestMapping(value="/getquestionlistanswer/{qid}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> getQuestionByIdListAnswerByQuestionId(
			  @PathVariable("qid") String qid 
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
			  @PathVariable("uid") String userid,
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
			e.printStackTrace();
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}

	@RequestMapping(value="/listquestion/c/{cid}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> listQuestionByCategoryId(
			  @PathVariable("cid") String cateid,
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
	
	@RequestMapping(value="/listquestion/t/{tag}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> listQuestionByTag(
			  @PathVariable("tag") String tag,
			  @RequestParam(value = "page", required = false , defaultValue="1") int page ,
			  @RequestParam(value="item" , required = false , defaultValue="20") int item  ){
		Map<String , Object> map = new HashMap<String , Object>();
		Pagination pagination = new Pagination();
		pagination.setItem(item);
		pagination.setPage(page);
		pagination.setTotalCount(forumCommentService.countQuestionByTag(tag));
		pagination.setTotalPages(pagination.totalPages());
		try{
			List<ForumComment> question = forumCommentService.listQuestionByTag(tag, pagination);
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
	
	@RequestMapping(value="/totalquestion" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> totalQuestion(){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			map.put("STATUS", true);
			map.put("TOTAL_QUESTION", forumCommentService.countQuestion());
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/totalanswer" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> totalAnswer(){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			map.put("STATUS", true);
			map.put("TOTAL_ANSWER", forumCommentService.countAnswer());
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getquestionbyquestionid/{qid}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> getQuestionByQuestionId(
			  @PathVariable("qid") String qid ){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			ForumComment question = forumCommentService.getQuestionById(qid);
			if(question == null){
				map.put("MESSAGE", "RECORD NOT FOUND");
				map.put("STATUS", false);
			}else{
				map.put("MESSAGE", "RECORD FOUND");
				map.put("STATUS", true);
				map.put("RESP_DATA", question);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/listanswerbyquestionid/{qid}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> listAnswerByQuestionId(
			  @PathVariable("qid") String qid 
			, @RequestParam(value = "page", required = false , defaultValue="1") int page 
			, @RequestParam(value="item" , required = false , defaultValue="20") int item  ){
		Map<String , Object> map = new HashMap<String , Object>();
		Pagination pagination = new Pagination();
		pagination.setItem(item);
		pagination.setPage(page);
		pagination.setTotalCount(forumCommentService.countAnswerByQuestionId(qid));
		pagination.setTotalPages(pagination.totalPages());
		try{
			List<ForumComment> listAnswer = forumCommentService.listAnswerByQuestionId(qid, pagination);
			if(listAnswer == null){
				map.put("MESSAGE", "RECORD NOT FOUND");
				map.put("STATUS", false);
			}else{
				map.put("MESSAGE", "RECORD FOUND");
				map.put("STATUS", true);
				map.put("RESP_DATA", listAnswer);
				map.put("PAGINATION", pagination);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/getselectedanswerbyquestionid/{qid}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> getSelectedAnswerByQuestionId(
			  @PathVariable("qid") String qid ){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			ForumComment selectedAnswer = forumCommentService.getSelectedAnswerByQuestionId(qid);
			if(selectedAnswer == null){
				map.put("MESSAGE", "RECORD NOT FOUND");
				map.put("STATUS", false);
			}else{
				map.put("MESSAGE", "RECORD FOUND");
				map.put("STATUS", true);
				map.put("RESP_DATA", selectedAnswer);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/listtagandcategory" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> getTagAndCategory(){
		Map<String , Object> map = new HashMap<String , Object>();
		Pagination pagination = new Pagination();
		pagination.setItem(1000);
		pagination.setPage(1);
		pagination.setTotalCount(forumCategoryService.countForumCate());
		pagination.setTotalPages(pagination.totalPages());
		try{
		    map.put("MESSAGE", "RECORD FOUND");
			map.put("STATUS", true);
			map.put("TAGS", forumCommentService.getAllTags());
			map.put("CATEGORY", forumCategoryService.listForumCate(pagination));
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
}
