package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.kaapi.app.entities.ForumComment;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.services.ForumCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ForumCommentServiceImpl implements ForumCommentService{

	@Autowired
	DataSource dataSource;
	
	@Override
	public List<ForumComment> listAllQuestion(Pagination pagination) {
		String sql =  " SELECT "
					+ " DISTINCT(C1.*), U.Username, U.UserImageURL, COUNT(DISTINCT(C2.Commentid)) COMMENTCOUNT, SUM(VOTETYPE) VOTECOUNT "
					+ " FROM TBLFORUMCOMMENT C1 LEFT JOIN TBLFORUMCOMMENT C2 ON C1.Commentid=C2.Parentid "
					+ " INNER JOIN TBLUSER U ON C1.Userid=U.Userid "
					+ " LEFT JOIN TBLFORUMVOTE FV ON C1.Commentid=FV.Commentid "
					+ " WHERE C1.Parentid IS NULL GROUP BY C1.Commentid, U.Userid, FV.Commentid "
					+ " ORDER BY COMMENTID DESC LIMIT ? OFFSET ?";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setInt(1, pagination.getItem());
				ps.setInt(2, pagination.offset());
				ResultSet rs = ps.executeQuery();
				List<ForumComment> list = new ArrayList<ForumComment>();
				ForumComment dto = null;
				while(rs.next()){
					dto  = new ForumComment();
					dto.setCommentId(rs.getInt("commentid"));
					dto.setPostDate(rs.getDate("postdate"));
					dto.setTitle(rs.getString("title"));
					dto.setDetail(rs.getString("detail"));
					dto.setTag(rs.getString("tag"));
					dto.setParentId(rs.getInt("parentid"));
					dto.setCategoryId(rs.getInt("categoryid"));
					dto.setUserId(rs.getInt("userid"));
					dto.setUsername(rs.getString("username"));
					dto.setSelected(rs.getBoolean("selected"));
					dto.setCommentCount(rs.getInt("commentcount"));
					dto.setVote(rs.getInt("votecount"));
					list.add(dto);
				}
				return list;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int countQuestion() {
		String sql = "SELECT COUNT(*) FROM TBLFORUMCOMMENT WHERE Parentid IS NULL";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					return rs.getInt(1);
				}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<ForumComment> listQuestionByUserid(int userid, Pagination pagination) {
		String sql =  " SELECT DISTINCT(C1.*), U.Username, COUNT(C2.Commentid) COMMENTCOUNT, COUNT(FV.Commentid) VOTECOUNT "
					+ " FROM TBLFORUMCOMMENT C1 LEFT JOIN TBLFORUMCOMMENT C2 ON C1.Commentid=C2.Parentid "
					+ " INNER JOIN TBLUSER U ON C1.Userid=U.Userid "
					+ " LEFT JOIN TBLFORUMVOTE FV ON C1.Commentid=FV.Commentid AND FV.Votetype=1 "
					+ " WHERE C1.Userid=? AND C1.Parentid IS NULL "
					+ " GROUP BY C1.Commentid, C2.Commentid, U.Userid, FV.Commentid ORDER BY COMMENTID DESC"
					+ " LIMIT ? OFFSET ?";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setInt(1, userid);
				ps.setInt(2, pagination.getItem());
				ps.setInt(3, pagination.offset());
				ResultSet rs = ps.executeQuery();
				List<ForumComment> list = new ArrayList<ForumComment>();
				ForumComment dto = null;
				while(rs.next()){
					dto  = new ForumComment();
					dto.setCommentId(rs.getInt("commentid"));
					dto.setPostDate(rs.getDate("postdate"));
					dto.setTitle(rs.getString("title"));
					dto.setDetail(rs.getString("detail"));
					dto.setTag(rs.getString("tag"));
					dto.setParentId(rs.getInt("parentid"));
					dto.setCategoryId(rs.getInt("categoryid"));
					dto.setUserId(rs.getInt("userid"));
					dto.setUsername(rs.getString("username"));
					dto.setSelected(rs.getBoolean("selected"));
					dto.setCommentCount(rs.getInt("commentcount"));
					dto.setVote(rs.getInt("votecount"));
					list.add(dto);
				}
				return list;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int countQuestionByUserid(int userid) {
		String sql = "SELECT COUNT(commentid) FROM tblforumcomment WHERE userid = ? AND Parentid IS NULL";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setInt(1, userid);
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					return rs.getInt(1);
				}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<ForumComment> listQuestionByCategoryId(int cateid , Pagination pagination) {
		String sql =  " SELECT DISTINCT(C1.*), U.Username, U.UserImageUrl, COUNT(C2.Commentid) COMMENTCOUNT, SUM(VOTETYPE) VOTECOUNT "
					+ " FROM TBLFORUMCOMMENT C1 LEFT JOIN TBLFORUMCOMMENT C2 ON C1.Commentid=C2.Parentid "
					+ " INNER JOIN TBLUSER U ON C1.Userid=U.Userid "
					+ " LEFT JOIN TBLFORUMVOTE FV ON C1.Commentid=FV.Commentid AND FV.Votetype=1 "
					+ " WHERE C1.Categoryid=? AND C1.Parentid IS NULL "
					+ " GROUP BY C1.Commentid, U.Userid, FV.Commentid "
					+ " ORDER BY COMMENTID DESC LIMIT ? OFFSET ?";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setInt(1, cateid);
				ps.setInt(2, pagination.getItem());
				ps.setInt(3, pagination.offset());
				ResultSet rs = ps.executeQuery();
				List<ForumComment> list = new ArrayList<ForumComment>();
				ForumComment dto = null;
				while(rs.next()){
					dto  = new ForumComment();
					dto.setCommentId(rs.getInt("commentid"));
					dto.setPostDate(rs.getDate("postdate"));
					dto.setTitle(rs.getString("title"));
					dto.setDetail(rs.getString("detail"));
					dto.setTag(rs.getString("tag"));
					dto.setParentId(rs.getInt("parentid"));
					dto.setCategoryId(rs.getInt("categoryid"));
					dto.setUserId(rs.getInt("userid"));
					dto.setUsername(rs.getString("username"));
					dto.setSelected(rs.getBoolean("selected"));
					dto.setCommentCount(rs.getInt("commentcount"));
					dto.setVote(rs.getInt("votecount"));
					list.add(dto);
				}
				return list;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int countQuestionByCategoryId(int cateid) {
		String sql = "SELECT COUNT(commentid) FROM tblforumcomment WHERE categoryid = ?";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setInt(1, cateid);
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					return rs.getInt(1);
				}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<ForumComment> listQuestionByTitle(String title, Pagination pagination) {
		String sql =      " SELECT DISTINCT(C1.*), U.Username, COUNT(C2.Commentid) COMMENTCOUNT, COUNT(FV.Commentid) VOTECOUNT"
						+ " FROM TBLFORUMCOMMENT C1 LEFT JOIN TBLFORUMCOMMENT C2 ON C1.Commentid=C2.Parentid"
						+ " INNER JOIN TBLUSER U ON C1.Userid=U.Userid"
						+ " LEFT JOIN TBLFORUMVOTE FV ON C1.Commentid=FV.Commentid AND FV.Votetype=1"
						+ " WHERE "
						+ " C1.title like ?"
						+ " AND C1.Parentid IS NULL"
						+ " GROUP BY C1.Commentid, C2.Commentid, U.Userid, FV.Commentid"
						+ " ORDER BY COMMENTID DESC offset ? limit ?";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setString(1, "%"+title+"%");
				ps.setInt(2, pagination.offset()); 
				ps.setInt(3, pagination.getItem());
				ResultSet rs = ps.executeQuery();
				List<ForumComment> list = new ArrayList<ForumComment>();
				ForumComment dto = null;
				while(rs.next()){
					dto  = new ForumComment();
					dto.setCommentId(rs.getInt("commentid"));
					dto.setPostDate(rs.getDate("postdate"));
					dto.setTitle(rs.getString("title"));
					dto.setDetail(rs.getString("detail"));
					dto.setTag(rs.getString("tag"));
					dto.setParentId(rs.getInt("parentid"));
					dto.setCategoryId(rs.getInt("categoryid"));
					dto.setUserId(rs.getInt("userid"));
					dto.setUsername(rs.getString("username"));
					dto.setSelected(rs.getBoolean("selected"));
					dto.setCommentCount(rs.getInt("commentcount"));
					dto.setVote(rs.getInt("votecount"));
					list.add(dto);
				}
				return list;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int countQuestionByTitle(String title) {
		String sql =  " SELECT COUNT(*) FROM TBLFORUMCOMMENT WHERE Parentid IS NULL"
					+ " AND title like ?";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setString(1, "%"+title+"%");
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					return rs.getInt(1);
				}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<ForumComment> listAnswerByQuestionId(int parendId, Pagination pagination) {
		String sql =  " SELECT DISTINCT(C1.*), U.Username, U.UserImageURL, C1.SELECTED, COUNT(C2.Commentid) COMMENTCOUNT, SUM(VOTETYPE) VOTECOUNT "
					+ " FROM TBLFORUMCOMMENT C1 LEFT JOIN TBLFORUMCOMMENT C2 ON C1.Commentid=C2.Parentid "
					+ " INNER JOIN TBLUSER U ON C1.Userid=U.Userid "
					+ " LEFT JOIN TBLFORUMVOTE FV ON C1.Commentid=FV.Commentid "
					+ " WHERE C1.Parentid=? GROUP BY C1.Commentid, U.Userid, FV.Commentid "
					+ " ORDER BY SELECTED DESC, COMMENTID DESC offset ? limit ?";

		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setInt(1, parendId);
				ps.setInt(2, pagination.offset()); 
				ps.setInt(3, pagination.getItem());	ResultSet rs = ps.executeQuery();
				List<ForumComment> list = new ArrayList<ForumComment>();
				ForumComment dto = null;
				while(rs.next()){
					dto  = new ForumComment();
					dto.setCommentId(rs.getInt("commentid"));
					dto.setPostDate(rs.getDate("postdate"));
					dto.setTitle(rs.getString("title"));
					dto.setDetail(rs.getString("detail"));
					dto.setTag(rs.getString("tag"));
					dto.setParentId(rs.getInt("parentid"));
					dto.setCategoryId(rs.getInt("categoryid"));
					dto.setUserId(rs.getInt("userid"));
					dto.setUsername(rs.getString("username"));
					dto.setSelected(rs.getBoolean("selected"));
					dto.setCommentCount(rs.getInt("commentcount"));
					dto.setVote(rs.getInt("votecount"));
					list.add(dto);
				}
				return list;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int countAnswerByQuestionId(int parentId) {
		String sql = "SELECT COUNT(*)  FROM tblforumcomment  WHERE Parentid=?";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setInt(1,parentId);
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					return rs.getInt(1);
				}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int countComment() {
		String sql = "SELECT COUNT(*) FROM tblforumcomment WHERE Parentid IS NOT NULL";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					return rs.getInt(1);
				}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public ForumComment getQuestionById(int commentId) {
		String sql =  " SELECT C1.*, U.Username, U.UserImageURL, COUNT(C2.Commentid) COMMENTCOUNT, COUNT(DISTINCT(V.Userid)) VOTECOUNT "
					+ " FROM TBLFORUMCOMMENT C1 INNER JOIN TBLUSER U ON C1.Userid=U.Userid "
					+ " LEFT JOIN (SELECT * FROM TBLFORUMCOMMENT WHERE Parentid=?) C2 ON C1.Commentid=C2.Parentid "
					+ " LEFT JOIN (SELECT * FROM TBLFORUMVOTE WHERE VOTETYPE=1) V ON C1.Commentid=V.Commentid "
					+ " WHERE C1.Commentid=? GROUP BY C1.Commentid, U.Userid";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setInt(1, commentId);
				ps.setInt(2, commentId);
				ResultSet rs = ps.executeQuery();
				if(rs!=null && rs.next()){
					ForumComment q= new ForumComment();
					q.setCommentId(rs.getInt("commentid"));
					q.setPostDate(rs.getDate("postdate"));
					q.setTitle(rs.getString("title"));
					q.setDetail(rs.getString("detail"));
					q.setTag(rs.getString("tag"));
					q.setCategoryId(rs.getInt("categoryid"));
					q.setUserId(rs.getInt("userid"));
					q.setUsername(rs.getString("username"));
					q.setUserImageUrl(rs.getString("userimageurl"));
					q.setCommentCount(rs.getInt("commentcount"));
					q.setVote(rs.getInt("VOTECOUNT"));
					return q;
				}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean inserForumComment(ForumComment dto) {
		String sql = "INSERT INTO tblforumcomment VALUES (NEXTVAL('seq_forumcomment'), ?, ?, ?, ?, ?, ?, ?, ?)";
		try(
			Connection cnn = dataSource.getConnection();
			PreparedStatement ps = cnn.prepareStatement(sql);
		){
			ps.setDate(1, (Date) dto.getPostDate());
			ps.setString(2, dto.getTitle());
			ps.setString(3, dto.getDetail());
			ps.setString(4, dto.getTag());
			ps.setInt(5, dto.getParentId());
			ps.setInt(6, dto.getCategoryId());
			ps.setInt(7, dto.getUserId());
			ps.setBoolean(8, false);
			if (ps.executeUpdate() > 0)
				return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean insertAnswer(ForumComment dto) {
		String sql = "INSERT INTO tblforumcomment VALUES (NEXTVAL('seq_forumcomment'), ?, ?, ?, ?, ?, NULL, ?, ?)";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
			){
				ps.setDate(1, (Date) dto.getPostDate());
				ps.setString(2, dto.getTitle());
				ps.setString(3, dto.getDetail());
				ps.setString(4, dto.getTag());
				ps.setInt(5, dto.getParentId());
				ps.setInt(6, dto.getUserId());
				ps.setBoolean(7, false);
				if (ps.executeUpdate() > 0)
					return true;
			}catch(SQLException e){
				e.printStackTrace();
			}
			return false;
	}

	@Override
	public boolean insetQuestion(ForumComment dto) {
		String sql = " INSERT INTO tblforumcomment (commentid, postdate, title, detail, tag, categoryid, userid, selected)"
				   + " values (NEXTVAL('seq_forumcomment'), ?, ?, ?, ?, ?, ?, ?)";
		   try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
			){
			   ps.setDate(1, (Date) dto.getPostDate());
				ps.setString(2, dto.getTitle());
				ps.setString(3, dto.getDetail());
				ps.setString(4, dto.getTag());
				ps.setInt(5, dto.getCategoryId());
				ps.setInt(6, dto.getUserId());
				ps.setBoolean(7, dto.isSelected());
				if (ps.executeUpdate() > 0)
					return true;
			}catch(SQLException e){
				e.printStackTrace();
			}
			return false;
	}

	@Override
	public boolean deleteComment(int commentId) {
		String sql = "DELETE FROM TBLFORUMCOMMENT WHERE categoryid=?";
		 try(
					Connection cnn = dataSource.getConnection();
					PreparedStatement ps = cnn.prepareStatement(sql);
		 ){
				  ps.setInt(1, commentId);
				  if (ps.executeUpdate() > 0)
						return true;
				}catch(SQLException e){
					e.printStackTrace();
				}
				return false;
	}

	@Override
	public String[] getAllTags() {
		String sql = "SELECT STRING_AGG(DISTINCT(TAGS), ', ') FROM (SELECT CAST(regexp_split_to_table(TAG, ', ') AS VARCHAR) AS TAGS FROM TBLFORUMCOMMENT) C";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString(1).split(",");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

}
