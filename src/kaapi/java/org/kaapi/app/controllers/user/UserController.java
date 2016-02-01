
package org.kaapi.app.controllers.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.User;
import org.kaapi.app.forms.FrmAddUpdateCoverPhoto;
import org.kaapi.app.forms.FrmAddUser;
import org.kaapi.app.forms.FrmChangePassword;
import org.kaapi.app.forms.FrmMobileRegister;
import org.kaapi.app.forms.FrmResetPassword;
import org.kaapi.app.forms.FrmUpdateUser;
import org.kaapi.app.forms.FrmValidateEmail;
import org.kaapi.app.services.UserService;
import org.kaapi.app.utilities.Encryption;
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
	public ResponseEntity<Map<String,Object>> validateEmail(@RequestBody FrmValidateEmail email){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			if(userService.validateEmail(email)){
				map.put("MESSAGE", "Email already exists.");
				map.put("EMAIL", email.getEmail());
				map.put("STATUS", true);
			}else{
				map.put("MESSAGE", "Email doesn't exist.");
				map.put("EMAIL", email.getEmail());
				map.put("STATUS", false);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.OK);	
	}
	
	@RequestMapping(method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> addUser(@RequestBody FrmAddUser user){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			FrmValidateEmail email = new FrmValidateEmail();
			email.setEmail(user.getEmail());
			if(userService.validateEmail(email)){
				map.put("MESSAGE", "Email already exists.");
				map.put("EMAIL", email.getEmail());
				map.put("STATUS", false);
			}else{
				if(userService.insertUser(user)){
					map.put("MESSAGE", "User has been inserted.");
					map.put("STATUS", true);
				}else{
					map.put("MESSAGE", "User has not been inserted.");
					map.put("STATUS", false);
				}
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.OK);	
	}
	
	@RequestMapping(value="mobileuserregister" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> mobileAddUser(@RequestBody FrmMobileRegister user){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			FrmValidateEmail email = new FrmValidateEmail();
			email.setEmail(user.getEmail());
			if(userService.validateEmail(email)){
				map.put("MESSAGE", "Email already exists.");
				map.put("EMAIL", email.getEmail());
				map.put("STATUS", false);
			}else{
				if(userService.mobileInsertUser(user)){
					map.put("MESSAGE", "User has been inserted.");
					map.put("STATUS", true);
				}else{
					map.put("MESSAGE", "User has not been inserted.");
					map.put("STATUS", false);
				}
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.OK);	
	}
	
	@RequestMapping( method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> updateUser(@RequestBody FrmUpdateUser user){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			User currentUser = userService.getUSerById(user.getUserId());
			if(currentUser == null){
				map.put("MESSAGE", "RECORD NOT FOUND");
				map.put("STATUS", false);
				return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
			}
			if(userService.updateUser(user)){
				map.put("MESSAGE", "User has been updated.");
				map.put("STATUS", true);
		    }else{
				map.put("MESSAGE", "User has not been updated.");
				map.put("STATUS", false);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.OK);	
	}
	
	@RequestMapping(value="/{uid}",method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> deleteUser (@PathVariable("uid") String id){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			User currentUser = userService.getUSerById(id);
			if(currentUser == null){
				map.put("MESSAGE", "RECORD NOT FOUND");
				map.put("STATUS", false);
				return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
			}
			if(userService.deleteUser(id)){
				map.put("MESSAGE", "User has been deleted.");
				map.put("STATUS",true);
			}else{
				map.put("MESSAGE", "User has not been deleted.");
				map.put("STATUS",false);
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>> (map , HttpStatus.OK);
	}
	
	@RequestMapping(value="/addcoverphoto",method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> addCoverPhoto(@RequestBody FrmAddUpdateCoverPhoto coverPhoto){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			if(userService.insertCoverPhoto(coverPhoto)){
				map.put("MESSAGE", "Cover photo has been added.");
				map.put("STATUS", true);
			}else{
				map.put("MESSAGE", "Cover photo has not been added.");
				map.put("STATUS", false);
			}
			
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.OK);	
	}
	
	@RequestMapping(value="/updatecoverphoto",method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> updateCoverPhoto(@RequestBody FrmAddUpdateCoverPhoto coverPhoto){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			if(userService.updateCoverPhoto(coverPhoto)){
				map.put("MESSAGE", "Cover photo has been updated.");
				map.put("STATUS", true);
			}else{
				map.put("MESSAGE", "Cover photo has not been updated.");
				map.put("STATUS", false);
			}
			
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.OK);	
	}
	
	@RequestMapping(value="/resetpassword" ,method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> resetPassword(@RequestBody FrmResetPassword resetPassword){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			if(userService.resetPassword(resetPassword)){
				map.put("MESSAGE", "Password has been reseted.");
				map.put("STATUS", true);
			}else{
				map.put("MESSAGE", "Password has not been reseted.");
				map.put("STATUS", false);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.OK);	
	}
	
	@RequestMapping(value="/changepassword" ,method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> changePassword(@RequestBody FrmChangePassword changePassword){
		Map<String , Object> map = new HashMap<String , Object>();
		System.out.println("Changed");
		try{
			System.out.println(changePassword.getNewPassword());
			System.out.println(changePassword.getOldPassword());
			System.out.println(Encryption.decode(changePassword.getUserId()));
			if(userService.changePassword(changePassword)){
				map.put("MESSAGE", "Password has been changed.");
				map.put("STATUS", true);
			}else{
				map.put("MESSAGE", "Password has not been changed.");
				map.put("STATUS", false);
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.OK);	
	}
	
	@RequestMapping(value="/updatetype",method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> updateType(@RequestParam("userid") String uid,
			@RequestParam("usertype")String utype){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			if(userService.updateType(uid, utype)){
				map.put("MESSAGE", "User type has been updated.");
				map.put("STATUS", true);
			}else{
				map.put("MESSAGE", "User type has not been updated.");
				map.put("STATUS", false);
			}
			
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.OK);	
	}
	
}
