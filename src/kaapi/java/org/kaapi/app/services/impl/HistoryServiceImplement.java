package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.kaapi.app.entities.History;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Playlist;
import org.kaapi.app.services.HistoryService;
import org.kaapi.app.utilities.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HistoryServiceImplement implements HistoryService{
	
	@Autowired
	DataSource dataSource;
	Connection con;
	
	//test well
	@Override
	public ArrayList<History> list(String search, String uid, Pagination pagin) {
		try {
			con = dataSource.getConnection();
			ArrayList<History> historys =new ArrayList<History>();
			ResultSet rs = null;
			String sql = "SELECT h.historyid, h.historydate, h.videoid,"
					   + "u.userid , u.username, v.videoname, v.youtubeurl, v.description, v.viewcount "
					   + "FROM TBLHISTORY H "
					   + "INNER JOIN TBLVIDEO V ON H.VIDEOID=V.VIDEOID "
					   + "INNER JOIN tbluser u ON V.userid = u.userid where LOWER(v.videoname) like LOWER(?) and h.userid=? "
					   + "order by h.historydate desc offset ? limit ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "%"+search+"%");
			ps.setInt(2, Integer.parseInt(Encryption.decode(uid)));
			ps.setInt(3, pagin.getPage());
			ps.setInt(4, pagin.getItem());
			rs = ps.executeQuery();
			while(rs.next()){
				History history=new History();
				history.setHistoryId(Encryption.encode(rs.getString("historyid")));
				history.setHistoryDate(rs.getDate("historydate"));
				history.setUserId(Encryption.encode(rs.getString("userid")));
				history.setUsername(rs.getString("username"));
				history.setVideoId(Encryption.encode(rs.getString("videoid")));
				history.setVideoName(rs.getString("videoname"));
				history.setVideoUrl(rs.getString("youtubeurl"));
				history.setVideoDescription(rs.getString("description"));
				history.setVideoViewCount(rs.getString("viewcount"));
				historys.add(history);
			}
			return historys;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
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
			ps.setInt(1, Integer.parseInt(Encryption.decode(dto.getUserId())));
			ps.setInt(2, Integer.parseInt(Encryption.decode(dto.getVideoId())));
			if (ps.executeUpdate() > 0){
				System.out.println("update new history");
				return true;
			}else{
				sql = "INSERT INTO TBLHISTORY VALUES(NEXTVAL('seq_history'), NOW(), ?, ?)";
				PreparedStatement ps2 = con.prepareStatement(sql);
				ps2.setInt(1, Integer.parseInt(Encryption.decode(dto.getUserId())));
				ps2.setInt(2, Integer.parseInt(Encryption.decode(dto.getVideoId())));
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
	public boolean delete(String historyid) {
		try {
			con = dataSource.getConnection();
			String sql = "DELETE FROM TBLHISTORY WHERE historyid = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, Integer.parseInt(Encryption.decode(historyid)));
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
	public boolean deleteAll(String userid) {
		try {
			con = dataSource.getConnection();
			String sql = "DELETE FROM TBLHISTORY where userid=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, Integer.parseInt(Encryption.decode(userid)));
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
	
	/*well
	 * we want to know how many time use view each 
	 * video
	 */
	@Override
	public int count(String search, String userid) {
		try {
			con = dataSource.getConnection();
			String sql = "SELECT COUNT(H.historyid) FROM TBLHISTORY H INNER JOIN TBLUSER U  "
					+ "ON H.USERID=U.USERID INNER JOIN TBLVIDEO V ON H.VIDEOID=V.VIDEOID where "
					+ "LOWER(v.videoname) like LOWER(?) and U.userid=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, "%"+search+"%");
			ps.setInt(2, Integer.parseInt(Encryption.decode(userid)));
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
	@Override
	public ArrayList<History> listAllHistory(Pagination pagin) {
		try {
			ArrayList<History> histories = new ArrayList<History>();
			con = dataSource.getConnection();
			ResultSet rs = null;
			String sql =	 "SELECT * FROM tblhistory offset ? limit ?";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, pagin.getPage());
			ps.setInt(2, pagin.getItem());
			rs = ps.executeQuery();
			
			while(rs.next()){
				History history = new History();
				history.setHistoryId(Encryption.encode(rs.getString("historyid")));
				history.setHistoryDate(rs.getDate("historydate"));
				history.setUserId(Encryption.encode(rs.getString("userid")));
				history.setVideoId(Encryption.encode(rs.getString("videoid")));
				histories.add(history);
			}
			return histories;
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
	public ArrayList<History> userHistory(String uid, Pagination pagin) {
		try {
			con = dataSource.getConnection();
			ArrayList<History> historys =new ArrayList<History>();
			ResultSet rs = null;
			String sql = "SELECT h.historyid, h.historydate, h.videoid,"
					   + "u.userid , u.username, v.videoname, v.youtubeurl, v.description, v.viewcount "
					   + "FROM TBLHISTORY H "
					   + "INNER JOIN TBLVIDEO V ON H.VIDEOID=V.VIDEOID "
					   + "INNER JOIN tbluser u ON V.userid = u.userid where  h.userid=? "
					   + "order by h.historydate desc offset ? limit ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(uid)));
			ps.setInt(2, pagin.getPage());
			ps.setInt(3, pagin.getItem());
			rs = ps.executeQuery();
			while(rs.next()){
				History history=new History();
				history.setHistoryId(Encryption.encode(rs.getString("historyid")));
				history.setHistoryDate(rs.getDate("historydate"));
				history.setUserId(Encryption.encode(rs.getString("userid")));
				history.setUsername(rs.getString("username"));
				history.setVideoId(Encryption.encode(rs.getString("videoid")));
				history.setVideoName(rs.getString("videoname"));
				history.setVideoUrl(rs.getString("youtubeurl"));
				history.setVideoDescription(rs.getString("description"));
				history.setVideoViewCount(rs.getString("viewcount"));
				historys.add(history);
			}
			return historys;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	@Override
	public int userHistoryCount(String userid) {
		try {
			con = dataSource.getConnection();
			String sql = "SELECT COUNT(H.historyid) FROM TBLHISTORY H INNER JOIN TBLUSER U  "
					+ "ON H.USERID=U.USERID INNER JOIN TBLVIDEO V ON H.VIDEOID=V.VIDEOID where "
					+ "U.userid=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(userid)));
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
