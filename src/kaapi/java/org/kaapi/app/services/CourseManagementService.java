package org.kaapi.app.services;

import java.util.ArrayList;
import java.util.List;

import org.kaapi.app.entities.CourseVideoManagement;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Playlist;

public interface CourseManagementService {
	
	 public ArrayList<Playlist> listCourses(String mainCategoryId,Pagination pagination);
	 public int countCourse(String mainCategoryId);
	 public ArrayList<CourseVideoManagement> listVideosInCourse(String curseId,Pagination pagination);
	

}
