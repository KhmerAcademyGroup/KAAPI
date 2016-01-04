package org.kaapi.app.services;

import org.kaapi.app.entities.History;
import org.kaapi.app.entities.Pagination;

public interface HistoryService {
	public History list(String search ,int uid , Pagination pagin);
	public boolean insert(History dto);
	public boolean delete(int historyid);
	public boolean deleteAll(int userid);
	public int count(String search , int userid);
	
}
