package org.kaapi.app.services.impl.shortcourse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.kaapi.app.entities.shortcourse.Course;
import org.kaapi.app.entities.shortcourse.CourseType;
import org.kaapi.app.entities.shortcourse.FrmShortCourse;
import org.kaapi.app.entities.shortcourse.Shift;
import org.kaapi.app.entities.shortcourse.ShortCourse;
import org.kaapi.app.entities.shortcourse.Student;
import org.kaapi.app.services.shortcourse.ShortCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ShortCourseImpl implements ShortCourseService{

	@Autowired
	private DataSource ds;
	
	private Connection con;
		
	@Override
	public ArrayList<ShortCourse> getRegisteredStudents() {

		ArrayList<ShortCourse> shortCourses = new ArrayList<ShortCourse>();
		
		try {
			con = ds.getConnection();
			ResultSet rs = null;	
			
			String sql = "SELECT " +
						    "SD.id, S.fullname, S.phonenumber, S.email, SD.ispaid, SD.register_date, SD.update_date , " + 
							"SC.course, SS.shift, ST.type " + 
							"FROM shortcourse.tblstudent_detail SD " +
							"LEFT JOIN shortcourse.tblstudent S ON S.id = SD.student_id " + 
							"LEFT JOIN shortcourse.tblcourse SC ON SD.course_id = SC.id " +
							"LEFT JOIN shortcourse.tblshift SS ON SD.shift_id = SS.id " +
							"LEFT JOIN shortcourse.tbltype ST ON SD.type_id = ST.id " + 
							"WHERE SD.ka_userid<>104 AND SD.status = true"; 
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				ShortCourse shortCourse = new ShortCourse();
				shortCourse.setCourse(new Course(rs.getString("course")));
				shortCourse.setCourseType(new CourseType(0, rs.getString("type")));
				shortCourse.setPaid(rs.getBoolean("ispaid"));
				
				Student student = new Student();
				student.setFullName(rs.getString("fullname"));
				student.setPhone(rs.getString("phonenumber"));
				student.setEmail(rs.getString("email"));
				
				shortCourse.setStudent(student);
				shortCourse.setShift(new Shift(0,"",rs.getString("shift")));
				
				
				shortCourse.setRegisterDate(rs.getTimestamp("register_date"));
					
				shortCourses.add(shortCourse);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return shortCourses;
	}

	@Override
	public boolean registerShortCourse(FrmShortCourse frmShortCourse) {
		try {
			
			con = ds.getConnection();
			
			String sql = "INSERT INTO shortcourse.tblstudent_detail(student_id, course_id, shift_id, type_id, ka_userid, generation) VALUES(?,?,?,?,?,?)"; 
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, frmShortCourse.getFrmStudent().getId());
			ps.setInt(2, frmShortCourse.getCourseId());
			ps.setInt(3, frmShortCourse.getShiftId());
			ps.setInt(4, frmShortCourse.getTypeId());
			ps.setInt(5, frmShortCourse.getKaUserId());
			ps.setInt(6, frmShortCourse.getGeneration());
			if(ps.executeUpdate()>0)
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

	@Override
	public boolean updateShortCourse(FrmShortCourse shortCourse) {
			
		String sql =  "UPDATE shortcourse.tblstudent_detail "
					+ "SET course_id=?, shift_id=? "
					+ "WHERE id=?";
		
		try {
			con = ds.getConnection();
			PreparedStatement p = con.prepareStatement(sql);
			p.setInt(1, shortCourse.getCourseId());
			p.setInt(2, shortCourse.getShiftId());
			p.setInt(3, shortCourse.getId());
			
			if(p.executeUpdate()>0)
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

	@Override
	public boolean deleteShortCourse(int id) {

		String sql = "DELETE FROM shortcourse.tblstudent_detail WHERE id=?";
		
		try {
			con = ds.getConnection();
			PreparedStatement p = con.prepareStatement(sql);
			p.setInt(1, id);
			if(p.executeUpdate()>0)
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

	@Override
	public ShortCourse getRegisterdStudent(int id) {
		
		
		return null;
	}

	@Override
	public ArrayList<Course> getCourses() {
		String sql = "SELECT id, course FROM shortcourse.tblcourse ORDER BY id ASC";
		
		ArrayList<Course> courses = new ArrayList<Course>();
		
		try {
			con = ds.getConnection();
			ResultSet rs = null;	
			PreparedStatement ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				Course course = new Course();
				course.setId(rs.getInt("id"));
				course.setName(rs.getString("course"));
				
				courses.add(course);
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return courses;
	}

	@Override
	public ArrayList<Shift> getShifts() {

		String sql = "SELECT id, shift, decription FROM shortcourse.tblshift ORDER BY id ASC";
		ArrayList<Shift> shifts = new ArrayList<Shift>(); 
		try {
			con = ds.getConnection();
			ResultSet rs = null;	
			
			PreparedStatement ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()){
				Shift shift = new Shift();
				shift.setId(rs.getInt("id"));
				shift.setShift(rs.getString("decription"));
				shift.setTime(rs.getString("shift"));
				shifts.add(shift);
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return shifts;
	}

	@Override
	public ArrayList<String> getUniversities() {
		
		ArrayList<String> universities = new ArrayList<String>();
		
		String sql = "SELECT universityid, universityname FROM public.tbluniversity";
		try {
			con = ds.getConnection();
			ResultSet rs = null;	
			
			PreparedStatement ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				universities.add(rs.getString("universityname"));
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return universities;
	}
	
	@Override
	public ArrayList<ShortCourse> getMyRegisteredCourses(int id) {
		
		ArrayList<ShortCourse> shortCourses = new ArrayList<ShortCourse>();
		
		try {
			con = ds.getConnection();
			ResultSet rs = null;	
			
			String sql = "SELECT " +
						    "SD.id, S.fullname, S.phonenumber, S.email, SD.ispaid, SD.register_date, SD.update_date , " + 
							"SC.course, SS.shift, ST.type " + 
							"FROM shortcourse.tblstudent_detail SD " +
							"LEFT JOIN shortcourse.tblstudent S ON S.id = SD.student_id " + 
							"LEFT JOIN shortcourse.tblcourse SC ON SD.course_id = SC.id " +
							"LEFT JOIN shortcourse.tblshift SS ON SD.shift_id = SS.id " +
							"LEFT JOIN shortcourse.tbltype ST ON SD.type_id = ST.id " + 
							"WHERE SD.ka_userid=? AND SD.status = true"; 
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				ShortCourse shortCourse = new ShortCourse();
				shortCourse.setId(rs.getInt("id"));
				
				Student student = new Student();
				student.setFullName(rs.getString("fullname"));
				student.setPhone(rs.getString("phonenumber"));
				student.setEmail(rs.getString("email"));
				
				shortCourse.setStudent(student);
				shortCourse.setRegisterDate(rs.getTimestamp("register_date"));
				shortCourse.setUpdateDate(rs.getTimestamp("update_date"));
				
				Course course = new Course();
				course.setName(rs.getString("course"));
				shortCourse.setCourse(course);
				
				Shift shift = new Shift();
				shift.setTime(rs.getString("shift"));
				shortCourse.setShift(shift);
				
				shortCourses.add(shortCourse);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return shortCourses;
	}

	@Override
	public ShortCourse getCourse(int id) {
		try {
			con = ds.getConnection();
			ResultSet rs = null;	
			
			String sql =  "SELECT sd. ID, C . ID AS cid, s. ID AS sid, C .course, s.shift "
						+ "FROM shortcourse.tblstudent_detail sd "
						+ "LEFT JOIN shortcourse.tblshift s ON sd.shift_id = s. ID " 
						+ "LEFT JOIN shortcourse.tblcourse C ON sd.course_id = C . ID "
						+ "WHERE sd. ID = ?"; 
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				ShortCourse shortCourse = new ShortCourse();
				shortCourse.setId(rs.getInt("id"));
				
				Course course = new Course();
				course.setId(rs.getInt("cid"));
				course.setName(rs.getString("course"));
				shortCourse.setCourse(course);
				
				Shift shift = new Shift();
				shift.setId(rs.getInt("sid"));
				shift.setTime(rs.getString("shift"));
				shortCourse.setShift(shift);
				
				return shortCourse;
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public boolean addShortCourse(FrmShortCourse frmShortCourse) {
		System.out.println(frmShortCourse.toString());
		try {
			String sql =  "INSERT INTO shortcourse.tblstudent_detail (student_id,course_id,shift_id,type_id,ka_userid,generation) "
						+ "VALUES ((SELECT DISTINCT id FROM shortcourse.tblstudent WHERE ka_userid=?), ?, ?, ?, ?, ?)";
	
			con = ds.getConnection();
			PreparedStatement p = con.prepareStatement(sql);
			
			p.setInt(1, frmShortCourse.getKaUserId());
			p.setInt(2, frmShortCourse.getCourseId());
			p.setInt(3, frmShortCourse.getShiftId());
			p.setInt(4, frmShortCourse.getTypeId());
			p.setInt(5, frmShortCourse.getKaUserId());
			p.setInt(6, frmShortCourse.getGeneration());
			
			if(p.executeUpdate()>0)
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

	@Override
	public boolean checkExistCourse(int courseId, int kaUserId, int generation) {

		try {
			String sql =  "SELECT COUNT (*) FROM shortcourse.tblstudent_detail WHERE (ka_userid = ? AND course_id = ? AND generation = ?)";
	
			con = ds.getConnection();
			PreparedStatement p = con.prepareStatement(sql);
			p.setInt(1, kaUserId);
			p.setInt(2, courseId);
			p.setInt(3, generation);
			ResultSet rs = p.executeQuery();
			rs.next();
			System.out.println("ExistCourse : " + rs.getInt(1));
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

	@Override
	public boolean checkExistShift(int shiftId, int kaUserId, int generation) {
		try {
			String sql =  "SELECT COUNT (*) FROM shortcourse.tblstudent_detail WHERE (ka_userid = ? AND shift_id = ? AND generation = ?)";
	
			con = ds.getConnection();
			PreparedStatement p = con.prepareStatement(sql);
			p.setInt(1, kaUserId);
			p.setInt(2, shiftId);
			p.setInt(3, generation);
			ResultSet rs = p.executeQuery();
			rs.next();
			System.out.println("ExistShift : " + rs.getInt(1));
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
