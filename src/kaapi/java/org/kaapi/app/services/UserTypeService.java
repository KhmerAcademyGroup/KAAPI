package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.UserType;

public interface UserTypeService {

	
	public List<UserType> listUserType(Pagination pagination);
	public int countUserType();
	public List<UserType> searchUserType(String name,Pagination pagination);
	public int countSearchUserType(String name);
	public UserType getUserType(String userTypeId);
	public boolean insertUserType(UserType userType);
	public boolean updateUserType(UserType userType);
	public boolean deleteUserType(String userTypeId);
	
}
