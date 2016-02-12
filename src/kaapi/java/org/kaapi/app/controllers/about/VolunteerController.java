package org.kaapi.app.controllers.about;

import java.util.HashMap;
import java.util.Map;

import org.kaapi.app.forms.FrmAddVolunteer;
import org.kaapi.app.services.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("api/about/volunteer")
public class VolunteerController {

	@Autowired
	VolunteerService volunteer;
	
	@RequestMapping(method=RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<Map<String,Object>> addAnswer(@RequestBody FrmAddVolunteer v){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			if(volunteer.addVolunteer(v)){
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
