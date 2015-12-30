package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Playlist;
import org.kaapi.app.services.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;





@Service("PlayListService")
public class PlayListServiceImplement implements PlayListService{
	@Autowired
	DataSource dataSource;
	Connection con;
	
	@Override
	public ArrayList<Playlist> list(Pagination pagin, Playlist dto) {
		try {
			ResultSet rs = null;
			String sql = "SELECT P.*, U.username, COUNT(DISTINCT PD.videoid) countvideos FROM TBLPLAYLIST P INNER JOIN TBLUSER U ON P.userid=U.userid "
					+ "LEFT JOIN TBLPLAYlISTDETAIL PD ON P.playlistid=PD.playlistid "
					+ "WHERE P.playlistname LIKE ? and  U.Userid = ? GROUP BY P.playlistid, U.username order by  P.playlistid desc offset ? limit ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "%"+dto.getPlaylistName()+"%");
			ps.setInt(2, dto.getUserId());
			pagin.getCurrentPage();
			ps.setInt(4, pagin.getPerPage());
			rs = ps.executeQuery();
			ArrayList<Playlist> playlists =new ArrayList<Playlist>();
			while(rs.next()){
				Playlist playlist = new Playlist();
				playlist.setPlaylistId(rs.getInt("playlistid"));
				playlist.setPlaylistName(rs.getString("playlistname"));
				playlist.setDescription(rs.getString("description"));
				playlist.setUserId(rs.getInt("userid"));
				playlist.setThumbnailUrl(rs.getString("thumbnailurl"));
				playlist.setPublicView(rs.getBoolean("publicview"));
				playlist.setMaincategory(rs.getInt("maincategory"));
				playlist.setBgImage(rs.getString("bgimage"));
				playlist.setColor(rs.getString("color"));
				playlist.setStatus(rs.getBoolean("status"));
				playlists.add(playlist);
			}
			return playlists;
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
	public ArrayList<Playlist> listVideoInPlaylist(int playlistid, Pagination pagin) {
		try {
			ArrayList<Playlist> playlists =new ArrayList<Playlist>();
			ResultSet rs = null;
			String sql = "SELECT PL.*, V.*, U.USERNAME, CC.CATEGORYNAMES, COUNT(DISTINCT C.VIDEOID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS, COUNT(DISTINCT VM.*) COUNTVOTEMINUS, PD.INDEX ,V.publicview  ispublic "
					+ "FROM TBLVIDEO V LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
					+ "LEFT JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT LEFT JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid GROUP BY CV.videoid) CC ON V.videoid=CC.videoid "
					+ "LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID "
					+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
					+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID "
					+ "INNER JOIN TBLPLAYLISTDETAIL PD ON PD.VIDEOID=V.VIDEOID "
					+ "INNER JOIN tblplaylist PL ON PD.PLAYLISTID = PL.playlistid "
					+ "WHERE PD.PLAYLISTID=? "
					+ "GROUP BY V.VIDEOID, U.USERNAME, CC.CATEGORYNAMES, PD.INDEX , PL.playlistid "
					+ "ORDER BY PD.INDEX  offset ? limit ? ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, playlistid);
			ps.setInt(2, pagin.offset());
			ps.setInt(3, pagin.getPerPage());
			rs = ps.executeQuery();
			return playlists;
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

	
	
	//get playlist
	@Override
	public String getPlaylistName(int playlistid) {
		try{	
				String pname= "";
				ResultSet rs = null;
				String sql = "select playlistname from tblplaylist where playlistid=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, playlistid);
				rs = ps.executeQuery();
				if(rs.next()){
					pname = rs.getString("playlistname");
				}
				return pname;
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

	//isin need
	@Override
	public ArrayList<Playlist> listVideo(int playlistid) {
		try {
			ArrayList<Playlist> playlists =new ArrayList<Playlist>();
			ResultSet rs = null;
			String sql = "SELECT V.*, U.USERNAME, CC.CATEGORYNAMES, COUNT(DISTINCT C.VIDEOID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS, COUNT(DISTINCT VM.*) COUNTVOTEMINUS, PD.INDEX "
					+ "FROM TBLVIDEO V LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
					+ "LEFT JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT LEFT JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid GROUP BY CV.videoid) CC ON V.videoid=CC.videoid "
					+ "LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID "
					+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
					+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID "
					+ "INNER JOIN TBLPLAYLISTDETAIL PD ON PD.VIDEOID=V.VIDEOID "
					+ "WHERE PD.PLAYLISTID=? "
					+ "GROUP BY V.VIDEOID, U.USERNAME, CC.CATEGORYNAMES, PD.INDEX "
					+ "ORDER BY PD.INDEX";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, playlistid);
			rs = ps.executeQuery();
			return playlists;
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
	public ArrayList<Playlist> listplaylistname(Playlist dto) {
		try {
			ArrayList<Playlist> playlists =new ArrayList<Playlist>();
			ResultSet rs = null;
			String sql = "select playlistid , playlistname,publicview from tblplaylist where playlistname like  ? and userid = ?  order by playlistid desc";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "%"+dto.getPlaylistName()+"%");
			ps.setInt(2, dto.getUserId());
			rs = ps.executeQuery();
			return playlists;
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
	public ArrayList<Playlist> listplaylistbyPublicView(boolean publicview) {
		try {
			ArrayList<Playlist> playlists =new ArrayList<Playlist>();
			String sql = "select playlistid,playlistname  from tblplaylist where publicview= ? order by playlistid desc";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setBoolean(1,publicview);
			ResultSet rs = ps.executeQuery();
			return playlists;
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
	public ArrayList<Playlist> listplaylistbyAdmin(boolean publicview) {
		try {
				ArrayList<Playlist> playlists =new ArrayList<Playlist>();
				String sql = "select playlistid, playlistname from tblplaylist P inner join tbluser U on P.userid=U.userid inner join tblusertype UT on U.usertypeid=UT.usertypeid where publicview=? and UT.userable=true order by playlistid desc";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setBoolean(1,publicview);
				ResultSet rs = ps.executeQuery();
				return playlists;
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
	public ArrayList<Playlist> listplaylistdetail(int userid) {
		try {
			ArrayList<Playlist> playlists =new ArrayList<Playlist>();
			ResultSet rs = null;
			String sql = "select D.playlistid , D.videoid , U.userid from TBLPLAYlISTDETAIL D "
					+ " INNER JOIN TBLPLAYLIST L ON D.playlistid = L.playlistid "
					+ " INNER JOIN TBLUSER U ON L.userid = L.userid "
					+ " Where U.Userid = ? order by D.Playlistid desc ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			return playlists;
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
	public ArrayList<Playlist> listplaylistdetail(int userid, int playlistid) {
		try {
			ArrayList<Playlist> playlists =new ArrayList<Playlist>();
			ResultSet rs = null;
			String sql = "select D.playlistid , D.videoid , U.userid from TBLPLAYlISTDETAIL D "
					+ " INNER JOIN TBLPLAYLIST L ON D.playlistid = L.playlistid "
					+ " INNER JOIN TBLUSER U ON L.userid = L.userid "
					+ " Where U.Userid = ? and D.playlistid = ? order by D.Playlistid desc ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, playlistid);
			rs = ps.executeQuery();
			return playlists;
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
	public Playlist get(int playlistid) {
		try {
			String sql = "SELECT P.*, U.username, COUNT(DISTINCT PD.videoid) countvideos FROM TBLPLAYLIST P INNER JOIN TBLUSER U ON P.userid=U.userid "
					+ "LEFT JOIN TBLPLAYlISTDETAIL PD ON P.playlistid=PD.playlistid "
					+ "WHERE P.playlistid="+playlistid+" GROUP BY P.playlistid, U.username";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				//Playlist dto = new Playlist(playlistid, rs.getString("playlistname"), rs.getString("description"), rs.getInt("userid"), rs.getString("thumbnailurl"), rs.getBoolean("publicview"));
				
				//change to seter method
				Playlist dto = new Playlist();
				dto.setPlaylistId(playlistid);
				dto.setPlaylistName(rs.getString("playlistname"));
				dto.setDescription(rs.getString("description"));
				dto.setUserId(rs.getInt("userid"));
				dto.setThumbnailUrl(rs.getString("thumbnailurl"));
				dto.setPublicView(rs.getBoolean("publicview"));
				
				dto.setUsername(rs.getString("username"));
				dto.setCountVideos(rs.getInt("countvideos"));
				return dto;
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
		return null;
	}

	@Override
	public Playlist getPlaylistForUpdate(int playlistid) {
		try {
			String sql = "select * from tblplaylist where playlistid = "+playlistid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				//Playlist dto = new Playlist(playlistid, rs.getString("playlistname"), rs.getString("description"), rs.getInt("userid"), rs.getString("thumbnailurl"), rs.getBoolean("publicview"));
				
				//change to seter method
				Playlist dto =new Playlist();
				dto.setPlaylistName(rs.getString("playlistname"));
				dto.setDescription(rs.getString("description"));
				dto.setUserId(rs.getInt("userid"));
				dto.setThumbnailUrl(rs.getString("thumbnailurl"));
				dto.setPublicView(rs.getBoolean("publicview"));
				
				return dto;
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
		return null;
	}

	@Override
	public boolean addVideoToPlst(int pid, int vid) {
		try {  
			PreparedStatement pstmt=con.prepareStatement("select max(index) from tblplaylistdetail");
			ResultSet rs = pstmt.executeQuery();
			int num = 1;
			if(rs.next())
				num =rs.getInt(1)+1;
			String sql = "INSERT INTO tblplaylistdetail VALUES( ?, ? , "+num+" )";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1,pid );
			ps.setInt(2, vid );
			if(ps.executeUpdate()>0){
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
	public boolean insert(Playlist dto) {
		try {
			String sql = "INSERT INTO TBLPLAYLIST VALUES(nextval('seq_playlist'), ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getPlaylistName());
			ps.setString(2, dto.getDescription());
			ps.setInt(3, dto.getUserId());
			ps.setString(4, dto.getThumbnailUrl());
			ps.setBoolean(5, dto.isPublicView());
			if(ps.executeUpdate()>0){
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
	public boolean update(Playlist dto) {
		try {
			String sql = "UPDATE TBLPLAYLIST SET playlistname=?, description=?, thumbnailurl=?, publicview=? WHERE playlistid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getPlaylistName());
			ps.setString(2, dto.getDescription());
			ps.setString(3, dto.getThumbnailUrl());
			ps.setBoolean(4, dto.isPublicView());
			ps.setInt(5, dto.getPlaylistId());
			if(ps.executeUpdate()>0){
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
	public boolean delete(int playlistid) {
		try {
			String sql = "DELETE FROM TBLPLAYLIST WHERE playlistid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, playlistid);
			if(ps.executeUpdate()>0){
				return true;
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
		return false;
	}

	@Override
	public int count(String keyword) {
		try {
			String sql = "SELECT COUNT(playlistid) FROM TBLPLAYLIST where playlistname like ?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, "%"+keyword+"%");
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
	public int countUserPlaylist(String keyword, int userid) {
		try {
			String sql = "SELECT COUNT(playlistid) FROM TBLPLAYLIST where playlistname like ? and userid = ?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, "%"+keyword+"%");
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

	@Override
	public int countvideos(int playlistid) {
		try {
			String sql = "SELECT COUNT(videoid) FROM TBLPLAYLISTDETAIL WHERE playlistid="+playlistid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
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
	public ArrayList<Playlist> recommendPlaylist() {
		try {
			con = dataSource.getConnection();
			ArrayList<Playlist> playlists =new ArrayList<Playlist>();
			ResultSet rs = null;
			String sql= "select p.playlistid, p.playlistname,p.description, p.publicview,p.userid,p.thumbnailurl,p.maincategory,p.bgimage,p.color,p.status,u.username, u.userimageurl from tbluser u "
					+ "INNER JOIN tblplaylist p on u.userid= p.userid "
					+ "where p.userid=1 limit 10";
			PreparedStatement ps = con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				Playlist playlist = new Playlist();
				playlist.setPlaylistId(rs.getInt("playlistid"));
				playlist.setPlaylistName(rs.getString("playlistname"));
				playlist.setDescription(rs.getString("description"));
				playlist.setUserId(rs.getInt("userid"));
				playlist.setThumbnailUrl(rs.getString("thumbnailurl"));
				playlist.setPublicView(rs.getBoolean("publicview"));
				playlist.setMaincategory(rs.getInt("maincategory"));
				playlist.setBgImage(rs.getString("bgimage"));
				playlist.setColor(rs.getString("color"));
				playlist.setStatus(rs.getBoolean("status"));
				playlist.setUsername(rs.getString("username"));
				
			
				playlists.add(playlist);
				
			}
			return playlists;
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
	public boolean deleteVideoFromPlaylist(int playlistid, int vid) {
		try {
			con = dataSource.getConnection();
			String sql = "DELETE FROM tblplaylistdetail WHERE playlistid=? and videoid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, playlistid);
			ps.setInt(2, vid);
			if(ps.executeUpdate()>0){
				return true;
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
		return false;
	}

	//well
	@Override
	public boolean updateThumbnail(int vid, int pid) {
		try {
			con = dataSource.getConnection();
			String sql = "UPDATE TBLPLAYLIST SET  thumbnailurl=(select youtubeurl from tblvideo where videoid=? ) WHERE playlistid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, vid);
			ps.setInt(2, pid);
			if(ps.executeUpdate()>0){
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
	public boolean updateThumbnailToDefault(int pid) {
		try {
			con = dataSource.getConnection();
			String sql = "UPDATE TBLPLAYLIST SET  thumbnailurl='default.png' WHERE playlistid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, pid);
			if(ps.executeUpdate()>0){
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
	
	
	/*public static void main(String[] args) {
		PlayListServiceImplement p =new PlayListServiceImplement();
		
			System.out.println(p.updateThumbnailToDefault(2));
		
	}*/

}
