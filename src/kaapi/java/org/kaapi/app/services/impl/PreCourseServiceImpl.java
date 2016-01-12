package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.PreCourse;
import org.kaapi.app.forms.FrmAddPreCourse;
import org.kaapi.app.forms.FrmEditPreCourse;
import org.kaapi.app.forms.FrmUpdatePreCourse;
import org.kaapi.app.services.PreCourseService;
import org.kaapi.app.utilities.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class PreCourseServiceImpl implements PreCourseService {

	@Autowired
	DataSource ds;
		
	@Override
	public int countPreCourse(){
		
		String sql = "SELECT count(pr.pc_id) from pre_course pr";
		try(Connection cnn = ds.getConnection(); Statement stm = cnn.createStatement();){
			
			ResultSet rs = stm.executeQuery(sql);
			if(rs.next())
				return rs.getInt("count");
			
			return 0;
		}catch(Exception ex){
			ex.printStackTrace();
			return 0;
		}		
	}
	
	@Override
	public boolean addPreCourse(FrmAddPreCourse preCourse) {
		
		final String SQL = "INSERT INTO precourse.pre_course VALUES(nextval('pre_course_id'),now(),?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		
		try(Connection cnn = ds.getConnection(); PreparedStatement pstmt = cnn.prepareStatement(SQL);) {			
			
			pstmt.setInt(1, Integer.parseInt(Encryption.decode(preCourse.getUserId())));
			pstmt.setString(2, preCourse.getUsername());
			pstmt.setString(3, preCourse.getEmail());
			pstmt.setString(4, preCourse.getTelephone());
			pstmt.setString(5, preCourse.getUniversity());
			pstmt.setDate(6, (Date) preCourse.getDob());
			pstmt.setString(7, preCourse.getPob());
			pstmt.setString(8, preCourse.getUserImage());
			pstmt.setString(9, preCourse.getJavaCourse());
			pstmt.setString(10, preCourse.getWebCourse());
			pstmt.setInt(11, preCourse.getPayment());
			pstmt.setString(12, preCourse.getComment());
			pstmt.setString(13, preCourse.getGender());
			pstmt.setString(14, preCourse.getYear());
			if(pstmt.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deletePreCourse(String id) {
		
		final String SQL = "DELETE FROM precourse.pre_course WHERE precourse.pre_course.pc_id = ?;";
		try(Connection cnn = ds.getConnection(); PreparedStatement pstmt = cnn.prepareStatement(SQL);) {
			
			pstmt.setInt(1, Integer.parseInt(Encryption.decode(id)));
			if(pstmt.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return false;
	}

	@Override
	public boolean updatePreCourse(FrmUpdatePreCourse preCourse) {
		
		final String SQL = "UPDATE precourse.pre_course SET pc_dob = ?, pc_email = ?, "
				+ "pc_java = ?, pc_pob = ?, pc_web = ?, pc_tel = ?, "
				+ "pc_university = ?, pc_userimage = ?, pc_username = ?, pc_comment = ?, "
				+ "pc_gender = ?, pc_year = ? WHERE pc_id = ?;";
		
		try(Connection cnn = ds.getConnection(); PreparedStatement pstmt = cnn.prepareStatement(SQL);) {
			
			pstmt.setDate(1, (Date) preCourse.getDob());
			pstmt.setString(2, preCourse.getEmail());
			pstmt.setString(3, preCourse.getJavaCourse());
			
			pstmt.setString(4, preCourse.getPob());
			pstmt.setString(5, preCourse.getWebCourse());
			pstmt.setString(6, preCourse.getTelephone());
			pstmt.setString(7, preCourse.getUniversity());
			pstmt.setString(8, preCourse.getUserImage());
			pstmt.setString(9, preCourse.getUsername());
			pstmt.setString(10, preCourse.getComment());
			pstmt.setString(11, preCourse.getGender());
			pstmt.setString(12, preCourse.getYear());
			pstmt.setInt(13, Integer.parseInt(Encryption.decode(preCourse.getId())));
			if(pstmt.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}

	@Override
	public ArrayList<PreCourse> getAllPreCourses(Pagination pg) {
		
		ArrayList<PreCourse> preCourses = new ArrayList<PreCourse>();
		final String SQL = "SELECT * FROM precourse.pre_course; offset ? limit ?";
		
		try(Connection cnn = ds.getConnection(); PreparedStatement pstmt = cnn.prepareStatement(SQL);) {
			
			pstmt.setInt(1, pg.offset());
			pstmt.setInt(2, pg.getItem());
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				PreCourse preCourse = new PreCourse();
				preCourse.setId(Encryption.encode(rs.getInt(1)+""));
				preCourse.setDateCreate(rs.getDate(2));
				preCourse.setUserId(Encryption.encode(rs.getInt(3)+""));
				preCourse.setUsername(rs.getString(4));
				preCourse.setEmail(rs.getString(5));
				preCourse.setTelephone(rs.getString(6));
				preCourse.setUniversity(rs.getString(7));
				preCourse.setDob(rs.getDate(8));
				preCourse.setPob(rs.getString(9));
				preCourse.setUserImage(rs.getString(10));
				preCourse.setJavaCourse(rs.getString(11));
				preCourse.setWebCourse(rs.getString(12));
				preCourse.setPayment(rs.getInt(13));
				preCourse.setComment(rs.getString(14));
				preCourse.setGender(rs.getString(15));
				preCourse.setYear(rs.getString(16));
				preCourses.add(preCourse);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preCourses;
	}

	@Override
	public boolean checkPrecourseStudent(String id) {
		
		final String SQL = "SELECT count(*) FROM precourse.pre_course WHERE pc_userid = ?";
		
		try(Connection cnn = ds.getConnection(); PreparedStatement pstmt = cnn.prepareStatement(SQL);) {
			
			pstmt.setInt(1, Integer.parseInt(Encryption.decode(id)));
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int count = rs.getInt("count");
			System.out.println(count);
			if(count>0){
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public PreCourse getPreCourse(String id) {
		
		PreCourse preCourse = new PreCourse();
		final String SQL = "SELECT * FROM precourse.pre_course WHERE pc_id = ?";
		
		try(Connection cnn = ds.getConnection(); PreparedStatement pstmt = cnn.prepareStatement(SQL);) {
			
			pstmt.setInt(1, Integer.parseInt(Encryption.decode(id)));
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				preCourse.setId(Encryption.encode(rs.getInt(1)+""));
				preCourse.setDateCreate(rs.getDate(2));
				preCourse.setUserId(Encryption.encode(rs.getInt(3)+""));
				preCourse.setUsername(rs.getString(4));
				preCourse.setEmail(rs.getString(5));
				preCourse.setTelephone(rs.getString(6));
				preCourse.setUniversity(rs.getString(7));
				preCourse.setDob(rs.getDate(8));
				preCourse.setPob(rs.getString(9));
				preCourse.setUserImage(rs.getString(10));
				preCourse.setJavaCourse(rs.getString(11));
				preCourse.setWebCourse(rs.getString(12));
				preCourse.setPayment(rs.getInt(13));
				preCourse.setComment(rs.getString(14));
				preCourse.setGender(rs.getString(15));
				preCourse.setYear(rs.getString(16));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preCourse;
	}

	@Override
	public PreCourse getPreCourseStudent(String uid) {
		
		PreCourse preCourse = new PreCourse();
		final String SQL = "SELECT * FROM precourse.pre_course WHERE pc_userid = ? limit 1";
		
		try(Connection cnn = ds.getConnection(); PreparedStatement pstmt = cnn.prepareStatement(SQL);) {
			
			pstmt.setInt(1, Integer.parseInt(Encryption.decode(uid)));
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				preCourse.setId(Encryption.encode(rs.getInt(1)+""));
				preCourse.setDateCreate(rs.getDate(2));
				preCourse.setUserId(Encryption.encode(rs.getInt(3)+""));
				preCourse.setUsername(rs.getString(4));
				preCourse.setEmail(rs.getString(5));
				preCourse.setTelephone(rs.getString(6));
				preCourse.setUniversity(rs.getString(7));
				preCourse.setDob(rs.getDate(8));
				preCourse.setPob(rs.getString(9));
				preCourse.setUserImage(rs.getString(10));
				preCourse.setJavaCourse(rs.getString(11));
				preCourse.setWebCourse(rs.getString(12));
				preCourse.setPayment(rs.getInt(13));
				preCourse.setComment(rs.getString(14));
				preCourse.setGender(rs.getString(15));
				preCourse.setYear(rs.getString(16));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preCourse;
	}
}
