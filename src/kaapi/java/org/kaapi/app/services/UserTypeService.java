package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.UserType;
import org.kaapi.app.forms.FrmAddUserType;
import org.kaapi.app.forms.FrmUpdateUserType;

public interface UserTypeService {

	
	public List<UserType> listUserType(Pagination pagination);
	public int countUserType();
	public List<UserType> searchUserType(String name,Pagination pagination);
	public int countSearchUserType(String name);
	public UserType getUserType(String usertypeid);
	public boolean insertUserType(FrmAddUserType userType);
	public boolean updateUserType(FrmUpdateUserType userType);
	public boolean deleteUserType(String userTypeId);
	
}
