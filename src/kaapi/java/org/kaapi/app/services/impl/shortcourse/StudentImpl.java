package org.kaapi.app.services.impl.shortcourse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.kaapi.app.entities.shortcourse.FrmStudent;
import org.kaapi.app.services.shortcourse.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StudentImpl implements StudentService{

	@Autowired
	private DataSource ds;
	
	private Connection con;	
	
	@Override
	public int insertStudent(FrmStudent student) {
		try {
			con = ds.getConnection();
			
			String sql = "INSERT INTO shortcourse.tblstudent(fullname,email,phonenumber,university,year,gender,address, ka_userid) "
					+ "VALUES(?,?,?,?,?,?,?,?)"; 
			
			PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, student.getFullName());
			ps.setString(2, student.getEmail());
			ps.setString(3, student.getTelephone());
			ps.setString(4, student.getUniversity());
			ps.setString(5, student.getYear());
			ps.setString(6, student.getGender());
			ps.setString(7, student.getAddress());	
			ps.setInt(8, student.getKaUserId());
			
			ps.executeUpdate();
			
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return generatedKeys.getInt("id");
	            }
	            else {
	                throw new SQLException("Creating Student failed, no ID obtained.");
	            }
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		return 0;
	}

	@Override
	public int getCurrentStudentId() {

		
		return 0;
	}

	@Override
	public boolean isExist(int id) {

		try {
			String sql = "SELECT COUNT(*) FROM shortcourse.tblstudent s "
						+ "LEFT JOIN shortcourse.tblstudent_detail sd "
						+ "ON s.id=sd.student_id WHERE s.ka_userid = ?";
			con = ds.getConnection();
			
			PreparedStatement p = con.prepareStatement(sql);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
		
			rs.next();
			if(rs.getInt(1)>0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		return false;
	}

}
