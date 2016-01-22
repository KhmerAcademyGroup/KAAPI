package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.kaapi.app.entities.Comment;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.services.CommentService;
import org.kaapi.app.utilities.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommentServiceImpl implements CommentService {

	@Autowired private DataSource dataSource;
	
	@Override
	public List<Comment> listCommentOnVideo(String videoid, Pagination page) {
		String sql = "SELECT CM.*, V.videoname, U.username, U.userimageurl "
				   + "FROM TBLCOMMENT CM "
				   + "INNER JOIN TBLVIDEO V ON CM.videoid=V.videoid "
				   + "INNER JOIN TBLUSER U ON CM.userid=U.userid "
				   + "WHERE CM.videoid=? "
				   + "ORDER BY commentdate DESC OFFSET ? LIMIT ?";
		
		List<Comment> list = new ArrayList<Comment>();
		Comment comment = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(videoid)));
			ps.setInt(2, page.offset());
			ps.setInt(3, page.getItem());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				comment = new Comment();
				comment.setCommentId(Encryption.encode(rs.getString("commentid")));
				comment.setCommentDate(rs.getDate("commentdate"));
				comment.setCommentText(rs.getString("commenttext"));
				comment.setVideoId(Encryption.encode(rs.getString("videoid")));
				comment.setUserId(Encryption.encode(rs.getString("userid")));
				comment.setUsername(rs.getString("username"));
				comment.setUserImageUrl(rs.getString("userimageurl"));
				comment.setReplyId(Encryption.encode(rs.getString("replycomid")));
				list.add(comment);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("ERROR covert");
			return null;
		}
		return null;
	}

	@Override
	public List<Comment> listComment(Pagination page) {
		String sql = "SELECT CM.*, V.videoname, U.username, U.userimageurl "
				   + "FROM TBLCOMMENT CM "
				   + "INNER JOIN TBLVIDEO V ON CM.videoid=V.videoid "
				   + "INNER JOIN TBLUSER U ON CM.userid=U.userid "
				   + "OFFSET ? LIMIT ?";
		
		List<Comment> list = new ArrayList<Comment>();
		Comment comment = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, page.offset());
			ps.setInt(2, page.getItem());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				comment = new Comment();
				comment.setCommentId(Encryption.encode(rs.getString("commentid")));
				comment.setCommentDate(rs.getDate("commentdate"));
				comment.setCommentText(rs.getString("commenttext"));
				comment.setVideoId(Encryption.encode(rs.getString("videoid")));
				comment.setUserId(Encryption.encode(rs.getString("userid")));
				comment.setVideoName(rs.getString("videoname"));
				comment.setUsername(rs.getString("username"));
				comment.setUserImageUrl(rs.getString("userimageurl"));
				comment.setReplyId(Encryption.encode(rs.getString("replycomid")));
				list.add(comment);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Comment> listComment(String commentText, Pagination page) {
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
			ps.setInt(2, page.offset());
			ps.setInt(3, page.getItem());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				comment = new Comment();
				comment.setCommentId(Encryption.encode(rs.getString("commentid")));
				comment.setCommentDate(rs.getDate("commentdate"));
				comment.setCommentText(rs.getString("commenttext"));
				comment.setVideoId(Encryption.encode(rs.getString("videoid")));
				comment.setUserId(Encryption.encode(rs.getString("userid")));
				comment.setVideoName(rs.getString("videoname"));
				comment.setUsername(rs.getString("username"));
				comment.setUserImageUrl(rs.getString("userimageurl"));
				comment.setReplyId(Encryption.encode(rs.getString("replycomid")));
				list.add(comment);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Comment> listSuperComment(Pagination page) {
		String sql = "SELECT CM.commentid, CM.commentdate, substr(CM.commenttext,0,40) as commenttext , CM.videoid, CM.userid, CM.replycomid, U.username, U.userimageurl "
				   + "FROM TBLCOMMENT CM "
				   + "INNER JOIN TBLUSER U ON CM.userid=U.userid "
				   + "WHERE replycomid is not null AND replycomid = 0 "
				   + "OFFSET ? LIMIT ?";
		
		List<Comment> list = new ArrayList<Comment>();
		Comment comment = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, page.offset());
			ps.setInt(2, page.getItem());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				comment = new Comment();
				comment.setCommentId(Encryption.encode(rs.getString("commentid")));
				comment.setCommentDate(rs.getDate("commentdate"));
				comment.setCommentText(rs.getString("commenttext"));
				comment.setVideoId(Encryption.encode(rs.getString("videoid")));
				comment.setUserId(Encryption.encode(rs.getString("userid")));
				comment.setUsername(rs.getString("username"));
				comment.setUserImageUrl(rs.getString("userimageurl"));
				comment.setReplyId(Encryption.encode(rs.getString("replycomid")));
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
		String sql = "INSERT INTO TBLCOMMENT VALUES(nextval('seq_comment'), NOW(), ?, ?, ?,0)";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, comment.getCommentText());
			ps.setInt(2, Integer.parseInt(Encryption.decode(comment.getVideoId())));
			ps.setInt(3, Integer.parseInt(Encryption.decode(comment.getUserId())));
			if(ps.executeUpdate()>0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1) {
			System.out.println(e1.getMessage());
			return false;
		}
		return false;
	}

	@Override
	public boolean reply(Comment comment) {
		String sql = "INSERT INTO TBLCOMMENT VALUES(nextval('seq_comment'), NOW(), ?, ?, ?,?)";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, comment.getCommentText());
			ps.setInt(2, Integer.parseInt(Encryption.decode(comment.getVideoId())));
			ps.setInt(3, Integer.parseInt(Encryption.decode(comment.getUserId())));
			ps.setInt(4, Integer.parseInt(Encryption.decode(comment.getReplyId())));
			if(ps.executeUpdate()>0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1) {
			System.out.println(e1.getMessage());
			return false;
		}
		return false;
	}

	@Override
	public boolean update(Comment comment) {
		String sql = "UPDATE TBLCOMMENT SET commenttext=?, videoid=?, userid=?, replycomid=? WHERE commentid=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, comment.getCommentText());
			ps.setInt(2, Integer.parseInt(Encryption.decode(comment.getVideoId())));
			ps.setInt(3, Integer.parseInt(Encryption.decode(comment.getUserId())));
			ps.setInt(4, Integer.parseInt(Encryption.decode(comment.getReplyId())));
			ps.setInt(5, Integer.parseInt(Encryption.decode(comment.getCommentId())));
			if(ps.executeUpdate()>0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1) {
			System.out.println(e1.getMessage());
			return false;
		}
		return false;
	}

	@Override
	public boolean delete(String commentId) {
		String sql = "DELETE FROM TBLCOMMENT WHERE commentid=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(commentId)));
			if(ps.executeUpdate()>0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(NumberFormatException e1){
			System.out.println("Error");
			return false;
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
	public int countCommentOnVideo(String videoId) {
		String sql = "SELECT COUNT(videoid) FROM TBLCOMMENT WHERE videoid=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(videoId)));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error");
			return 0;
		}
		return 0;
	}

	@Override
	public Comment getComment(String commentId) {
		String sql = "SELECT CM.*, V.videoname, U.username, U.userimageurl "
				   + "FROM TBLCOMMENT CM "
				   + "INNER JOIN TBLVIDEO V ON CM.videoid=V.videoid "
				   + "INNER JOIN TBLUSER U ON CM.userid=U.userid "
				   + "WHERE CM.commentid=?";
		
		Comment comment = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(commentId)));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				comment = new Comment();
				comment.setCommentId(Encryption.encode(rs.getString("commentid")));
				comment.setCommentDate(rs.getDate("commentdate"));
				comment.setCommentText(rs.getString("commenttext"));
				comment.setVideoId(Encryption.encode(rs.getString("videoid")));
				comment.setUserId(Encryption.encode(rs.getString("userid")));
				comment.setVideoName(rs.getString("videoname"));
				comment.setUsername(rs.getString("username"));
				comment.setUserImageUrl(rs.getString("userimageurl"));
				comment.setReplyId(Encryption.encode(rs.getString("replycomid")));
			}
			return comment;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error");
			return null;
		}
		return null;
	}

	@Override
	public List<Comment> listReplyComment(String videoId, String replyId, Pagination page) {
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
			ps.setInt(1, Integer.parseInt(Encryption.decode(videoId)));
			ps.setInt(2, Integer.parseInt(Encryption.decode(replyId)));
			ps.setInt(3, page.offset());
			ps.setInt(4, page.getItem());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				comment = new Comment();
				comment.setCommentId(Encryption.encode(rs.getString("commentid")));
				comment.setCommentDate(rs.getDate("commentdate"));
				comment.setCommentText(rs.getString("commenttext"));
				comment.setVideoId(Encryption.encode(rs.getString("videoid")));
				comment.setUserId(Encryption.encode(rs.getString("userid")));
				comment.setVideoName(rs.getString("videoname"));
				comment.setUsername(rs.getString("username"));
				comment.setUserImageUrl(rs.getString("userimageurl"));
				comment.setReplyId(Encryption.encode(rs.getString("replycomid")));
				list.add(comment);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (NumberFormatException e1){
			System.out.println("Error" + e1.getMessage());
			return null;
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
		String sql = "SELECT COUNT(commentid) "
				   + "FROM TBLCOMMENT "
				   + "WHERE replycomid is not null AND replycomid = 0";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int countReplyComment(String videoId, String replyId) {
		String sql = "SELECT COUNT(CM.commentid) "
				   + "FROM TBLCOMMENT CM "
				   + "WHERE CM.videoid=? and CM.replycomid=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(videoId)));
			ps.setInt(2, Integer.parseInt(Encryption.decode(replyId)));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1) {
			System.out.println(e1.getMessage());
			return 0;
		}
		return 0;
	}

	@Override
	public List<Comment> listSuperCommentOnVideo(String videoid, Pagination page) {
		String sql = "SELECT CM.*, V.videoname, U.username, U.userimageurl "
				   + "FROM TBLCOMMENT CM "
				   + "INNER JOIN TBLVIDEO V ON CM.videoid=V.videoid "
				   + "INNER JOIN TBLUSER U ON CM.userid=U.userid "
				   + "WHERE CM.videoid=? AND CM.replycomid is not null AND CM.replycomid = 0 "
				   + "ORDER BY commentdate DESC OFFSET ? LIMIT ?";
		
		List<Comment> list = new ArrayList<Comment>();
		Comment comment = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(videoid)));
			ps.setInt(2, page.offset());
			ps.setInt(3, page.getItem());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				comment = new Comment();
				comment.setCommentId(Encryption.encode(rs.getString("commentid")));
				comment.setCommentDate(rs.getDate("commentdate"));
				comment.setCommentText(rs.getString("commenttext"));
				comment.setVideoId(Encryption.encode(rs.getString("videoid")));
				comment.setUserId(Encryption.encode(rs.getString("userid")));
				comment.setUsername(rs.getString("username"));
				comment.setUserImageUrl(rs.getString("userimageurl"));
				comment.setReplyId(Encryption.encode(rs.getString("replycomid")));	
				list.add(comment);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("ERROR covert");
			return null;
		}
		return null;
	}

	@Override
	public List<Comment> listReplyCommentOnVideo(String videoId) {
		String sql = "SELECT CM.*, V.videoname, U.username, U.userimageurl "
				   + "FROM TBLCOMMENT CM "
				   + "INNER JOIN TBLVIDEO V ON CM.videoid=V.videoid "
				   + "INNER JOIN TBLUSER U ON CM.userid=U.userid "
				   + "WHERE CM.videoid=? AND CM.replycomid is not null AND CM.replycomid <> 0 "
				   + "ORDER BY commentdate DESC";
		
		List<Comment> list = new ArrayList<Comment>();
		Comment comment = null;
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(videoId)));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				comment = new Comment();
				comment.setCommentId(Encryption.encode(rs.getString("commentid")));
				comment.setCommentDate(rs.getDate("commentdate"));
				comment.setCommentText(rs.getString("commenttext"));
				comment.setVideoId(Encryption.encode(rs.getString("videoid")));
				comment.setUserId(Encryption.encode(rs.getString("userid")));
				comment.setUsername(rs.getString("username"));
				comment.setUserImageUrl(rs.getString("userimageurl"));
				comment.setReplyId(Encryption.encode(rs.getString("replycomid")));
				list.add(comment);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("ERROR covert");
			return null;
		}
		return null;
	}

	@Override
	public int countSuperCommentOnVideo(String videoId) {
		String sql = "SELECT COUNT(videoid) FROM TBLCOMMENT WHERE videoid=? AND replycomid is not null AND replycomid = 0";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(videoId)));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error");
			return 0;
		}
		return 0;
	}

}
