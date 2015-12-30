package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.University;

public interface UniversityService {
	
	public boolean createUniverstiy(University university);
	public boolean updateUniversityById(University university);
	public boolean deleteUniversityById(String universityId);
	public List<University> findAllUniverstiyByName(Pagination pagination,String keyword); 
	public String findUniversityById(String universityId);
	public int countUniversity();

}
