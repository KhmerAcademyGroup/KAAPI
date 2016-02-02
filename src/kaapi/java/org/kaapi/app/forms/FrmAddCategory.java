package org.kaapi.app.forms;

public class FrmAddCategory {

	
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
	public String getMainCategoryId() {
		return mainCategoryId;
	}
	public void setMainCategoryId(String mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}
	private String categoryLogoUrl;
	private String mainCategoryId;
	
	
}
