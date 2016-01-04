package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.kaapi.app.entities.History;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HistoryServiceImplement implements HistoryService{
	
	@Autowired
	DataSource dataSource;
	Connection con;
	
	//test well
	@Override
	public History list(String search, int uid, Pagination pagin) {
		try {
			con = dataSource.getConnection();
			ResultSet rs = null;
			History history = new History();
			String sql =	 "SELECT h.historyid, h.historydate, h.videoid,"
						   + "u.userid , u.username, v.videoname, v.youtubeurl, v.description, v.viewcount "
						   + "FROM TBLHISTORY H "
						   + "INNER JOIN TBLVIDEO V ON H.VIDEOID=V.VIDEOID "
						   + "INNER JOIN tbluser u ON V.userid = u.userid where LOWER(v.videoname) like LOWER(?) and h.userid=? "
						   + "order by h.historydate desc offset ? limit ?";
			
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "%"+search+"%");
			ps.setInt(2, uid);
			ps.setInt(3, pagin.getPage());
			ps.setInt(4, pagin.getItem());
			rs = ps.executeQuery();
			if(rs.next()){
				history.setUserId(rs.getInt("historyid"));
				history.setHistoryDate(rs.getDate("historydate"));
				history.setUserId(rs.getInt("userid"));
				history.setUsername(rs.getString("username"));
				history.setVideoId(rs.getInt("videoid"));
				history.setVideoName(rs.getString("videoname"));
				history.setVideoUrl(rs.getString("youtubeurl"));
				history.setVideoDescription(rs.getString("description"));
				history.setVideoViewCount(rs.getString("viewcount"));
				return history;
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
		return null;
	}
	//well
	@Override
	public boolean insert(History dto) {
		try {
			con = dataSource.getConnection();
			String sql = "UPDATE TBLHISTORY SET historydate=NOW() WHERE userid=? AND videoid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, dto.getUserId());
			ps.setInt(2, dto.getVideoId());
			if (ps.executeUpdate() > 0){
				System.out.println("update new history");
				return true;
			}else{
				sql = "INSERT INTO TBLHISTORY VALUES(NEXTVAL('seq_history'), NOW(), ?, ?)";
				PreparedStatement ps2 = con.prepareStatement(sql);
				ps2.setInt(1, dto.getUserId());
				ps2.setInt(2, dto.getVideoId());
				if (ps2.executeUpdate() > 0)
					System.out.println("insert new history");
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

	//well
	@Override
	public boolean delete(int historyid) {
		try {
			con = dataSource.getConnection();
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
	// well
	@Override 
	public boolean deleteAll(int userid) {
		try {
			con = dataSource.getConnection();
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
	//well
	@Override
	public int count(String search, int userid) {
		try {
			con = dataSource.getConnection();
			String sql = "SELECT COUNT(H.historyid) FROM TBLHISTORY H INNER JOIN TBLUSER U  "
					+ "ON H.USERID=U.USERID INNER JOIN TBLVIDEO V ON H.VIDEOID=V.VIDEOID where "
					+ "LOWER(v.videoname) like LOWER(?) and U.userid=?";
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
