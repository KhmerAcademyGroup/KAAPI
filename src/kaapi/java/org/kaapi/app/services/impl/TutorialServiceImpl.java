package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.kaapi.app.entities.Tutorial;
import org.kaapi.app.services.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TutorialServiceImpl implements TutorialService{
	
	@Autowired
	private DataSource ds;
	private Connection con;
	
	@Override
	public ArrayList<Tutorial> lists(int userid, int offset, int limit) {
		try {
			con = ds.getConnection();
			ResultSet rs = null;	
			ArrayList<Tutorial> tutorials= new ArrayList<Tutorial>();
			String sql = "SELECT T.tutorialid,T.index, T.title,T.userid,T.categoryid, C.CATEGORYNAME, U.USERNAME FROM TBLTUTORIAL T INNER JOIN TBLCATEGORY C ON T.CATEGORYID=C.CATEGORYID INNER JOIN TBLUSER U ON T.USERID=U.USERID where u.userid=? ORDER BY T.CATEGORYID, T.INDEX OFFSET ? LIMIT ? ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, offset);
			ps.setInt(3, limit);
			rs = ps.executeQuery();
			Tutorial dto = null;
			while(rs.next()){
				dto = new Tutorial();
				dto.setTutorialId(rs.getInt("tutorialid"));
				dto.setTitle(rs.getString("title"));
				dto.setCategoryId(rs.getInt("categoryid"));
				dto.setCategoryName(rs.getString("categoryname"));
				dto.setUsername(rs.getString("username"));
				dto.setIndex(rs.getInt("index"));
				
				tutorials.add(dto);

			}
			rs.close();
			return tutorials;
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
		return null;	}

	@Override
	public ArrayList<Tutorial> list(int categoryid) {
		try {
			con = ds.getConnection();
			ResultSet rs = null;			
			String sql = "SELECT T.title, T.tutorialid, C.CATEGORYNAME, U.USERNAME FROM TBLTUTORIAL T INNER JOIN TBLCATEGORY C ON T.CATEGORYID=C.CATEGORYID INNER JOIN TBLUSER U ON T.USERID=U.USERID WHERE T.CATEGORYID=? ORDER BY T.INDEX ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, categoryid);
			rs = ps.executeQuery();
			ArrayList<Tutorial> tutorials= new ArrayList<Tutorial>();
			while(rs.next()){
				Tutorial dto= new Tutorial();
				dto.setTutorialId(rs.getInt("tutorialid"));
				dto.setTitle(rs.getString("title"));
				dto.setCategoryName(rs.getString("categoryname"));
				tutorials.add(dto);
			}
			rs.close();
			return tutorials;
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
	public Tutorial get(int tutorialid) {
		try {
			con = ds.getConnection();
			ResultSet rs = null;
			Tutorial dto = null;
			String sql = "SELECT T.tutorialid, T.title, T.index, T.description, C.CATEGORYNAME, U.USERNAME FROM TBLTUTORIAL T INNER JOIN TBLCATEGORY C ON T.CATEGORYID=C.CATEGORYID INNER JOIN TBLUSER U ON T.USERID=U.USERID WHERE T.TUTORIALID=? ORDER BY T.INDEX ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, tutorialid);
			rs = ps.executeQuery();
			if(rs.next()){
				dto = new Tutorial();
				dto.setTutorialId(rs.getInt("tutorialid"));
				dto.setTitle(rs.getString("title"));
				dto.setDescription(rs.getString("description"));
				dto.setIndex(rs.getInt("index"));
				/*dto.setUserid(rs.getInt("userid"));*/
				/*dto.setCategoryid(rs.getInt("categoryid"));*/
				dto.setUsername(rs.getString("username"));
				dto.setCategoryName(rs.getString("categoryname"));
			}
			rs.close();
			return dto;
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
	public Tutorial getFirstDetail(int categoryid) {
		try {
			con = ds.getConnection();
			ResultSet rs = null;
			Tutorial dto = null;
			String sql = "SELECT T.*, C.CATEGORYNAME, U.USERNAME FROM TBLTUTORIAL T INNER JOIN TBLCATEGORY C ON T.CATEGORYID=C.CATEGORYID INNER JOIN TBLUSER U ON T.USERID=U.USERID WHERE C.Categoryid=? ORDER BY T.INDEX Limit 1";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, categoryid);
			rs = ps.executeQuery();
			if(rs.next()){
				dto = new Tutorial();
				dto.setTutorialId(rs.getInt("tutorialid"));
				dto.setTitle(rs.getString("title"));
				dto.setDescription(rs.getString("description"));
				dto.setIndex(rs.getInt("index"));
				dto.setUserId(rs.getInt("userid"));
				dto.setCategoryId(rs.getInt("categoryid"));
				dto.setUsername(rs.getString("username"));
				dto.setCategoryName(rs.getString("categoryname"));
			}
			rs.close();
			return dto;
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
	public boolean insert(Tutorial dto) {
		try {
			con = ds.getConnection();
			String sql = "INSERT INTO TBLTUTORIAL VALUES(NEXTVAL('seq_tutorial'), ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getDescription());
			ps.setInt(3, dto.getIndex());
			ps.setInt(4, dto.getUserId());
			ps.setInt(5, dto.getCategoryId());
			if(ps.executeUpdate()>0)
				return true;
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
	public boolean delete(int tutorialid) {
		try {
			con = ds.getConnection();
			String sql = "DELETE FROM TBLTUTORIAL WHERE tutorialid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, tutorialid);
			if(ps.executeUpdate()>0)
				return true;
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
	public int count() {
		try {
			con = ds.getConnection();
			ResultSet rs = null;
			String sql = "SELECT COUNT(tutorialid) FROM TBLTUTORIAL";
			Statement stmt = con.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next())
				return rs.getInt(1);
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
	public int count(int categoryid) {
		
		try {
			con = ds.getConnection();
			ResultSet rs = null;
			String sql = "SELECT COUNT(tutorialid) FROM TBLTUTORIAL WHERE Categoryid="+categoryid;
			Statement stmt = con.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next())
				return rs.getInt(1);
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
	public boolean update(Tutorial dto) {
		try {
			con = ds.getConnection();
			String sql = "UPDATE TBLTUTORIAL SET title=?, description=?, index=?, userid=?, categoryid=? WHERE tutorialid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getDescription());
			ps.setInt(3, dto.getIndex());
			ps.setInt(4, dto.getUserId());
			ps.setInt(5, dto.getCategoryId());
			ps.setInt(6, dto.getTutorialId());
			if(ps.executeUpdate()>0)
				return true;
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

}
