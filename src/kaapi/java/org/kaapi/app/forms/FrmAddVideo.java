package org.kaapi.app.forms;

public class FrmAddVideo {
	
	private String videoName;
	private String description;
	private String youtubeUrl;
	private String fileUrl;
	private boolean publicView;
	private String userId;
	private boolean status;
	private String []categoryId;
	
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String[] getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String[] categoryId) {
		this.categoryId = categoryId;
	}
}
