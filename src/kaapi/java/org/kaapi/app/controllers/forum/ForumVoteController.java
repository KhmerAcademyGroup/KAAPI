package org.kaapi.app.controllers.forum;

import java.util.HashMap;
import java.util.Map;

import org.kaapi.app.forms.FrmSelectAnswer;
import org.kaapi.app.forms.FrmVote;
import org.kaapi.app.services.ForumVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity<Map<String,Object>> likeQuestion(@RequestBody FrmVote vote){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			int total_vote = voteService.votePlus(vote);
			map.put("STATUS",true);
			map.put("TOTAL_VOTE", total_vote);
			map.put("MESSAGE", "QUESTION WAS LIKED");
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/unlikequestion", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Map<String,Object>> unLikeQuestion(@RequestBody FrmVote vote){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			int total_vote = voteService.voteMinus(vote);
			map.put("STATUS",true);
			map.put("TOTAL_VOTE", total_vote);
			map.put("MESSAGE", "QUESTION WAS LIKED");
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/likeanswer", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Map<String,Object>> likeAnswer(@RequestBody FrmVote vote){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			int total_vote = voteService.votePlus(vote);
			map.put("STATUS",true);
			map.put("TOTAL_VOTE", total_vote);
			map.put("MESSAGE", "ANSWER WAS LIKED");
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/unlikeanswer", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Map<String,Object>> unLikeAnswer(@RequestBody FrmVote vote){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			int total_vote = voteService.voteMinus(vote);
			map.put("STATUS",true);
			map.put("MESSAGE", "ANSWER WAS UNLIKED");
			map.put("TOTAL_VOTE", total_vote);
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/selectanswer", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Map<String,Object>> selectAnswer(@RequestBody FrmSelectAnswer selectAnswer){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			if(voteService.selectAnswer(selectAnswer)){
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
