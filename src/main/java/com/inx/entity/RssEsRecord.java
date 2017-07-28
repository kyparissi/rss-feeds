package com.inx.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RssEsRecord extends DefaultHandler{
	
	private String title;	
	private boolean titleBool;
	
	private String rssBaseLink;
	private boolean rssBaseLinkBool;
	
	private String rssBaseDescription;
	private boolean rssBaseDescriptionBool;
	
	private Long pubDate;
	private boolean pubDateBool;
	
	private Long lastPubDate;
	private boolean lastPubDateBool;
	
	private String copyRight;
	private boolean copyRightBool;
	
	private List<String>skipDays;
	private boolean skipDaysBool;
	
	private List<Image>images;
	private Image anImage;
	private boolean imageBool;
	
	
	private List<Integer>skipHours;
	private Integer aSkipHour;
	private boolean skipHoursBool;
	
	private List<RssArticle>articlesList;
	private RssArticle anArticle;
	
	private boolean itemBool;
	private boolean itemTitleBool;
	private boolean itemPubDateBool;

	private boolean itemAuthorBool;
	private boolean itemCreatorBool;
	private boolean itemDescriptionBool;
	private boolean itemLinkBool;
	private boolean itemMediaThumbnailBool;
	private boolean itemMediaConentBool;
	private boolean itemCategoriesBool;
	private boolean itemACategoryBool;
	
	private boolean imageUrlBool;
	private boolean imageTitleBool;
	private boolean imageLinkBool;
	private boolean imageWidthBool;
	private boolean imageHeightBool;
    
	public void startElement(String uri, 
			                 String localName, 
			                 String qName, 
			                 Attributes attributes)throws SAXException {
    	
    	if (qName.equalsIgnoreCase("channel")){
    		this.titleBool=false;
    		this.imageTitleBool=false;
    		this.itemTitleBool=false;
    		this.rssBaseLinkBool=false;  
    		
    	}
    	else if(qName.equalsIgnoreCase("width")){
    		this.imageWidthBool=true;
    	}
    	else if(qName.equalsIgnoreCase("height")){
    		this.imageHeightBool=true;
    	}    
    	else if(qName.equalsIgnoreCase("url")){
    		this.imageUrlBool=true;
    	}     	
    	else if(qName.equalsIgnoreCase("channel")){
    		//We are at the beginning of the rss feed
    		//Set all the titles to false except the top most one
    		this.titleBool=false;
    		this.imageTitleBool=false;
    		this.itemTitleBool=false;    		    		    		
    	}
    	else if(qName.equalsIgnoreCase("image")){
    		this.anImage=new Image();
    		this.imageBool=true;
    		if(null==this.images){
    			this.images=new ArrayList<Image>();
    		}
    	}
    	else if(qName.equalsIgnoreCase("title")){
    		//This is the first title
    		if(!this.titleBool && !this.imageBool && !this.imageTitleBool && !this.itemBool && !this.itemTitleBool){
    			this.titleBool=true;
    	    }
    		//This is the image title
    		else if(!this.titleBool && this.imageBool && !this.imageTitleBool && !this.itemBool && !this.itemTitleBool){    			
    	    	this.imageTitleBool=true;
    	    }
    		//This is the item title
    		else if(!this.titleBool && !this.imageBool && !this.imageTitleBool && this.itemBool && !this.itemTitleBool){    			
    			this.itemTitleBool=true;
    		}    		
    	}
    	else if(qName.equalsIgnoreCase("item")){
    		this.itemBool=true;
    		this.anArticle=new RssArticle();
    		if(null==this.articlesList){
    			this.articlesList=new ArrayList<RssArticle>();
    		}
    	}
    	else if(qName.equalsIgnoreCase("pubDate")){
    		//This is the first title
    		if(!this.pubDateBool && !this.itemBool && !this.itemPubDateBool){
    			this.pubDateBool=true;
    	    }
    		//This is an article pubDate
    		else if(!this.pubDateBool && this.itemBool && !this.itemPubDateBool){
    	    	this.itemPubDateBool=true;
    	    }
    	}
    	else if(qName.equalsIgnoreCase("link")){
    		
    		//This is the first link
    		if(!this.rssBaseLinkBool && !this.imageBool && !this.imageLinkBool && !this.itemBool && !this.itemLinkBool){
    			this.rssBaseLinkBool=true;
    	    }
    		//This is the image link
    		else if(!this.rssBaseLinkBool && this.imageBool && !this.imageLinkBool && !this.itemBool && !this.itemLinkBool){    			
    			this.imageLinkBool=true;
    	    }
    		//This is an article link
    		else if(!this.rssBaseLinkBool && !this.imageBool && !this.imageLinkBool && this.itemBool && !this.itemLinkBool){    	    			
    			this.itemLinkBool=true;
    	    }
    	}
    	
    }
    
    public void endElement(String uri,
    		               String localName,
    		               String qName)throws SAXException{
    	
    	if (qName.equalsIgnoreCase("channel")){
    		this.titleBool=false;
    		this.imageTitleBool=false;
    		this.itemTitleBool=false;
    	}
    	else if(qName.equalsIgnoreCase("width")){
    		this.imageWidthBool=false;
    	}
    	else if(qName.equalsIgnoreCase("height")){
    		this.imageHeightBool=false;
    	}    
    	else if(qName.equalsIgnoreCase("url")){
    		this.imageUrlBool=false;
    	}
    	else if (qName.equalsIgnoreCase("title")){
    		if(this.titleBool && !this.imageBool && !this.imageTitleBool && !this.itemBool && !this.itemTitleBool){
    			this.titleBool=false;
    		}
    		else if(!this.titleBool && this.imageBool && this.imageTitleBool && !this.itemBool && !this.itemTitleBool){
    			this.imageTitleBool=false;
    	    }
    		else if(!this.titleBool && !this.imageBool && !this.imageTitleBool && this.itemBool && this.itemTitleBool){
    			this.itemTitleBool=false;
    	    }    		
    	}
    	else if(qName.equalsIgnoreCase("image")){
    		this.images.add(this.anImage);
    		this.imageBool=false;
    		this.imageTitleBool=false;
    		this.imageLinkBool=false;
    		this.imageUrlBool=false;
    		this.imageWidthBool=false;
    		this.imageHeightBool=false;
    				
    	}
    	else if(qName.equalsIgnoreCase("item")){
    		this.articlesList.add(anArticle);
    		this.itemTitleBool=false;
    		this.itemACategoryBool=false;
    		this.itemAuthorBool=false;
    		this.itemBool=false;
    		this.itemCategoriesBool=false;
    		this.itemCreatorBool=false;
    		this.itemDescriptionBool=false;
    		this.itemLinkBool=false;
    		this.itemMediaConentBool=false;
    		this.itemMediaThumbnailBool=false;
    		this.itemPubDateBool=false;
    		this.itemTitleBool=false;
    		this.itemDescriptionBool=false;
    	}
    }
    
    public void characters(char ch[],int start, int length)throws SAXException{
    	if(this.titleBool && !this.imageBool && !this.imageTitleBool && !this.itemBool && !this.itemTitleBool){
    		this.title=new String(ch,start,length);
    		this.titleBool=false;
    	}
    	else if(!this.titleBool && this.imageBool && this.imageTitleBool && !this.itemBool && !this.itemTitleBool){
    		String imageTitle=new String(ch,start,length);
    		this.anImage.setImageTitle(imageTitle);
    		this.imageTitleBool=false;
    	}
    	else if(this.imageUrlBool){
    		String imageUrl=new String(ch,start,length);
    		if(null==this.anImage){
    			this.anImage=new Image();
    		}
    		this.anImage.setUrl(imageUrl);
    		this.imageUrlBool=false;
    	}
    	else if(this.imageWidthBool){
    		String imageWidth=new String(ch,start,length);    		
    		this.anImage.setWidth(imageWidth);  
    		this.imageWidthBool=false;
    	}
    	else if(this.imageHeightBool){
    		String imageHeight=new String(ch,start,length);    		
    		this.anImage.setHeight(imageHeight);  
    		this.imageHeightBool=false;
    	}    	
    	else if(!this.titleBool && !this.imageBool && !this.imageTitleBool && this.itemBool && this.itemTitleBool){
    		
    		String articleTitle=new String(ch,start,length);    		
    		this.anArticle.setArticleTitle(articleTitle);
    		this.itemTitleBool=false;
    	}
    	else if(this.rssBaseLinkBool && !this.imageBool && !this.imageLinkBool && !this.itemBool && !this.itemLinkBool){
    		
    		String rssBaseLink=new String(ch,start,length);
    		this.rssBaseLink=rssBaseLink;
    		this.rssBaseLinkBool=false;
    	}
    	else if(!this.rssBaseLinkBool && this.imageBool && this.imageLinkBool && !this.itemBool && !this.itemLinkBool){
    		String imageLink=new String(ch,start,length);
    		this.anImage.setLink(imageLink);
    		this.imageLinkBool=false;
    	}
    	else if(!this.rssBaseLinkBool && !this.imageBool && !this.imageLinkBool && this.itemBool && this.itemLinkBool){
    		String itemLink=new String(ch,start,length);
    		this.anArticle.setArticleLink(itemLink);
    		this.itemLinkBool=false;
    	}
    	else if(this.pubDateBool && !this.itemBool && !this.itemPubDateBool){
    		String pubDateStr=new String(ch,start,length);
    		SimpleDateFormat sdf=new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
    		Date thePubDate=null;
    		Long thePubDateLong=null;
			try {
				thePubDate = sdf.parse(pubDateStr);
				thePubDateLong=thePubDate.getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				thePubDate=null;
			}
			this.pubDate=thePubDateLong;
    		this.pubDateBool=false;
	    }
    	else if(this.pubDateBool && this.itemBool && this.itemPubDateBool){
    		String pubDateStr=new String(ch,start,length);
    		SimpleDateFormat sdf=new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
    		Date thePubDate;
    		Long thePubDateLong=null;
			try {
				thePubDate = sdf.parse(pubDateStr);
				thePubDateLong=thePubDate.getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				thePubDate=null;
			}
    		this.anArticle.setArticlePubDate(thePubDateLong);
    		this.itemPubDateBool=false;
	    }
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRssBaseLink() {
		return rssBaseLink;
	}

	public void setRssBaseLink(String rssBaseLink) {
		this.rssBaseLink = rssBaseLink;
	}

	public String getRssBaseDescription() {
		return rssBaseDescription;
	}

	public void setRssBaseDescription(String rssBaseDescription) {
		this.rssBaseDescription = rssBaseDescription;
	}

	public Long getPubDate() {
		return pubDate;
	}

	public void setPubDate(Long pubDate) {
		this.pubDate = pubDate;
	}

	public Long getLastPubDate() {
		return lastPubDate;
	}

	public void setLastPubDate(Long lastPubDate) {
		this.lastPubDate = lastPubDate;
	}

	public String getCopyRight() {
		return copyRight;
	}

	public void setCopyRight(String copyRight) {
		this.copyRight = copyRight;
	}

	public List<String> getSkipDays() {
		return skipDays;
	}

	public void setSkipDays(List<String> skipDays) {
		this.skipDays = skipDays;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public Image getAnImage() {
		return anImage;
	}

	public void setAnImage(Image anImage) {
		this.anImage = anImage;
	}

	public List<Integer> getSkipHours() {
		return skipHours;
	}

	public void setSkipHours(List<Integer> skipHours) {
		this.skipHours = skipHours;
	}

	public List<RssArticle> getArticlesList() {
		return articlesList;
	}

	public void setArticlesList(List<RssArticle> articlesList) {
		this.articlesList = articlesList;
	}
    
    

}
