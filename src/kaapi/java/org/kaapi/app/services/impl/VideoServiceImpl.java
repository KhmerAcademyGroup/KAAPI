package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.kaapi.app.entities.CategoryVideo;
import org.kaapi.app.entities.Comment;
import org.kaapi.app.entities.Playlist;
import org.kaapi.app.entities.Video;
import org.kaapi.app.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VideoServiceImpl implements VideoService {

	@Autowired
	private DataSource dataSource;

	@Override
	public List<Video> listVideo(int offset, int limit) {
		
		String sql = "SELECT V.*, U.USERNAME, CC.CATEGORYNAMES, COUNT(DISTINCT C.VIDEOID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS, COUNT(DISTINCT VM.*) COUNTVOTEMINUS "
				+ "FROM TBLVIDEO V "
				+ "LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
				+ "LEFT JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT "
				+ "LEFT JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid GROUP BY CV.videoid) CC ON V.videoid=CC.videoid " 
				+ "LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID "
				+ "GROUP BY V.VIDEOID, U.USERNAME, CC.CATEGORYNAMES "
				+ "OFFSET ? LIMIT ?";
		
		List<Video> list = new ArrayList<Video>();
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, offset);
			ps.setInt(2, limit);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(rs.getInt("videoid"));
				video.setVideoName(rs.getString("videoname"));
				//String description = (rs.getString("description").length()>50 ? rs.getString("description").substring(0, 49)+"..." : rs.getString("description"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(rs.getInt("userid"));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setCategoryName(rs.getString("categorynames"));
				video.setCountComments(rs.getInt("countcomments"));
				video.setCountVoteMinus(rs.getInt("countvoteminus"));
				video.setCountVotePlus(rs.getInt("countvoteplus"));
				video.setUsername(rs.getString("username"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Video> listVideo(String videoName, int offset, int limit) {
		String sql = "SELECT V.*, U.USERNAME, CC.CATEGORYNAMES, COUNT(DISTINCT C.VIDEOID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS, COUNT(DISTINCT VM.*) COUNTVOTEMINUS "
				+ "FROM TBLVIDEO V "
				+ "LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
				+ "LEFT JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT "
				+ "LEFT JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid GROUP BY CV.videoid) CC ON V.videoid=CC.videoid " 
				+ "LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID "
				+ "WHERE lower(V.VIDEONAME) LIKE lower(?)"
				+ "GROUP BY V.VIDEOID, U.USERNAME, CC.CATEGORYNAMES "
				+ "OFFSET ? LIMIT ?";
		
		List<Video> list = new ArrayList<Video>();
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, videoName);
			ps.setInt(2, offset);
			ps.setInt(3, limit);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(rs.getInt("videoid"));
				video.setVideoName(rs.getString("videoname"));
				//String description = (rs.getString("description").length()>50 ? rs.getString("description").substring(0, 49)+"..." : rs.getString("description"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(rs.getInt("userid"));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setCategoryName(rs.getString("categorynames"));
				video.setCountComments(rs.getInt("countcomments"));
				video.setCountVoteMinus(rs.getInt("countvoteminus"));
				video.setCountVotePlus(rs.getInt("countvoteplus"));
				video.setUsername(rs.getString("username"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Video> listVideo(int userId, int offset, int limit) {
		String sql = "SELECT V.*, U.USERNAME, CC.CATEGORYNAMES, COUNT(DISTINCT C.VIDEOID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS, COUNT(DISTINCT VM.*) COUNTVOTEMINUS "
				+ "FROM TBLVIDEO V "
				+ "LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
				+ "LEFT JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT "
				+ "LEFT JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid GROUP BY CV.videoid) CC ON V.videoid=CC.videoid " 
				+ "LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID "
				+ "WHERE V.USERID=?"
				+ "GROUP BY V.VIDEOID, U.USERNAME, CC.CATEGORYNAMES "
				+ "OFFSET ? LIMIT ?";
		
		List<Video> list = new ArrayList<Video>();
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, userId);
			ps.setInt(2, offset);
			ps.setInt(3, limit);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(rs.getInt("videoid"));
				video.setVideoName(rs.getString("videoname"));
				//String description = (rs.getString("description").length()>50 ? rs.getString("description").substring(0, 49)+"..." : rs.getString("description"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(rs.getInt("userid"));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setCategoryName(rs.getString("categorynames"));
				video.setCountComments(rs.getInt("countcomments"));
				video.setCountVoteMinus(rs.getInt("countvoteminus"));
				video.setCountVotePlus(rs.getInt("countvoteplus"));
				video.setUsername(rs.getString("username"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Video> listVideo(int userId, String VideoName, int offset, int limit) {
		String sql = "SELECT V.*, U.USERNAME, CC.CATEGORYNAMES, COUNT(DISTINCT C.VIDEOID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS, COUNT(DISTINCT VM.*) COUNTVOTEMINUS "
				+ "FROM TBLVIDEO V "
				+ "LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
				+ "LEFT JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT "
				+ "LEFT JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid GROUP BY CV.videoid) CC ON V.videoid=CC.videoid " 
				+ "LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID "
				+ "WHERE V.USERID=? AND lower(V.VIDEONAME) LIKE lower(?)"
				+ "GROUP BY V.VIDEOID, U.USERNAME, CC.CATEGORYNAMES "
				+ "OFFSET ? LIMIT ?";
		
		List<Video> list = new ArrayList<Video>();
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, userId);
			ps.setString(2, VideoName);
			ps.setInt(3, offset);
			ps.setInt(4, limit);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(rs.getInt("videoid"));
				video.setVideoName(rs.getString("videoname"));
				//String description = (rs.getString("description").length()>50 ? rs.getString("description").substring(0, 49)+"..." : rs.getString("description"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(rs.getInt("userid"));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setCategoryName(rs.getString("categorynames"));
				video.setCountComments(rs.getInt("countcomments"));
				video.setCountVoteMinus(rs.getInt("countvoteminus"));
				video.setCountVotePlus(rs.getInt("countvoteplus"));
				video.setUsername(rs.getString("username"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Video> getRelateVideo(String categoryName, int offset, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CategoryVideo> categoryVideo(int categoryid, int offset, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comment> listComment(int videoid, int offset, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Playlist> listPlaylist(int videoId, int offset, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Video> topvVoteAndRecent(int offset, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Video getVideo(int videoId, boolean viewCount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(Video video) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Video video) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int videoId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertVideoToCategory(int videoId, int categoryId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeVideoFromCategory(int videoId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeVideoFromCategory(int videoId, int categoryId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertVideoToPlaylist(int videoId, int playlistId, int index) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeVideoFromPlaylist(int videoId, int playlistId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateVideoIndex(int videoId, int playlistId, int index) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int countVideo() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countVideo(String videoName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countUser() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countPlaylist() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countPlaylist(int videoId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countUserVideo(int userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countCategoryVideo(int categoryId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countVotePlus(int videoId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countVoteMinus(int videoId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countComment(int videoId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countForum() {
		// TODO Auto-generated method stub
		return 0;
	}

}
