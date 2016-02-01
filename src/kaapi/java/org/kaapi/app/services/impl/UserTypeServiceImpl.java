package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.UserType;
import org.kaapi.app.forms.FrmAddUserType;
import org.kaapi.app.forms.FrmUpdateUserType;
import org.kaapi.app.services.UserTypeService;
import org.kaapi.app.utilities.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("UserTypeServiceImpl")
public class UserTypeServiceImpl  implements UserTypeService{

	@Autowired
	private DataSource dataSource;
	
	// List All UserType
	@Override
	public List<UserType> listUserType(Pagination pagination) {
		String sql = "SELECT "
				+ "UT.*, "
			+ "COUNT(DISTINCT USERID) COUNTUSERS "
				+ "FROM "
			+ "TBLUSERTYPE UT "
				+ "LEFT JOIN "
			+ "TBLUSER U ON UT.USERTYPEID=U.USERTYPEID "
				+ "GROUP BY UT.USERTYPEID ORDER BY UT.USERTYPEID DESC LIMIT ? OFFSET ?";
		
		List<UserType> list = new ArrayList<UserType>();
		UserType userType = null;
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
			ps.setInt(1, pagination.getItem());
			ps.setInt(2, pagination.offset());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				userType = new UserType();
				userType.setUserTypeId(Encryption.encode(rs.getString("usertypeid")));
				userType.setUserTypeName(rs.getString("usertypename"));
				userType.setViewable(rs.getBoolean("viewable"));
				userType.setCommentable(rs.getBoolean("commentable"));
				userType.setPostable(rs.getBoolean("postable"));
				userType.setDeleteable(rs.getBoolean("deleteable"));
				userType.setUserable(rs.getBoolean("userable"));
				userType.setCountUsers(rs.getInt("countusers"));
				list.add(userType);
			}
			return list;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	// Count UserType
	@Override
	public int countUserType() {
		String sql = "SELECT "
				+ "COUNT(USERTYPEID) COUNT "
			+ "FROM "
				+ "TBLUSERTYPE;";
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
	
	//List Search UserType by name
	@Override
	public List<UserType> searchUserType(String name,Pagination pagination) {
		String sql = "SELECT UT.*, COUNT(DISTINCT USERID) COUNTUSERS FROM TBLUSERTYPE "
				+ "UT LEFT JOIN TBLUSER U ON UT.USERTYPEID=U.USERTYPEID WHERE LOWER(UT.USERTYPENAME) LIKE LOWER(?) GROUP BY UT.USERTYPEID ORDER BY UT.USERTYPEID DESC LIMIT ? OFFSET ?";
		List<UserType> list = new ArrayList<UserType>();
		UserType userType = null;
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
			){
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, pagination.getItem());
			ps.setInt(3, pagination.offset());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				userType = new UserType();
				userType.setUserTypeId(Encryption.encode(rs.getString("usertypeid")));
				userType.setUserTypeName(rs.getString("usertypename"));
				userType.setViewable(rs.getBoolean("viewable"));
				userType.setCommentable(rs.getBoolean("commentable"));
				userType.setPostable(rs.getBoolean("postable"));
				userType.setDeleteable(rs.getBoolean("deleteable"));
				userType.setUserable(rs.getBoolean("userable"));
				userType.setCountUsers(rs.getInt("countusers"));
				list.add(userType);
			}
			return list;
		}catch(SQLException e){
				e.printStackTrace();
			}
	return null;
	}	

	// List UserType By Id
	@Override
	public UserType getUserType(String usertypeid) {
		String sql = "SELECT UT.*, COUNT(DISTINCT USERID) COUNTUSERS FROM TBLUSERTYPE UT LEFT JOIN TBLUSER U ON UT.USERTYPEID=U.USERTYPEID WHERE UT.USERTYPEID=? GROUP BY UT.USERTYPEID";
		UserType userType = null;
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
			){
			ps.setInt(1, Integer.parseInt(Encryption.decode(usertypeid)));
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				userType = new UserType();
				userType.setUserTypeId(Encryption.encode(rs.getString("usertypeid")));
				userType.setUserTypeName(rs.getString("usertypename"));
				userType.setViewable(rs.getBoolean("viewable"));
				userType.setCommentable(rs.getBoolean("commentable"));
				userType.setPostable(rs.getBoolean("postable"));
				userType.setDeleteable(rs.getBoolean("deleteable"));
				userType.setUserable(rs.getBoolean("userable"));
				userType.setCountUsers(rs.getInt("countusers"));
			}
			return userType;
		}catch(SQLException e){
				e.printStackTrace();
			}
	return null;
	}

	// Count SearchUserType by name
	@Override
	public int countSearchUserType(String name) {
		String sql = "SELECT "
					+ "COUNT(UT.USERTYPEID) "
						+ "FROM "
					+ "TBLUSERTYPE UT "
						+ "WHERE "
					+ "LOWER(UT.USERTYPENAME) "
						+ "LIKE LOWER(?) ";
		try (
				Connection cnn = dataSource.getConnection(); 
				PreparedStatement ps = cnn.prepareStatement(sql);
			) {
			ps.setString(1, "%" + name + "%");
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// Insert new UserType
	@Override
	public boolean insertUserType(FrmAddUserType userType) {
		String sql = "INSERT "
				+ "INTO "
					+ "TBLUSERTYPE VALUES(NEXTVAL('seq_usertype'), ?, ?, ?, ?, ?, ?)";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
			){
			ps.setString(1, userType.getUserTypeName());
			ps.setBoolean(2, userType.isViewable());
			ps.setBoolean(3, userType.isCommentable());
			ps.setBoolean(4, userType.isPostable());
			ps.setBoolean(5, userType.isDeleteable());
			ps.setBoolean(6, userType.isUserable());
			if(ps.executeUpdate() > 0)
				return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	// Update UserType
	@Override
	public boolean updateUserType(FrmUpdateUserType userType) {
		String sql = "UPDATE "
					+ "TBLUSERTYPE "
				+ "SET "
					+ "USERTYPENAME = ?, "
					+ "VIEWABLE = ?, "
					+ "COMMENTABLE = ?, "
					+ "POSTABLE = ?, " 
					+ "DELETEABLE = ?, "
					+ "USERABLE = ? " 
				+ "WHERE "
					+ "USERTYPEID = ?;";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
			){
			ps.setString(1, userType.getUserTypeName());
			ps.setBoolean(2, userType.isViewable());
			ps.setBoolean(3, userType.isCommentable());
			ps.setBoolean(4, userType.isPostable());
			ps.setBoolean(5, userType.isDeleteable());
			ps.setBoolean(6, userType.isUserable());
			ps.setInt(7, Integer.parseInt(Encryption.decode(userType.getUserTypeId())));
			if(ps.executeUpdate() > 0)
				return true;
		}catch(SQLException e){
				e.printStackTrace();
			}
		return false;
	}

	// Delete UserType
	@Override
	public boolean deleteUserType(String userTypeId) {
		String sql = "DELETE "
				+ "FROM "
					+ "TBLUSERTYPE "
				+ "WHERE "
					+ "USERTYPEID = ? ";
			try(
					Connection cnn = dataSource.getConnection();
					PreparedStatement ps = cnn.prepareStatement(sql);
				){
				ps.setInt(1, Integer.parseInt(Encryption.decode(userTypeId)));
				if(ps.executeUpdate() > 0)
					return true;
			}catch(SQLException e){
					e.printStackTrace();
				}
			return false;
		}

}
