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
import org.kaapi.app.forms.FrmAddAnswer;
import org.kaapi.app.forms.FrmAddQuestion;
import org.kaapi.app.forms.FrmUpdateAnswer;
import org.kaapi.app.forms.FrmUpdateQuestion;
import org.kaapi.app.services.ForumCommentService;
import org.kaapi.app.utilities.Encryption;
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
					dto.setCommentId(Encryption.encode(rs.getString("commentid")));
					dto.setPostDate(rs.getDate("postdate"));
					dto.setTitle(rs.getString("title"));
					dto.setDetail(rs.getString("detail"));
					dto.setTag(rs.getString("tag"));
					if(rs.getString("parentid") != null){
						dto.setParentId(Encryption.encode(rs.getString("parentid")));
					}
					if(rs.getString("categoryid") != null){
						dto.setCategoryId(Encryption.encode(rs.getString("categoryid")));
					}
					dto.setUserId(Encryption.encode(rs.getString("userid")));
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
	public List<ForumComment> listQuestionByUserid(String userid, Pagination pagination) {
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
				ps.setInt(1, Integer.parseInt(Encryption.decode(userid)));
				ps.setInt(2, pagination.getItem());
				ps.setInt(3, pagination.offset());
				ResultSet rs = ps.executeQuery();
				List<ForumComment> list = new ArrayList<ForumComment>();
				ForumComment dto = null;
				while(rs.next()){
					dto  = new ForumComment();
					dto.setCommentId(Encryption.encode(rs.getString("commentid")));
					dto.setPostDate(rs.getDate("postdate"));
					dto.setTitle(rs.getString("title"));
					dto.setDetail(rs.getString("detail"));
					dto.setTag(rs.getString("tag"));
					if(rs.getString("parentid") != null){
						dto.setParentId(Encryption.encode(rs.getString("parentid")));
					}
					if(rs.getString("categoryid") != null){
						dto.setCategoryId(Encryption.encode(rs.getString("categoryid")));
					}
					dto.setUserId(Encryption.encode(rs.getString("userid")));
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
	public int countQuestionByUserid(String userid) {
		String sql = "SELECT COUNT(commentid) FROM tblforumcomment WHERE userid = ? AND Parentid IS NULL";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setInt(1, Integer.parseInt(Encryption.decode(userid)));
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
	public List<ForumComment> listQuestionByCategoryId(String cateid , Pagination pagination) {
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
				ps.setInt(1, Integer.parseInt(Encryption.decode(cateid)));
				ps.setInt(2, pagination.getItem());
				ps.setInt(3, pagination.offset());
				ResultSet rs = ps.executeQuery();
				List<ForumComment> list = new ArrayList<ForumComment>();
				ForumComment dto = null;
				while(rs.next()){
					dto  = new ForumComment();
					dto.setCommentId(Encryption.encode(rs.getString("commentid")));
					dto.setPostDate(rs.getDate("postdate"));
					dto.setTitle(rs.getString("title"));
					dto.setDetail(rs.getString("detail"));
					dto.setTag(rs.getString("tag"));
					if(rs.getString("parentid") != null){
						dto.setParentId(Encryption.encode(rs.getString("parentid")));
					}
					if(rs.getString("categoryid") != null){
						dto.setCategoryId(Encryption.encode(rs.getString("categoryid")));
					}
					dto.setUserId(Encryption.encode(rs.getString("userid")));
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
	public int countQuestionByCategoryId(String cateid) {
		String sql = "SELECT COUNT(commentid) FROM tblforumcomment WHERE categoryid = ?";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setInt(1, Integer.parseInt(Encryption.decode(cateid)));
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
						+ " LOWER(C1.title) like  LOWER(?)"
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
					dto.setCommentId(Encryption.encode(rs.getString("commentid")));
					dto.setPostDate(rs.getDate("postdate"));
					dto.setTitle(rs.getString("title"));
					dto.setDetail(rs.getString("detail"));
					dto.setTag(rs.getString("tag"));
					if(rs.getString("parentid") != null){
						dto.setParentId(Encryption.encode(rs.getString("parentid")));
					}
					if(rs.getString("categoryid") != null){
						dto.setCategoryId(Encryption.encode(rs.getString("categoryid")));
					}
					dto.setUserId(Encryption.encode(rs.getString("userid")));
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
					+ " AND  LOWER(title) like  LOWER(?)";
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
	public List<ForumComment> listQuestionByTag(String tag, Pagination pagination) {
		String sql =      " SELECT DISTINCT(C1.*), U.Username, COUNT(C2.Commentid) COMMENTCOUNT, COUNT(FV.Commentid) VOTECOUNT"
						+ " FROM TBLFORUMCOMMENT C1 LEFT JOIN TBLFORUMCOMMENT C2 ON C1.Commentid=C2.Parentid"
						+ " INNER JOIN TBLUSER U ON C1.Userid=U.Userid"
						+ " LEFT JOIN TBLFORUMVOTE FV ON C1.Commentid=FV.Commentid AND FV.Votetype=1"
						+ " WHERE "
						+ "  LOWER(C1.tag) like  LOWER(?)"
						+ " AND C1.Parentid IS NULL"
						+ " GROUP BY C1.Commentid, C2.Commentid, U.Userid, FV.Commentid"
						+ " ORDER BY COMMENTID DESC offset ? limit ?";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setString(1, "%"+tag+"%");
				ps.setInt(2, pagination.offset()); 
				ps.setInt(3, pagination.getItem());
				ResultSet rs = ps.executeQuery();
				List<ForumComment> list = new ArrayList<ForumComment>();
				ForumComment dto = null;
				while(rs.next()){
					dto  = new ForumComment();
					dto.setCommentId(Encryption.encode(rs.getString("commentid")));
					dto.setPostDate(rs.getDate("postdate"));
					dto.setTitle(rs.getString("title"));
					dto.setDetail(rs.getString("detail"));
					dto.setTag(rs.getString("tag"));
					if(rs.getString("parentid") != null){
						dto.setParentId(Encryption.encode(rs.getString("parentid")));
					}
					if(rs.getString("categoryid") != null){
						dto.setCategoryId(Encryption.encode(rs.getString("categoryid")));
					}
					dto.setUserId(Encryption.encode(rs.getString("userid")));
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
	public int countQuestionByTag(String tag) {
		String sql =  " SELECT COUNT(*) FROM TBLFORUMCOMMENT WHERE Parentid IS NULL"
					+ " AND  LOWER(tag) like  LOWER(?)";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setString(1, "%"+tag+"%");
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
	public List<ForumComment> listAnswerByQuestionId(String parendId, Pagination pagination) {
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
				ps.setInt(1, Integer.parseInt(Encryption.decode(parendId)));
				ps.setInt(2, pagination.offset()); 
				ps.setInt(3, pagination.getItem());	ResultSet rs = ps.executeQuery();
				List<ForumComment> list = new ArrayList<ForumComment>();
				ForumComment dto = null;
				while(rs.next()){
					dto  = new ForumComment();
					dto.setCommentId(Encryption.encode(rs.getString("commentid")));
					dto.setPostDate(rs.getDate("postdate"));
					dto.setTitle(rs.getString("title"));
					dto.setDetail(rs.getString("detail"));
					dto.setTag(rs.getString("tag"));
					if(rs.getString("parentid") != null){
						dto.setParentId(Encryption.encode(rs.getString("parentid")));
					}
					if(rs.getString("categoryid") != null){
						dto.setCategoryId(Encryption.encode(rs.getString("categoryid")));
					}
					dto.setUserId(Encryption.encode(rs.getString("userid")));
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
	public int countAnswerByQuestionId(String parentId) {
		String sql = "SELECT COUNT(*)  FROM tblforumcomment  WHERE Parentid=?";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setInt(1,Integer.parseInt(Encryption.decode(parentId)));
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
	public int countAnswer() {
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
	public ForumComment getQuestionById(String commentId) {
		String sql =  " SELECT C1.*, U.Username, U.UserImageURL, COUNT(C2.Commentid) COMMENTCOUNT, COUNT(DISTINCT(V.Userid)) VOTECOUNT "
					+ " FROM TBLFORUMCOMMENT C1 INNER JOIN TBLUSER U ON C1.Userid=U.Userid "
					+ " LEFT JOIN (SELECT * FROM TBLFORUMCOMMENT WHERE Parentid=?) C2 ON C1.Commentid=C2.Parentid "
					+ " LEFT JOIN (SELECT * FROM TBLFORUMVOTE WHERE VOTETYPE=1) V ON C1.Commentid=V.Commentid "
					+ " WHERE C1.Commentid=? GROUP BY C1.Commentid, U.Userid";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
		){
				ps.setInt(1, Integer.parseInt(Encryption.decode(commentId)));
				ps.setInt(2, Integer.parseInt(Encryption.decode(commentId)));
				ResultSet rs = ps.executeQuery();
				if(rs!=null && rs.next()){
					ForumComment q= new ForumComment();
					q.setCommentId(Encryption.encode(rs.getString("commentid")));
					q.setPostDate(rs.getDate("postdate"));
					q.setTitle(rs.getString("title"));
					q.setDetail(rs.getString("detail"));
					q.setTag(rs.getString("tag"));
					if(rs.getString("categoryid") != null){
						q.setCategoryId(Encryption.encode(rs.getString("categoryid")));
					}
					q.setUserId(Encryption.encode(rs.getString("userid")));
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
	public boolean insertAnswer(FrmAddAnswer dto) {
		String sql = " INSERT INTO tblforumcomment (commentid, postdate, title, detail, tag, parentid , categoryid, userid, selected)"
					+" VALUES (NEXTVAL('seq_forumcomment'), NOW(), ? , ? , ? , ? , NULL , ? , false);";
		try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
			){
				ps.setString(1, dto.getTitle());
				ps.setString(2, dto.getDetail());
				ps.setString(3, dto.getTags());
				ps.setInt(4, Integer.parseInt(Encryption.decode(dto.getParentId())));
				ps.setInt(5, Integer.parseInt(Encryption.decode(dto.getUserId())));
				if (ps.executeUpdate() > 0)
					return true;
			}catch(SQLException e){
				e.printStackTrace();
			}
			return false;
	}

	@Override
	public boolean insetQuestion(FrmAddQuestion dto) {
		String sql = " INSERT INTO tblforumcomment (commentid, postdate, title, detail, tag, categoryid, userid, selected)"
				   + " values (NEXTVAL('seq_forumcomment'), now(), ?, ?, ?,?,?, true)";
		   try(
				Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
			){
				ps.setString(1, dto.getTitle());
				ps.setString(2, dto.getDetail());
				ps.setString(3, dto.getTags());
				ps.setInt(4, Integer.parseInt(Encryption.decode(dto.getCategoryId())));
				ps.setInt(5, Integer.parseInt(Encryption.decode(dto.getUserId())));
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



	@Override
	public boolean deleteAnswer(String answerId) {
		String sql = "UPDATE tblforumcomment SET status = ? WHERE commentid = ?";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)) {
		   ps.setBoolean(1, false);
		   ps.setInt(2, Integer.parseInt(Encryption.decode(answerId)));
			if(ps.executeUpdate()>0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}



	@Override
	public boolean updateAnswer(FrmUpdateAnswer updateAnswer) {
		String sql = "UPDATE tblforumcomment SET title=?, detail=?, tag=? , categoryid = ? WHERE commentid = ?";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)) {
			ps.setString(1, updateAnswer.getTitle());
			ps.setString(2, updateAnswer.getDetail());
			ps.setString(3, updateAnswer.getTags());
			ps.setInt(4, Integer.parseInt(Encryption.decode(updateAnswer.getCategoryId())));
			ps.setInt(5, Integer.parseInt(Encryption.decode(updateAnswer.getAnswerId())));
			if(ps.executeUpdate()>0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}



	@Override
	public boolean deleteQuestion(String questionId) {
		String sql = "UPDATE tblforumcomment SET status = ? WHERE commentid = ?";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)) {
			 ps.setBoolean(1, false);
			ps.setInt(2, Integer.parseInt(Encryption.decode(questionId)));
			if(ps.executeUpdate()>0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}



	@Override
	public boolean updateQuestion(FrmUpdateQuestion updateQuestion) {
		String sql = "UPDATE tblforumcomment SET title=?, detail=?, tag=? , categoryid = ? WHERE commentid = ?";
		try(Connection cnn = dataSource.getConnection() ; PreparedStatement ps = cnn.prepareStatement(sql)) {
			ps.setString(1, updateQuestion.getTitle());
			ps.setString(2, updateQuestion.getDetail());
			ps.setString(3, updateQuestion.getTags());
			ps.setInt(4, Integer.parseInt(Encryption.decode(updateQuestion.getCategoryId())));
			ps.setInt(5, Integer.parseInt(Encryption.decode(updateQuestion.getQuestionId())));
			if(ps.executeUpdate()>0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}



	@Override
	public ForumComment getSelectedAnswerByQuestionId(String parentId) {
		String sql =  " SELECT DISTINCT(C1.*), U.Username, U.UserImageURL, C1.SELECTED, COUNT(C2.Commentid) COMMENTCOUNT, SUM(VOTETYPE) VOTECOUNT "
				+ " FROM TBLFORUMCOMMENT C1 LEFT JOIN TBLFORUMCOMMENT C2 ON C1.Commentid=C2.Parentid "
				+ " INNER JOIN TBLUSER U ON C1.Userid=U.Userid "
				+ " LEFT JOIN TBLFORUMVOTE FV ON C1.Commentid=FV.Commentid "
				+ " WHERE C1.Parentid=? AND C1.SELECTED=true GROUP BY C1.Commentid, U.Userid, FV.Commentid ";

	try(
			Connection cnn = dataSource.getConnection();
			PreparedStatement ps = cnn.prepareStatement(sql);
	){
			ps.setInt(1, Integer.parseInt(Encryption.decode(parentId)));	
			ResultSet rs = ps.executeQuery();
			ForumComment dto = null;
			while(rs.next()){
				dto  = new ForumComment();
				dto.setCommentId(Encryption.encode(rs.getString("commentid")));
				dto.setPostDate(rs.getDate("postdate"));
				dto.setTitle(rs.getString("title"));
				dto.setDetail(rs.getString("detail"));
				dto.setTag(rs.getString("tag"));
				if(rs.getString("parentid") != null){
					dto.setParentId(Encryption.encode(rs.getString("parentid")));
				}
				if(rs.getString("categoryid") != null){
					dto.setCategoryId(Encryption.encode(rs.getString("categoryid")));
				}
				dto.setUserId(Encryption.encode(rs.getString("userid")));
				dto.setUsername(rs.getString("username"));
				dto.setSelected(rs.getBoolean("selected"));
				dto.setCommentCount(rs.getInt("commentcount"));
				dto.setVote(rs.getInt("votecount"));
			}
			return dto;
	}catch(SQLException e){
		e.printStackTrace();
	}
	return null;
	}

	
}
