
package org.kaapi.app.controllers.user;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.kaapi.app.entities.Department;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.University;
import org.kaapi.app.entities.User;
import org.kaapi.app.forms.FrmAddUpdateCoverPhoto;
import org.kaapi.app.forms.FrmAddUser;
import org.kaapi.app.forms.FrmChangePassword;
import org.kaapi.app.forms.FrmMobileRegister;
import org.kaapi.app.forms.FrmResetPassword;
import org.kaapi.app.forms.FrmUpdateUser;
import org.kaapi.app.forms.FrmValidateEmail;
import org.kaapi.app.forms.FrmWebLogin;
import org.kaapi.app.forms.accountSetting;
import org.kaapi.app.services.DepartmentService;
import org.kaapi.app.services.UniversityService;
import org.kaapi.app.services.UserService;
import org.kaapi.app.utilities.Encryption;
import org.kaapi.app.utilities.SendMailTLS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@PropertySource(
		value={"classpath:applications.properties"}
)
@RestController	
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private Environment environment;
	
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
	public ResponseEntity<Map<String , Object>> mobileAddUser(@RequestBody FrmMobileRegister user, HttpServletRequest request){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
			FrmValidateEmail email = new FrmValidateEmail();
			email.setEmail(user.getEmail());
			if(userService.validateEmail(email)){
				map.put("MESSAGE", "Email already exists.");
				map.put("EMAIL", email.getEmail());
				map.put("STATUS", false);
			}else{
				
				String userImageUrl = (user.getUserImageUrl() == null)
						? request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
								+ request.getContextPath() + "/resources/upload/file/user/avatar.jpg"
						: user.getUserImageUrl();
				user.setUserImageUrl(userImageUrl);
				
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
			e.printStackTrace();
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
	
	/*@RequestMapping(value="/resetpassword" ,method = RequestMethod.POST , headers = "Accept=application/json")
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
	}*/
	
	@RequestMapping(value="/changepassword" ,method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> changePassword(@RequestBody FrmChangePassword changePassword){
		Map<String , Object> map = new HashMap<String , Object>();
//		System.out.println("Changed");
		try{
//			System.out.println(changePassword.getNewPassword());
//			System.out.println(changePassword.getOldPassword());
//			System.out.println(Encryption.decode(changePassword.getUserId()));
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
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	UniversityService universityService;

	
	@RequestMapping(value="/listuniversity_department" , method = RequestMethod.GET , headers = "accept=Application/json")
	public ResponseEntity<Map<String , Object>> listDepartmentAndUniversity(
			 @RequestParam(value = "page", required = false , defaultValue="1") int page 
		   , @RequestParam(value="item" , required = false , defaultValue="1000") int item){
		Map<String , Object> map = new HashMap<String , Object> ();
		try{
			Pagination pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(departmentService.countDepartment(""));
			pagination.setTotalPages(pagination.totalPages());
			List<Department> listDepartment = departmentService.listDepartment(pagination, "");
			
			pagination = new Pagination();
			pagination.setItem(item);
			pagination.setPage(page);
			pagination.setTotalCount(universityService.countUniversity());
			pagination.setTotalPages(pagination.totalPages());
			List<University> listUniversity = universityService.findAllUniverstiyByName(pagination, "");
			map.put("MESSAGE", "RECORD FOUND");
			map.put("STATUS", true);
			map.put("DEPARTMENT",listDepartment);
			map.put("UNIVERSITY",listUniversity);
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/sendmail",method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> getUserByEmail(
			@RequestParam("email") String email,
			@RequestParam("type") String type			
			){		
		Map<String , Object> map = new HashMap<String , Object> ();				
		try{
//			System.out.println("type: "+type);
			User u = userService.getUSerEmail(email);
//			System.out.println("Email: "+u.getEmail());
			if(u != null){
					SecureRandom random = new SecureRandom();
				    byte bytes[] = new byte[20];
				    random.nextBytes(bytes);
				    String token = bytes.toString();	
				    userService.insertHistoryResetPassWord(token,u.getEmail(),type);
				    if(type.equals("reset")){
				    	new SendMailTLS().sendMaile(email,type, "<h4>We have just received a password reset request for "+u.getEmail()+" </h4> <h4> Please click <a href='"+environment.getProperty("KA.UI_PATH")+"/reset?code="+token+"'>here</a> to reset your password.  </h4> "
				    			+ "<h4>If the link does not work for you, please copy and paste the following into your browser address bar:</h4> "+environment.getProperty("KA.UI_PATH")+"/reset?code="+token);
				    }
				    else{
				    	new SendMailTLS().sendMaile(email,type, "<h1>Welcome to Khmer Academy</h1> <h4>Please click the link below to verify your Khmer Acadmey account.</h4> <br/> "+environment.getProperty("KA.UI_PATH")+"/confirmemail?code="+token);
				    }
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
	
	@RequestMapping(value="/resetpassword" ,method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> sarinResetpassword(@RequestParam("code") String code ,@RequestParam("password") String password){
		Map<String , Object> map = new HashMap<String , Object>();		
		try{
			accountSetting setting=userService.getHistoryAccountSetting(code);
			
			if(setting.isStatus()==true){

				FrmResetPassword resetPassword = new FrmResetPassword();
				resetPassword.setEmail(setting.getEmail());
				resetPassword.setNewPassword(password);
				userService.updateHistoryResetPassword(code);
				if(userService.resetPassword(resetPassword)){
					map.put("MESSAGE", "Password has been reseted.");
					map.put("STATUS", true);
				}else{
					map.put("MESSAGE", "Password has not been reseted.");
					map.put("STATUS", false);
				}
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
	
	@RequestMapping(value="/confirm" ,method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> confirmEmail(@RequestParam("code") String code){
		Map<String , Object> map = new HashMap<String , Object>();		
		try{
			accountSetting resetPass=userService.getHistoryAccountSetting(code);
			
			if(resetPass.isStatus()==true){
				userService.updateHistoryResetPassword(code);
				if(userService.confirmEmail(resetPass.getEmail())){
					map.put("MESSAGE", "Confirm Success !");
					map.put("STATUS", true);
				}else{
					map.put("MESSAGE", "Confirm unsuccess !");
					map.put("STATUS", false);
				}
			}else{
				map.put("MESSAGE", "Confirm unsuccess !");
				map.put("STATUS", false);
			}
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.OK);	
	}		
	
	@RequestMapping(value="/add_user_sc" ,method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<Map<String , Object>> addUserSocial(@RequestBody FrmAddUser user){
		Map<String , Object> map = new HashMap<String , Object>();
		try{
//			System.out.println(user.getEmail());
			if(user.getEmail() == null){
				user.setEmail(user.getScID());
			}
			if(userService.insertUserSC(user)){
				FrmWebLogin w = new FrmWebLogin(); 
				w.setEmail(user.getEmail());
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
		}catch(Exception e){
			map.put("MESSAGE", "OPERATION FAIL");
			map.put("STATUS", false);
		}
		return new ResponseEntity<Map<String , Object>>(map , HttpStatus.OK);	
	}
}
