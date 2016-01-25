package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.kaapi.app.entities.Category;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.Tutorial;
import org.kaapi.app.forms.FrmTutorial;
import org.kaapi.app.forms.FrmUpdateTutorial;
import org.kaapi.app.services.TutorialService;
import org.kaapi.app.utilities.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TutorialServiceImpl implements TutorialService{
	
	@Autowired
	private DataSource ds;
	private Connection con;
	
	@Override
	public ArrayList<Tutorial> lists(String userid, Pagination pagination) {
		try {
			con = ds.getConnection();
			ResultSet rs = null;	
			ArrayList<Tutorial> tutorials= new ArrayList<Tutorial>();
			String sql = "SELECT T.tutorialid,T.index, T.title,T.userid,T.categoryid, C.CATEGORYNAME, U.USERNAME FROM TBLTUTORIAL T INNER JOIN TBLCATEGORY C ON T.CATEGORYID=C.CATEGORYID INNER JOIN TBLUSER U ON T.USERID=U.USERID where u.userid=? ORDER BY T.CATEGORYID, T.INDEX OFFSET ? LIMIT ? ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(userid)));
			ps.setInt(2, pagination.offset());
			ps.setInt(3, pagination.getItem());
			rs = ps.executeQuery();
			Tutorial dto = null;
			while(rs.next()){
				dto = new Tutorial();
				dto.setTutorialId(Encryption.encode(rs.getString("tutorialid")));
				dto.setTitle(rs.getString("title"));
				dto.setCategoryId(Encryption.encode(rs.getString("categoryid")));
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
	public ArrayList<Tutorial> list(String categoryid) {
		try {
			con = ds.getConnection();
			ResultSet rs = null;			
			String sql = "SELECT T.title, T.tutorialid, C.CATEGORYNAME, U.USERNAME FROM TBLTUTORIAL T INNER JOIN TBLCATEGORY C ON T.CATEGORYID=C.CATEGORYID INNER JOIN TBLUSER U ON T.USERID=U.USERID WHERE T.CATEGORYID=? ORDER BY T.INDEX ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(categoryid)));
			rs = ps.executeQuery();
			ArrayList<Tutorial> tutorials= new ArrayList<Tutorial>();
			while(rs.next()){
				Tutorial dto= new Tutorial();
				dto.setTutorialId(Encryption.encode(rs.getString("tutorialid")));
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
	public Tutorial get(String tutorialid) {
		try {
			con = ds.getConnection();
			ResultSet rs = null;
			Tutorial dto = null;
			String sql = "SELECT T.tutorialid, T.title, T.index, T.description, C.CATEGORYNAME, U.USERNAME FROM TBLTUTORIAL T INNER JOIN TBLCATEGORY C ON T.CATEGORYID=C.CATEGORYID INNER JOIN TBLUSER U ON T.USERID=U.USERID WHERE T.TUTORIALID=? ORDER BY T.INDEX ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(tutorialid)));
			rs = ps.executeQuery();
			if(rs.next()){
				dto = new Tutorial();
				dto.setTutorialId(Encryption.encode(rs.getString("tutorialid")));
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
	public Tutorial getFirstDetail(String categoryid) {
		try {
			con = ds.getConnection();
			ResultSet rs = null;
			Tutorial dto = null;
			String sql = "SELECT T.*, C.CATEGORYNAME, U.USERNAME FROM TBLTUTORIAL T INNER JOIN TBLCATEGORY C ON T.CATEGORYID=C.CATEGORYID INNER JOIN TBLUSER U ON T.USERID=U.USERID WHERE C.Categoryid=? ORDER BY T.INDEX Limit 1";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(categoryid)));
			rs = ps.executeQuery();
			if(rs.next()){
				dto = new Tutorial();
				dto.setTutorialId(Encryption.encode(rs.getString("tutorialid")));
				dto.setTitle(rs.getString("title"));
				dto.setDescription(rs.getString("description"));
				dto.setIndex(rs.getInt("index"));
				dto.setUserId(Encryption.encode(rs.getString("userid")));
				dto.setCategoryId(Encryption.encode(rs.getString("categoryid")));
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
	public boolean insert(FrmTutorial dto) {
		try {
			con = ds.getConnection();
			String sql = "INSERT INTO TBLTUTORIAL VALUES(NEXTVAL('seq_tutorial'), ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getDescription());
			ps.setInt(3, dto.getIndex());
			ps.setInt(4, Integer.parseInt(Encryption.decode(dto.getUserId())));
			ps.setInt(5, Integer.parseInt(Encryption.decode(dto.getCategoryId())));
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
	public boolean delete(String tutorialid) {
		try {
			con = ds.getConnection();
			String sql = "DELETE FROM TBLTUTORIAL WHERE tutorialid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(tutorialid)));
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
	public int count(String categoryid) {
		
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
	public boolean update(FrmUpdateTutorial dto) {
		try {
			con = ds.getConnection();
			String sql = "UPDATE TBLTUTORIAL SET title=?, description=?, index=?, userid=?, categoryid=? WHERE tutorialid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getDescription());
			ps.setInt(3, dto.getIndex());
			ps.setInt(4, Integer.parseInt(Encryption.decode(dto.getUserId())));
			ps.setInt(5, Integer.parseInt(Encryption.decode(dto.getCategoryId())));
			ps.setInt(6, Integer.parseInt(Encryption.decode(dto.getTutorialId())));
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
	public ArrayList<Category> listCategories() {
		try {
			con = ds.getConnection();
			ResultSet rs = null;			
			String sql = "select c.categoryid, c.categoryname, c.categorylogourl from tblcategory c INNER JOIN tbltutorial t on c.categoryid = t.categoryid GROUP BY c.categoryid ORDER BY c.categoryname";
			PreparedStatement ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			ArrayList<Category> categories= new ArrayList<Category>();
			while(rs.next()){
				Category dto= new Category();
				dto.setCategoryId(Encryption.encode(rs.getString("categoryid")));
				dto.setCategoryName(rs.getString("categoryname"));
				dto.setCategoryLogoUrl(rs.getString("categorylogourl"));
				categories.add(dto);
			}
			rs.close();
			return categories;
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
	public int countByUser(String userid) {
		try {
			con = ds.getConnection();
			ResultSet rs = null;
			String sql = "SELECT COUNT(tutorialid) FROM TBLTUTORIAL where userid=?";
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(Encryption.decode(userid)));
			System.out.println(Integer.parseInt(Encryption.decode(userid)));
			rs = ps.executeQuery();
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


}
