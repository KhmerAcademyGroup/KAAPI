package org.kaapi.app.services;

import java.util.ArrayList;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.PreCourse;
import org.kaapi.app.forms.FrmAddPreCourse;
import org.kaapi.app.forms.FrmEditPreCourse;
import org.kaapi.app.forms.FrmUpdatePreCourse;

public interface PreCourseService {
	
	boolean addPreCourse(FrmAddPreCourse preCourse);
	boolean deletePreCourse(String id);
	boolean updatePreCourse(FrmUpdatePreCourse preCourse);
	ArrayList<PreCourse> getAllPreCourses(Pagination pg);
	boolean checkPrecourseStudent(String id);
	PreCourse getPreCourse(String id);
	PreCourse getPreCourseStudent(String uid);
	int countPreCourse();
	
}
