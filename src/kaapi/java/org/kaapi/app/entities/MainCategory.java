package org.kaapi.app.entities;

public class MainCategory {
	
	private String mainCategoryId;
	
	private String mainCategoryName;
	private String mainCategoryLogoUrl;
	private int mainCategoryOrder;
	private int countCategory;
	private String backgroundImage;
	private String Color;
	private boolean status;
	private String description;
	
	public String getMainCategoryId() {
		return mainCategoryId;
	}
	public void setMainCategoryId(String mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}
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
	public int getMainCategoryOrder() {
		return mainCategoryOrder;
	}
	public void setMainCategoryOrder(int mainCategoryOrder) {
		this.mainCategoryOrder = mainCategoryOrder;
	}
	public int getCountCategory() {
		return countCategory;
	}
	public void setCountCategory(int countCategory) {
		this.countCategory = countCategory;
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
