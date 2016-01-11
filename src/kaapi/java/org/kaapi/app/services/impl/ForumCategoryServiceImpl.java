package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.kaapi.app.entities.ForumCategory;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.forms.FrmAddForumCategory;
import org.kaapi.app.forms.FrmUpdateForumCategory;
import org.kaapi.app.services.ForumCategoryService;
import org.kaapi.app.utilities.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ForumCategoryServiceImpl implements ForumCategoryService {

	@Autowired
	private DataSource dataSource;
	
	@Override
	public List<ForumCategory> searchForumCate(String categoryName , Pagination pagination) {
		String sql = 	  " SELECT "
						+ " CA.*, COUNT(C.categoryid) COUNTVIDEOS "
						+ " FROM "
						+ " tblforumcategory CA LEFT JOIN tblforumcomment C ON CA.categoryid=C.categoryid "
						+ " WHERE "
						+ " LOWER(categoryname) LIKE LOWER(?) "
						+ " GROUP BY C.categoryid, CA.categoryid ORDER BY categoryname ASC LIMIT ? OFFSET ?";
		try(
			Connection cnn = dataSource.getConnection();
			PreparedStatement ps = cnn.prepareStatement(sql);
		){
			ps.setString(1, "%"+categoryName+"%");
			ps.setInt(2, pagination.getItem());
			ps.setInt(3, pagination.offset());
			ResultSet rs = ps.executeQuery();
			ArrayList<ForumCategory> list = new ArrayList<ForumCategory>();
			ForumCategory fCate = null;
			while(rs.next()){
				fCate = new ForumCategory();
				fCate.setCategoryId(Encryption.encode(rs.getString("categoryid")));
				fCate.setCategoryName(rs.getString("categoryname"));
				fCate.setCommentCount(rs.getInt("countvideos"));
				list.add(fCate);
			}
			return list;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<ForumCategory> listForumCate(Pagination pagination) {
		String sql = 	  " SELECT "
						+ " CA.*, COUNT(C.categoryid) COUNTVIDEOS "
						+ " FROM "
						+ " tblforumcategory CA LEFT JOIN tblforumcomment C ON CA.categoryid=C.categoryid "
						+ " GROUP BY C.categoryid, CA.categoryid ORDER BY categoryname ASC LIMIT ? OFFSET ?";
		try(
			Connection cnn = dataSource.getConnection();
			PreparedStatement ps = cnn.prepareStatement(sql);
		){
			ps.setInt(1, pagination.getItem());
			ps.setInt(2, pagination.offset());
			ResultSet rs = ps.executeQuery();
			ArrayList<ForumCategory> list = new ArrayList<ForumCategory>();
			ForumCategory fCate = null;
			while(rs.next()){
				fCate = new ForumCategory();
				fCate.setCategoryId(Encryption.encode(rs.getString("categoryid")));
				fCate.setCategoryName(rs.getString("categoryname"));
				fCate.setCommentCount(rs.getInt("countvideos"));
				list.add(fCate);
			}
			return list;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ForumCategory getForumCate(String id) {
		String sql = 	  " SELECT"
						+ " CA.*, COUNT(C.categoryid) COUNTCOMMENTS"
						+ " FROM"
						+ " tblforumcategory CA LEFT JOIN tblforumcomment C ON CA.categoryid=C.categoryid"
						+ " WHERE"
						+ " CA.Categoryid=?"
						+ " GROUP BY C.categoryid, CA.categoryid";
		try(
			Connection cnn = dataSource.getConnection();
			PreparedStatement ps = cnn.prepareStatement(sql);
		){
			ps.setInt(1, Integer.parseInt(Encryption.decode(id)));
			ResultSet rs = ps.executeQuery();
			ForumCategory dto  = null;
			if(rs.next()){
				dto = new ForumCategory();
				dto.setCategoryId(Encryption.encode(rs.getString("categoryid")));
				dto.setCategoryName(rs.getString("categoryname"));
				dto.setCommentCount(rs.getInt("countcomments"));
				return dto;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteForumCate(String id) {
		String sql = "DELETE FROM tblforumcategory WHERE categoryid=?";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setInt(1, Integer.parseInt(Encryption.decode(id)));
				if(ps.executeUpdate() > 0) return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addForumCategory(FrmAddForumCategory froumCategory) {
		String sql = "INSERT INTO tblforumcategory VALUES (NEXTVAL('seq_forumcategory'), ?)";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
				
		) {
				ps.setString(1, froumCategory.getCategoryName());
				if (ps.executeUpdate() > 0)
					return true;
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean updateForumCate(FrmUpdateForumCategory forumCate) {
		String sql = "UPDATE tblforumcategory SET categoryname=? WHERE categoryid=?";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps  =  cnn.prepareStatement(sql);
		){
				ps.setString(1, forumCate.getCategoryName());
				ps.setInt(2, Integer.parseInt(Encryption.decode(forumCate.getCategoryId())));
				if(ps.executeUpdate() > 0 ) return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int countSearchForumCate(String categoryName) {
		String sql = "SELECT COUNT(categoryid) FROM tblforumcategory WHERE categoryname LIKE ?";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setString(1, "%"+categoryName+"%");
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
	public int countForumCate() {
		String sql = "SELECT COUNT(categoryid) FROM tblforumcategory";
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


}
