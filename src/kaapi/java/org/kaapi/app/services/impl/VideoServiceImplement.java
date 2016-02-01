package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Playlist;
import org.kaapi.app.entities.Video;
import org.kaapi.app.services.VideosService;
import org.kaapi.app.utilities.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VideoServiceImplement implements VideosService{

	@Autowired
	private DataSource dataSource;

	//list all videos
	@Override
	public List<Video> listVideo(Pagination page) {
		
		String sql = "SELECT V.*, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES, COUNT(DISTINCT C.VIDEOID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS, COUNT(DISTINCT VM.*) COUNTVOTEMINUS "
				+ "FROM TBLVIDEO V "
				+ "LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
				+ "LEFT JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT "
				+ "LEFT JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid GROUP BY CV.videoid) CC ON V.videoid=CC.videoid " 
				+ "LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID "
				+ "GROUP BY V.VIDEOID, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES "
				+ "ORDER BY V.VIDEOID DESC OFFSET ? LIMIT ?";
		
		List<Video> list = new ArrayList<Video>();
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, page.offset());
			ps.setInt(2, page.getItem());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(Encryption.encode(rs.getString("videoid")));
				video.setVideoName(rs.getString("videoname"));
				//String description = (rs.getString("description").length()>50 ? rs.getString("description").substring(0, 49)+"..." : rs.getString("description"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(Encryption.encode(rs.getString("userid")));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setCategoryName(rs.getString("categorynames"));
				video.setCountComments(rs.getInt("countcomments"));
				video.setCountVoteMinus(rs.getInt("countvoteminus"));
				video.setCountVotePlus(rs.getInt("countvoteplus"));
				video.setUsername(rs.getString("username"));
				video.setUserImageUrl(rs.getString("userimageurl"));
				video.setStatus(rs.getBoolean("status"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	//list video by name or search video by name
	@Override
	public List<Video> listVideo(String videoName, Pagination page) {
		String sql = "SELECT V.*, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES, COUNT(DISTINCT C.VIDEOID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS, COUNT(DISTINCT VM.*) COUNTVOTEMINUS "
				+ "FROM TBLVIDEO V "
				+ "LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
				+ "LEFT JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT "
				+ "LEFT JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid GROUP BY CV.videoid) CC ON V.videoid=CC.videoid " 
				+ "LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID "
				+ "WHERE lower(V.VIDEONAME) LIKE lower(?)"
				+ "GROUP BY V.VIDEOID, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES "
				+ "ORDER BY V.VIDEOID DESC OFFSET ? LIMIT ?";
		
		List<Video> list = new ArrayList<Video>();
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, "%" + videoName + "%");
			ps.setInt(2, page.offset());
			ps.setInt(3, page.getItem());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(Encryption.encode(rs.getString("videoid")));
				video.setVideoName(rs.getString("videoname"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(Encryption.encode(rs.getString("userid")));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setCategoryName(rs.getString("categorynames"));
				video.setCountComments(rs.getInt("countcomments"));
				video.setCountVoteMinus(rs.getInt("countvoteminus"));
				video.setCountVotePlus(rs.getInt("countvoteplus"));
				video.setUsername(rs.getString("username"));
				video.setUserImageUrl(rs.getString("userimageurl"));
				video.setStatus(rs.getBoolean("status"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	//list video by user id
	@Override
	public List<Video> listVideoUser(String userId, Pagination page) {
		String sql = "SELECT V.*, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES, COUNT(DISTINCT C.VIDEOID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS " //, COUNT(DISTINCT VM.*) COUNTVOTEMINUS "
				+ "FROM TBLVIDEO V "
				+ "LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
				+ "LEFT JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT "
				+ "LEFT JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid GROUP BY CV.videoid) CC ON V.videoid=CC.videoid " 
				+ "LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
//				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID "
				+ "WHERE V.USERID=?"
				+ "GROUP BY V.VIDEOID, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES "
				+ "ORDER BY V.VIDEOID DESC OFFSET ? LIMIT ?";
		
		List<Video> list = new ArrayList<Video>();
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(userId)));
			ps.setInt(2, page.offset());
			ps.setInt(3, page.getItem());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(Encryption.encode(rs.getString("videoid")));
				video.setVideoName(rs.getString("videoname"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(Encryption.encode(rs.getString("userid")));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setCategoryName(rs.getString("categorynames"));
				video.setCountComments(rs.getInt("countcomments"));
//				video.setCountVoteMinus(rs.getInt("countvoteminus"));
				video.setCountVotePlus(rs.getInt("countvoteplus"));
				video.setUsername(rs.getString("username"));
				video.setUserImageUrl(rs.getString("userimageurl"));
				video.setStatus(rs.getBoolean("status"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error Convert ID To Integer!");
			return null;
		}
		return null;
	}

	//list video by user id and video name
	@Override
	public List<Video> listVideo(String userId, String videoName, Pagination page) {
		String sql = "SELECT V.*, U.USERNAME,U.USERIMAGEURL, CC.CATEGORYNAMES, COUNT(DISTINCT C.VIDEOID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS, COUNT(DISTINCT VM.*) COUNTVOTEMINUS "
				+ "FROM TBLVIDEO V "
				+ "LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
				+ "LEFT JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT "
				+ "LEFT JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid GROUP BY CV.videoid) CC ON V.videoid=CC.videoid " 
				+ "LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID "
				+ "WHERE V.USERID=? AND lower(V.VIDEONAME) LIKE lower(?)"
				+ "GROUP BY V.VIDEOID, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES "
				+ "ORDER BY V.VIDEOID DESC OFFSET ? LIMIT ?";
		
		List<Video> list = new ArrayList<Video>();
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(userId)));
			ps.setString(2, "%" + videoName + "%");
			ps.setInt(3, page.offset());
			ps.setInt(4, page.getItem());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(Encryption.encode(rs.getString("videoid")));
				video.setVideoName(rs.getString("videoname"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(Encryption.encode(rs.getString("userid")));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setCategoryName(rs.getString("categorynames"));
				video.setCountComments(rs.getInt("countcomments"));
				video.setCountVoteMinus(rs.getInt("countvoteminus"));
				video.setCountVotePlus(rs.getInt("countvoteplus"));
				video.setUsername(rs.getString("username"));
				video.setUserImageUrl(rs.getString("userimageurl"));
				video.setStatus(rs.getBoolean("status"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error Convert ID To Integer!");
			return null;
		}
		return null;
	}

	//list related video
	@Override
	public List<Video> getRelateVideo(String categoryName, int limit) {
		String sql = "SELECT V.*, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES, COUNT(DISTINCT C.VIDEOID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS " //, COUNT(DISTINCT VM.*) COUNTVOTEMINUS "
				+ "FROM TBLVIDEO V "
				+ "LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
				+ "LEFT JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT "
				+ "LEFT JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid GROUP BY CV.videoid) CC ON V.videoid=CC.videoid " 
				+ "LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
//				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID "
				+ "WHERE lower(CC.CATEGORYNAMES) LIKE lower(?) AND V.STATUS=true "
				+ "GROUP BY V.VIDEOID, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES "
				+ "ORDER BY random() LIMIT ?";
		
		List<Video> list = new ArrayList<Video>();
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, "%" + categoryName + "%");
			ps.setInt(2, limit);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(Encryption.encode(rs.getString("videoid")));
				video.setVideoName(rs.getString("videoname"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(Encryption.encode(rs.getString("userid")));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setCategoryName(rs.getString("categorynames"));
				video.setCountComments(rs.getInt("countcomments"));
//				video.setCountVoteMinus(rs.getInt("countvoteminus"));
				video.setCountVotePlus(rs.getInt("countvoteplus"));
				video.setUsername(rs.getString("username"));
				video.setUserImageUrl(rs.getString("userimageurl"));
				video.setStatus(rs.getBoolean("status"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	//list video by category
	@Override
	public List<Video> categoryVideo(String categoryid, Pagination page) {
		String sql = "SELECT V.*, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES, COUNT(DISTINCT C.VIDEOID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS, COUNT(DISTINCT VM.*) COUNTVOTEMINUS " 
					+"FROM TBLVIDEO V LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
					+"INNER JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT "
					+"INNER JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid where CV.categoryid=? "
					+"GROUP BY CV.videoid) CC ON CC.videoid=V.videoid "  
					+"LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID  "
					+"LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
					+"LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID  "
					+"GROUP BY V.VIDEOID, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES "
					+ "OFFSET ? LIMIT ?";
		
		List<Video> list = new ArrayList<Video>();
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(categoryid)));
			ps.setInt(2, page.offset());
			ps.setInt(3, page.getItem());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(Encryption.encode(rs.getString("videoid")));
				video.setVideoName(rs.getString("videoname"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(Encryption.encode(rs.getString("userid")));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setCategoryName(rs.getString("categorynames"));
				video.setCountComments(rs.getInt("countcomments"));
				video.setCountVoteMinus(rs.getInt("countvoteminus"));
				video.setCountVotePlus(rs.getInt("countvoteplus"));
				video.setUsername(rs.getString("username"));
				video.setUserImageUrl(rs.getString("userimageurl"));
				video.setStatus(rs.getBoolean("status"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error Convert ID To Integer!");
			return null;
		}
		return null;
	}

	@Override
	public List<Video> topVoteAndRecent(int limit) {
		String sql = "SELECT * FROM (select DISTINCT v.*,count(VO.videoid) vote, u.username, u.userimageurl "
				    + "FROM tblvote vo "
				    + "INNER JOIN tblvideo v on vo.videoid=v.videoid "
				    + "INNER JOIN tbluser u on v.userid=u.userid "
				    + "WHERE vo.votetype=1 AND v.status=true "
					+ "GROUP BY v.videoid, VO.videoid, u.userid "
					+ "ORDER BY count(VO.videoid) DESC LIMIT ?) c1 "
					+ "UNION ALL (SELECT DISTINCT v.*, 1 as count, u.username, u.userimageurl "
					+ "FROM  tblvote vo "
					+ "INNER JOIN tblvideo v on vo.videoid=v.videoid "
					+ "INNER JOIN tbluser u on v.userid=u.userid "
					+ "WHERE v.status=true "
					+ "ORDER BY postdate DESC LIMIT ?)";
		
		List<Video> list = new ArrayList<Video>();
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, limit);
			ps.setInt(2, limit);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(Encryption.encode(rs.getString("videoid")));
				video.setVideoName(rs.getString("videoname"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(Encryption.encode(rs.getString("userid")));
				video.setCountVotePlus(rs.getInt("vote"));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setUsername(rs.getString("username"));
				video.setUserImageUrl(rs.getString("userimageurl"));
				video.setStatus(rs.getBoolean("status"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Video> topVote(int limit) {
		String sql = "SELECT DISTINCT v.*,count(VO.videoid) vote, u.username, u.userimageurl "
				    + "FROM tblvote vo "
				    + "INNER JOIN tblvideo v on vo.videoid=v.videoid "
				    + "INNER JOIN tbluser u on v.userid=u.userid "
				    + "WHERE vo.votetype=1 AND v.status=true "
					+ "GROUP BY v.videoid, VO.videoid, u.userid "
					+ "ORDER BY count(VO.videoid) DESC LIMIT ?";
		
		List<Video> list = new ArrayList<Video>();
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, limit);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(Encryption.encode(rs.getString("videoid")));
				video.setVideoName(rs.getString("videoname"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(Encryption.encode(rs.getString("userid")));
				video.setCountVotePlus(rs.getInt("vote"));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setUsername(rs.getString("username"));
				video.setUserImageUrl(rs.getString("userimageurl"));
				video.setStatus(rs.getBoolean("status"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Video> recentVideo(int limit) {
		String sql = "SELECT DISTINCT v.*, u.username, u.userimageurl "
					+ "FROM  tblvote vo "
					+ "INNER JOIN tblvideo v on vo.videoid=v.videoid "
					+ "INNER JOIN tbluser u on v.userid=u.userid "
					+ "WHERE vo.votetype=1 AND v.status=true "
					+ "ORDER BY postdate DESC LIMIT ?";
		
		List<Video> list = new ArrayList<Video>();
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, limit);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(Encryption.encode(rs.getString("videoid")));
				video.setVideoName(rs.getString("videoname"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(Encryption.encode(rs.getString("userid")));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setUsername(rs.getString("username"));
				video.setUserImageUrl(rs.getString("userimageurl"));
				video.setStatus(rs.getBoolean("status"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Video getVideo(String videoId, boolean viewCount) {
		String sql = "SELECT V.*, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES, COUNT(DISTINCT C.COMMENTID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS " //, COUNT(DISTINCT VM.*) COUNTVOTEMINUS "
					+ "FROM TBLVIDEO V LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
					+ "LEFT JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT "
					+ "LEFT JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid GROUP BY CV.videoid) CC ON V.videoid=CC.videoid "
					+ "LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID "
					+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
//					+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID "
					+ "WHERE V.VIDEOID=?  "
					+ "GROUP BY V.VIDEOID, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES";
		
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			int id = Integer.parseInt(Encryption.decode(videoId));
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(Encryption.encode(rs.getString("videoid")));
				video.setVideoName(rs.getString("videoname"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(Encryption.encode(rs.getString("userid")));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setCategoryName(rs.getString("categorynames"));
				video.setCountComments(rs.getInt("countcomments"));
//				video.setCountVoteMinus(rs.getInt("countvoteminus"));
				video.setCountVotePlus(rs.getInt("countvoteplus"));
				video.setUsername(rs.getString("username"));
				video.setUserImageUrl(rs.getString("userimageurl"));
				video.setStatus(rs.getBoolean("status"));
				if(viewCount){
					Statement s2 = cnn.createStatement();
					s2.executeUpdate("UPDATE TBLVIDEO SET VIEWCOUNT=VIEWCOUNT+1 WHERE videoid=" + id);
				}
			}
			return video;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error Convert ID To Integer!");
			return null;
		}
		return null;
	}

	@Override
	public int insert(Video video) {
		String sql = "INSERT INTO TBLVIDEO(videoid, videoname, description, youtubeurl, fileurl, publicview, postdate, userid, viewcount, status) VALUES(nextval('seq_video'), ?, ?, ?, ?, ?, NOW(), ?, 0, ?)";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
			ps.setString(1, video.getVideoName());
			ps.setString(2, video.getDescription());
			ps.setString(3, video.getYoutubeUrl());
			ps.setString(4, video.getFileUrl());
			ps.setBoolean(5, video.isPublicView());
			ps.setInt(6, Integer.parseInt(Encryption.decode(video.getUserId())));
			ps.setBoolean(7, video.isStatus());
			if(ps.executeUpdate()>0){
				ResultSet rs = ps.getGeneratedKeys();
		    	rs.next();
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error Convert ID To Integer!");
			return 0;
		}
		return 0;
	}

	@Override
	public boolean update(Video video) {
		String sql = "UPDATE TBLVIDEO SET videoname=?, description=?, youtubeurl=?, fileurl=?, publicview=?, status=? WHERE videoid=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, video.getVideoName());
			ps.setString(2, video.getDescription());
			ps.setString(3, video.getYoutubeUrl());
			ps.setString(4, video.getFileUrl());
			ps.setBoolean(5, video.isPublicView());
			ps.setBoolean(6, video.isStatus());
			ps.setInt(7, Integer.parseInt(Encryption.decode(video.getVideoId())));
			if(ps.executeUpdate()>0){
				return true;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error Convert ID To Integer!");
			return false;
		}
		return false;
	}

	@Override
	public boolean delete(String videoId) {
		String sql = "DELETE FROM TBLVIDEO WHERE videoid=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(videoId)));
			if(ps.executeUpdate()>0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Delete Error Convert ID To Integer!");
			return false;
		}
		return false;
	}

	@Override
	public boolean insertVideoToCategory(String videoId, String categoryId) {
		String sql = "INSERT INTO TBLCATEGORYVIDEO VALUES(?, ?)";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(videoId)));
			ps.setInt(2, Integer.parseInt(Encryption.decode(categoryId)));
			if(ps.executeUpdate()>0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error Convert ID To Integer!");
			return false;
		}
		return false;
	}

	@Override
	public boolean removeVideoFromCategory(String videoId) {
		String sql = "DELETE FROM TBLCATEGORYVIDEO WHERE videoid=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(videoId)));
			if(ps.executeUpdate()>0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error Convert ID To Integer!");
			return false;
		}
		return false;
	}

	@Override
	public int countVideo() {
		String sql = "SELECT COUNT(V.videoid) FROM TBLVIDEO V";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int countVideo(String videoName) {
		String sql = "SELECT COUNT(V.videoid) FROM TBLVIDEO V WHERE lower(V.VIDEONAME) LIKE lower(?)";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, "%" + videoName + "%");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int countUser() {
		String sql="Select count(userid) from tbluser";
		try(Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {			
			ResultSet rs= ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);			
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int countPlaylist() {
		String sql="Select count(playlistid) from tblplaylist";
		try(Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql);){
			ResultSet rs= ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int countCategoryVideo(String categoryId) {
		String sql = "SELECT COUNT(V.videoid) FROM TBLVIDEO V "
				   + "INNER JOIN tblcategoryvideo c on v.videoid=c.videoid "
				   + "WHERE c.categoryid=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(categoryId)));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error Convert ID To Integer!");
			return 0;
		}
		return 0;
	}

	@Override
	public int countForum() {
		String sql="Select count(commentid) from tblforumcomment";
		try(Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql);){
			ResultSet rs= ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int countVideoUser(String userId) {
		String sql = "SELECT COUNT(V.videoid) FROM TBLVIDEO V LEFT JOIN TBLUSER U ON V.USERID=U.USERID WHERE U.USERID=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(userId)));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error Convert ID To Integer!");
			return 0;
		}
		return 0;
	}

	@Override
	public int countVideo(String userId, String videoName) {
		String sql = "SELECT COUNT(V.videoid) FROM TBLVIDEO V LEFT JOIN TBLUSER U ON V.USERID=U.USERID WHERE U.USERID=? AND lower(V.VIDEONAME) LIKE lower(?)";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(userId)));
			ps.setString(2, "%" + videoName + "%");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error Convert ID To Integer!");
			return 0;
		}
		return 0;
	}

	@Override
	public List<Video> listVideo(boolean status, Pagination page) {
		String sql = "SELECT V.*, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES, COUNT(DISTINCT C.VIDEOID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS, COUNT(DISTINCT VM.*) COUNTVOTEMINUS "
				+ "FROM TBLVIDEO V "
				+ "LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
				+ "LEFT JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT "
				+ "LEFT JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid GROUP BY CV.videoid) CC ON V.videoid=CC.videoid " 
				+ "LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID "
				+ "WHERE V.STATUS=? "
				+ "GROUP BY V.VIDEOID, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES "
				+ "ORDER BY V.VIDEOID DESC OFFSET ? LIMIT ?";
		
		List<Video> list = new ArrayList<Video>();
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setBoolean(1, status);
			ps.setInt(2, page.offset());
			ps.setInt(3, page.getItem());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(Encryption.encode(rs.getString("videoid")));
				video.setVideoName(rs.getString("videoname"));
				//String description = (rs.getString("description").length()>50 ? rs.getString("description").substring(0, 49)+"..." : rs.getString("description"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(Encryption.encode(rs.getString("userid")));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setCategoryName(rs.getString("categorynames"));
				video.setCountComments(rs.getInt("countcomments"));
				video.setCountVoteMinus(rs.getInt("countvoteminus"));
				video.setCountVotePlus(rs.getInt("countvoteplus"));
				video.setUsername(rs.getString("username"));
				video.setUserImageUrl(rs.getString("userimageurl"));
				video.setStatus(rs.getBoolean("status"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Video> listVideo(String videoName, boolean status, Pagination page) {
		String sql = "SELECT V.*, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES, COUNT(DISTINCT C.VIDEOID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS, COUNT(DISTINCT VM.*) COUNTVOTEMINUS "
				+ "FROM TBLVIDEO V "
				+ "LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
				+ "LEFT JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT "
				+ "LEFT JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid GROUP BY CV.videoid) CC ON V.videoid=CC.videoid " 
				+ "LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID "
				+ "WHERE lower(V.VIDEONAME) LIKE lower(?) AND V.STATUS=?"
				+ "GROUP BY V.VIDEOID, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES "
				+ "ORDER BY V.VIDEOID DESC OFFSET ? LIMIT ?";
		
		List<Video> list = new ArrayList<Video>();
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, "%" + videoName + "%");
			ps.setBoolean(2, status);
			ps.setInt(3, page.offset());
			ps.setInt(4, page.getItem());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(Encryption.encode(rs.getString("videoid")));
				video.setVideoName(rs.getString("videoname"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(Encryption.encode(rs.getString("userid")));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setCategoryName(rs.getString("categorynames"));
				video.setCountComments(rs.getInt("countcomments"));
				video.setCountVoteMinus(rs.getInt("countvoteminus"));
				video.setCountVotePlus(rs.getInt("countvoteplus"));
				video.setUsername(rs.getString("username"));
				video.setUserImageUrl(rs.getString("userimageurl"));
				video.setStatus(rs.getBoolean("status"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Video> listVideoUser(String userId, boolean status, Pagination page) {
		String sql = "SELECT V.*, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES, COUNT(DISTINCT C.VIDEOID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS, COUNT(DISTINCT VM.*) COUNTVOTEMINUS "
				+ "FROM TBLVIDEO V "
				+ "LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
				+ "LEFT JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT "
				+ "LEFT JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid GROUP BY CV.videoid) CC ON V.videoid=CC.videoid " 
				+ "LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID "
				+ "WHERE V.USERID=? AND V.STATUS=?"
				+ "GROUP BY V.VIDEOID, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES "
				+ "ORDER BY V.VIDEOID DESC OFFSET ? LIMIT ?";
		
		List<Video> list = new ArrayList<Video>();
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(userId)));
			ps.setBoolean(2, status);
			ps.setInt(3, page.offset());
			ps.setInt(4, page.getItem());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(Encryption.encode(rs.getString("videoid")));
				video.setVideoName(rs.getString("videoname"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(Encryption.encode(rs.getString("userid")));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setCategoryName(rs.getString("categorynames"));
				video.setCountComments(rs.getInt("countcomments"));
				video.setCountVoteMinus(rs.getInt("countvoteminus"));
				video.setCountVotePlus(rs.getInt("countvoteplus"));
				video.setUsername(rs.getString("username"));
				video.setUserImageUrl(rs.getString("userimageurl"));
				video.setStatus(rs.getBoolean("status"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error Convert ID To Integer!");
			return null;
		}
		return null;
	}

	@Override
	public List<Video> listVideo(String userId, String videoName, boolean status, Pagination page) {
		String sql = "SELECT V.*, U.USERNAME,U.USERIMAGEURL, CC.CATEGORYNAMES, COUNT(DISTINCT C.VIDEOID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS, COUNT(DISTINCT VM.*) COUNTVOTEMINUS "
				+ "FROM TBLVIDEO V "
				+ "LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
				+ "LEFT JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT "
				+ "LEFT JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid GROUP BY CV.videoid) CC ON V.videoid=CC.videoid " 
				+ "LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID "
				+ "WHERE V.USERID=? AND lower(V.VIDEONAME) LIKE lower(?) AND V.STATUS=?"
				+ "GROUP BY V.VIDEOID, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES "
				+ "ORDER BY V.VIDEOID DESC OFFSET ? LIMIT ?";
		
		List<Video> list = new ArrayList<Video>();
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(userId)));
			ps.setString(2, "%" + videoName + "%");
			ps.setBoolean(3, status);
			ps.setInt(4, page.offset());
			ps.setInt(5, page.getItem());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(Encryption.encode(rs.getString("videoid")));
				video.setVideoName(rs.getString("videoname"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(Encryption.encode(rs.getString("userid")));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setCategoryName(rs.getString("categorynames"));
				video.setCountComments(rs.getInt("countcomments"));
				video.setCountVoteMinus(rs.getInt("countvoteminus"));
				video.setCountVotePlus(rs.getInt("countvoteplus"));
				video.setUsername(rs.getString("username"));
				video.setUserImageUrl(rs.getString("userimageurl"));
				video.setStatus(rs.getBoolean("status"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error Convert ID To Integer!");
			return null;
		}
		return null;
	}

	@Override
	public boolean toggleVideo(String videoId) {
		String sql = "UPDATE TBLVIDEO SET status=not status WHERE videoid=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(videoId)));
			if(ps.executeUpdate()>0){
				return true;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error Convert ID To Integer!");
			return false;
		}
		return false;
	}

	@Override
	public int countVideo(boolean status) {
		String sql = "SELECT COUNT(V.videoid) FROM TBLVIDEO V WHERE V.STATUS=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setBoolean(1, status);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int countVideo(String videoName, boolean status) {
		String sql = "SELECT COUNT(V.videoid) FROM TBLVIDEO V WHERE lower(V.VIDEONAME) LIKE lower(?) AND V.STATUS=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, "%" + videoName + "%");
			ps.setBoolean(2, status);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int countVideoUser(String userId, boolean status) {
		String sql = "SELECT COUNT(V.videoid) FROM TBLVIDEO V LEFT JOIN TBLUSER U ON V.USERID=U.USERID WHERE U.USERID=? AND V.STATUS=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(userId)));
			ps.setBoolean(2, status);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error Convert ID To Integer!");
			return 0;
		}
		return 0;
	}

	@Override
	public int countVideo(String userId, String videoName, boolean status) {
		String sql = "SELECT COUNT(V.videoid) FROM TBLVIDEO V LEFT JOIN TBLUSER U ON V.USERID=U.USERID WHERE U.USERID=? AND lower(V.VIDEONAME) LIKE lower(?) AND V.STATUS=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(userId)));
			ps.setString(2, "%" + videoName + "%");
			ps.setBoolean(3, status);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error Convert ID To Integer!");
			return 0;
		}
		return 0;
	}

	@Override
	public List<Video> categoryVideo(String categoryid, boolean status, Pagination page) {
		String sql = "SELECT V.*, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES, COUNT(DISTINCT C.VIDEOID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS, COUNT(DISTINCT VM.*) COUNTVOTEMINUS " 
				+"FROM TBLVIDEO V LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
				+"INNER JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT "
				+"INNER JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid where CV.categoryid=? "
				+"GROUP BY CV.videoid) CC ON CC.videoid=V.videoid "  
				+"LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID  "
				+"LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
				+"LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID "
				+ "WHERE V.STATUS=? "
				+"GROUP BY V.VIDEOID, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES "
				+ "OFFSET ? LIMIT ?";
	
		List<Video> list = new ArrayList<Video>();
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(categoryid)));
			ps.setBoolean(2, status);
			ps.setInt(3, page.offset());
			ps.setInt(4, page.getItem());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(Encryption.encode(rs.getString("videoid")));
				video.setVideoName(rs.getString("videoname"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(Encryption.encode(rs.getString("userid")));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setCategoryName(rs.getString("categorynames"));
				video.setCountComments(rs.getInt("countcomments"));
				video.setCountVoteMinus(rs.getInt("countvoteminus"));
				video.setCountVotePlus(rs.getInt("countvoteplus"));
				video.setUsername(rs.getString("username"));
				video.setUserImageUrl(rs.getString("userimageurl"));
				video.setStatus(rs.getBoolean("status"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error Convert ID To Integer!");
			return null;
		}
		return null;
	}

	@Override
	public int countCategoryVideo(String categoryId, boolean status) {
		String sql = "SELECT COUNT(V.videoid) FROM TBLVIDEO V "
				   + "INNER JOIN tblcategoryvideo c on v.videoid=c.videoid "
				   + "WHERE c.categoryid=? AND v.status=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(categoryId)));
			ps.setBoolean(2, status);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error Convert ID To Integer!");
			return 0;
		}
		return 0;
	}

	@Override
	public List<Playlist> listMainCategory() {
		String sql = "SELECT DISTINCT(P.maincategory), M.maincategoryname "
				   + "FROM tblplaylist P "
				   + "INNER JOIN tblmaincategory M ON P.maincategory=M.maincategoryid "
				   + "WHERE P.maincategory NOTNULL AND P.status=TRUE";
	
		List<Playlist> list = new ArrayList<Playlist>();
		Playlist playlist = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				playlist = new Playlist();
				playlist.setMaincategory(Encryption.encode(rs.getString("maincategory")));
				playlist.setMaincategoryname(rs.getString("maincategoryname"));
				list.add(playlist);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Playlist> listPlaylist() {
		String sql = "SELECT P.playlistid, P.playlistname, P.description, P.userid, P.thumbnailurl, P.publicview, P.maincategory, P.bgimage, p.color, " 
				   + "P.status, M.maincategoryname " 
				   + "FROM tblplaylist P " 
				   + "INNER JOIN tblmaincategory M ON P.maincategory=M.maincategoryid " 
				   + "WHERE P.maincategory NOTNULL AND P.status=TRUE";
	
		List<Playlist> list = new ArrayList<Playlist>();
		Playlist playlist = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				playlist = new Playlist();
				playlist.setPlaylistId(Encryption.encode(rs.getString("playlistid")));
				playlist.setPlaylistName(rs.getString("playlistname"));
				playlist.setDescription(rs.getString("description"));
				playlist.setUserId(Encryption.encode(rs.getString("userid")));
				playlist.setThumbnailUrl(rs.getString("thumbnailurl"));
				playlist.setPublicView(rs.getBoolean("publicview"));
				playlist.setMaincategory(Encryption.encode(rs.getString("maincategory")));
				playlist.setBgImage(rs.getString("bgimage"));
				playlist.setColor(rs.getString("color"));
				playlist.setStatus(rs.getBoolean("status"));
				playlist.setMaincategoryname(rs.getString("maincategoryname"));
				playlist.setVideoId(getVideoId(rs.getInt("playlistid")));
				list.add(playlist);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getVideoId(int playlistId) {
		String sql = "SELECT PD.VIDEOID "
				   + "FROM TBLPLAYLISTDETAIL PD "
				   + "WHERE PD.PLAYLISTID=? "
				   + "ORDER BY PD.INDEX "
				   + "LIMIT 1";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, playlistId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return Encryption.encode(rs.getString("videoid"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int countCourse() {
	   String sql = "SELECT COUNT(playlistid) " 
			   	  + "FROM tblplaylist " 
			   	  + "WHERE maincategory NOTNULL AND status=TRUE";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Video getVideoWithStatus(String videoId, boolean viewCount, boolean status) {
		String sql = "SELECT V.*, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES, COUNT(DISTINCT C.COMMENTID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS " //, COUNT(DISTINCT VM.*) COUNTVOTEMINUS "
				+ "FROM TBLVIDEO V LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
				+ "LEFT JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT "
				+ "LEFT JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid GROUP BY CV.videoid) CC ON V.videoid=CC.videoid "
				+ "LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
//				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID "
				+ "WHERE V.VIDEOID=? AND V.STATUS=? "
				+ "GROUP BY V.VIDEOID, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES";
	
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			int id = Integer.parseInt(Encryption.decode(videoId));
			ps.setInt(1, id);
			ps.setBoolean(2, status);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(Encryption.encode(rs.getString("videoid")));
				video.setVideoName(rs.getString("videoname"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(Encryption.encode(rs.getString("userid")));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setCategoryName(rs.getString("categorynames"));
				video.setCountComments(rs.getInt("countcomments"));
	//			video.setCountVoteMinus(rs.getInt("countvoteminus"));
				video.setCountVotePlus(rs.getInt("countvoteplus"));
				video.setUsername(rs.getString("username"));
				video.setUserImageUrl(rs.getString("userimageurl"));
				video.setStatus(rs.getBoolean("status"));
				if(viewCount){
					Statement s2 = cnn.createStatement();
					s2.executeUpdate("UPDATE TBLVIDEO SET VIEWCOUNT=VIEWCOUNT+1 WHERE videoid=" + id);
				}
			}
			return video;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error Convert ID To Integer!");
			return null;
		}
		return null;
	}
	
}
