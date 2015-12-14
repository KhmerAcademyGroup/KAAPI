package org.kaapi.app.entities;

public class MainCategory {
	
	private int mainCategoryId;
	private String mainCategoryName;
	private String mainCategoryLogoUrl;
	private int mainCategoryOrder;
	private int countCategory;
	
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

}
