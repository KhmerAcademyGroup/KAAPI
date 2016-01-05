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
import org.kaapi.app.services.UserService;
import org.kaapi.app.utilities.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserServiceImpl implements UserService {

	@Autowired
	DataSource dataSource;

	@Override
	public User mobileLogin(String email, String password) {
		String sql =  " SELECT u.userid , u.username , u.email, u.userimageurl ,co.coverphoto as coverphotourl"
					+ " FROM tbluser u LEFT JOIN tblcoverphoto co ON u.userid = co.userid"
					+ " WHERE LOWER(u.email)=LOWER(?) AND u.password = ? AND u.userstatus = '1';";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, email);
			ps.setString(2, password);
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
	public User webLogin(String email) {
		String sql =  " SELECT  u.userid, u.email, u.password, u.username, u.gender, u.dateofbirth, u.phonenumber,u.registerdate,u.userimageurl, u.universityid , u.departmentid , co.coverphoto as coverphotourl,"
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
					+ " WHERE LOWER(u.EMAIL)=LOWER(?)"
					+ " GROUP BY u.USERID, ut.USERTYPEID, co.coverid";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
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
				u.setUniversityId(Encryption.encode(rs.getString("universityid")));
				u.setDepartmentId(Encryption.encode(rs.getString("departmentid")));
				u.setCoverphoto(Encryption.encode(rs.getString("coverphotourl")));
				u.setCountComments(rs.getInt("countcomments"));
				u.setCountPlaylists(rs.getInt("countplaylist"));
				u.setCountVideos(rs.getInt("countvideos"));
				return u;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> listUser(Pagination pagination) {
		String sql = " SELECT  u.userid, u.email, u.password, u.username, u.gender, u.dateofbirth, u.phonenumber,u.registerdate,u.userimageurl, u.universityid , u.departmentid , u.point, co.coverphoto as coverphotourl,"
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
				+ " GROUP BY u.USERID, ut.USERTYPEID, co.coverid offset ? limit ?;";
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
				u.setUniversityId(Encryption.encode(rs.getString("universityid")));
				u.setDepartmentId(Encryption.encode(rs.getString("departmentid")));
				u.setCoverphoto(Encryption.encode(rs.getString("coverphotourl")));
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
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public List<User> searchUser(String username , Pagination pagination) {
		String sql = " SELECT  u.userid, u.email, u.password, u.username, u.gender, u.dateofbirth, u.phonenumber,u.registerdate,u.userimageurl, u.universityid , u.departmentid , u.point, co.coverphoto as coverphotourl,"
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
				+ " WHERE LOWER(u.username)=LOWER(?)"
				+ " GROUP BY u.USERID, ut.USERTYPEID, co.coverid offset ? limit ?;";
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
				u.setUniversityId(Encryption.encode(rs.getString("universityid")));
				u.setDepartmentId(Encryption.encode(rs.getString("departmentid")));
				u.setCoverphoto(Encryption.encode(rs.getString("coverphotourl")));
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
	public int countSearchUser(String username) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getUSer(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateEmail(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertCoverPhoto() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateCoverPhoto(String coverId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getPasswordByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean resetPassword(String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateOldPassword(String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
