package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.APIUser;
import org.kaapi.app.entities.Pagination;

public interface APIUserService {

	public APIUser findUserByUsername(String username);
	public List<APIUser> findAllUserByUsername(Pagination pagination , String username);
	public boolean addUser(APIUser user);
	public boolean updateUser();
	public boolean disabledUser();
	public boolean updateUserRole();
	public boolean updateUserStatus();
	public int countAPIUser();
	public boolean isUsernameExist(String username);
	public boolean isEmailExist(String email);
	public boolean addUserRoles(int userID,int roleID);
	public List<APIUser> listRequestedUser();
	public APIUser findUserReqestedByID(int id);
	public boolean acceptRequest( int userID , int adminID);
	public boolean rejectRequest( int userID);
}
