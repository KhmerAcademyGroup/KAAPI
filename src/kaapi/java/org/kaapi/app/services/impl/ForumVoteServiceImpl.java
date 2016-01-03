package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.kaapi.app.services.ForumVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ForumVoteServiceImpl implements ForumVoteService{

	@Autowired
	DataSource dataSource;
	
	@Override
	public int votePlus(int userId, int commentId) {
		return vote(userId, commentId, 1); 
	}

	@Override
	public int vote(int userId, int commentId, int voteType) {
		String sql = "INSERT INTO TBLFORUMVOTE VALUES(?, ?, ?)";
		String sql2 = "UPDATE TBLFORUMVOTE SET votetype=? WHERE userid=? AND commentid=?";
		String sql3 = "SELECT SUM(VoteType) FROM TBLFORUMVOTE WHERE commentid="+ commentId;
		Connection cnn = null;
		try {
			cnn = dataSource.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try{ 
			PreparedStatement ps = cnn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, commentId);
			ps.setInt(3, voteType);
			ps.executeUpdate();
		} catch (SQLException e) {
			try{
				PreparedStatement ps2 = cnn.prepareStatement(sql2);
				ps2.setInt(1, voteType);
				ps2.setInt(2, userId);
				ps2.setInt(3, commentId);
				ps2.executeUpdate();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} finally {
			try{
				PreparedStatement ps3 = cnn.prepareStatement(sql3);
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
	public int voteMinus(int userId, int commentId) {
		return vote(userId, commentId, -1);
	}

	@Override
	public int unvote(int userId, int commentId) {
		return vote(userId, commentId, 0);
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
	public int count(int commentid) {
		String sql = "SELECT SUM(VoteType) FROM TBLFORUMVOTE WHERE commentid=?";
		try (
			Connection cnn = dataSource.getConnection();
			PreparedStatement ps = cnn.prepareStatement(sql);
		){
			ps.setInt(1, commentid);
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
	public int checkUserVote(int userId, int commentId) {
		String sql = "SELECT VP.*, VM.* FROM "
				+ "(SELECT COUNT(commentid) COUNTMINUS FROM TBLFORUMVOTE WHERE userid=? AND commentid=? AND votetype=1) VP, "
				+ "(SELECT COUNT(commentid) COUNTPLUS FROM TBLFORUMVOTE WHERE userid=? AND commentid=? AND votetype=-1) VM";
		try(
			Connection cnn = dataSource.getConnection();
			PreparedStatement ps = cnn.prepareStatement(sql);
		){
			ps.setInt(1, userId);
			ps.setInt(2, commentId);
			ps.setInt(3, userId);
			ps.setInt(4, commentId);
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
	public boolean selectAnswer(int commentId, int parentId) {System.out.println("I am here");
		String sql1 = "UPDATE TBLFORUMCOMMENT SET selected=false WHERE parentid =?";
		String sql2 = "UPDATE TBLFORUMCOMMENT SET selected=true WHERE parentid = ? and commentid = ?";
		Connection cnn = null;
		try{
			cnn = dataSource.getConnection();
			PreparedStatement ps1 = cnn.prepareStatement(sql1);
			ps1.setInt(1, parentId);
			if(ps1.executeUpdate()>0){
				PreparedStatement ps2 = cnn.prepareStatement(sql2);		
				ps2.setInt(1, parentId);
				ps2.setInt(2, commentId);
				if(ps2.executeUpdate()>0){
					System.out.println("I am here");
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
