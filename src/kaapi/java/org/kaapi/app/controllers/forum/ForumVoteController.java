package org.kaapi.app.controllers.forum;

import java.util.HashMap;
import java.util.Map;

import org.kaapi.app.services.ForumVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/forum/vote")
public class ForumVoteController {

	@Autowired
	ForumVoteService voteService;
	
	@RequestMapping(value="/likequestion" , method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Map<String,Object>> likeQuestion(
			@RequestParam("userid") int userId , 
			@RequestParam("questionid") int questionId){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			int vote = voteService.votePlus(userId, questionId);
			map.put("STATUS",true);
			map.put("LIKE", vote);
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/unlikequestion", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Map<String,Object>> unLikeQuestion(
			@RequestParam("userid") int userId , 
			@RequestParam("questionid") int questionId){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			int vote = voteService.voteMinus(userId, questionId);
			map.put("STATUS",true);
			map.put("UNLIKE", vote);
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/likeanswer", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Map<String,Object>> likeAnswer(
			@RequestParam("userid") int userId , 
			@RequestParam("answerid") int answerId){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			int vote = voteService.votePlus(userId, answerId);
			map.put("STATUS",true);
			map.put("UNLIKE", vote);
			map.put("MESSAGE", "ANSWER WAS LIKED");
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/unlikeanswer", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Map<String,Object>> unLikeAnswer(
			@RequestParam("userid") int userId , 
			@RequestParam("answerid") int answerId){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			int vote = voteService.voteMinus(userId, answerId);
			map.put("STATUS",true);
			map.put("MESSAGE", "ANSWER WAS UNLIKED");
			map.put("UNLIKE", vote);
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/selectanswer", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Map<String,Object>> selectAnswer(
			@RequestParam("question") int questionId , 
			@RequestParam("answerid") int answerId){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			if(voteService.selectAnswer(answerId,questionId)){
				map.put("STATUS",true);
				map.put("MESSAGE", "ANSWER WAS SELECTED");
		    }else{
		    	map.put("STATUS", false);
		    	map.put("MESSAGE", "ANSWER WAS NOT SELECTED");
		    }
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map, HttpStatus.OK);
	}
	
}
