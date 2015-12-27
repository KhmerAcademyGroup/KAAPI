package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.kaapi.app.entities.Comment;
import org.kaapi.app.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommentServiceImpl implements CommentService {

	@Autowired private DataSource dataSource;
	
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

	@Override
	public List<Comment> listComment(int offset, int limit) {
		String sql = "SELECT CM.*, V.videoname, U.username, U.userimageurl "
				   + "FROM TBLCOMMENT CM "
				   + "INNER JOIN TBLVIDEO V ON CM.videoid=V.videoid "
				   + "INNER JOIN TBLUSER U ON CM.userid=U.userid "
				   + "OFFSET ? LIMIT ?";
		
		List<Comment> list = new ArrayList<Comment>();
		Comment comment = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, (offset-1)*limit);
			ps.setInt(2, limit);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				comment = new Comment();
				comment.setCommentId(rs.getInt("commentid"));
				comment.setCommentDate(rs.getDate("commentdate"));
				comment.setCommentText(rs.getString("commenttext"));
				comment.setVideoId(rs.getInt("videoid"));
				comment.setUserId(rs.getInt("userid"));
				comment.setVideoName(rs.getString("videoname"));
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

	@Override
	public List<Comment> listComment(String commentText, int offset, int limit) {
		String sql = "SELECT CM.*, V.videoname, U.username, U.userimageurl "
				   + "FROM TBLCOMMENT CM "
				   + "INNER JOIN TBLVIDEO V ON CM.videoid=V.videoid "
				   + "INNER JOIN TBLUSER U ON CM.userid=U.userid "
				   + "WHERE lower(CM.commenttext) LIKE lower(?) "
				   + "OFFSET ? LIMIT ?";
		
		List<Comment> list = new ArrayList<Comment>();
		Comment comment = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, "%" + commentText + "%");
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
				comment.setVideoName(rs.getString("videoname"));
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

	@Override
	public List<Comment> listSuperComment(int offset, int limit) {
		String sql = "SELECT CM.commentid, CM.commentdate, substr(CM.commenttext,0,40) as commenttext , CM.videoid, CM.userid, CM.replycomid, U.username, U.userimageurl "
				   + "FROM TBLCOMMENT CM "
				   + "INNER JOIN TBLUSER U ON CM.userid=U.userid "
				   + "WHERE replycomid is not null AND replycomid = 0 "
				   + "OFFSET ? LIMIT ?";
		
		List<Comment> list = new ArrayList<Comment>();
		Comment comment = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, (offset-1)*limit);
			ps.setInt(2, limit);
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

	@Override
	public boolean insert(Comment comment) {
		String sql = "INSERT INTO TBLCOMMENT VALUES(nextval('seq_comment'), NOW(), ?, ?, ?,?)";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, comment.getCommentText());
			ps.setInt(2, comment.getVideoId());
			ps.setInt(3, comment.getUserId());
			ps.setInt(4, comment.getReplyId());
			if(ps.executeUpdate()>0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean reply(Comment comment) {
		String sql = "INSERT INTO TBLCOMMENT VALUES(nextval('seq_comment'), NOW(), ?, ?, ?,?)";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, comment.getCommentText());
			ps.setInt(2, comment.getVideoId());
			ps.setInt(3, comment.getUserId());
			ps.setInt(4, comment.getReplyId());
			if(ps.executeUpdate()>0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Comment comment) {
		String sql = "UPDATE TBLCOMMENT SET commenttext=?, videoid=?, userid=?, replycomid=? WHERE commentid=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, comment.getCommentText());
			ps.setInt(2, comment.getVideoId());
			ps.setInt(3, comment.getUserId());
			ps.setInt(4, comment.getReplyId());
			ps.setInt(5, comment.getCommentId());
			if(ps.executeUpdate()>0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(int commentId) {
		String sql = "DELETE TBLCOMMENT WHERE commentid=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, commentId);
			if(ps.executeUpdate()>0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int countComment() {
		String sql = "SELECT COUNT(commentid) FROM TBLCOMMENT";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	public Comment getComment(int commentId) {
		String sql = "SELECT CM.*, V.videoname, U.username, U.userimageurl "
				   + "FROM TBLCOMMENT CM "
				   + "INNER JOIN TBLVIDEO V ON CM.videoid=V.videoid "
				   + "INNER JOIN TBLUSER U ON CM.userid=U.userid "
				   + "WHERE CM.commentid=?";
		
		Comment comment = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, commentId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				comment = new Comment();
				comment.setCommentId(rs.getInt("commentid"));
				comment.setCommentDate(rs.getDate("commentdate"));
				comment.setCommentText(rs.getString("commenttext"));
				comment.setVideoId(rs.getInt("videoid"));
				comment.setUserId(rs.getInt("userid"));
				comment.setVideoName(rs.getString("videoname"));
				comment.setUsername(rs.getString("username"));
				comment.setUserImageUrl(rs.getString("userimageurl"));
				comment.setReplyId(rs.getInt("replycomid"));
			}
			return comment;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Comment> listReplyComment(int videoId, int replyId, int limit, int offset) {
		String sql = "SELECT CM.*, V.videoname, U.username, U.userimageurl "
				   + "FROM TBLCOMMENT CM "
				   + "INNER JOIN TBLVIDEO V ON CM.videoid=V.videoid "
				   + "INNER JOIN TBLUSER U ON CM.userid=U.userid "
				   + "WHERE CM.videoid=? and CM.replycomid=? "
				   + "ORDER BY commentdate DESC "
				   + "OFFSET ? LIMIT ?";
		
		List<Comment> list = new ArrayList<Comment>();
		Comment comment = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, videoId);
			ps.setInt(2, replyId);
			ps.setInt(3, (offset-1)*limit);
			ps.setInt(4, limit);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				comment = new Comment();
				comment.setCommentId(rs.getInt("commentid"));
				comment.setCommentDate(rs.getDate("commentdate"));
				comment.setCommentText(rs.getString("commenttext"));
				comment.setVideoId(rs.getInt("videoid"));
				comment.setUserId(rs.getInt("userid"));
				comment.setVideoName(rs.getString("videoname"));
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

	@Override
	public int countComment(String commentText) {
		String sql = "SELECT COUNT(commentid) FROM TBLCOMMENT WHERE lower(commenttext) LIKE lower(?)";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, "%" + commentText + "%");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int countSuperComment() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countReplyComment(int videoId, int replyId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
