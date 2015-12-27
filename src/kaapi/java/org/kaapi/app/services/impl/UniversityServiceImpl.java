package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.University;
import org.kaapi.app.services.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("universityServiceImpl")
public class UniversityServiceImpl implements UniversityService{

	@Autowired
	private DataSource dataSource;
	
	@Override
	public boolean createUniverstiy(University university) {
<<<<<<< HEAD
		String sql = "INSERT INTO tbluniversity(university_id,university_name) VALUES(NEXTVAL('seq_university'),?);";
=======
		String sql = "INSERT "
				+ "INTO"
			+ "tbluniversity(universityid,universityname)"
				+ "VALUES(NEXTVAL('seq_university'),?);";
>>>>>>> 4be2d5c37efa0de6ec25019ee71e123b5a36417b
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
			){
		ps.setString(1, university.getUniversityName());
		if(ps.executeUpdate() > 0)
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateUniversityById(University university) {
<<<<<<< HEAD
		String sql = "UPDATE tbluniversity SET university_name = ? WHERE university_id = ?;";
=======
		String sql = "UPDATE "
				+ "tbluniversity "
			+ "SET "
				+ "universityname = ?"
			+ "WHERE"
				+ "universityid = ?;";		
>>>>>>> 4be2d5c37efa0de6ec25019ee71e123b5a36417b
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
			){
			ps.setString(1, university.getUniversityName());
			ps.setInt(2, university.getUniversityId());
			if(ps.executeUpdate() > 0)
				return true;
			
		}catch(SQLException e){
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public boolean deleteUniversityById(int id) {
<<<<<<< HEAD
		String sql = "DELETE FROM tbluniversity WHERE university_id = ?;";
=======
		String sql = "DELETE "
			+ "FROM "
				+ "tbluniversity "
			+ "WHERE "
				+ "universityid = ?;";
>>>>>>> 4be2d5c37efa0de6ec25019ee71e123b5a36417b
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
			){
			ps.setInt(1, id);
			if(ps.executeUpdate() > 0)
				return true;			
		}catch(SQLException e){
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public List<University> findAllUniverstiyByName(Pagination pagination,String keyword) {
		String sql = "SELECT "
						+ "university_id,"
						+ "university_name "
					+ "FROM "
						+ "tbluniversity "
					+ "WHERE "
						+ "university_name LIKE ?"
					+ "LIMIT ? OFFSET ?;";
		List<University> lst = new ArrayList<University>();
		University university = null;
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
			ps.setString(1, "%" + keyword + "%");
			ps.setInt(2, pagination.getPerPage());
			ps.setInt(3, pagination.offset());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				university = new University();
				university.setUniversityId(rs.getInt("university_id"));
				university.setUniversityName(rs.getString("university_name"));
				lst.add(university);
			}
			return lst;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String findUniversityById(int id) {
		String sql = "SELECT "
				+ "universityname "
			+ "FROM "
				+ "tbluniversity "
			+ "WHERE "
				+ "universityid=?;";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
			){
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return rs.getString("universityname");
			}	
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int countUniversity() {
<<<<<<< HEAD
		String sql = "SELECT COUNT(university_id) as count FROM tbluniversity;";
=======
		String sql = "SELECT "
				+ "COUNT(universityid) as count"
			+ "FROM "
				+ "tbluniversity;";
>>>>>>> 4be2d5c37efa0de6ec25019ee71e123b5a36417b
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
