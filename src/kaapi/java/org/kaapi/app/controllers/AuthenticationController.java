package org.kaapi.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	
	@RequestMapping(value={"/403" , "/accessDenied"})
	public String error403(ModelMap m){
		m.addAttribute("msg","Error 403 | Access Denied Page");
		return "errors/403";
	}
}
