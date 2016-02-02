package org.kaapi.app.services;


import java.util.List;

import org.kaapi.app.entities.Category;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Video;
import org.kaapi.app.forms.FrmAddCategory;
import org.kaapi.app.forms.FrmUpdateCategory;



public interface CategoryService {

	public List<Category> listCategory(Pagination pagination,String keyword);
	public List<Category> listCategory();
	public Category getCategory(String categoryid);
	public boolean insertCategory(FrmAddCategory dto) ;
	public boolean updateCategory(FrmUpdateCategory dto);
	public boolean deleteCategory(String categoryid);
	public int countCategory();
	public int countVideoByCategory(String categoryid);
	public List<Video> listVideosInCategory(String categoryid, int page, int maxview);
	
	
}
