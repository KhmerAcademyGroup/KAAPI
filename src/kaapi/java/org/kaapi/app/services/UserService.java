
package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.User;



import java.util.List;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.User;

public interface UserService {

	
	public User mobileLogin(String email , String password);
	public User webLogin(String email);
	public List<User> listUser(Pagination pagination);
	public int countUser();
	public List<User> searchUser(String username,Pagination pagination);
	public int countSearchUser(String username);
	public User getUSer(String id);
	public boolean validateEmail(String email);
	public boolean insertUser(User user);
	public boolean updateUser(User user);
	public boolean deleteUser(String id);
	public boolean insertCoverPhoto(String coverPhotoUrl , String userId);
	public boolean updateCoverPhoto(String coverPhotoUrl , String userId);
	
	public String getPasswordByEmail(String email);
	public boolean resetPassword(String newPassword , String oldPassword , String email);
	public boolean checkOldPassword(String oldpassword , String userId);
	
}

