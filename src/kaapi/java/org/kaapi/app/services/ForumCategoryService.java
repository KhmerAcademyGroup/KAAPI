package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.ForumCategory;
import org.kaapi.app.entities.Pagination;

public interface ForumCategoryService {
	
	public List<ForumCategory> listForumCate(Pagination pagination , String categoryName);
	public ForumCategory getForumCate(int id);
	public boolean deleteForumCate(int id);
	public boolean updateForumCate(int id);
	public int countForumCate(String categoryName);
	
}
