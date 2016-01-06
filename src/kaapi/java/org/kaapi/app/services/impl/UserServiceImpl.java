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
		String sql =  " SELECT  u.userid, u.email, u.password, u.username, u.gender, u.dateofbirth, u.phonenumber,u.registerdate,u.userimageurl, u.universityid , uni.universityname, u.departmentid ,dep.departmentname , u.point , co.coverphoto as coverphotourl,"
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
					+ " GROUP BY u.USERID, ut.USERTYPEID, uni.universityid , dep.departmentid co.coverid";
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
				+ " GROUP BY u.USERID, ut.USERTYPEID, co.coverid , uni.universityid , dep.departmentid offset ? limit ?;";
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
				+ " WHERE LOWER(u.username) LIKE LOWER(?)"
				+ " GROUP BY u.USERID, ut.USERTYPEID, co.coverid, uni.universityid , dep.departmentid offset ? limit ?;";
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
		String sql = "SELECT COUNT(userid) FROM tbluser WHERE username LIKE ?;";
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
	public boolean validateEmail(String email) {
		String sql = "select email , count(userid) from tbluser where LOWER(email)=LOWER(?) GROUP BY EMAIL";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql) ){
			ps.setString(1, email);
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
	public boolean insertUser(User dto) {
		String sql = "INSERT INTO TBLUSER VALUES(NEXTVAL('seq_user'), ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?)";
		try (Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)){
			ps.setString(1, dto.getEmail());
			ps.setString(2, dto.getPassword());
			ps.setString(3, dto.getUsername());
			ps.setString(4, dto.getGender());
			if(dto.getDateOfBirth()!=null){
				ps.setDate(5, new java.sql.Date(dto.getDateOfBirth().getTime()));
			}else{
				ps.setDate(5, null);
			}
			ps.setString(6, dto.getPhoneNumber());
			ps.setString(7, dto.getUserImageUrl());
			ps.setInt(8, Integer.parseInt(Encryption.decode(dto.getUserTypeId())));
			ps.setInt(9, dto.getPoint());
			ps.setInt(10, Integer.parseInt(Encryption.decode(dto.getUniversityId())));
			ps.setInt(11, Integer.parseInt(Encryption.decode(dto.getDepartmentId())));
			ps.setInt(12, 1);
			if(ps.executeUpdate()>0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateUser(User dto) {
		String sql = "UPDATE TBLUSER SET password=?, username=?, gender=?, dateofbirth=?, phonenumber=?, userimageurl=?,"
				+ " usertypeid=?, universityid=?, departmentid=? WHERE userid=?";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)) {
			if(dto.getPassword()!=null){
			ps.setString(1, dto.getPassword());
			}
			if(dto.getUsername()!=null){
			ps.setString(2, dto.getUsername());
			}
			if(dto.getGender()!=null){
			ps.setString(3, dto.getGender());
			}
			if(dto.getDateOfBirth()!=null){
			ps.setDate(4, new java.sql.Date(dto.getDateOfBirth().getTime()));
			}
			if(dto.getPhoneNumber()!=null){
			ps.setString(5, dto.getPhoneNumber());
			}
			if(dto.getUserImageUrl()!=null){
			ps.setString(6, dto.getUserImageUrl());
			}
			ps.setInt(7, Integer.parseInt(Encryption.decode(dto.getUserTypeId())));
			ps.setInt(8, Integer.parseInt(Encryption.decode(dto.getUniversityId())));
			ps.setInt(9, Integer.parseInt(Encryption.decode(dto.getDepartmentId())));
			ps.setInt(10, Integer.parseInt(Encryption.decode(dto.getUserId())));
			if(ps.executeUpdate()>0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteUser(String id) {
		String sql = "UPDATE TBLUSER SET userstatus=? WHERE userid=?";
		try (Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)){
			ps.setInt(1, Integer.parseInt(Encryption.decode(id)));
			if(ps.executeUpdate(sql)>0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean insertCoverPhoto(String coverPhotoUrl , String userId) {
		String sql= "insert into tblCoverPhoto values (NEXTVAL('seq_cover'),?,? )";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)){
			ps.setString(1, coverPhotoUrl);
			ps.setInt(2, Integer.parseInt(Encryption.decode(userId)));
			if(ps.executeUpdate()>0){
				return true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateCoverPhoto(String coverPhotoUrl , String userId) {
		String sql= "Update tblCoverPhoto set coverphoto=? where userid=?";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)){
			if(coverPhotoUrl!=null){
				ps.setString(1, coverPhotoUrl);
			}
			ps.setInt(2, Integer.parseInt(Encryption.decode(userId)));			
			if(ps.executeUpdate()>0)
				return true;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getPasswordByEmail(String email) {
		String sql = "select password from tbluser where email=?";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)) {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return email = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean resetPassword(String newPassword , String oldPassword , String email) {
		String sql="UPDATE TBLUSER SET Password=? WHERE email=? AND Password=?";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)){
			ps.setString(1, newPassword);
			ps.setString(2, email);
			ps.setString(3, oldPassword);
			if(ps.executeUpdate()>0){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	public boolean checkOldPassword(String oldPassword, String userId) {
		String sql= "select password from tbluser where userid=? and password=?";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)){
			ps.setString(1, oldPassword);
			ps.setString(2, userId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
}
