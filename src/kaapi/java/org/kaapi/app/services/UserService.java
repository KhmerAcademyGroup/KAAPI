package org.kaapi.app.services;

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
	public boolean getUSer(String id);
	public boolean validateEmail(String email);
	public boolean insertUser(User user);
	public boolean updateUser(User user);
	public boolean deleteUser(String id);
	public boolean insertCoverPhoto();
	public boolean updateCoverPhoto(String coverId);
	
	public String getPasswordByEmail(String email);
	public boolean resetPassword(String password);
	public boolean validateOldPassword(String password);
	
}
