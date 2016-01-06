package org.kaapi.app.entities.addAndEdit;

public class addMainCategory {
	
	private String mainCategoryName;
	private String mainCategoryLogoUrl;		
	private String backgroundImage;
	private String Color;
	private boolean status;
	
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
	
	
}
