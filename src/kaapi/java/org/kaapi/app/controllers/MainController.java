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

import com.mangofactory.swagger.annotations.ApiIgnore;

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
		
//		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
//		APIUser user = (APIUser) authentication.getPrincipal();
//		System.out.println(user.getUsername()+" | "+user.getId() + " | " + user.getRoles().get(0).getName() );
	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		APIUser user = (APIUser)authentication.getPrincipal();
		System.out.print("____________adminID " +user.getId());
		System.out.print("____________adminID " +user.getUsername());
		
		
		return "admin/admin";
	}
	
	@ApiIgnore
	@RequestMapping(value={"/wsapi",} , method = RequestMethod.GET)
	public String kaAPIPage(ModelMap m){
		m.addAttribute("msg","API PAGE");
		m.addAttribute("kaapi" , header);
		return "kaapi/index";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/help")
	public String help(ModelMap m) {			
		m.addAttribute("msg","Swagger Page");
		return "help";
	}
	
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
	
	@RequestMapping(value="/participants" , method = RequestMethod.GET)
	public String paticipants(ModelMap m){
		m.addAttribute("msg","participants");
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		APIUser user = (APIUser)authentication.getPrincipal();
//		System.out.print("____________adminID " +user.getId());
//		System.out.print("____________adminID " +user.getUsername());
//		m.addAttribute("username",user.getUsername());
		m.addAttribute("kaapi" , header);
		return "participants/participants";
	}
}
