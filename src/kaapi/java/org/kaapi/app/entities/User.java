package org.kaapi.app.entities;

import java.sql.Date;

/**
 * @author User
 *
 */
public class User {
	private String userId;
	private String email;
	private String password;
	private String username;
	private String gender;
	private Date dateOfBirth;
	private String phoneNumber;
	private Date registerDate;
	private String userImageUrl;
	private String userTypeId;
	private String userTypeName;
	private int countVideos;
	private int countVotePlus;
	private int countVoteMinus;
	private int countComments;
	private int countPlaylists;
	private boolean viewable;
	private boolean commentable;
	private boolean postable;
	private boolean deleteable;
	private boolean userable;
	private String coverphoto;
//	private String school;
	private String universityId;
	private String departmentId;
	private String universityName;
	private String departmentName;
	private int point;
	private boolean userStatus;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
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
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public String getUserImageUrl() {
		return userImageUrl;
	}
	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}
	public String getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(String userTypeId) {
		this.userTypeId = userTypeId;
	}
	public String getUserTypeName() {
		return userTypeName;
	}
	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
	public int getCountVideos() {
		return countVideos;
	}
	public void setCountVideos(int countVideos) {
		this.countVideos = countVideos;
	}
	public int getCountVotePlus() {
		return countVotePlus;
	}
	public void setCountVotePlus(int countVotePlus) {
		this.countVotePlus = countVotePlus;
	}
	public int getCountVoteMinus() {
		return countVoteMinus;
	}
	public void setCountVoteMinus(int countVoteMinus) {
		this.countVoteMinus = countVoteMinus;
	}
	public int getCountComments() {
		return countComments;
	}
	public void setCountComments(int countComments) {
		this.countComments = countComments;
	}
	public int getCountPlaylists() {
		return countPlaylists;
	}
	public void setCountPlaylists(int countPlaylists) {
		this.countPlaylists = countPlaylists;
	}
	public boolean isViewable() {
		return viewable;
	}
	public void setViewable(boolean viewable) {
		this.viewable = viewable;
	}
	public boolean isCommentable() {
		return commentable;
	}
	public void setCommentable(boolean commentable) {
		this.commentable = commentable;
	}
	public boolean isPostable() {
		return postable;
	}
	public void setPostable(boolean postable) {
		this.postable = postable;
	}
	public boolean isDeleteable() {
		return deleteable;
	}
	public void setDeleteable(boolean deleteable) {
		this.deleteable = deleteable;
	}
	public boolean isUserable() {
		return userable;
	}
	public void setUserable(boolean userable) {
		this.userable = userable;
	}
	public String getCoverphoto() {
		return coverphoto;
	}
	public void setCoverphoto(String coverphoto) {
		this.coverphoto = coverphoto;
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
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public boolean isUserStatus() {
		return userStatus;
	}
	public void setUserStatus(boolean userStatus) {
		this.userStatus = userStatus;
	}
	

}
