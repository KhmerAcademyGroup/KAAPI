package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.kaapi.app.forms.FrmAddVolunteer;
import org.kaapi.app.services.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VolunteerImpl implements VolunteerService{

	@Autowired
	DataSource dataSource;
	
	@Override
	public boolean addVolunteer(FrmAddVolunteer volunteer) {
		String sql = "INSERT INTO tbl_volunteer(fullname, email, phone, detail) values( ?,?,?,? );";
		try(
			Connection cnn = dataSource.getConnection();
			PreparedStatement ps = cnn.prepareStatement(sql);
		){
			ps.setString(1, volunteer.getFullname());
			ps.setString(2, volunteer.getEmail());
			ps.setString(3, volunteer.getPhone());
			ps.setString(4, volunteer.getDetail());
			
			if(ps.executeUpdate() > 0){
				return true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

}
