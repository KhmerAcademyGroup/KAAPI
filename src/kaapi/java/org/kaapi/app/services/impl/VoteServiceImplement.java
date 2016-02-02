package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.kaapi.app.services.VoteService;
import org.kaapi.app.utilities.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VoteServiceImplement implements VoteService {

	@Autowired
	private DataSource dataSource;
	
	@Override
	public int countVote(String videoid) {
		String sql = "SELECT COUNT(*) FROM TBLVOTE WHERE VOTETYPE=1 AND VIDEOID=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(videoid)));
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
	public boolean checkVote(String videoid, String userid) {
		String sql = "SELECT COUNT(VIDEOID) FROM TBLVOTE WHERE userid=? AND videoid=? AND votetype=1";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(userid)));
			ps.setInt(2, Integer.parseInt(Encryption.decode(videoid)));
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)>0) return true;
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
	public boolean vote(String videoid, String userid) {
		
		String sql="";
		if(check(videoid,userid)){
			sql = "UPDATE TBLVOTE SET votetype=1 WHERE userid=? AND videoid=?";	
		}else{
			sql = "INSERT INTO TBLVOTE VALUES(?, ?, 1)";
		}
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(userid)));
			ps.setInt(2, Integer.parseInt(Encryption.decode(videoid)));
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
	public boolean unvote(String videoid, String userid) {
		String sql = "UPDATE TBLVOTE SET votetype=0 WHERE userid=? AND videoid=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(userid)));
			ps.setInt(2, Integer.parseInt(Encryption.decode(videoid)));
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
	public boolean check(String videoid, String userid) {
		String sql = "SELECT COUNT(VIDEOID) FROM TBLVOTE WHERE userid=? AND videoid=?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, Integer.parseInt(Encryption.decode(userid)));
			ps.setInt(2, Integer.parseInt(Encryption.decode(videoid)));
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)>0) return true;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			System.out.println("Error Convert ID To Integer!");
			return false;
		}
		return false;
	}

}
