package org.kaapi.app.services;

import java.util.ArrayList;

import org.kaapi.app.entities.Category;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Tutorial;
import org.kaapi.app.forms.FrmTutorial;
import org.kaapi.app.forms.FrmUpdateTutorial;

public interface TutorialService {
	public ArrayList<Tutorial> lists(String userid, Pagination pagination);
	public ArrayList<Tutorial> list(String categoryid);
	public Tutorial get(String tutorialid);
	public Tutorial getFirstDetail(String categoryid);
	public boolean insert(FrmTutorial dto);
	public boolean update(FrmUpdateTutorial dto);
	public boolean delete(String tutorialid);
	public int count();
	public int countByUser(String userid);
	public int count(String categoryid);
	public ArrayList<Category> listCategories();
}
