package org.kaapi.app.services;

import java.util.ArrayList;

import org.kaapi.app.entities.PreCourse;

public interface PreCourseService {
	
	boolean addPreCourse(PreCourse preCourse);
	boolean deletePreCourse(String id);
	boolean editPreCourse(PreCourse preCourse);
	boolean updatePreCourse(PreCourse preCourse);
	ArrayList<PreCourse> getAllPreCourses();
	boolean checkPrecourseStudent(String id);
	PreCourse getPreCourse(String id);
	PreCourse getPreCourseStudent(String uid);
}
