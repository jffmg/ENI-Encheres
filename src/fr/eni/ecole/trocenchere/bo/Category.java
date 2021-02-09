package fr.eni.ecole.trocenchere.bo;

public class Category {
	
	private int categoryId;
	private String categoryLabel;
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryLabel() {
		return categoryLabel;
	}
	public void setCategoryLabel(String categoryLabel) {
		this.categoryLabel = categoryLabel;
	}
	
	public Category(int categoryId, String categoryLabel) {
		this.categoryId = categoryId;
		this.categoryLabel = categoryLabel;
	}
	
	public Category() {
	}
	
	
	
}
