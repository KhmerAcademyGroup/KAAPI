package org.kaapi.app.forms;

public class FrmAddMainCategory {
	
	private String mainCategoryName;
	private String mainCategoryLogoUrl;		
	private String backgroundImage;
	private String Color;
	private boolean status;
	private String description;
	
	public String getMainCategoryName() {
		return mainCategoryName;
	}
	public void setMainCategoryName(String mainCategoryName) {
		this.mainCategoryName = mainCategoryName;
	}
	public String getMainCategoryLogoUrl() {
		return mainCategoryLogoUrl;
	}
	public void setMainCategoryLogoUrl(String mainCategoryLogoUrl) {
		this.mainCategoryLogoUrl = mainCategoryLogoUrl;
	}	
	public String getBackgroundImage() {
		return backgroundImage;
	}
	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	public String getColor() {
		return Color;
	}
	public void setColor(String color) {
		Color = color;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
