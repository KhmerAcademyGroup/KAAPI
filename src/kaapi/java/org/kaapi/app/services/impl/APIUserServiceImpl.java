package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.kaapi.app.entities.APIUser;
import org.kaapi.app.entities.APIUserRole;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.services.APIUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository("APIUserService")
public class APIUserServiceImpl implements APIUserService {

	@Autowired
	private DataSource dataSource;

	@Override
	public APIUser findUserByUsername(String username) {
		String sql = "SELECT id, username, password, email, enabled , position, approved_by, approved_date, created_date, created_by , updated_by, updated_date, locked FROM api_user WHERE username = ?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				APIUser user = new APIUser();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setPosition(rs.getString("position"));
				user.setApprovedDate(rs.getDate("approved_date"));
				user.setApprovedBy(rs.getInt("approved_by"));
				user.setCreatedBy(rs.getInt("created_by"));
				user.setCreatedDate(rs.getDate("created_date"));
				user.setEnabled(rs.getBoolean("enabled"));
				user.setAccountNonLocked(rs.getBoolean("locked"));
				user.setRoles(this.findAPIUserRoleByUserId(user.getId()));
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<APIUserRole> findAPIUserRoleByUserId(int id) {
		List<APIUserRole> roles = new ArrayList<APIUserRole>();
		String sql = "SELECT " + "api_role.id , api_role.role_name " + "FROM " + "api_user "
				+ "LEFT JOIN api_user_role ON api_user.id = api_user_role.api_user_id "
				+ "LEFT JOIN api_role ON api_role.id = api_user_role.api_role_id " + "WHERE api_user.id = ?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				APIUserRole role = new APIUserRole();
				role.setId(rs.getInt("id"));
				role.setName("ROLE_" + rs.getString("role_name"));
				roles.add(role);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roles;
	}

	@Override
	public List<APIUser> findAllUserByUsername(Pagination pagination , String username) {
		String sql = "SELECT id, username, password, email, enabled ,locked ,position, approved_by, approved_date, created_date, created_by , updated_by, updated_date , locked "
				   + "FROM api_user "
				   + "WHERE username LIKE ? and position <> ? and locked = 1 and enabled = 1 LIMIT ? OFFSET ?;";
		List<APIUser> users = new ArrayList<APIUser>();
		APIUser user = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, "%" + username + "%");
			ps.setString(2, "API");
			ps.setInt(3, pagination.getItem());
			ps.setInt(4, pagination.offset());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user = new APIUser();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setPosition(rs.getString("position"));
				user.setAccountNonLocked(rs.getBoolean("locked"));
				user.setApprovedDate(rs.getDate("approved_date"));
				user.setApprovedBy(rs.getInt("approved_by"));
				user.setCreatedBy(rs.getInt("created_by"));
				user.setCreatedDate(rs.getDate("created_date"));
				user.setAccountNonLocked(rs.getBoolean("locked"));
				user.setRoles(this.findAPIUserRoleByUserId(user.getId()));
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addUser(APIUser user) {
		String sql = "INSERT INTO api_user (username,email,password,position, enabled,locked,created_date) "
					+"VALUES "
					+"(?,?,?,?,1,1,NOW());";
		try(
			Connection cnn = dataSource.getConnection();
			PreparedStatement ps = cnn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		){
			ps.setString(1,user.getUsername());
			ps.setString(3, new BCryptPasswordEncoder().encode(user.getPassword()));
			ps.setString(2, user.getEmail());
			ps.setString(4, user.getPosition());
			if(ps.executeUpdate() > 0){
				ResultSet rs = ps.getGeneratedKeys();
				if ( rs.next() ) {
				    // Retrieve the auto generated key(s).
				    System.out.println(rs.getInt(1));
				    this.addUserRoles(rs.getInt(1), 4);
				}
				return true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addUserRoles(int userID,int roleID) {
		String sql = "INSERT INTO api_user_role( api_user_id  ,  api_role_id ) VALUES ( ? , ?);";
		try(
			Connection cnn = dataSource.getConnection();
			PreparedStatement ps = cnn.prepareStatement(sql);
		){
			ps.setInt(1,userID);
			ps.setInt(2, roleID);
			if(ps.executeUpdate() > 0){
				return true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	
	@Override
	public boolean updateUser() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean disabledUser() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUserRole() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUserStatus() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int countAPIUser() {
		String sql = "SELECT COUNT(api_user) as count FROM api_user WHERE position <> ?;";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setString(1, "API");
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
	public boolean isUsernameExist(String username) {
		String sql = "SELECT COUNT(username) as count FROM api_user WHERE username = ?;";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setString(1,username);
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					if(rs.getInt("count") > 0){
						return true;
					}
				}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean isEmailExist(String email) {
		String sql = "SELECT COUNT(email) as count FROM api_user WHERE email = ?;";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setString(1,email);
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					if(rs.getInt("count") > 0){
						System.out.println("Count : " + rs.getInt("count"));
						return true;
					}
				}
		}catch(SQLException e){
			e.printStackTrace();
		}
		System.out.println("Count : 0");
		return false;
	}

	@Override
	public List<APIUser> listRequestedUser() {
		String sql = "SELECT id , username , email , position ,created_date FROM api_user WHERE locked = 0 ORDER BY id DESC;";
		List<APIUser> users = new ArrayList<APIUser>();
		APIUser user = null;
		try (	
			Connection cnn = dataSource.getConnection(); 
			PreparedStatement ps = cnn.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user = new APIUser();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setCreatedDate(rs.getDate("created_date"));
				user.setPosition(rs.getString("position"));
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public APIUser findUserReqestedByID(int id) {
		String sql = "SELECT id , username , email , position ,created_date FROM api_user WHERE id=? and locked = 0 ORDER BY id DESC;";
		APIUser user = null;
		try (	
				Connection cnn = dataSource.getConnection(); 
				PreparedStatement ps = cnn.prepareStatement(sql);) {
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					user = new APIUser();
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setEmail(rs.getString("email"));
					user.setCreatedDate(rs.getDate("created_date"));
					user.setPosition(rs.getString("position"));
					return user;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return null;
	}

	@Override
	public boolean acceptRequest(int userID , int adminID) {
		String sql = "Update api_user set locked = 1 , approved_by=? , approved_date = now() WHERE id =?";
		try(
				Connection cnn = dataSource.getConnection(); 
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setInt(1, adminID);
				ps.setInt(2, userID);
				if(ps.executeUpdate() > 0) return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean rejectRequest( int userID) {
		String sql = "DELETE FROM api_user_role WHERE id=?;DELETE FROM api_user WHERE id =?";
		try(
				Connection cnn = dataSource.getConnection(); 
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setInt(1, userID);
				ps.setInt(2, userID);
				if(ps.executeUpdate() > 0) return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	
	


}
