package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.kaapi.app.forms.FrmParticipants;
import org.kaapi.app.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ParticipantServiceImpl implements ParticipantService{

	@Autowired
	private DataSource dataSource;
	
	@Override
	public List<FrmParticipants> listAll() {
		List<FrmParticipants> list = new ArrayList<FrmParticipants>();
		String sql = "SELECT * FROM tbl_participants ORDER BY id DESC";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			FrmParticipants part = null;
			while (rs.next()) {
			    part = new FrmParticipants();
				part.setId(rs.getInt("id"));
				part.setUsername(rs.getString("username"));
				part.setContents(rs.getString("contents"));
				part.setPostDate(rs.getString("post_date"));
				part.setPhoto1(rs.getString("photo1"));
				part.setPhoto2(rs.getString("photo2"));
				list.add(part);
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean add(FrmParticipants part) {
		String sql = "INSERT INTO tbl_participants (username,contents,photo1,photo2) "
				+"VALUES "
				+"(?,?,?,?);";
		try(
			Connection cnn = dataSource.getConnection();
			PreparedStatement ps = cnn.prepareStatement(sql);
		){
			ps.setString(1, part.getUsername());
			ps.setString(2, part.getContents());
			ps.setString(3, part.getPhoto1());
			ps.setString(4, part.getPhoto2());
			if(ps.executeUpdate() > 0){
				return true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

}
