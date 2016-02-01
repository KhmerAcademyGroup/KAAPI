package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.MainCategory;
import org.kaapi.app.forms.FrmAddMainCategory;
import org.kaapi.app.forms.FrmUpdateMainCategory;

public interface MainCategoryService {
	public List<MainCategory> listMainCategory(String keyword);

	public MainCategory getMainCategory(String maincategoryid);

	public boolean insertMainCategory(FrmAddMainCategory dto);

	public boolean updateMainCategory(FrmUpdateMainCategory dto);

	public boolean deleteMainCategory(String maincategoryid);

	public int countMainCategories();

	public int countCategory(String maincategoryid);

	public int getMaxMaincategoryId();
}
