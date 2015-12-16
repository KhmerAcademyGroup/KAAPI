package org.kaapi.app.services;

import java.sql.ResultSet;

import org.kaapi.app.entities.History;

public interface HistoryService {
	public ResultSet list(String search ,int id , int offset , int limit);
	public boolean insert(History dto);
	public boolean delete(int historyid);
	public boolean deleteAll(int userid);
	public int count(String search , int userid);
}
