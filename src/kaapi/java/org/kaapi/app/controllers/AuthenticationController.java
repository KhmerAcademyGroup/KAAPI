package org.kaapi.app.controllers;

import java.util.Map;

import org.kaapi.app.entities.User;
import org.kaapi.app.services.UserService;
import org.kaapi.app.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class AuthenticationController {

	@Autowired 
	@Qualifier("header")
	private String header;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/login" , method = RequestMethod.GET)
	public String loginPage(ModelMap m){
		m.addAttribute("msg","Login");
		return "login";
	}
	
	@RequestMapping(value="/register" , method = RequestMethod.GET)
	public String registerPage(ModelMap m){
		m.addAttribute("msg","Register");
		m.addAttribute("kaapi" , header);
		return "register";
	}
	
	/*@RequestMapping(value="/mobile_login" , method = RequestMethod.POST)
	public ResponseEntity<Map<String , Object>> mobileLogin(){
		User u  = userService.mobileLogin("tolapheng199@gmail.com", "123456");
		System.out.println(u.getUserId());
		return null;
	}*/
	
}
