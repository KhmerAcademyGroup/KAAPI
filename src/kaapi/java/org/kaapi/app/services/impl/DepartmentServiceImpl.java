package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.kaapi.app.entities.Department;
import org.kaapi.app.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentServiceImpl implements DepartmentService {
	
	@Autowired
	private DataSource dataSource;

	@Override
	public boolean createDepartment(Department department) {
		String sql = "INSERT INTO tbldepartment(departmentid,departmentname) VALUES(NEXTVAL('seq_department'),?);";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
			){
			ps.setString(1, department.getDepartmentName());
			if(ps.executeUpdate() > 0)
				return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateDepartment(Department department) {
		String sql = "UPDATE tbldepartment SET departmentname = ? WHERE departmentid = ?;";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
			){
			ps.setString(1, department.getDepartmentName());
			ps.setInt(2, department.getDepartmentId());
			if(ps.executeUpdate() > 0)
				return true;
		}catch(SQLException e){
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public boolean deleteDepartment(int id) {
		String sql = "DELETE FROM tbldepartment WHERE id = ?;";
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
	public List<Department> listDepartment(int offset, int limit) {
		String sql = "SELECT departmentId, departmentName FROM tbldepartment OFFSET ? LIMIT ?;";
		List<Department> list = new ArrayList<Department>();
		Department department = null;
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
			){
			ps.setInt(1, offset);
			ps.setInt(2, limit);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				department = new Department();
				department.setDepartmentId(rs.getInt("departmentId"));
				department.setDepartmentName(rs.getString("departmentName"));
				list.add(department);
			}
			return list;
		}catch(SQLException e){
				e.printStackTrace();
			}
	return null;
	}
}
