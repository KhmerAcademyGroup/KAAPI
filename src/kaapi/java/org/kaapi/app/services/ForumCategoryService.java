package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.entities.ForumCategory;
import org.kaapi.app.entities.Pagination;

public interface ForumCategoryService {
	
	public List<ForumCategory> searchForumCate(String categoryName , Pagination pagination);
	public List<ForumCategory> listForumCate(Pagination pagination);
	public ForumCategory getForumCate(String id);
	public boolean deleteForumCate(String id);
	public boolean addForumCategory(ForumCategory froumCate);
	public boolean updateForumCate(ForumCategory forumCate);
	public int countSearchForumCate(String categoryName);
	public int countForumCate();
	
}
