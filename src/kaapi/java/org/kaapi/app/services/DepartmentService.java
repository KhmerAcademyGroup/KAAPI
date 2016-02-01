package org.kaapi.app.services;
import java.util.List;

import org.kaapi.app.entities.Department;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.forms.FrmAddDepartment;
import org.kaapi.app.forms.FrmUpdateDepartment;
public interface DepartmentService {
	public boolean createDepartment(FrmAddDepartment department);
	public boolean updateDepartment(FrmUpdateDepartment department);
	public boolean deleteDepartment(String departmentId);
	public List<Department> listDepartment(Pagination pagination, String keyword);
	public String findDepartmentById(String departmentId);
	public int countDepartment(String keyword);
}
