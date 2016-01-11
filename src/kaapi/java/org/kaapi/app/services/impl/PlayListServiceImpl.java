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
import org.kaapi.app.entities.Video;
import org.kaapi.app.forms.FrmCreatePlaylist;
import org.kaapi.app.forms.FrmUpdatePlaylist;
import org.kaapi.app.services.PlayListServics;
import org.kaapi.app.utilities.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;





@Service("PlayListService")
public class PlayListServiceImpl implements PlayListServics{
	@Autowired
	DataSource dataSource;
	Connection con;
	
	@Override
	public ArrayList<Playlist> list(Pagination pagin, Playlist dto) {
		try {
			con = dataSource.getConnection();
			ArrayList<Playlist> playlists =new ArrayList<Playlist>();
			ResultSet rs = null;
			String sql = "SELECT P.*, U.username, COUNT(DISTINCT PD.videoid) countvideos FROM TBLPLAYLIST P INNER JOIN TBLUSER U ON P.userid=U.userid "
					+ "LEFT JOIN TBLPLAYlISTDETAIL PD ON P.playlistid=PD.playlistid "
					+ "WHERE LOWER(P.playlistname) LIKE LOWER(?) and  U.Userid = ? GROUP BY P.playlistid, U.username order by  P.playlistid desc offset ? limit ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "%"+dto.getPlaylistName()+"%");
			ps.setInt(2, Integer.parseInt(Encryption.decode(dto.getUserId())));
			ps.setInt(3, pagin.getPage());
			ps.setInt(4, pagin.getItem());
			rs = ps.executeQuery();
			while(rs.next()){
				Playlist playlist = new Playlist();
				playlist.setPlaylistId(Encryption.encode(rs.getString("playlistid")));
				playlist.setPlaylistName(rs.getString("playlistname"));
				playlist.setDescription(rs.getString("description"));
				playlist.setUserId(Encryption.encode(rs.getString("userid")));
				playlist.setThumbnailUrl(rs.getString("thumbnailurl"));
				playlist.setPublicView(rs.getBoolean("publicview"));
				playlist.setMaincategory(rs.getInt("maincategory"));
				playlist.setBgImage(rs.getString("bgimage"));
				playlist.setColor(rs.getString("color"));
				playlist.setStatus(rs.getBoolean("status"));
				playlist.setUsername(rs.getString("username"));
				playlist.setCountVideos(rs.getInt("countvideos"));
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
	public ArrayList<Video> listVideoInPlaylist(String playlistid, Pagination pagin) {
		try {
			con = dataSource.getConnection();
			ArrayList<Video> playlists =new ArrayList<Video>();
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
			ps.setInt(1, Integer.parseInt(Encryption.decode(playlistid)));
			ps.setInt(2, pagin.getPage());
			ps.setInt(3, pagin.getItem());
			rs = ps.executeQuery();
			Video dto=null;
			while(rs.next()){
				
				dto = new Video();
//				dto.setVideoId(rs.getInt("videoid"));
				dto.setVideoName(rs.getString("videoname"));
				dto.setDescription(rs.getString("description"));
				dto.setYoutubeUrl(rs.getString("youtubeurl"));
				dto.setFileUrl(rs.getString("fileurl"));
				dto.setPublicView(rs.getBoolean("publicview"));
				dto.setPostDate(rs.getDate("postdate"));
				dto.setUserId(Encryption.encode(rs.getString("userid")));
				dto.setViewCounts(rs.getInt("viewcount"));
				dto.setUsername(rs.getString("username"));
				dto.setCountVotePlus(rs.getInt("countvoteplus"));
				dto.setCountVoteMinus(rs.getInt("countvoteminus"));
				playlists.add(dto);
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
	public String getPlaylistName(String playlistid) {
		try{	
				con = dataSource.getConnection();
				String pname= "";
				ResultSet rs = null;
				String sql = "select playlistname from tblplaylist where playlistid=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(Encryption.decode(playlistid)));
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

	//well
	@Override
	public ArrayList<Video> listVideo(String playlistid) {
		try {
			con = dataSource.getConnection();
			ArrayList<Video> playlists =new ArrayList<Video>();
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
			ps.setInt(1, Integer.parseInt(Encryption.decode(playlistid)));
			rs = ps.executeQuery();
			Video dto=null;
			while(rs.next()){
				dto = new Video();
//				dto.setVideoId(rs.getInt("videoid"));
				dto.setVideoName(rs.getString("videoname"));
				dto.setDescription(rs.getString("description"));
				dto.setYoutubeUrl(rs.getString("youtubeurl"));
				dto.setFileUrl(rs.getString("fileurl"));
				dto.setPublicView(rs.getBoolean("publicview"));
				dto.setPostDate(rs.getDate("postdate"));
				dto.setUserId(Encryption.encode(rs.getString("userid")));
				dto.setViewCounts(rs.getInt("viewcount"));
				dto.setUsername(rs.getString("username"));
				dto.setCountVotePlus(rs.getInt("countvoteplus"));
				dto.setCountVoteMinus(rs.getInt("countvoteminus"));
				playlists.add(dto);
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
	public Playlist listplaylistname(Playlist dto) {
		try {
			con = dataSource.getConnection();
			//ArrayList<Playlist> playlists =new ArrayList<Playlist>();
			Playlist playlist = new Playlist();
			ResultSet rs = null;
			String sql = "select playlistid , playlistname,publicview from tblplaylist where LOWER(playlistname) like  LOWER(?) and userid = ?  order by playlistid desc";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "%"+dto.getPlaylistName()+"%");
			ps.setInt(2, Integer.parseInt(Encryption.decode(dto.getUserId())));
			rs = ps.executeQuery();
			while(rs.next()){
				playlist.setPlaylistId(Encryption.encode(rs.getString("playlistid")));
				playlist.setPlaylistName(rs.getString("playlistname"));
				playlist.setPublicView(rs.getBoolean("publicview"));
			}
			return playlist;
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
	
	//well but i don't see where this fucntion was used
	@Override
	public ArrayList<Playlist> listplaylistbyPublicView(boolean publicview) {
		try {
			con = dataSource.getConnection();
			ArrayList<Playlist> playlists =new ArrayList<Playlist>();
			String sql = "select playlistid,playlistname  from tblplaylist where publicview= ? order by playlistid desc";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setBoolean(1,publicview);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				
				Playlist playlist = new Playlist();
				playlist.setPlaylistId(Encryption.encode(rs.getString("playlistid")));
				playlist.setPlaylistName(rs.getString("playlistname"));
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
	public ArrayList<Playlist> listplaylistbyAdmin(boolean publicview) {
		try {
				con = dataSource.getConnection();
				ArrayList<Playlist> playlists =new ArrayList<Playlist>();
				String sql = "select playlistid, playlistname from tblplaylist P inner join tbluser U on P.userid=U.userid inner join tblusertype UT on U.usertypeid=UT.usertypeid where publicview=? and UT.userable=true order by playlistid desc";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setBoolean(1,publicview);
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					
					Playlist playlist = new Playlist();
					playlist.setPlaylistId(Encryption.encode(rs.getString("playlistid")));
					playlist.setPlaylistName(rs.getString("playlistname"));
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
	public ArrayList<Playlist> listplaylistdetail(String userid) {
		try {
			con = dataSource.getConnection();
			ArrayList<Playlist> playlists =new ArrayList<Playlist>();
			ResultSet rs = null;
			String sql = "select D.playlistid , D.videoid , U.userid from TBLPLAYlISTDETAIL D "
					+ " INNER JOIN TBLPLAYLIST L ON D.playlistid = L.playlistid "
					+ " INNER JOIN TBLUSER U ON L.userid = L.userid "
					+ " Where U.Userid = ? order by D.Playlistid desc ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(userid)));
			rs = ps.executeQuery();
			while(rs.next()){
				
				Playlist playlist = new Playlist();
				playlist.setPlaylistId(Encryption.encode(rs.getString("playlistid")));
				playlist.setVideoId(Encryption.encode(rs.getString("videoid")));
				playlist.setUserId(Encryption.encode(rs.getString("userid")));
				
				
				/*playlist.setPlaylistName(rs.getString("playlistname"));
				playlist.setDescription(rs.getString("description"));
				playlist.setUserId(rs.getInt("userid"));
				playlist.setThumbnailUrl(rs.getString("thumbnailurl"));
				playlist.setPublicView(rs.getBoolean("publicview"));
				playlist.setMaincategory(rs.getInt("maincategory"));
				playlist.setBgImage(rs.getString("bgimage"));
				playlist.setColor(rs.getString("color"));
				playlist.setStatus(rs.getBoolean("status"));
				playlist.setUsername(rs.getString("username"));
				playlist.setUserImageUrl(rs.getString("userimageurl"));*/
				
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
	//not sure
	@Override
	public ArrayList<Playlist> listplaylistdetail(String userid, String playlistid) {
		try {
			con = dataSource.getConnection();
			ArrayList<Playlist> playlists =new ArrayList<Playlist>();
			ResultSet rs = null;
			String sql = "select D.playlistid , D.videoid , U.userid from TBLPLAYlISTDETAIL D "
					+ " INNER JOIN TBLPLAYLIST L ON D.playlistid = L.playlistid "
					+ " INNER JOIN TBLUSER U ON L.userid = L.userid "
					+ " Where U.Userid = ? and D.playlistid = ? order by D.Playlistid desc ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(userid)));
			ps.setInt(2, Integer.parseInt(Encryption.decode(playlistid)));
			rs = ps.executeQuery();
			while(rs.next()){
				
				Playlist playlist = new Playlist();
				playlist.setPlaylistId(Encryption.encode(rs.getString("playlistid")));
				playlist.setPlaylistName(rs.getString("playlistname"));
				playlist.setDescription(rs.getString("description"));
				playlist.setUserId(Encryption.encode(rs.getString("userid")));
				playlist.setThumbnailUrl(rs.getString("thumbnailurl"));
				playlist.setPublicView(rs.getBoolean("publicview"));
				playlist.setMaincategory(rs.getInt("maincategory"));
				playlist.setBgImage(rs.getString("bgimage"));
				playlist.setColor(rs.getString("color"));
				playlist.setStatus(rs.getBoolean("status"));
				playlist.setUsername(rs.getString("username"));
				playlist.setUserImageUrl(rs.getString("userimageurl"));
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
	public Playlist get(String playlistid) {
		try {
			con = dataSource.getConnection();
			String sql = "SELECT P.*, U.username, COUNT(DISTINCT PD.videoid) countvideos FROM TBLPLAYLIST P INNER JOIN TBLUSER U ON P.userid=U.userid "
					+ "LEFT JOIN TBLPLAYlISTDETAIL PD ON P.playlistid=PD.playlistid "
					+ "WHERE P.playlistid="+playlistid+" GROUP BY P.playlistid, U.username";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				Playlist dto = new Playlist();
				dto.setPlaylistId(Encryption.encode(rs.getString("playlistid")));
				dto.setPlaylistName(rs.getString("playlistname"));
				dto.setDescription(rs.getString("description"));
				dto.setUserId(Encryption.encode(rs.getString("userid")));
				dto.setThumbnailUrl(rs.getString("thumbnailurl"));
				dto.setPublicView(rs.getBoolean("publicview"));
				dto.setUsername(rs.getString("username"));
				dto.setCountVideos(rs.getInt("countvideos"));
				dto.setMaincategory(rs.getInt("maincategory"));
				dto.setBgImage(rs.getString("bgimage"));
				dto.setColor(rs.getString("color"));
				dto.setStatus(rs.getBoolean("status"));
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
	//well
	@Override
	public Playlist getPlaylistForUpdate(String playlistid) {
		try {
			con = dataSource.getConnection();
			String sql = "select * from tblplaylist where playlistid = ?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(playlistid)));
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				Playlist playlist =new Playlist();
				playlist.setPlaylistId(Encryption.encode(rs.getString("playlistid")));
				playlist.setPlaylistName(rs.getString("playlistname"));
				playlist.setDescription(rs.getString("description"));
				playlist.setUserId(Encryption.encode(rs.getString("userid")));
				playlist.setThumbnailUrl(rs.getString("thumbnailurl"));
				playlist.setPublicView(rs.getBoolean("publicview"));
				playlist.setMaincategory(rs.getInt("maincategory"));
				playlist.setBgImage(rs.getString("bgimage"));
				playlist.setColor(rs.getString("color"));
				playlist.setStatus(rs.getBoolean("status"));
				return playlist;
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

	//well
	@Override
	public boolean addVideoToPlst(String pid, String vid) {
		try {  
			con = dataSource.getConnection();
			PreparedStatement pstmt=con.prepareStatement("select max(index) from tblplaylistdetail");
			ResultSet rs = pstmt.executeQuery();
			int num = 1;
			if(rs.next())
				num =rs.getInt(1)+1;
			String sql = "INSERT INTO tblplaylistdetail VALUES( ?, ? , "+num+" )";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1,Integer.parseInt(Encryption.decode(pid )));
			ps.setInt(2, Integer.parseInt(Encryption.decode(vid )));
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
	public boolean insert(FrmCreatePlaylist playlist) {
		try {
			con = dataSource.getConnection();
			String sql = "INSERT INTO TBLPLAYLIST VALUES(nextval('seq_playlist'), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, playlist.getPlaylistName());
			ps.setString(2, playlist.getDescription());
			ps.setInt(3, Integer.parseInt(Encryption.decode(playlist.getUserId())));
			ps.setString(4, playlist.getThumbnailUrl());
			ps.setBoolean(5, playlist.isPublicView());
			ps.setInt(6, playlist.getMaincategory());
			ps.setString(7, playlist.getBgImage());
			ps.setString(8, playlist.getColor());
			ps.setBoolean(9, true);
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
	public boolean update(FrmUpdatePlaylist playlist) {
		try {
			con = dataSource.getConnection();
			String sql = "UPDATE TBLPLAYLIST SET playlistname=?, description=?, thumbnailurl=?, publicview=?, maincategory=?, bgimage=?, color=?, status=? WHERE playlistid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, playlist.getPlaylistName());
			ps.setString(2, playlist.getDescription());
			ps.setString(3, playlist.getThumbnailUrl());
			ps.setBoolean(4, playlist.isPublicView());
			ps.setInt(5, playlist.getMaincategory());
			ps.setString(6, playlist.getBgImage());
			ps.setString(7, playlist.getColor());
			ps.setBoolean(8, playlist.isStatus());
			ps.setInt(9, Integer.parseInt(Encryption.decode(playlist.getPlaylistId())));
			if(ps.executeUpdate()>0){
				System.out.println();
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
	public boolean delete(String playlistid) {
		try {
			con = dataSource.getConnection();
			String sql = "DELETE FROM TBLPLAYLIST WHERE playlistid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(playlistid)));
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
	public int count(String keyword) {
		try {
			con = dataSource.getConnection();
			String sql = "SELECT COUNT(playlistid) FROM TBLPLAYLIST where LOWER(playlistname) like LOWER(?)";
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
	//well
	@Override
	public int countUserPlaylist(String keyword, String userid) {
		try {
			con = dataSource.getConnection();
			String sql = "SELECT COUNT(playlistid) FROM TBLPLAYLIST where LOWER(playlistname) like LOWER(?) and userid = ?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, "%"+keyword+"%");
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
	
	//well
	/*
	 * after add video to playlistdetail
	 * will check if countvideos have playlistid ==1 so we process update thumnails
	 * 
	 * after delete from playlist
	 * will check if countvideos have playlistid ==0 so we process update thumnails to default
	 * 
	 */
	@Override
	public int countvideos(String playlistid) {
		try {
			con = dataSource.getConnection();
			String sql = "SELECT COUNT(videoid) FROM TBLPLAYLISTDETAIL WHERE playlistid=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(playlistid)));
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
	//well
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
				playlist.setPlaylistId(Encryption.encode(rs.getString("playlistid")));
				playlist.setPlaylistName(rs.getString("playlistname"));
				playlist.setDescription(rs.getString("description"));
				playlist.setUserId(Encryption.encode(rs.getString("userid")));
				playlist.setThumbnailUrl(rs.getString("thumbnailurl"));
				playlist.setPublicView(rs.getBoolean("publicview"));
				playlist.setMaincategory(rs.getInt("maincategory"));
				playlist.setBgImage(rs.getString("bgimage"));
				playlist.setColor(rs.getString("color"));
				playlist.setStatus(rs.getBoolean("status"));
				playlist.setUsername(rs.getString("username"));
				playlist.setUserImageUrl(rs.getString("userimageurl"));
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
	public boolean deleteVideoFromPlaylist(String playlistid, String vid) {
		try {
			con = dataSource.getConnection();
			String sql = "DELETE FROM tblplaylistdetail WHERE playlistid=? and videoid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(playlistid)));
			ps.setInt(2, Integer.parseInt(Encryption.decode(vid)));
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
	/*
	 * we use when insert data to playlistdetail
	 */
	@Override
	public boolean updateThumbnail(String vid, String pid) {
		try {
			con = dataSource.getConnection();
			String sql = "UPDATE TBLPLAYLIST SET  thumbnailurl=(select youtubeurl from tblvideo where videoid=? ) WHERE playlistid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(vid)));
			ps.setInt(2, Integer.parseInt(Encryption.decode(pid)));
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
	/*
	 * after delete playlist if playlist id =0 will update thumbnailur to default
	 */
	@Override
	public boolean updateThumbnailToDefault(String pid) {
		try {
			con = dataSource.getConnection();
			String sql = "UPDATE TBLPLAYLIST SET  thumbnailurl='default.png' WHERE playlistid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(pid)));
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
	public ArrayList<Playlist> listAllPlaylist() {
		try {
			con = dataSource.getConnection();
			ArrayList<Playlist> playlists =new ArrayList<Playlist>();
			ResultSet rs = null;
			String sql = " SELECT * FROM tblplaylist WHERE status=true";
			PreparedStatement ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				Playlist playlist = new Playlist();
				playlist.setPlaylistId(Encryption.encode(rs.getString("playlistid")));
				playlist.setPlaylistName(rs.getString("playlistname"));
				playlist.setDescription(rs.getString("description"));
				playlist.setUserId(Encryption.encode(rs.getString("userid")));
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
	
	
	/*public static void main(String[] args) {
		PlayListServiceImplement p =new PlayListServiceImplement();
		
			System.out.println(p.updateThumbnailToDefault(2));
		
	}*/

}
