package org.kaapi.app.controllers;

import java.util.HashMap;
import java.util.Map;

import org.kaapi.app.entities.User;
import org.kaapi.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/authentication")
public class AuthenticationController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/mobilelogin" , method = RequestMethod.POST)
	public ResponseEntity<Map<String , Object>> mobileLogin(
			@RequestParam("email") String email ,
			@RequestParam("password") String password ){
		Map<String, Object> map = new HashMap<String , Object>();
		try{
			User u = userService.mobileLogin(email, password);
			if(u == null){
				map.put("MESSAGE", "Logined success");
				map.put("STATUS", true);
			}else{
				
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return null;
	}
	
	
	
}
