package org.kaapi.app.services;

import java.util.ArrayList;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Tutorial;

public interface TutorialService {
	public ArrayList<Tutorial> lists(int userid, Pagination pagination);
	public ArrayList<Tutorial> list(int categoryid);
	public Tutorial get(int tutorialid);
	public Tutorial getFirstDetail(int categoryid);
	public boolean insert(Tutorial dto);
	public boolean update(Tutorial dto);
	public boolean delete(int tutorialid);
	public int count();
	public int count(int categoryid);
	
}
