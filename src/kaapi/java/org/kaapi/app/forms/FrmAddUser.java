package org.kaapi.app.forms;

import java.sql.Date;

public class FrmAddUser {
	
	private String email;
	private String password;
	private String username;
	private String gender;
	private String universityId;
	private String departmentId;
	private String imageUrl;
	private Date dateofbirth;
	
	private String scID;
	private String scType;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getUniversityId() {
		return universityId;
	}
	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getScID() {
		return scID;
	}
	public void setScID(String scID) {
		this.scID = scID;
	}
	public String getScType() {
		return scType;
	}
	public void setScType(String scType) {
		this.scType = scType;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Date getDateofbirth() {
		return dateofbirth;
	}
	
	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	
}
