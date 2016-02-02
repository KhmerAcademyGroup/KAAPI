
package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.User;
import org.kaapi.app.forms.FrmMobileLogin;
import org.kaapi.app.forms.FrmMobileRegister;
import org.kaapi.app.forms.FrmResetPassword;
import org.kaapi.app.forms.FrmAddUpdateCoverPhoto;
import org.kaapi.app.forms.FrmAddUser;
import org.kaapi.app.forms.FrmChangePassword;
import org.kaapi.app.forms.FrmUpdateUser;
import org.kaapi.app.forms.FrmValidateEmail;
import org.kaapi.app.forms.FrmWebLogin;

import java.util.List;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.User;

public interface UserService {

	
	public User mobileLogin(FrmMobileLogin mFrm);
	public User webLogin(FrmWebLogin wFrm);
	public List<User> listUser(Pagination pagination);
	public int countUser();
	public List<User> searchUserByUsername(String username,Pagination pagination);
	public int countSearchUserByUsername(String username);
	public User getUSerById(String id);
	public boolean validateEmail(FrmValidateEmail email);
	public boolean insertUser(FrmAddUser user);
	public boolean mobileInsertUser(FrmMobileRegister user);
	public boolean updateUser(FrmUpdateUser user);
	public boolean deleteUser(String id);
	public boolean insertCoverPhoto(FrmAddUpdateCoverPhoto coverPhoto);
	public boolean updateCoverPhoto(FrmAddUpdateCoverPhoto coverPhoto);
	public boolean resetPassword(FrmResetPassword resetPassword);
	public boolean changePassword(FrmChangePassword changePassword);
	public boolean updateType(String userId, String typeId);

	
}

