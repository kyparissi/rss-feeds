package com.inx.entity;

import java.util.List;

public class CsvMetaData {
	private String             magazineName;	
	private String             testStatus;
	private String             description;
	private String             techCategory;
	private String             magazineUrl;
	private Long               dateAdded;
	private String             title;
	private String             rssHomelink;
	private List<Image>        imagesList;
	public String getMagazineName() {
		return magazineName;
	}
	public void setMagazineName(String magazineName) {
		this.magazineName = magazineName;
	}
	public String getTestStatus() {
		return testStatus;
	}
	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTechCategory() {
		return techCategory;
	}
	public void setTechCategory(String techCategory) {
		this.techCategory = techCategory;
	}
	public String getMagazineUrl() {
		return magazineUrl;
	}
	public void setMagazineUrl(String magazineUrl) {
		this.magazineUrl = magazineUrl;
	}
	public Long getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Long dateAdded) {
		this.dateAdded = dateAdded;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRssHomelink() {
		return rssHomelink;
	}
	public void setRssHomelink(String rssHomelink) {
		this.rssHomelink = rssHomelink;
	}
	public List<Image> getImagesList() {
		return imagesList;
	}
	public void setImagesList(List<Image> imagesList) {
		this.imagesList = imagesList;
	}
	
	
}
