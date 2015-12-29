package org.kaapi.app.services;

import java.util.ArrayList;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Tutorial;

public interface TutorialService {
	public ArrayList<Tutorial> lists(String userid, Pagination pagination);
	public ArrayList<Tutorial> list(String categoryid);
	public Tutorial get(String tutorialid);
	public Tutorial getFirstDetail(String categoryid);
	public boolean insert(Tutorial dto);
	public boolean update(Tutorial dto);
	public boolean delete(String tutorialid);
	public int count();
	public int count(String categoryid);
	
}
