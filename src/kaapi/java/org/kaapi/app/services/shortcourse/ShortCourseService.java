package org.kaapi.app.services.shortcourse;

import java.util.ArrayList;

import org.kaapi.app.entities.shortcourse.Course;
import org.kaapi.app.entities.shortcourse.FrmShortCourse;
import org.kaapi.app.entities.shortcourse.Shift;
import org.kaapi.app.entities.shortcourse.ShortCourse;

public interface ShortCourseService {
	
	public ArrayList<ShortCourse> getRegisteredStudents();
	
	public ShortCourse getRegisterdStudent(int id);
	
	public ArrayList<ShortCourse> getMyRegisteredCourses(int id);
	
	public boolean registerShortCourse(FrmShortCourse frmShortCourse);
	
	public boolean updateShortCourse(FrmShortCourse frmShortCourse);
	
	public boolean addShortCourse(FrmShortCourse frmShortCourse);
	
	public boolean deleteShortCourse(int id);
	
	public ArrayList<Course> getCourses();
	
	public ArrayList<Shift> getShifts();
	
	public ArrayList<String> getUniversities();
	
	public ShortCourse getCourse(int id);
	
	public boolean checkExistCourse(int courseId, int kaUserId, int generation);
	
	public boolean checkExistShift(int shiftId, int kaUserId, int generation);
	

}
