package org.kaapi.app.entities;

import java.sql.Date;

public class Video {

	private int videoId;
	private String videoName;
	private String description;
	private String youtubeUrl;
	private String fileUrl;
	private boolean publicView;
	private Date postDate;
	private int userId;
	private String username;
	private String categoryName;	//e.g. "Java, Jsp, J2EE, " it's multiple concated string
	private int countVotePlus;
	private int countVoteMinus;
	private int countComments;
	private int viewCounts;
	private String userImageUrl;
	
	private int countAllUser;
	private int countAllPlaylist;
	private int countAllQuestion;
	private int countAllVideo;
	
	public int getVideoId() {
		return videoId;
	}
	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getYoutubeUrl() {
		return youtubeUrl;
	}
	public void setYoutubeUrl(String youtubeUrl) {
		this.youtubeUrl = youtubeUrl;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public boolean isPublicView() {
		return publicView;
	}
	public void setPublicView(boolean publicView) {
		this.publicView = publicView;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	public int getViewCounts() {
		return viewCounts;
	}
	public void setViewCounts(int viewCounts) {
		this.viewCounts = viewCounts;
	}
	public String getUserImageUrl() {
		return userImageUrl;
	}
	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}
	public int getCountAllUser() {
		return countAllUser;
	}
	public void setCountAllUser(int countAllUser) {
		this.countAllUser = countAllUser;
	}
	public int getCountAllPlaylist() {
		return countAllPlaylist;
	}
	public void setCountAllPlaylist(int countAllPlaylist) {
		this.countAllPlaylist = countAllPlaylist;
	}
	public int getCountAllQuestion() {
		return countAllQuestion;
	}
	public void setCountAllQuestion(int countAllQuestion) {
		this.countAllQuestion = countAllQuestion;
	}
	public int getCountAllVideo() {
		return countAllVideo;
	}
	public void setCountAllVideo(int countAllVideo) {
		this.countAllVideo = countAllVideo;
	}
	
}
