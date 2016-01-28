package org.kaapi.app.controllers.participants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.forms.FrmParticipants;
import org.kaapi.app.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mangofactory.swagger.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/participants")
public class ParticipantsRestCtr {
	
	@Autowired
	ParticipantService participants;

	@RequestMapping(value="/list" , method = RequestMethod.GET ,headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> listAll(){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			List<FrmParticipants> list = participants.listAll();
			if(list == null){
				map.put("MESSAGE", "RECORD NOT FOUND");
				map.put("STATUS", false);
			}else{
				map.put("MESSAGE", "RECORD FOUND");
				map.put("STATUS", true);
				map.put("RESP_DATA",list);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/add" ,  method = RequestMethod.POST ,headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> addForumCate(@RequestBody FrmParticipants part){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			if(participants.add(part)){
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

}
