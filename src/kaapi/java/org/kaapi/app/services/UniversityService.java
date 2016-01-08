package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.University;
import org.kaapi.app.forms.FrmAddUniversity;
import org.kaapi.app.forms.FrmUpdateUniversity;

public interface UniversityService {
	
	public boolean createUniverstiy(FrmAddUniversity university);
	public boolean updateUniversityById(FrmUpdateUniversity university);
	public boolean deleteUniversityById(String universityId);
	public List<University> findAllUniverstiyByName(Pagination pagination,String keyword); 
	public String findUniversityById(String universityId);
	public int countUniversity();

}
