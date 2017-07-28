package com.inx.entity;

import java.util.List;

public class RssArticle {
	private String             atricleGuid;
	private String             articleTitle;
    private Long               articlePubDate;
    private String             articleAuthor;
    private String             articleCreator;
    private String             articleLink;
    private String             articleDescription;
    private String             articleMedia;
    private List<String>       categories;
    private String             rawArticle;
    private String             keywordArticle;
	public String getAtricleGuid() {
		return atricleGuid;
	}
	public void setAtricleGuid(String atricleGuid) {
		this.atricleGuid = atricleGuid;
	}
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public Long getArticlePubDate() {
		return articlePubDate;
	}
	public void setArticlePubDate(Long articlePubDate) {
		this.articlePubDate = articlePubDate;
	}
	public String getArticleAuthor() {
		return articleAuthor;
	}
	public void setArticleAuthor(String articleAuthor) {
		this.articleAuthor = articleAuthor;
	}
	public String getArticleCreator() {
		return articleCreator;
	}
	public void setArticleCreator(String articleCreator) {
		this.articleCreator = articleCreator;
	}
	public String getArticleLink() {
		return articleLink;
	}
	public void setArticleLink(String articleLink) {
		this.articleLink = articleLink;
	}
	public String getArticleDescription() {
		return articleDescription;
	}
	public void setArticleDescription(String articleDescription) {
		this.articleDescription = articleDescription;
	}
	public String getArticleMedia() {
		return articleMedia;
	}
	public void setArticleMedia(String articleMedia) {
		this.articleMedia = articleMedia;
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public String getRawArticle() {
		return rawArticle;
	}
	public void setRawArticle(String rawArticle) {
		this.rawArticle = rawArticle;
	}
	public String getKeywordArticle() {
		return keywordArticle;
	}
	public void setKeywordArticle(String keywordArticle) {
		this.keywordArticle = keywordArticle;
	}
    
    
}
