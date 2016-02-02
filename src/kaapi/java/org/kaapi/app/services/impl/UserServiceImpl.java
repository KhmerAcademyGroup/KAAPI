package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

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
import org.kaapi.app.services.UserService;
import org.kaapi.app.utilities.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserServiceImpl implements UserService {

	@Autowired
	DataSource dataSource;

	
	@Override
	public User mobileLogin(FrmMobileLogin mFrm) {
		String sql =  " SELECT u.userid , u.username , u.email, u.userimageurl ,co.coverphoto as coverphotourl"
					+ " FROM tbluser u LEFT JOIN tblcoverphoto co ON u.userid = co.userid"
					+ " WHERE LOWER(u.email)=LOWER(?) AND u.password = ? AND u.userstatus = '1';";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, mFrm.getEmail());
			ps.setString(2, mFrm.getPassword());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				User u = new User();
				u.setUserId(Encryption.encode(rs.getString("userid")));
				u.setUsername(rs.getString("username"));
				u.setEmail(rs.getString("email"));
				u.setUserImageUrl(rs.getString("userimageurl"));
				u.setCoverphoto(rs.getString("coverphotourl"));
				return u;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User webLogin(FrmWebLogin wFrm) {
		String sql =  " SELECT  u.userid, u.email, u.password, u.username, u.gender, u.dateofbirth, u.phonenumber,u.registerdate,u.userimageurl, u.universityid , uni.universityname, u.departmentid ,dep.departmentname , u.point , co.coverphoto as coverphotourl, u.userstatus,"
					+ " ut.usertypeid, ut.usertypename  ,"
					+ " COUNT(DISTINCT V.VIDEOID) COUNTVIDEOS, COUNT(DISTINCT C.COMMENTID) COUNTCOMMENTS , "
					+ " COUNT(DISTINCT pl.PLAYLISTID) COUNTPLAYLIST"
					+ " FROM TBLUSER u INNER JOIN TBLUSERTYPE ut ON u.USERTYPEID=ut.USERTYPEID "
					+ " LEFT JOIN TBLVIDEO v ON u.USERID=v.USERID "
					+ " LEFT JOIN TBLCOMMENT c ON u.USERID=c.USERID"
					+ " LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON u.USERID=vp.USERID "
					+ " LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON u.USERID=vm.USERID"
					+ " LEFT JOIN tblplaylist PL ON u.USERID=pl.userid "
					+ " LEFT JOIN tblcoverphoto co ON u.userid = co.userid"
					+ " LEFT JOIN tbluniversity uni ON u.universityid = uni.universityid"
					+ " LEFT JOIN tbldepartment dep ON u.departmentid = dep.departmentid" 
					+ " WHERE LOWER(u.EMAIL)=LOWER(?)  AND u.userstatus = '1'"
					+ " GROUP BY u.USERID, ut.USERTYPEID, uni.universityid , dep.departmentid ,co.coverid";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, wFrm.getEmail());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				User u = new User();
				u.setUserId(Encryption.encode(rs.getString("userid")));
				u.setUsername(rs.getString("username"));
				u.setEmail(rs.getString("email"));
				u.setPassword(rs.getString("password"));
				u.setGender(rs.getString("gender"));
				u.setDateOfBirth(rs.getDate("dateofbirth"));
				u.setPhoneNumber(rs.getString("phonenumber"));
				u.setRegisterDate(rs.getDate("registerdate"));
				u.setUserImageUrl(rs.getString("userimageurl"));
				u.setUserTypeId(Encryption.encode(rs.getString("usertypeid")));
				u.setUserTypeName(rs.getString("usertypename"));
				u.setPoint(rs.getInt("point"));
				if(rs.getString("universityid") != null){
					u.setUniversityId(Encryption.encode(rs.getString("universityid")));
				}
				if(rs.getString("departmentid") != null){
					u.setDepartmentId(Encryption.encode(rs.getString("departmentid")));
				}
				u.setUniversityName(rs.getString("universityname"));
				u.setDepartmentName(rs.getString("departmentname"));
				u.setCoverphoto(rs.getString("coverphotourl"));
				u.setCountComments(rs.getInt("countcomments"));
				u.setCountPlaylists(rs.getInt("countplaylist"));
				u.setCountVideos(rs.getInt("countvideos"));
				u.setUserStatus(rs.getBoolean("userstatus"));
				return u;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> listUser(Pagination pagination) {
		String sql = " SELECT  u.userid, u.email, u.password, u.username, u.gender, u.dateofbirth, u.phonenumber,u.registerdate,u.userimageurl, u.universityid , uni.universityname, u.departmentid ,dep.departmentname, u.point, co.coverphoto as coverphotourl,"
				+ " ut.usertypeid, ut.usertypename  ,"
				+ " COUNT(DISTINCT V.VIDEOID) COUNTVIDEOS, COUNT(DISTINCT C.COMMENTID) COUNTCOMMENTS ,"
				+ " COUNT(DISTINCT pl.PLAYLISTID) COUNTPLAYLIST"
				+ " FROM TBLUSER u INNER JOIN TBLUSERTYPE ut ON u.USERTYPEID=ut.USERTYPEID"
				+ " LEFT JOIN TBLVIDEO v ON u.USERID=v.USERID"
				+ " LEFT JOIN TBLCOMMENT c ON u.USERID=c.USERID"
				+ " LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON u.USERID=vp.USERID"
				+ " LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON u.USERID=vm.USERID"
				+ " LEFT JOIN tblplaylist PL ON u.USERID=pl.userid "
				+ " LEFT JOIN tblcoverphoto co ON u.userid = co.userid "
				+ " LEFT JOIN tbluniversity uni ON u.universityid = uni.universityid"
				+ " LEFT JOIN tbldepartment dep ON u.departmentid = dep.departmentid" 
				+ " WHERE u.userstatus = '1'"
				+ " GROUP BY u.USERID, ut.USERTYPEID, co.coverid , uni.universityid , dep.departmentid ORDER BY u.userid DESC offset ? limit ?;";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)){
			ps.setInt(1,pagination.offset());
			ps.setInt(2, pagination.getItem());
			ResultSet rs = ps.executeQuery();
			List<User> lst = new ArrayList<User>();
			User u = null;
			while(rs.next()){
				u = new User();
				u.setUserId(Encryption.encode(rs.getString("userid")));
				u.setUsername(rs.getString("username"));
				u.setEmail(rs.getString("email"));
				u.setGender(rs.getString("gender"));
				u.setDateOfBirth(rs.getDate("dateofbirth"));
				u.setPhoneNumber(rs.getString("phonenumber"));
				u.setRegisterDate(rs.getDate("registerdate"));
				u.setUserImageUrl(rs.getString("userimageurl"));
				u.setUserTypeId(Encryption.encode(rs.getString("usertypeid")));
				u.setUserTypeName(rs.getString("usertypename"));
				u.setPoint(rs.getInt("point"));
				if(rs.getString("universityid") != null){
					u.setUniversityId(Encryption.encode(rs.getString("universityid")));
				}
				if(rs.getString("departmentid") != null){
					u.setDepartmentId(Encryption.encode(rs.getString("departmentid")));
				}
				u.setUniversityName(rs.getString("universityname"));
				u.setDepartmentName(rs.getString("departmentname"));
				u.setCoverphoto(rs.getString("coverphotourl"));
				u.setCountComments(rs.getInt("countcomments"));
				u.setCountPlaylists(rs.getInt("countplaylist"));
				u.setCountVideos(rs.getInt("countvideos"));
				lst.add(u);
			}
			return lst;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int countUser() {
		String sql = "SELECT COUNT(userid) FROM tbluser;";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					return rs.getInt(1);
				}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public List<User> searchUserByUsername(String username , Pagination pagination) {
		String sql = " SELECT  u.userid, u.email, u.password, u.username, u.gender, u.dateofbirth, u.phonenumber,u.registerdate,u.userimageurl, u.universityid , uni.universityname, u.departmentid ,dep.departmentname , u.point, co.coverphoto as coverphotourl,"
				+ " ut.usertypeid, ut.usertypename  ,"
				+ " COUNT(DISTINCT V.VIDEOID) COUNTVIDEOS, COUNT(DISTINCT C.COMMENTID) COUNTCOMMENTS ,"
				+ " COUNT(DISTINCT pl.PLAYLISTID) COUNTPLAYLIST"
				+ " FROM TBLUSER u INNER JOIN TBLUSERTYPE ut ON u.USERTYPEID=ut.USERTYPEID"
				+ " LEFT JOIN TBLVIDEO v ON u.USERID=v.USERID"
				+ " LEFT JOIN TBLCOMMENT c ON u.USERID=c.USERID"
				+ " LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON u.USERID=vp.USERID"
				+ " LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON u.USERID=vm.USERID"
				+ " LEFT JOIN tblplaylist PL ON u.USERID=pl.userid "
				+ " LEFT JOIN tblcoverphoto co ON u.userid = co.userid"
				+ " LEFT JOIN tbluniversity uni ON u.universityid = uni.universityid"
				+ " LEFT JOIN tbldepartment dep ON u.departmentid = dep.departmentid" 
				+ " WHERE LOWER(u.username) LIKE LOWER(?) and u.userstatus='1'"
				+ " GROUP BY u.USERID, ut.USERTYPEID, co.coverid, uni.universityid , dep.departmentid ORDER BY u.userid DESC offset ? limit ?;";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)){
			ps.setString(1, "%"+ username + "%");
			ps.setInt(2,pagination.offset());
			ps.setInt(3, pagination.getItem());
			ResultSet rs = ps.executeQuery();
			List<User> lst = new ArrayList<User>();
			User u = null;
			while(rs.next()){
				u = new User();
				u.setUserId(Encryption.encode(rs.getString("userid")));
				u.setUsername(rs.getString("username"));
				u.setEmail(rs.getString("email"));
				u.setGender(rs.getString("gender"));
				u.setDateOfBirth(rs.getDate("dateofbirth"));
				u.setPhoneNumber(rs.getString("phonenumber"));
				u.setRegisterDate(rs.getDate("registerdate"));
				u.setUserImageUrl(rs.getString("userimageurl"));
				u.setUserTypeId(Encryption.encode(rs.getString("usertypeid")));
				u.setUserTypeName(rs.getString("usertypename"));
				u.setPoint(rs.getInt("point"));
				if(rs.getString("universityid") != null){
					u.setUniversityId(Encryption.encode(rs.getString("universityid")));
				}
				if(rs.getString("departmentid") != null){
					u.setDepartmentId(Encryption.encode(rs.getString("departmentid")));
				}
				u.setUniversityName(rs.getString("universityname"));
				u.setDepartmentName(rs.getString("departmentname"));
				u.setCoverphoto(rs.getString("coverphotourl"));
				u.setCountComments(rs.getInt("countcomments"));
				u.setCountPlaylists(rs.getInt("countplaylist"));
				u.setCountVideos(rs.getInt("countvideos"));
				lst.add(u);
			}
			return lst;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int countSearchUserByUsername(String username) {
		String sql = "SELECT COUNT(userid) FROM tbluser WHERE LOWER(username) LIKE LOWER(?);";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setString(1, "%"+username+"%");
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					return rs.getInt(1);
				}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public User getUSerById(String id) {
		String sql = " SELECT  u.userid, u.email, u.password, u.username, u.gender, u.dateofbirth, u.phonenumber,u.registerdate,u.userimageurl, u.universityid , uni.universityname, u.departmentid ,dep.departmentname , u.point, co.coverphoto as coverphotourl,"
				+ " ut.usertypeid, ut.usertypename  ,"
				+ " COUNT(DISTINCT V.VIDEOID) COUNTVIDEOS, COUNT(DISTINCT C.COMMENTID) COUNTCOMMENTS ,"
				+ " COUNT(DISTINCT pl.PLAYLISTID) COUNTPLAYLIST"
				+ " FROM TBLUSER u INNER JOIN TBLUSERTYPE ut ON u.USERTYPEID=ut.USERTYPEID"
				+ " LEFT JOIN TBLVIDEO v ON u.USERID=v.USERID"
				+ " LEFT JOIN TBLCOMMENT c ON u.USERID=c.USERID"
				+ " LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON u.USERID=vp.USERID"
				+ " LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON u.USERID=vm.USERID"
				+ " LEFT JOIN tblplaylist PL ON u.USERID=pl.userid "
				+ " LEFT JOIN tblcoverphoto co ON u.userid = co.userid"
				+ " LEFT JOIN tbluniversity uni ON u.universityid = uni.universityid"
				+ " LEFT JOIN tbldepartment dep ON u.departmentid = dep.departmentid" 
				+ " WHERE u.userid = ?"
				+ " GROUP BY u.USERID, ut.USERTYPEID, co.coverid, uni.universityid , dep.departmentid ";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)){
			ps.setInt(1, Integer.parseInt(Encryption.decode(id)));
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				User u = new User();
				u.setUserId(Encryption.encode(rs.getString("userid")));
				u.setUsername(rs.getString("username"));
				u.setEmail(rs.getString("email"));
				u.setGender(rs.getString("gender"));
				u.setDateOfBirth(rs.getDate("dateofbirth"));
				u.setPhoneNumber(rs.getString("phonenumber"));
				u.setRegisterDate(rs.getDate("registerdate"));
				u.setUserImageUrl(rs.getString("userimageurl"));
				u.setUserTypeId(Encryption.encode(rs.getString("usertypeid")));
				u.setUserTypeName(rs.getString("usertypename"));
				u.setPoint(rs.getInt("point"));
				if(rs.getString("universityid") != null){
					u.setUniversityId(Encryption.encode(rs.getString("universityid")));
				}
				if(rs.getString("departmentid") != null){
					u.setDepartmentId(Encryption.encode(rs.getString("departmentid")));
				}
				u.setUniversityName(rs.getString("universityname"));
				u.setDepartmentName(rs.getString("departmentname"));
				u.setCoverphoto(rs.getString("coverphotourl"));
				u.setCountComments(rs.getInt("countcomments"));
				u.setCountPlaylists(rs.getInt("countplaylist"));
				u.setCountVideos(rs.getInt("countvideos"));
				return u;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean validateEmail(FrmValidateEmail vFrm) {
		String sql = "select email , count(userid) from tbluser where LOWER(email)=LOWER(?) GROUP BY EMAIL";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql) ){
			ps.setString(1, vFrm.getEmail());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				System.out.println(rs.getInt("count") + " " + rs.getString("email"));
				if(rs.getInt("count")>0){
					return true;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean insertUser(FrmAddUser user) {
		String sql =  " INSERT INTO TBLUSER"
					+ " (userid,email,password,username,gender,registerdate,userimageurl,usertypeid,universityid,departmentid,userstatus)"
					+ " VALUES"
					+ " (NEXTVAL('seq_user'),?,?,?,?,NOW(),'user/avatar.jpg',2,?,?,'1');";
		try (Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)){
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getUsername());
			ps.setString(4, user.getGender());
			ps.setInt(5, Integer.parseInt(Encryption.decode(user.getUniversityId())));
			ps.setInt(6, Integer.parseInt(Encryption.decode(user.getDepartmentId())));
			if(ps.executeUpdate()>0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean updateUser(FrmUpdateUser user) {
		String sql = "UPDATE TBLUSER SET username=?, gender=?, dateofbirth=?, phonenumber=?, userimageurl=?,"
				+ "  universityid=?, departmentid=? WHERE userid=?";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)) {
		    ps.setString(1, user.getUsername());
		    ps.setString(2, user.getGender());
			ps.setDate(3, new java.sql.Date(user.getDateOfBirth().getTime()));
			ps.setString(4, user.getPhoneNumber());
			ps.setString(5, user.getUserImageUrl());
			ps.setInt(6, Integer.parseInt(Encryption.decode(user.getUniversityId())));
			ps.setInt(7, Integer.parseInt(Encryption.decode(user.getDepartmentId())));
			ps.setInt(8, Integer.parseInt(Encryption.decode(user.getUserId())));
			if(ps.executeUpdate()>0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteUser(String id) {
		String sql = "UPDATE TBLUSER SET userstatus='0' WHERE userid=?";
		try (Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)){
			ps.setInt(1, Integer.parseInt(Encryption.decode(id)));
			if(ps.executeUpdate()>0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean insertCoverPhoto(FrmAddUpdateCoverPhoto coverPhoto) {
		String sql= "insert into tblCoverPhoto values (NEXTVAL('seq_cover'),?,? )";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)){
			ps.setString(1, coverPhoto.getCouverPhotoUrl());
			ps.setInt(2, Integer.parseInt(Encryption.decode(coverPhoto.getUserId())));
			if(ps.executeUpdate()>0){
				return true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateCoverPhoto(FrmAddUpdateCoverPhoto coverPhoto) {
		String sql= "Update tblCoverPhoto set coverphoto=? where userid=?";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)){
			ps.setString(1, coverPhoto.getCouverPhotoUrl());
			ps.setInt(2, Integer.parseInt(Encryption.decode(coverPhoto.getUserId())));
			if(ps.executeUpdate()>0)
				return true;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean resetPassword(FrmResetPassword resetPassword) {
		String sql="UPDATE TBLUSER SET Password=? WHERE email=?";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)){
			ps.setString(1, resetPassword.getNewPassword());
			ps.setString(2, resetPassword.getEmail());
			if(ps.executeUpdate()>0){
				return true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean changePassword(FrmChangePassword changePassword) {
		String sql= "UPDATE tbluser set password=?  where userid=? and password=?";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)){
			ps.setString(1, changePassword.getNewPassword());
			ps.setInt(2, Integer.parseInt(Encryption.decode(changePassword.getUserId())));
			ps.setString(3, changePassword.getOldPassword());
			if(ps.executeUpdate()>0){
				return true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean mobileInsertUser(FrmMobileRegister user) {
		String sql =  " INSERT INTO TBLUSER"
				+ " (userid,email,password,username,gender,registerdate,userimageurl,usertypeid,universityid,departmentid,userstatus)"
				+ " VALUES"
				+ " (NEXTVAL('seq_user'),?,?,?,?,NOW(),'user/avatar.jpg',2,?,?,'1');";
		try (Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)){
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getUsername());
			ps.setString(4, user.getGender());
			ps.setInt(5,36);
			ps.setInt(6, 12 );
			if(ps.executeUpdate()>0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean updateType(String userId, String typeId) {
		
		String sql= "Update tbluser set usertypeid=? where userid=?";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)){
			ps.setInt(1, Integer.parseInt(Encryption.decode(typeId)));
			ps.setInt(2, Integer.parseInt(Encryption.decode(userId)));
			if(ps.executeUpdate()>0)
				return true;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}	
}
