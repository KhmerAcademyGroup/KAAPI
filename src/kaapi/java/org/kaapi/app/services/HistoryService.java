package org.kaapi.app.services;

import java.util.ArrayList;

import org.kaapi.app.entities.History;
import org.kaapi.app.entities.Pagination;

public interface HistoryService {
	public ArrayList<History> listAllHistory(Pagination pagin);
	public ArrayList<History> list(String search ,String uid , Pagination pagin);
	public ArrayList<History> userHistory(String uid , Pagination pagin);
	public boolean insert(History dto);
	public boolean delete(String historyid);
	public boolean deleteAll(String userid);
	public int count(String search , String userid);
	public int userHistoryCount(String userid);
	
}
