package org.kaapi.app.controllers;

import org.kaapi.app.entities.APIUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired 
	@Qualifier("header")
	private String header;
	
	@RequestMapping(value="/" , method = RequestMethod.GET)
	public String mainPage(ModelMap m){
		m.addAttribute("msg","Main Page");
		return "home";
	}
	
	@RequestMapping(value={"/admin"} , method = RequestMethod.GET)
	public String adminPage(ModelMap m){
		m.addAttribute("msg","ADMIN | ADMIN PAGE "  + header);
		m.addAttribute("kaapi" , header);
		
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		APIUser user = (APIUser) authentication.getPrincipal();
		System.out.println(user.getUsername()+" | "+user.getId() + " | " + user.getRoles().get(0).getName() );
	
		return "admin/admin";
	}
	
	@RequestMapping(value={"/kaapi",} , method = RequestMethod.GET)
	public String kaAPIPage(ModelMap m){
		m.addAttribute("msg","API PAGE");
		return "kaapi/kaapi";
	}
	
	
}
