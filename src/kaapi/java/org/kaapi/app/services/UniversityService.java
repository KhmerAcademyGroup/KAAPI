package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.University;

public interface UniversityService {
	
	public boolean createUniverstiy();
	public boolean updateUniversityById(University university);
	public boolean deleteUniversityById(int id);
	public List<University> findAllUniverstiyByName(Pagination pagination,String keyword); 
	public University findUniversityById(int id);
	public int countUniversity();

}
