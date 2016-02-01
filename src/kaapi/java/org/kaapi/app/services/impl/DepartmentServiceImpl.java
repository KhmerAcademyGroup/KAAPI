package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.kaapi.app.entities.Department;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.forms.FrmAddDepartment;
import org.kaapi.app.forms.FrmUpdateDepartment;
import org.kaapi.app.services.DepartmentService;
import org.kaapi.app.utilities.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("DepartmentServiceImpl")
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DataSource dataSource;

	@Override
	public boolean createDepartment(FrmAddDepartment department) {
		String sql = "INSERT " + "INTO "
				+ "tbldepartment(departmentid,departmentname) "
				+ "VALUES(NEXTVAL('seq_department'),?);";
		try (Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, department.getDepartmentName());
			if (ps.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateDepartment(FrmUpdateDepartment department) {
		String sql = "UPDATE " + "tbldepartment " + "SET "
				+ "departmentname = ? " + "WHERE " + "departmentid = ?";
		try (Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, department.getDepartmentName());
			ps.setInt(2, Integer.parseInt(Encryption.decode(department
					.getDepartmentId())));
			if (ps.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteDepartment(String departmentId) {
		String sql = "DELETE " + "FROM " + "tbldepartment " + "WHERE "
				+ "departmentid = ?";
		try (Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(departmentId)));
			if (ps.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Department> listDepartment(Pagination pagination, String keyword) {
		String sql = "SELECT " + "departmentid," + "departmentname " + "FROM "
				+ "tbldepartment " + "WHERE "
				+ "lower(departmentname) LIKE lower(?)" + "LIMIT ? OFFSET ?;";
		List<Department> list = new ArrayList<Department>();
		Department department = null;
		try (Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, "%" + keyword + "%");
			ps.setInt(2, pagination.getItem());
			ps.setInt(3, pagination.offset());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				department = new Department();
				department.setDepartmentId(Encryption.encode(rs
						.getString("departmentid")));
				department.setDepartmentName(rs.getString("departmentname"));
				list.add(department);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int countDepartment(String keyword) {
		String sql = "SELECT " 
					+ "COUNT(departmentid) as count " 
				+ "FROM "
					+ "tbldepartment WHERE departmentname LIKE ?";

		try (Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, "%" + keyword + "%");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}

	@Override
	public String findDepartmentById(String departmentId) {
		String sql = "SELECT "  
						+ "departmentname " 
					+ "FROM "
						+ "tbldepartment "
					+ "WHERE "
						+ "departmentid=?;";
						
		
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
			){
			ps.setInt(1, Integer.parseInt(Encryption.decode(departmentId)));
		
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return rs.getString("departmentname");
			}	
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
}
