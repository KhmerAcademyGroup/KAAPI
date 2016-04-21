package org.kaapi.app.services.impl;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.forms.FrmStudentDetail;
import org.kaapi.app.services.KSHRDStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class KSHRDStudentServiceImpl implements KSHRDStudentService{

	@Autowired
	DataSource dataSource;
	
	@Override
	public List<FrmStudentDetail> listKSHRDStudent(FrmStudentDetail d , Pagination pagination) {
		String betwwen = "";
		if(d.getRegisteredDate()!= null && d.getUntilDate()!= null){ 
			betwwen  = " AND SD.register_date BETWEEN '"+d.getRegisteredDate()+"' AND '"+d.getUntilDate()+"' ";
		} 
		String sql = "SELECT"
				+ " S.fullname , s.gender , SC.course,  SS.shift, S.phonenumber , S.email, SD.ispaid,"
				+ " SD.register_date, SD.update_date,SD.status ,"
				+ " SD.id as student_detial_id,"
				+ " ST.type, ST.id as type_id ,SD.course_id,SD.shift_id ,SD.ka_userid"
				+ " FROM shortcourse.tblstudent_detail SD"
				+ " LEFT JOIN shortcourse.tblstudent S ON S.id = SD.student_id"
				+ " LEFT JOIN shortcourse.tblcourse SC ON SD.course_id = SC.id"
				+ " LEFT JOIN shortcourse.tblshift SS ON SD.shift_id = SS.id"
				+ " LEFT JOIN shortcourse.tbltype ST ON SD.type_id = ST.id"
				+ " WHERE"
				+ " LOWER(S.fullname) LIKE LOWER(?) AND" // 1
				+ " CAST(SC.id as VARCHAR) LIKE ? AND"   // 2 
				+ " CAST(SS.shift as VARCHAR) LIKE ? AND"// 3  
				+ " CAST(ST.id as VARCHAR) LIKE ? AND"   // 4
				+ " CAST(SD.ispaid as VARCHAR) LIKE ? AND" // 5
				+ " CAST(SD.status as VARCHAR) LIKE ? "     // 6
				+ betwwen
				+ " ORDER BY SC.id ASC OFFSET ? LIMIT ?;"; // 7, 8 
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ArrayList<FrmStudentDetail> studenetList =new ArrayList<FrmStudentDetail>();
			ps.setString(1,"%"+d.getFullname()+"%");
			ps.setString(2,"%"+d.getCourseId()+"%");
			ps.setString(3,"%"+d.getShifId()+"%");
			ps.setString(4,"%"+d.getTypeId()+"%");
			ps.setString(5,"%"+d.getIsPaid()+"%");
			ps.setString(6,"%"+d.getStatus()+"%");
			ps.setInt(7,pagination.offset());
			ps.setInt(8, pagination.getItem());
			System.out.print("vvv"+d.getFullname());
			System.out.print(d.getCourseId());
			System.out.print(d.getShifId());
			System.out.print(d.getTypeId());
			System.out.print(d.getIsPaid());
			System.out.print(d.getStatus());
			FrmStudentDetail student = null;
			ResultSet rs = null;
			rs = ps.executeQuery();
			while(rs.next()){
				student = new FrmStudentDetail();
				student.setFullname(rs.getString("fullname"));
				student.setGender(rs.getString("gender"));
				student.setCourse(rs.getString("course"));
				student.setShift(rs.getString("shift"));
				student.setPhoneNumber(rs.getString("phonenumber"));
				student.setEmail(rs.getString("email"));
				student.setIsPaid(rs.getString("ispaid"));
				student.setRegisteredDate(rs.getString("register_date"));
				student.setUpdatedDate(rs.getString("update_date"));
				student.setStatus(rs.getString("status"));
				student.setStudentDetailId(rs.getString("student_detial_id"));
				student.setType(rs.getString("type"));
				student.setTypeId(rs.getString("type_id"));
				student.setCourseId(rs.getString("course_id"));
				student.setShifId(rs.getString("shift_id"));
				student.setKaUserid(rs.getString("ka_userid"));
				studenetList.add(student);
			}
			return studenetList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int countKSHRDStudent(FrmStudentDetail d ) {
		String betwwen = "";
		if(d.getRegisteredDate()!= null && d.getUntilDate()!= null){ 
			betwwen  = " AND SD.register_date BETWEEN '"+d.getRegisteredDate()+"' AND '"+d.getUntilDate()+"' ";
		}
		String sql = "SELECT"
				+ " COUNT( SD.id)" 
				+ " FROM shortcourse.tblstudent_detail SD"
				+ " LEFT JOIN shortcourse.tblstudent S ON S.id = SD.student_id"
				+ " LEFT JOIN shortcourse.tblcourse SC ON SD.course_id = SC.id"
				+ " LEFT JOIN shortcourse.tblshift SS ON SD.shift_id = SS.id"
				+ " LEFT JOIN shortcourse.tbltype ST ON SD.type_id = ST.id"
				+ " WHERE"
				+ " LOWER(S.fullname) LIKE LOWER(?) AND" // 1
				+ " CAST(SC.id as VARCHAR) LIKE ? AND"   // 2 
				+ " CAST(SS.shift as VARCHAR) LIKE ? AND"// 3  
				+ " CAST(ST.id as VARCHAR) LIKE ? AND"   // 4
				+ " CAST(SD.ispaid as VARCHAR) LIKE ? AND" // 5
				+ " CAST(SD.status as VARCHAR) LIKE ? "     // 6	
		        + betwwen;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1,"%"+d.getFullname()+"%");
			ps.setString(2,"%"+d.getCourseId()+"%");
			ps.setString(3,"%"+d.getShifId()+"%");
			ps.setString(4,"%"+d.getTypeId()+"%");
			ps.setString(5,"%"+d.getIsPaid()+"%");
			ps.setString(6,"%"+d.getStatus()+"%");
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	
}
