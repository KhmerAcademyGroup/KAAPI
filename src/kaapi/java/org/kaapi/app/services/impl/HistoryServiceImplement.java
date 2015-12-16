package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.kaapi.app.entities.History;
import org.kaapi.app.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HistoryServiceImplement implements HistoryService{
	
	@Autowired
	DataSource dataSource;
	Connection con;
	
	@Override
	public ResultSet list(String search, int id, int offset, int limit) {
		try {
			ResultSet rs = null;
			String sql =	 "SELECT h.historyid, h.historydate, h.videoid,"
						   + "u.userid , u.username, v.videoname, v.youtubeurl, v.description, v.viewcount "
						   + "FROM TBLHISTORY H "
						   + "INNER JOIN TBLVIDEO V ON H.VIDEOID=V.VIDEOID "
						   + "INNER JOIN tbluser u ON V.userid = u.userid where v.videoname like ? and h.userid=? "
						   + "order by h.historydate desc offset ? limit ?";
			
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(2, id);
			ps.setString(1, "%"+search+"%");
			ps.setInt(3, (offset-1)*limit);
			ps.setInt(4, limit);
			rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public boolean insert(History dto) {
		try {
			String sql = "UPDATE TBLHISTORY SET historydate=NOW() WHERE userid=? AND videoid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, dto.getUserId());
			ps.setInt(2, dto.getVideoId());
			if (ps.executeUpdate() > 0){
				return true;
			}else{
				sql = "INSERT INTO TBLHISTORY VALUES(NEXTVAL('seq_history'), NOW(), ?, ?)";
				PreparedStatement ps2 = con.prepareStatement(sql);
				ps2.setInt(1, dto.getUserId());
				ps2.setInt(2, dto.getVideoId());
				if (ps2.executeUpdate() > 0)
					return true;
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean delete(int historyid) {
		try {
			String sql = "DELETE FROM TBLHISTORY WHERE historyid = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, historyid);
			if (stmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean deleteAll(int userid) {
		try {
			String sql = "DELETE FROM TBLHISTORY where userid=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, userid);
			if (stmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public int count(String search, int userid) {
		try {
			String sql = "SELECT COUNT(H.historyid) FROM TBLHISTORY H INNER JOIN TBLUSER U  "
					+ "ON H.USERID=U.USERID INNER JOIN TBLVIDEO V ON H.VIDEOID=V.VIDEOID where "
					+ "v.videoname like ? and U.userid=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, "%"+search+"%");
			ps.setInt(2, userid);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	
	}

}
