package org.kaapi.app.controllers;

import java.util.HashMap;
import java.util.Map;

import org.kaapi.app.entities.User;
import org.kaapi.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api/authentication")
public class AuthenticationController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/mobilelogin" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> mobileLogin(
			@RequestBody User user
		){
		Map<String, Object> map = new HashMap<String , Object>();
		try{
			User u = userService.mobileLogin(user.getEmail(), user.getPassword());
			if(u != null){
				map.put("MESSAGE", "Logined success");
				map.put("STATUS", true);
				map.put("USERID", u.getUserId());
				map.put("USERNAME" , u.getUsername());
				map.put("EMAIL", u.getEmail());
				map.put("PROFILE_IMG_URL", u.getUserImageUrl());
				map.put("COVER_IMG_URL", u.getCoverphoto());
			}else{
				map.put("MESSAGE", "Logined unsuccess! Invalid username or password!");
				map.put("STATUS", false);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/weblogin" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> webLogin(
			@RequestBody User user
		){
		Map<String, Object> map = new HashMap<String , Object>();
		try{
			User u = userService.webLogin(user.getEmail());
			if(u != null){
				map.put("MESSAGE", "Logined success!");
				map.put("STATUS", true);
				map.put("USER", u);
			}else{
				map.put("MESSAGE", "Logined unsuccess! Invalid email!");
				map.put("STATUS", false);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.OK);
	}
	
}
