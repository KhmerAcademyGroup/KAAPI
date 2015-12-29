package org.kaapi.app.services.impl;

import java.util.List;

import javax.sql.DataSource;

import org.kaapi.app.entities.ForumCategory;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.services.ForumCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ForumCategoryServiceImpl implements ForumCategoryService {

	@Autowired
	DataSource dataSource;
	
	@Override
	public List<ForumCategory> listForumCate(Pagination pagination, String categoryName) {
//		String sql = "";
//		try(
//			
//		){
//			
//		}try(){
//			
//		}
		return null;
	}

	@Override
	public ForumCategory getForumCate(int id) {
		return null;
	}

	@Override
	public boolean deleteForumCate(int id) {
		return false;
	}

	@Override
	public boolean updateForumCate(int id) {
		return false;
	}

	@Override
	public int countForumCate(String categoryName) {
		return 0;
	}

}
