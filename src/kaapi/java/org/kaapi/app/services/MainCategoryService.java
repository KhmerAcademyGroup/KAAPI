package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.MainCategory;

public interface MainCategoryService {
	public List<MainCategory> listMainCategory(String keyword);

	public MainCategory getMainCategory(int maincategoryid);

	public boolean insertMainCategory(MainCategory dto);

	public boolean updateMainCategory(MainCategory dto);

	public boolean deleteMainCategory(int maincategoryid);

	public int countMainCategories();

	public int countCategory(int maincategoryid);

	public int getMaxMaincategoryId();
}
