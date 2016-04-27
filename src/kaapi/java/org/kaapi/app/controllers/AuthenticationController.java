package org.kaapi.app.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.kaapi.app.entities.User;
import org.kaapi.app.forms.FrmAddUser;
import org.kaapi.app.forms.FrmLoginWithSC;
import org.kaapi.app.forms.FrmMobileLogin;
import org.kaapi.app.forms.FrmValidateEmail;
import org.kaapi.app.forms.FrmWebLogin;
import org.kaapi.app.services.UserService;
import org.kaapi.app.utilities.SendMailTLS;
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
		Map<String, Object> userMap =  new HashMap<>();
		try{
			User u = userService.mobileLogin(mobileLoginFrm);
			if(u != null){
				
				userMap.put("userId", u.getUserId());
				userMap.put("username" , u.getUsername());
				userMap.put("email", u.getEmail());
				userMap.put("gender", u.getGender());
				userMap.put("userImageUrl", u.getUserImageUrl());
				userMap.put("coverphoto", u.getCoverphoto());
				userMap.put("scFacebookId", u.getScFacebookId());
				userMap.put("scTwitterId", u.getScTwitterId());
				userMap.put("scGmailId",u.getScGmailId());
				userMap.put("scType", u.getScType());
				userMap.put("originalID", u.getOriginalID());
				userMap.put("isConfirmed", u.isConfirmed());
				map.put("MESSAGE", "Logined success");
				map.put("STATUS", true);
				map.put("USER", userMap);
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
	
	@RequestMapping(value="/login_with_fb" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> loginWithFB(
			@RequestBody FrmAddUser s
		){
		Map<String, Object> map = new HashMap<String , Object>();
		if(s.getEmail() == null){
			map.put("MESSAGE", "Email is required!.");
			map.put("STATUS", false);
			return new ResponseEntity<Map<String , Object>>(map , HttpStatus.OK);
		}
		try{
//			System.out.println(s.getEmail());
//			if(s.getEmail() == null){
//				s.setEmail(s.getScID());
//			}
			String password = UUID.randomUUID()+"";
			s.setPassword(password);
			System.out.println(s.getPassword());
			
			FrmWebLogin wFrm = new FrmWebLogin();
			wFrm.setEmail(s.getEmail());
			FrmValidateEmail v = new FrmValidateEmail();
			v.setEmail(wFrm.getEmail());
			if(userService.validateEmail(v)){
				User u = userService.webLogin(wFrm);
				if(u != null){
					map.put("MESSAGE", "Logined success!");
					map.put("STATUS", true);
					map.put("USER", u);
				}else{
					map.put("MESSAGE", "Logined unsuccess! Invalid email!");
					map.put("STATUS", false);
				}
			}else{
				if(userService.checkSocialID(s.getScType(), s.getScID())){
					User u = userService.webLogin(wFrm);
					if(u != null){
						map.put("MESSAGE", "Logined success!");
						map.put("STATUS", true);
						map.put("USER", u);
					}else{
						map.put("MESSAGE", "Logined unsuccess! Invalid email!");
						map.put("STATUS", false);
					}
				}else{
					map.put("MESSAGE", "Let signup with fb process!");
					map.put("STATUS", true);
					
					if(userService.insertUserSC(s)){
					   
						new SendMailTLS().sendMaile(s.getEmail(),"fbSignUp", "<h4>You recently registered for Khmer Academy with your facebook account.</h4> <p>If you want to login without facebook account you can use this email and password : <p> <p> Email : "+s.getEmail()+" </p><p> Password : "+password+" </p> ");
				
						FrmWebLogin w = new FrmWebLogin(); 
						w.setEmail(s.getEmail());
						User u = userService.webLogin(w);
						if(u != null){
							map.put("MESSAGE", "User has been inserted. Logined success and logged success!");
							map.put("STATUS", true);
							map.put("USER", u);
						}else{
							map.put("MESSAGE", "Logined unsuccess! Invalid email, but User has been inserted!");
							map.put("STATUS", false);
						}
					}else{
						map.put("MESSAGE", "User has not been inserted.");
						map.put("STATUS", false);
					}	
				}
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.OK);
	}
	
}
