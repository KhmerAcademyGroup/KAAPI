package org.kaapi.app.forms;

public class FrmUpdatePlaylist {
	private String playlistId;
	private String playlistName;
	private String description;
	private String userId;
	private String thumbnailUrl;
	private boolean publicView;
	private int maincategory;
	private String bgImage;
	private String color;
	private boolean status;
	public String getPlaylistId() {
		return playlistId;
	}
	public void setPlaylistId(String playlistId) {
		this.playlistId = playlistId;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
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
}
