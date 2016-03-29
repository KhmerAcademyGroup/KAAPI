package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.CourseVideoManagement;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Playlist;

public interface CourseManagementService {
	
	 public List<Playlist> listCourses(String mainCategoryId,Pagination pagination);
	 public List<CourseVideoManagement> listVideosInCourse(String curseId,Pagination pagination);
	

}
