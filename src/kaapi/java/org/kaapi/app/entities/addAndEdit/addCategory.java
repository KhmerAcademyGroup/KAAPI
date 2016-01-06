package org.kaapi.app.entities.addAndEdit;

public class addCategory {

	
	private String categoryName;
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
	private String categoryLogoUrl;
	private int mainCategoryId;
	
	
}
