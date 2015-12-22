package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.kaapi.app.entities.Comment;
import org.kaapi.app.entities.Video;
import org.kaapi.app.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VideoServiceImpl implements VideoService {

	@Autowired
	private DataSource dataSource;

	//list all videos
	@Override
	public List<Video> listVideo(int offset, int limit) {
		
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
			ps.setInt(1, (offset-1)*limit);
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
				video.setUserImageUrl(rs.getString("userimageurl"));
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
	public List<Video> listVideo(String videoName, int offset, int limit) {
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
			ps.setInt(2, (offset-1)*limit);
			ps.setInt(3, limit);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(rs.getInt("videoid"));
				video.setVideoName(rs.getString("videoname"));
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
				video.setUserImageUrl(rs.getString("userimageurl"));
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
	public List<Video> listVideo(int userId, int offset, int limit) {
		String sql = "SELECT V.*, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES, COUNT(DISTINCT C.VIDEOID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS, COUNT(DISTINCT VM.*) COUNTVOTEMINUS "
				+ "FROM TBLVIDEO V "
				+ "LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
				+ "LEFT JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT "
				+ "LEFT JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid GROUP BY CV.videoid) CC ON V.videoid=CC.videoid " 
				+ "LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID "
				+ "WHERE V.USERID=?"
				+ "GROUP BY V.VIDEOID, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES "
				+ "ORDER BY V.VIDEOID DESC OFFSET ? LIMIT ?";
		
		List<Video> list = new ArrayList<Video>();
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, userId);
			ps.setInt(2, (offset-1)*limit);
			ps.setInt(3, limit);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(rs.getInt("videoid"));
				video.setVideoName(rs.getString("videoname"));
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
				video.setUserImageUrl(rs.getString("userimageurl"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	//list video by user id and video name
	@Override
	public List<Video> listVideo(int userId, String videoName, int offset, int limit) {
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
			ps.setInt(1, userId);
			ps.setString(2, "%" + videoName + "%");
			ps.setInt(3, (offset-1)*limit);
			ps.setInt(4, limit);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(rs.getInt("videoid"));
				video.setVideoName(rs.getString("videoname"));
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
				video.setUserImageUrl(rs.getString("userimageurl"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	//list related video
	@Override
	public List<Video> getRelateVideo(String categoryName, int limit) {
		String sql = "SELECT V.*, U.USERNAME, U.USERIMAGEURL, CC.CATEGORYNAMES, COUNT(DISTINCT C.VIDEOID) COUNTCOMMENTS, COUNT(DISTINCT VP.*) COUNTVOTEPLUS, COUNT(DISTINCT VM.*) COUNTVOTEMINUS "
				+ "FROM TBLVIDEO V "
				+ "LEFT JOIN TBLUSER U ON V.USERID=U.USERID "
				+ "LEFT JOIN (SELECT CV.videoid, string_agg(CT.categoryname, ', ') CATEGORYNAMES FROM TBLCATEGORY CT "
				+ "LEFT JOIN TBLCATEGORYVIDEO CV ON CT.categoryid=CV.categoryid GROUP BY CV.videoid) CC ON V.videoid=CC.videoid " 
				+ "LEFT JOIN TBLCOMMENT C ON V.VIDEOID=C.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=1) VP ON V.VIDEOID=VP.VIDEOID "
				+ "LEFT JOIN (SELECT * FROM TBLVOTE WHERE VOTETYPE=-1) VM ON V.VIDEOID=VM.VIDEOID "
				+ "WHERE lower(CC.CATEGORYNAMES) LIKE lower(?)"
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
				video.setVideoId(rs.getInt("videoid"));
				video.setVideoName(rs.getString("videoname"));
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
				video.setUserImageUrl(rs.getString("userimageurl"));
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
	public List<Video> categoryVideo(int categoryid, int offset, int limit) {
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
			ps.setInt(1, categoryid);
			ps.setInt(2, (offset-1)*limit);
			ps.setInt(3, limit);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(rs.getInt("videoid"));
				video.setVideoName(rs.getString("videoname"));
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
				video.setUserImageUrl(rs.getString("userimageurl"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	//list comment
	@Override
	public List<Comment> listComment(int videoid, int offset, int limit) {
		String sql = "SELECT CM.*, V.videoname, U.username, U.userimageurl "
				   + "FROM TBLCOMMENT CM "
				   + "INNER JOIN TBLVIDEO V ON CM.videoid=V.videoid "
				   + "INNER JOIN TBLUSER U ON CM.userid=U.userid "
				   + "WHERE CM.videoid=? "
				   + "ORDER BY commentdate DESC OFFSET ? LIMIT ?";
		
		List<Comment> list = new ArrayList<Comment>();
		Comment comment = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, videoid);
			ps.setInt(2, (offset-1)*limit);
			ps.setInt(3, limit);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				comment = new Comment();
				comment.setCommentId(rs.getInt("commentid"));
				comment.setCommentDate(rs.getDate("commentdate"));
				comment.setCommentText(rs.getString("commenttext"));
				comment.setVideoId(rs.getInt("videoid"));
				comment.setUserId(rs.getInt("userid"));
				comment.setUsername(rs.getString("username"));
				comment.setUserImageUrl(rs.getString("userimageurl"));
				comment.setReplyId(rs.getInt("replycomid"));
				list.add(comment);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

//	@Override
//	public List<Playlist> listPlaylist(int videoId, int offset, int limit) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<Video> topVoteAndRecent(int limit) {
		String sql = "SELECT * FROM (select DISTINCT v.*,count(VO.videoid) vote, u.username, u.userimageurl "
				    + "FROM tblvote vo "
				    + "INNER JOIN tblvideo v on vo.videoid=v.videoid "
				    + "INNER JOIN tbluser u on v.userid=u.userid "
				    + "WHERE vo.votetype=1 "
					+ "GROUP BY v.videoid, VO.videoid, u.userid "
					+ "ORDER BY count(VO.videoid) DESC LIMIT ?) c1 "
					+ "UNION ALL (SELECT DISTINCT v.*, 1 as count, u.username, u.userimageurl "
					+ "FROM  tblvote vo "
					+ "INNER JOIN tblvideo v on vo.videoid=v.videoid "
					+ "INNER JOIN tbluser u on v.userid=u.userid "
					+ "ORDER BY postdate DESC LIMIT ?)";
		
		List<Video> list = new ArrayList<Video>();
		Video video = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, limit/2);
			ps.setInt(2, limit/2);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video();
				video.setVideoId(rs.getInt("videoid"));
				video.setVideoName(rs.getString("videoname"));
				video.setDescription(rs.getString("description"));
				video.setYoutubeUrl(rs.getString("youtubeurl"));
				video.setFileUrl(rs.getString("fileurl"));
				video.setPublicView(rs.getBoolean("publicview"));
				video.setPostDate(rs.getDate("postdate"));
				video.setUserId(rs.getInt("userid"));
				video.setCountVotePlus(rs.getInt("vote"));
				video.setViewCounts(rs.getInt("viewcount"));
				video.setUsername(rs.getString("username"));
				video.setUserImageUrl(rs.getString("userimageurl"));
				list.add(video);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
		String sql = "SELECT COUNT(V.videoid) FROM TBLVIDEO V "
				   + "INNER JOIN tblcategoryvideo c on v.videoid=c.videoid "
				   + "WHERE c.categoryid=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, categoryId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
		String sql = "SELECT COUNT(videoid) FROM TBLCOMMENT WHERE videoid=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, videoId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int countForum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countVideo(int userId) {
		String sql = "SELECT COUNT(V.videoid) FROM TBLVIDEO V LEFT JOIN TBLUSER U ON V.USERID=U.USERID WHERE U.USERID=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int countVideo(int userId, String videoName) {
		String sql = "SELECT COUNT(V.videoid) FROM TBLVIDEO V LEFT JOIN TBLUSER U ON V.USERID=U.USERID WHERE U.USERID=? AND lower(V.VIDEONAME) LIKE lower(?)";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, userId);
			ps.setString(2, "%" + videoName + "%");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
