package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.UserType;

public interface UserTypeService {

	
	public List<UserType> listUserType();
	public int countUserType();
	public List<UserType> searchUserType(String name);
	public int countSearchUserType(String name);
	public UserType getUserType(String userTypeId);
	public boolean insertUserType(UserType userType);
	public boolean updateUserType(UserType userType);
	public boolean deleteUserType(String userTypeId);
	
}
