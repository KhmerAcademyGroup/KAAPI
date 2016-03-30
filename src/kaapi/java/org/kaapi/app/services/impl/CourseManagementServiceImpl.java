package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.kaapi.app.entities.CourseVideoManagement;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Playlist;
import org.kaapi.app.services.CourseManagementService;
import org.kaapi.app.utilities.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CourseManagementServiceImpl implements CourseManagementService{

	@Autowired
	DataSource dataSource;
	
	@Override
	public ArrayList<Playlist> listCourses(String mainCategoryId,Pagination pagination) {
		if(mainCategoryId.equals("empty")){
			mainCategoryId = "";
		}else{
			mainCategoryId = Encryption.decode(mainCategoryId);
		}
		String sql = "SELECT A.playlistid, A.playlistname, A.description, A.userid, B.email, B.username, A.bgimage, A.color, A.thumbnailurl, A.status ,MC.maincategoryid "
				   + ",(SELECT videoid from tblplaylistdetail where playlistid=A.playlistid and index=(select min(index) from tblplaylistdetail where playlistid=A.playlistid) ) as videoid " 
				   + ",MC.maincategoryname "
				   + " ,( select COUNT(videoid) FROM tblplaylistdetail where playlistid = A.playlistid GROUP BY playlistid ) as conutvideo "
				   + " FROM tblplaylist A "
				   + " INNER JOIN tbluser B ON A.userid = B.userid "
				   + " INNER JOIN tblmaincategory MC ON A.maincategory = MC.maincategoryid "
				   + " WHERE A.status = true " + ((mainCategoryId=="") ? "" : " AND A.maincategory= "+ mainCategoryId+ " ")
				   + " ORDER BY A.playlistid DESC "
				   + " offset ? limit ?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ArrayList<Playlist> playlists =new ArrayList<Playlist>();
			ps.setInt(1,pagination.offset());
			ps.setInt(2, pagination.getItem());
			Playlist playlist = null;
			ResultSet rs = null;
			rs = ps.executeQuery();
			while(rs.next()){
				playlist =new Playlist();
				playlist.setPlaylistId(Encryption.encode(rs.getString("playlistid")));
				playlist.setPlaylistName(rs.getString("playlistname"));
				playlist.setDescription(rs.getString("description"));
				playlist.setUsername(rs.getString("username"));
				playlist.setUserId(rs.getString("userid"));
				playlist.setBgImage(rs.getString("bgimage"));
				playlist.setColor(rs.getString("color"));
				playlist.setThumbnailUrl(rs.getString("thumbnailurl"));
				playlist.setStatus(rs.getBoolean("status"));
				playlist.setVideoId(Encryption.encode(rs.getString("videoid")));
				playlist.setCountVideos(rs.getInt("conutvideo"));
				playlist.setMaincategoryname(rs.getString("maincategoryname"));
				playlist.setMaincategory(Encryption.encode(rs.getString("maincategoryid")));
				playlists.add(playlist);
			}
			return playlists;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int countCourse(String mainCategoryId) {
		if(mainCategoryId.equals("empty")){
			mainCategoryId = "";
		}else{
			mainCategoryId = Encryption.decode(mainCategoryId);
		}
		String sql = "SELECT COUNT(playlistid) FROM TBLPLAYLIST where   status=true   " + ((mainCategoryId=="") ? "" : " AND maincategory= "+ mainCategoryId+ " ");	
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	@Override
	public ArrayList<CourseVideoManagement> listVideosInCourse(String curseId, Pagination pagination) {
		String sql = "SELECT PL.playlistid, PL.playlistname, PL.description playlist_description, PL.thumbnailurl, PL.status playlist_status,"
				+ " V.videoid , V.videoname, V.description video_description, V.youtubeurl, V.fileurl, V.postdate, V.userid, V.viewcount,"
				+ " U.USERNAME, CC.CATEGORYNAMES, COUNT(DISTINCT C.VIDEOID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS, COUNT(DISTINCT VM.*) COUNTVOTEMINUS, PD.INDEX ,V.status video_status"
				+ " FROM TBLVIDEO V LEFT JOIN TBLUSER U ON V.USERID=U.USERID"
				+ " LEFT JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT LEFT JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid GROUP BY CV.videoid) CC ON V.videoid=CC.videoid"
				+ " LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID"
				+ " LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID"
				+ " LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID"
				+ " INNER JOIN TBLPLAYLISTDETAIL PD ON PD.VIDEOID=V.VIDEOID "
				+ " INNER JOIN tblplaylist PL ON PD.PLAYLISTID = PL.playlistid"
				+ " WHERE PD.PLAYLISTID=?"
				+ " GROUP BY V.VIDEOID, U.USERNAME, CC.CATEGORYNAMES, PD.INDEX , PL.playlistid"
				+ " ORDER BY PD.INDEX" 
				+ " offset ? limit ?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ArrayList<CourseVideoManagement> cArr =new ArrayList<CourseVideoManagement>();
			ps.setInt(1,Integer.parseInt(Encryption.decode(curseId)));
			ps.setInt(2,pagination.offset());
			ps.setInt(3, pagination.getItem());
			CourseVideoManagement c = null;
			ResultSet rs = null;
			rs = ps.executeQuery();
			while(rs.next()){
				c = new CourseVideoManagement();
				c.setPlaylistId(Encryption.encode(rs.getString("playlistid")));
				c.setPlaylistName(rs.getString("playlistname"));
				c.setPlaylistDescription(rs.getString("playlist_description"));
				c.setPlaylistThumbnailUrl(rs.getString("thumbnailurl"));
				c.setPlaylistStatus(rs.getBoolean("playlist_status"));
				c.setVideoId(Encryption.encode(rs.getString("videoid")));
				c.setVideoName(rs.getString("videoname"));
				c.setVideoDescription(rs.getString("video_description"));
				c.setYoutubeUrl(rs.getString("youtubeurl"));
				c.setFileUrl(rs.getString("fileurl"));
				c.setPostDate(rs.getDate("postdate"));
				c.setUserId(Encryption.encode(rs.getString("userid")));
				c.setViewCount(rs.getInt("viewcount"));
				c.setUsername(rs.getString("username"));
				c.setCategoryName(rs.getString("categorynames"));
				c.setCountComment(rs.getInt("countcomments"));
				c.setCountVotePlus(rs.getInt("countvoteplus"));
				c.setCountVotePlus(rs.getInt("countvoteminus"));
				c.setIndex(rs.getInt("index"));
				c.setVideoStatus(rs.getBoolean("video_status"));
				cArr.add(c);
			}
			return cArr;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
}