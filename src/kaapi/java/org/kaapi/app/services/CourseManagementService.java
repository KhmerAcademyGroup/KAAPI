package org.kaapi.app.services;

import java.util.ArrayList;

import org.kaapi.app.entities.CourseVideoManagement;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Playlist;
import org.kaapi.app.forms.FrmUpdatePlaylist;

public interface CourseManagementService {
	
	 public ArrayList<Playlist> listCourses(String playlistName , String mainCategoryId,Pagination pagination);
	 public int countCourse(String playlistName , String mainCategoryId);
	 public ArrayList<CourseVideoManagement> listVideosInCourse(String curseId,Pagination pagination);
	 public Playlist getCourse(String courseId);
	 public boolean updateCourse(FrmUpdatePlaylist p);
	 public boolean updateStatus(String courseId, boolean value);
	

}
