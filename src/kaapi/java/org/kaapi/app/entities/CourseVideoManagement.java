package org.kaapi.app.entities;

import java.sql.Date;

public class CourseVideoManagement {
	
	private String playlistId;
	private String playlistName;
	private String playlistDescription;
	private String playlistThumbnailUrl;
	private boolean playlistStatus;
	
	private String videoId;
	private String videoName;
	private String videoDescription;
	private String youtubeUrl;
	private String fileUrl;
	private Date postDate;
	private int viewCount;
	private int countVotePlus;
	private int countVoteMinus;
	private String categoryName;
	private String mainCategoryName;
	private int index;
	private boolean videoStatus;
	private int countComment;
	
	private String userId;
	private String username;
	public String getPlaylistId() {
		return playlistId;
	}
	public void setPlaylistId(String playlistId) {
		this.playlistId = playlistId;
	}
	public String getPlaylistName() {
		return playlistName;
	}
	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}
	public String getPlaylistDescription() {
		return playlistDescription;
	}
	public void setPlaylistDescription(String playlistDescription) {
		this.playlistDescription = playlistDescription;
	}
	public String getPlaylistThumbnailUrl() {
		return playlistThumbnailUrl;
	}
	public void setPlaylistThumbnailUrl(String playlistThumbnailUrl) {
		this.playlistThumbnailUrl = playlistThumbnailUrl;
	}
	public boolean isPlaylistStatus() {
		return playlistStatus;
	}
	public void setPlaylistStatus(boolean playlistStatus) {
		this.playlistStatus = playlistStatus;
	}
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public String getVideoDescription() {
		return videoDescription;
	}
	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
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
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
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
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public boolean isVideoStatus() {
		return videoStatus;
	}
	public void setVideoStatus(boolean videoStatus) {
		this.videoStatus = videoStatus;
	}
	public int getCountComment() {
		return countComment;
	}
	public void setCountComment(int countComment) {
		this.countComment = countComment;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setMainCategoryName(String mainCategoryName) {
		this.mainCategoryName = mainCategoryName;
	}
	
	public String getMainCategoryName() {
		return mainCategoryName;
	}
	

}
