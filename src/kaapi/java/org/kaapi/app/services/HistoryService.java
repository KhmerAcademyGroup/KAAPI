package org.kaapi.app.services;

import org.kaapi.app.entities.History;
import org.kaapi.app.entities.Pagination;

public interface HistoryService {
	public History list(String search ,String uid , Pagination pagin);
	public boolean insert(History dto);
	public boolean delete(String historyid);
	public boolean deleteAll(String userid);
	public int count(String search , String userid);
	
}
