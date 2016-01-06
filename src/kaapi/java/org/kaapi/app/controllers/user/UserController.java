package org.kaapi.app.controllers.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.User;
import org.kaapi.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController	
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value="/" , method = RequestMethod.GET , headers = "accept=Application/json")
	public ResponseEntity<Map<String , Object>> listUser(
			 @RequestParam(value = "page", required = false , defaultValue="1") int page 
		   , @RequestParam(value="item" , required = false , defaultValue="20") int item){
		Map<String , Object> map = new HashMap<String , Object> ();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(userService.countUser());
			pagination.setTotalPages(pagination.totalPages());
			List<User> list = userService.listUser(pagination);
			if(list != null){
				map.put("MESSAGE", "RECORD FOUND");
				map.put("STATUS", true);
				map.put("RES_DATA",list);
				map.put("PAGINATION", pagination);
			}else{
				map.put("MESSAGE", "RECORD NOT FOUND");
				map.put("STATUS", false);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/search" , method = RequestMethod.GET , headers = "accept=Application/json")
	public ResponseEntity<Map<String , Object>> searchUserByUsername(
			 @RequestParam(value="username" , required = false , defaultValue="" ) String username
		   , @RequestParam(value = "page", required = false , defaultValue="1") int page 
		   , @RequestParam(value="item" , required = false , defaultValue="20") int item){
		Map<String , Object> map = new HashMap<String , Object> ();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(userService.countSearchUserByUsername(username));
			pagination.setTotalPages(pagination.totalPages());
			List<User> list = userService.searchUserByUsername(username, pagination);
			if(list != null){
				map.put("MESSAGE", "RECORD FOUND");
				map.put("STATUS", true);
				map.put("RES_DATA",list);
				map.put("PAGINATION", pagination);
			}else{
				map.put("MESSAGE", "RECORD NOT FOUND");
				map.put("STATUS", false);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/{uid}" , method = RequestMethod.GET , headers = "accept=Application/json")
	public ResponseEntity<Map<String , Object>> getUserById(
			 @PathVariable("uid") String userid){
		Map<String , Object> map = new HashMap<String , Object> ();
		try{
			User u = userService.getUSerById(userid);
			if(u != null){
				map.put("MESSAGE", "RECORD FOUND");
				map.put("STATUS", true);
				map.put("RES_DATA",u);
			}else{
				map.put("MESSAGE", "RECORD NOT FOUND");
				map.put("STATUS", false);
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/validateemail" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<Map<String,Object>> validateEmail(@RequestBody User user){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			System.out.println(user.getEmail());
			/*if(userService.validateEmail(email.get())){
				map.put("MESSAGE", "Email already exists.");
				map.put("EMAIL", email);
				map.put("STATUS", true);
			}else{
				map.put("MESSAGE", "Email doesn't exist.");
				map.put("EMAIL", email);
				map.put("STATUS", false);
			}*/
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.OK);	
	}
}
