package org.kaapi.app.services;

import java.util.ArrayList;

import org.kaapi.app.entities.PreCourse;

public interface PreCourseService {
	
	boolean addPreCourse(PreCourse preCourse);
	boolean deletePreCourse(int id);
	boolean editPreCourse(PreCourse preCourse);
	boolean updatePreCourse(PreCourse preCourse);
	ArrayList<PreCourse> getAllPreCourses();
	boolean checkPrecourseStudent(int id);
	PreCourse getPreCourse(int id);
	PreCourse getPreCourseStudent(int uid);
}
