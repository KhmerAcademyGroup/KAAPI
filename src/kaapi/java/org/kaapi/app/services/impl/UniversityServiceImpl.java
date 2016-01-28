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
import org.kaapi.app.forms.FrmAddUniversity;
import org.kaapi.app.forms.FrmUpdateUniversity;
import org.kaapi.app.services.UniversityService;
import org.kaapi.app.utilities.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UniversityServiceImpl implements UniversityService{

	@Autowired
	private DataSource dataSource;
	
	@Override
	public boolean createUniverstiy(FrmAddUniversity university) {

		String sql = "INSERT INTO tbluniversity(universityid,universityname) VALUES(NEXTVAL('seq_university'),?);";

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
	public boolean updateUniversityById(FrmUpdateUniversity university) {

		String sql = "UPDATE "
				+ "tbluniversity "
			+ "SET "
				+ "universityname = ?"
			+ "WHERE "
				+ "universityid = ?;";	

		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
			){
			ps.setString(1, university.getUniversityName());
			ps.setInt(2, Integer.parseInt(Encryption.decode(university.getUniversityId())));
			if(ps.executeUpdate() > 0)
				return true;
			
		}catch(SQLException e){
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public boolean deleteUniversityById(String universityId) {

		String sql = "DELETE "
			+ "FROM "
				+ "tbluniversity "
			+ "WHERE "
				+ "universityid = ?;";

		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
			){
			ps.setInt(1, Integer.parseInt(Encryption.decode(universityId)));
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
						+ "universityid,"
						+ "universityname "
					+ "FROM "
						+ "tbluniversity "
					+ "WHERE "
						+ "lower(universityname) LIKE lower(?)"
					+ "LIMIT ? OFFSET ?;";
		List<University> lst = new ArrayList<University>();
		University university = null;
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
			ps.setString(1, "%" + keyword + "%");
			ps.setInt(2, pagination.getItem());
			ps.setInt(3, pagination.offset());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				university = new University();
				university.setUniversityId(Encryption.encode(rs.getString("universityid")));
				university.setUniversityName(rs.getString("universityname"));
				lst.add(university);
			}
			return lst;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String findUniversityById(String universityId) {
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
			ps.setInt(1, Integer.parseInt(Encryption.decode(universityId)));
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

		String sql = "SELECT "
				+ "COUNT(universityid) as count "
			+ "FROM "
				+ "tbluniversity;";

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
