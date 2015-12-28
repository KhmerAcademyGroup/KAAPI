package org.kaapi.app.services;
import java.util.List;

import org.kaapi.app.entities.Department;
import org.kaapi.app.entities.Pagination;
public interface DepartmentService {
	public boolean createDepartment(Department department);
	public boolean updateDepartment(Department department);
	public boolean deleteDepartment(int id);
	public List<Department> listDepartment(Pagination pagination, String keyword);
	public int countDepartment();
}
