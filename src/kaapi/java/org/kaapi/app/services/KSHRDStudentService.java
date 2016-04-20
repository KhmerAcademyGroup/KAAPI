package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.forms.FrmStudentDetail;

public interface KSHRDStudentService {
	
	public List<FrmStudentDetail> listKSHRDStudent(FrmStudentDetail d , Pagination pagin);
	public int countKSHRDStudent(FrmStudentDetail d);

}
