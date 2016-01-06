package org.kaapi.app.entities;

import java.sql.Date;

public class History {

	private String historyId;
	private Date historyDate;
	private String userId;
	private String videoId;
	private String username;
	private String videoName;
	private String videoUrl;
	private String videoDescription;
	private String videoViewCount;
	
	
	public Date getHistoryDate() {
		return historyDate;
	}
	public void setHistoryDate(Date historyDate) {
		this.historyDate = historyDate;
	}
	
	public String getHistoryId() {
		return historyId;
	}
	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getVideoDescription() {
		return videoDescription;
	}
	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}
	public String getVideoViewCount() {
		return videoViewCount;
	}
	public void setVideoViewCount(String videoViewCount) {
		this.videoViewCount = videoViewCount;
	}
	
}
