package org.kaapi.app.services;

import java.sql.ResultSet;
import java.util.List;

import org.kaapi.app.entities.Category;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Video;



public interface CategoryService {

	public List<Category> listCategory(Pagination pagination,String keyword);
	public Category getCategory(String categoryid);
	public boolean insertCategory(Category dto) ;
	public boolean updateCategory(Category dto);
	public boolean deleteCategory(String categoryid);
	public int countCategory();
	public int countVideoByCategory(String categoryid);
	public List<Video> listVideosInCategory(String categoryid, int page, int maxview);
	
	
}
