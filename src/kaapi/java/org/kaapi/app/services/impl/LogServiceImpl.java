package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.kaapi.app.entities.Log;
import org.kaapi.app.services.LogService;
import org.kaapi.app.utilities.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LogServiceImpl implements LogService{
	
	@Autowired
	DataSource ds;
	Connection con;

	@Override
	public int insert(Log dto) {
				
		try {
			con = ds.getConnection();
			String sql = "INSERT INTO TBLLOG VALUES(NEXTVAL('seq_log'), ?, ?, NOW(), NOW())";
			PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, Integer.parseInt(Encryption.decode(dto.getUserId())));
			ps.setInt(2, Integer.parseInt(Encryption.decode(dto.getVideoId())));
			if(ps.executeUpdate()>0){
				ResultSet rs=ps.getGeneratedKeys();
				if(rs.next()){
					return rs.getInt(1); 
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public boolean stopWatching(Log dto) {
		
		try {
			con = ds.getConnection();
			String sql = "UPDATE TBLLOG Set stoptime=NOW() WHERE LOGID=? AND USERID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(dto.getLogId()));
			ps.setInt(2, Integer.parseInt(Encryption.decode(dto.getUserId())));
			if(ps.executeUpdate()>0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public ArrayList<Log> listUserInCategory(String categoryid) {
		
		try {
			con = ds.getConnection();
			String sql = "SELECT U.Userid, U.Username, COUNT(L.Videoid) Views, SUM(L.Stoptime-L.Starttime) Duration FROM TBLUser U INNER JOIN TBLLog L ON U.Userid=L.Userid INNER JOIN TBLVideo V ON L.Videoid=V.Videoid INNER JOIN TBLCategoryVideo CV ON V.Videoid=CV.Videoid WHERE CV.Categoryid=? GROUP BY U.Userid";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(categoryid)));
			ResultSet rs = ps.executeQuery();
			
			ArrayList<Log> logs = new ArrayList<Log>(); 
			while(rs.next()){
				Log log = new Log();
				log.setUserId(Encryption.encode((rs.getInt("userid")+"")));
				log.setUsername(rs.getString("username"));
				log.setViews(rs.getInt("views"));
				log.setDuration(rs.getString("duration"));
				logs.add(log);
			}
			return logs;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public ArrayList<Log> listCategory() {
		
		try {
			con = ds.getConnection();
			String sql = "SELECT C.Categoryid, C.CategoryName, COUNT(L.Videoid) Views, SUM(L.Stoptime-L.Starttime) Duration FROM TBLLog L INNER JOIN TBLVideo V ON L.Videoid=V.Videoid INNER JOIN TBLCategoryVideo CV ON V.Videoid=CV.Videoid INNER JOIN TBLCategory C ON C.Categoryid=CV.Categoryid GROUP BY C.Categoryid";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			ArrayList<Log> logs = new ArrayList<Log>(); 
			while(rs.next()){
				Log log = new Log();
				log.setCategoryId(Encryption.encode(rs.getInt("categoryid")+""));
				log.setCategoryName(rs.getString("categoryname"));
				log.setViews(rs.getInt("views"));
				log.setDuration(rs.getString("duration"));
				logs.add(log);
			}
			return logs;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public ArrayList<Log> listCategoryInUser(String userid) {
		
		try {
			con = ds.getConnection();
			String sql = "SELECT C.Categoryid, C.CategoryName, COUNT(L.Videoid) Views, SUM(L.Stoptime-L.Starttime) Duration FROM TBLLog L INNER JOIN TBLVideo V ON L.Videoid=V.Videoid INNER JOIN TBLCategoryVideo CV ON V.Videoid=CV.Videoid INNER JOIN TBLCategory C ON C.Categoryid=CV.Categoryid WHERE L.Userid=? GROUP BY C.Categoryid";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(userid)));
			ResultSet rs = ps.executeQuery();

			ArrayList<Log> logs = new ArrayList<Log>(); 
			while(rs.next()){
				Log log = new Log();
				log.setCategoryId(Encryption.encode(rs.getInt("categoryid")+""));
				log.setCategoryName(rs.getString("categoryname"));
				log.setViews(rs.getInt("views"));
				log.setDuration(rs.getString("duration"));
				logs.add(log);
			}
			return logs;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public ArrayList<Log> listUserInDepartmentAndUniversity(String departmentid,
			String universityid) {
		
		try {
			con = ds.getConnection();
			String sql = "SELECT U.Userid, U.Username, COUNT(L.Videoid) Views, SUM(L.Stoptime-L.Starttime) duration FROM TBLUser U INNER JOIN TBLLog L ON U.Userid=L.Userid WHERE U.Departmentid=? AND U.Universityid=? GROUP BY U.Userid";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(departmentid)));
			ps.setInt(2, Integer.parseInt(Encryption.decode(universityid)));
			ResultSet rs = ps.executeQuery();
			
			ArrayList<Log> logs = new ArrayList<Log>(); 
			while(rs.next()){
				Log log = new Log();
				log.setUserId(Encryption.encode(rs.getInt("userid")+""));
				log.setUsername(rs.getString("username"));
				log.setViews(rs.getInt("views"));
				log.setDuration(rs.getString("duration"));
				logs.add(log);
			}
			return logs;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public ArrayList<Log> listDeparmentByUniversity(String universityid) {
		
		try {
			con = ds.getConnection();
			String sql = "SELECT T1.*, Views, Duration FROM (SELECT D.Departmentid, D.Departmentname, Count(U.Userid) Users FROM TBLDepartment D LEFT JOIN TBLUser U ON D.Departmentid=U.Departmentid AND U.Universityid=? GROUP BY D.Departmentid) T1 LEFT JOIN (SELECT U2.DepartmentID, COUNT(L.Logid) Views, SUM(L.Stoptime-L.Starttime) Duration FROM TBLLog L INNER JOIN TBLUser U2 ON L.Userid=U2.Userid WHERE U2.Departmentid IN (SELECT Departmentid FROM TBLDepartment) AND U2.Universityid=? GROUP BY U2.Departmentid) T2 ON T1.Departmentid=T2.Departmentid";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(universityid)));
			ps.setInt(2, Integer.parseInt(Encryption.decode(universityid)));
			ResultSet rs = ps.executeQuery();
			
			ArrayList<Log> logs = new ArrayList<Log>(); 
			while(rs.next()){
				Log log = new Log();
				log.setDepartmentId(Encryption.encode(rs.getInt("departmentid")+""));
				log.setDepartmentName(rs.getString("departmentname"));
				log.setUsers(rs.getInt("users"));
				log.setViews(rs.getInt("views"));
				log.setDuration(rs.getString("duration"));
				logs.add(log);
			}
			return logs;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public ArrayList<Log> listUniversity() {
		
		try {
			con = ds.getConnection();
			String sql = "SELECT T1.*, Views, Duration FROM (SELECT Un.Universityid, Un.Universityname, Count(U.Userid) Users FROM TBLUniversity Un LEFT JOIN TBLUser U ON Un.Universityid=U.Universityid GROUP BY Un.Universityid) T1 LEFT JOIN (SELECT U2.Universityid, COUNT(L.Logid) Views, SUM(L.Stoptime-L.Starttime) Duration FROM TBLLog L INNER JOIN TBLUser U2 ON L.Userid=U2.Userid WHERE U2.Universityid IN (SELECT Universityid FROM TBLUniversity) GROUP BY U2.Universityid) T2 ON T1.Universityid=T2.Universityid";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			ArrayList<Log> logs = new ArrayList<Log>(); 
			while(rs.next()){
				Log log = new Log();
				log.setUniversityId(Encryption.encode(rs.getInt("universityid")+""));
				log.setUniversityName(rs.getString("universityname"));
				log.setUsers(rs.getInt("users"));
				log.setViews(rs.getInt("views"));
				log.setDuration(rs.getString("duration"));
				logs.add(log);
			}
			return logs;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
