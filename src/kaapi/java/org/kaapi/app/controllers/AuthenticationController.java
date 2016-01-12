package org.kaapi.app.controllers;

import java.util.HashMap;
import java.util.Map;

import org.kaapi.app.entities.User;
import org.kaapi.app.forms.FrmMobileLogin;
import org.kaapi.app.forms.FrmWebLogin;
import org.kaapi.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/mobilelogin" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> mobileLogin(
			@RequestBody FrmMobileLogin mobileLoginFrm
		){
		Map<String, Object> map = new HashMap<String , Object>();
		try{
			User u = userService.mobileLogin(mobileLoginFrm);
			if(u != null){
				map.put("MESSAGE", "Logined success");
				map.put("STATUS", true);
				map.put("USERID", u.getUserId());
				map.put("USERNAME" , u.getUsername());
				map.put("EMAIL", u.getEmail());
				map.put("PROFILE_IMG_URL", u.getUserImageUrl());
				map.put("COVER_IMG_URL", u.getCoverphoto());
			}else{
				map.put("MESSAGE", "Logined unsuccess! Invalid email or password!");
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
			@RequestBody FrmWebLogin wFrm
		){
		Map<String, Object> map = new HashMap<String , Object>();
		try{
			User u = userService.webLogin(wFrm);
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
