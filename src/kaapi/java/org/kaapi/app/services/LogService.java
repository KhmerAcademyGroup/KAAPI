package org.kaapi.app.services;

import java.util.ArrayList;

import org.kaapi.app.entities.Log;

public interface LogService {
	
	public int insert(Log dto);
	public boolean stopWatching(Log dto);
	public ArrayList<Log> listUserInCategory(int categoryid);
	public ArrayList<Log> listCategory();
	public ArrayList<Log> listCategoryInUser(int userid);
	public ArrayList<Log> listUserInDepartmentAndUniversity(int departmentid, int universityid);
	public ArrayList<Log> listDeparmentByUniversity(int universityid);
	public ArrayList<Log> listUniversity();

}
