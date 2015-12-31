package org.kaapi.app.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.kaapi.app.entities.MainCategory;
import org.kaapi.app.services.MainCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("MainCategoryService")
public class MainCategoryServiceImpl implements MainCategoryService {

	private Connection con;
	@Autowired
	private DataSource dataSource;

	@Override
	public List<MainCategory> listMainCategory(String keyword) {
		try {
			ResultSet rs = null;
			List<MainCategory> maincategory = new ArrayList<MainCategory>();
			MainCategory dto = null;
			String sql = "SELECT MC.*, COUNT(C.categoryid) COUNTCATEGORY FROM TBLMAINCATEGORY MC LEFT JOIN TBLCATEGORY C ON MC.maincategoryid=C.maincategoryid WHERE MC.maincategoryname LIKE ? GROUP BY MC.maincategoryid";
			con = dataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "%" + keyword + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				dto = new MainCategory();
				dto.setMainCategoryName(rs.getString("Maincategoryname"));
				dto.setMainCategoryLogoUrl(rs.getString("Maincategorylogourl"));
				dto.setMainCategoryOrder(rs.getInt("Maincategoryorder"));
				dto.setMainCategoryId(rs.getInt("Maincategoryid"));
				dto.setBackgroundImage(rs.getString("bgImage"));
				dto.setColor(rs.getString("color"));
				dto.setStatus(rs.getBoolean("status"));
				maincategory.add(dto);
			}
			return maincategory;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public MainCategory getMainCategory(int maincategoryid) {
		MainCategory dto = null;
		try {
			ResultSet rs = null;
			List<MainCategory> maincategory = new ArrayList<MainCategory>();
			String sql = "SELECT MC.*, COUNT(C.categoryid) COUNTCATEGORY FROM TBLMAINCATEGORY MC LEFT JOIN TBLCATEGORY C ON MC.maincategoryid=C.maincategoryid WHERE MC.maincategoryid=? GROUP BY MC.maincategoryid";
			con = dataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, maincategoryid);
			rs = ps.executeQuery();

			if (rs.next()) {
				dto = new MainCategory();
				dto.setMainCategoryId(rs.getInt("maincategoryid"));
				dto.setMainCategoryName(rs.getString("maincategoryname"));
				dto.setMainCategoryLogoUrl(rs.getString("maincategorylogourl"));
				dto.setMainCategoryOrder(rs.getInt("maincategoryorder"));
				dto.setCountCategory(rs.getInt("countcategory"));
				dto.setBackgroundImage(rs.getString("bgImage"));
				dto.setColor(rs.getString("color"));
				dto.setStatus(rs.getBoolean("status"));
				maincategory.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return dto;
	}

	@Override
	public boolean insertMainCategory(MainCategory dto) {
		try {
			con = dataSource.getConnection();
			PreparedStatement pstmt = con.prepareStatement("select max(maincategoryorder) from TBLMAINCATEGORY");
			ResultSet rs = pstmt.executeQuery();
			int num = 1;
			if (rs.next())
				num = rs.getInt(1) + 1;

			String sql = "INSERT INTO TBLMAINCATEGORY VALUES(NEXTVAL('seq_maincategory'), ?, ?, ?, ?, ?, ?)";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getMainCategoryName());
			ps.setString(2, dto.getMainCategoryLogoUrl());
			ps.setInt(3, num);
			ps.setString(4, dto.getBackgroundImage());
			ps.setString(5, dto.getColor());
			ps.setBoolean(6, dto.isStatus());
			if (ps.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean updateMainCategory(MainCategory dto) {
		try {
			String sql = "update tblmaincategory set maincategoryorder=(select maincategoryorder "
					+ "from tblmaincategory "
					+ "where maincategoryid=?) "
					+ "where maincategoryorder=?;"
					+ "update tblmaincategory set Maincategoryname=?, Maincategorylogourl=?, Maincategoryorder=?,bgimage=?,color=?,status=? WHERE maincategoryid=?";
			con = dataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, dto.getMainCategoryId());
			ps.setInt(2, dto.getMainCategoryOrder());
			ps.setString(3, dto.getMainCategoryName());
			ps.setString(4, dto.getMainCategoryLogoUrl());
			ps.setInt(5, dto.getMainCategoryOrder());
			ps.setString(6, dto.getBackgroundImage());
			ps.setString(7, dto.getColor());
			ps.setBoolean(8, dto.isStatus());
			ps.setInt(9, dto.getMainCategoryId());
			if (ps.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean deleteMainCategory(int maincategoryid) {

		try {
			System.out.println(maincategoryid + " hellow world");
			String sql = "DELETE FROM TBLMAINCATEGORY WHERE maincategoryid=" + maincategoryid;
			con = dataSource.getConnection();
			Statement stmt = con.createStatement();
			if (stmt.executeUpdate(sql) > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public int countMainCategories() {
		try {
			ResultSet rs = null;
			String sql = "SELECT COUNT(MAINCATEGORYID) FROM TBLMAINCATEGORY";
			con = dataSource.getConnection();
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public int countCategory(int maincategoryid) {
		try {
			ResultSet rs = null;
			String sql = "SELECT COUNT(CATEGORYID) FROM TBLCATEGORY WHERE maincategoryid=" + maincategoryid;
			con = dataSource.getConnection();
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public int getMaxMaincategoryId() {
		ResultSet rs = null;
		int r = 0;
		try {
			String sql = "select LAST_VALUE from seq_maincategory";
			con = dataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
			r = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r + 1;
	}

}
