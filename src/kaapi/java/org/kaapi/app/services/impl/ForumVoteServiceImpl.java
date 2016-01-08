package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.kaapi.app.forms.FrmSelectAnswer;
import org.kaapi.app.forms.FrmVote;
import org.kaapi.app.services.ForumVoteService;
import org.kaapi.app.utilities.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ForumVoteServiceImpl implements ForumVoteService{

	@Autowired
	DataSource dataSource;
	
	@Override
	public int votePlus(FrmVote vote) {
		return vote(vote.getUserId(), vote.getCommentId(), 1); 
	}

	@Override
	public int vote(String userId, String commentId, int voteType) {
		String sql = "INSERT INTO TBLFORUMVOTE VALUES(?, ?, ?)";
		String sql2 = "UPDATE TBLFORUMVOTE SET votetype=? WHERE userid=? AND commentid=?";
		String sql3 = "SELECT SUM(VoteType) FROM TBLFORUMVOTE WHERE commentid=?";
		Connection cnn = null;
		try {
			cnn = dataSource.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try{ 
			PreparedStatement ps = cnn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(userId)));
			ps.setInt(2, Integer.parseInt(Encryption.decode(commentId)));
			ps.setInt(3, voteType);
			ps.executeUpdate();
		} catch (SQLException e) {
			try{
				PreparedStatement ps2 = cnn.prepareStatement(sql2);
				ps2.setInt(1, voteType);
				ps2.setInt(2, Integer.parseInt(Encryption.decode(userId)));
				ps2.setInt(3, Integer.parseInt(Encryption.decode(commentId)));
				ps2.executeUpdate();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} finally {
			try{
				PreparedStatement ps3 = cnn.prepareStatement(sql3);
				ps3.setInt(1, Integer.parseInt(Encryption.decode(commentId)));
				ResultSet rs = ps3.executeQuery();
				if (rs.next()) {
					return rs.getInt(1);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}finally {
				try {
					cnn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	@Override
	public int voteMinus(FrmVote vote) {
		return vote(vote.getUserId(), vote.getCommentId(), -1);
	}

	@Override
	public int unvote(FrmVote vote) {
		return vote(vote.getUserId(), vote.getCommentId(), 0);
	}

	@Override
	public int countPlus() {
		String sql = "SELECT COUNT(*) FROM TBLFORUMVOTE WHERE votetype=1";
		try(
			Connection cnn = dataSource.getConnection(); 
			PreparedStatement ps = cnn.prepareStatement(sql);
		){
			ResultSet rs = ps.executeQuery(sql);
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int countMinus() {
		String sql = "SELECT COUNT(*) FROM TBLFORUMVOTE WHERE votetype=-1";
		try(
			Connection cnn = dataSource.getConnection();
			PreparedStatement ps = cnn.prepareStatement(sql);
		){
			ResultSet rs = ps.executeQuery(sql);
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int count(String commentId) {
		String sql = "SELECT SUM(VoteType) FROM TBLFORUMVOTE WHERE commentid=?";
		try (
			Connection cnn = dataSource.getConnection();
			PreparedStatement ps = cnn.prepareStatement(sql);
		){
			ps.setInt(1, Integer.parseInt(Encryption.decode(commentId)));
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
	public int checkUserVote(FrmVote vote) {
		String sql = "SELECT VP.*, VM.* FROM "
				+ "(SELECT COUNT(commentid) COUNTMINUS FROM TBLFORUMVOTE WHERE userid=? AND commentid=? AND votetype=1) VP, "
				+ "(SELECT COUNT(commentid) COUNTPLUS FROM TBLFORUMVOTE WHERE userid=? AND commentid=? AND votetype=-1) VM";
		try(
			Connection cnn = dataSource.getConnection();
			PreparedStatement ps = cnn.prepareStatement(sql);
		){
			ps.setInt(1, Integer.parseInt(Encryption.decode(vote.getUserId())));
			ps.setInt(2, Integer.parseInt(Encryption.decode(vote.getCommentId())));
			ps.setInt(3, Integer.parseInt(Encryption.decode(vote.getUserId())));
			ps.setInt(4, Integer.parseInt(Encryption.decode(vote.getCommentId())));
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)>0)
					return 1;	//already liked
				else if(rs.getInt(2)>0)
					return -1;	//already disliked
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean selectAnswer(FrmSelectAnswer selectAnswer) {
		String sql1 = "UPDATE TBLFORUMCOMMENT SET selected=false WHERE parentid =?";
		String sql2 = "UPDATE TBLFORUMCOMMENT SET selected=true WHERE parentid = ? and commentid = ?";
		Connection cnn = null;
		try{
			cnn = dataSource.getConnection();
			PreparedStatement ps1 = cnn.prepareStatement(sql1);
			ps1.setInt(1, Integer.parseInt(Encryption.decode(selectAnswer.getQuestionId())));
			if(ps1.executeUpdate()>0){
				PreparedStatement ps2 = cnn.prepareStatement(sql2);		
				ps2.setInt(1, Integer.parseInt(Encryption.decode(selectAnswer.getQuestionId())));
				ps2.setInt(2, Integer.parseInt(Encryption.decode(selectAnswer.getAnswerId())));
				if(ps2.executeUpdate()>0){
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				cnn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
