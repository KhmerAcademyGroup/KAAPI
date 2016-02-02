package org.kaapi.app.controllers.elearning;

import java.util.HashMap;
import java.util.Map;

import org.kaapi.app.entities.Video;
import org.kaapi.app.services.VoteService;
import org.kaapi.app.utilities.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/elearning")
public class VoteController {
	
	@Autowired private VoteService voteService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/vote/u/{userid}/v/{videoid}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getVote(
			@PathVariable("userid") String userid,
			@PathVariable("videoid") String videoid) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			map.put("STATUS", true);
			map.put("CHECKVOTE", voteService.checkVote(videoid, userid));
			map.put("COUNTVOTE", voteService.countVote(videoid));
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/vote/u/{userid}/v/{videoid}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> insertVote(
			@PathVariable("userid") String userid,
			@PathVariable("videoid") String videoid) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (voteService.vote(videoid, userid)) {
			map.put("STATUS", true);
			map.put("MESSAGE", "OPERATION SUCCESS");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}else{
			map.put("STATUS", false);
			map.put("MESSAGE", "OPERATION FAIL");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/vote/u/{userid}/v/{videoid}", headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> unVote(
			@PathVariable("userid") String userid,
			@PathVariable("videoid") String videoid) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (voteService.unvote(videoid, userid)) {
			map.put("STATUS", true);
			map.put("MESSAGE", "OPERATION SUCCESS");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}else{
			map.put("STATUS", false);
			map.put("MESSAGE", "OPERATION FAIL");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		
	}

}
