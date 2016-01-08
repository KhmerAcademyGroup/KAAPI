package org.kaapi.app.services;

import java.util.ArrayList;

import org.kaapi.app.entities.Log;

public interface LogService {
	
	public int insert(Log dto);
	public boolean stopWatching(Log dto);
	public ArrayList<Log> listUserInCategory(String categoryid);
	public ArrayList<Log> listCategory();
	public ArrayList<Log> listCategoryInUser(String userid);
	public ArrayList<Log> listUserInDepartmentAndUniversity(String departmentid, String universityid);
	public ArrayList<Log> listDeparmentByUniversity(String universityid);
	public ArrayList<Log> listUniversity();

}
