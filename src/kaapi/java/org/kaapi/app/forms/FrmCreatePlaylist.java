package org.kaapi.app.forms;

public class FrmCreatePlaylist {
	private String playlistName;
	private String description;
	private String userId;
	private String thumbnailUrl;
	private boolean publicView;
	private String maincategory;
	private String bgImage;
	private String color;
	private boolean status;
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
	
	public String getMaincategory() {
		return maincategory;
	}
	public void setMaincategory(String maincategory) {
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
