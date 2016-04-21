package org.kaapi.app.services.shortcourse;

import org.kaapi.app.entities.shortcourse.FrmStudent;

public interface StudentService {
	
	public int insertStudent(FrmStudent student);
	
	public int getCurrentStudentId();
	
	public boolean isExist(int id);
	
}
	