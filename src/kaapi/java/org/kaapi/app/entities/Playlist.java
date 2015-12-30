package org.kaapi.app.entities;

public class Playlist {

	private int playlistId;
	private String playlistName;
	private String description;
	private int userId;
	private String thumbnailUrl;
	private boolean publicView;
	private String username;
	private int countVideos;
	private int videoId;
	public int getVideoId() {
		return videoId;
	}
	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}
	private int maincategory;
	private String bgImage;
	private String color;
	private boolean status;
	private String userImageUrl;
	
	public String getUserImageUrl() {
		return userImageUrl;
	}
	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}
	public int getMaincategory() {
		return maincategory;
	}
	public void setMaincategory(int maincategory) {
		this.maincategory = maincategory;
	}
	public String getBgImage() {
		return bgImage;
	}
	public void setBgImage(String bgImage) {
		this.bgImage = bgImage;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getPlaylistId() {
		return playlistId;
	}
	public void setPlaylistId(int playlistId) {
		this.playlistId = playlistId;
	}
	public String getPlaylistName() {
		return playlistName;
	}
	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public boolean isPublicView() {
		return publicView;
	}
	public void setPublicView(boolean publicView) {
		this.publicView = publicView;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getCountVideos() {
		return countVideos;
	}
	public void setCountVideos(int countVideos) {
		this.countVideos = countVideos;
	}
	
}
