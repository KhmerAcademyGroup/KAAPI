package org.kaapi.app.entities;

public class Category {
	
	private int categoryId;
	private String categoryName;
	private String categoryLogoUrl;
	private int mainCategoryId;
	private String mainCategoryName;
	private int countVideos;
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryLogoUrl() {
		return categoryLogoUrl;
	}
	public void setCategoryLogoUrl(String categoryLogoUrl) {
		this.categoryLogoUrl = categoryLogoUrl;
	}
	public int getMainCategoryId() {
		return mainCategoryId;
	}
	public void setMainCategoryId(int mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}
	public String getMainCategoryName() {
		return mainCategoryName;
	}
	public void setMainCategoryName(String mainCategoryName) {
		this.mainCategoryName = mainCategoryName;
	}
	public int getCountVideos() {
		return countVideos;
	}
	public void setCountVideos(int countVideos) {
		this.countVideos = countVideos;
	}
	
}
