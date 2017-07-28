package com.inx.entity;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class CSVRssFeed {
	private String rssTestStatus;
	private String magazineName;
	private String description;
	private String techCategory;
	private String magazineSourceLink;
	private String sourceLink;
	private String status;
	private Long   dateAdded;
	private String include;
	private String exclude;
	
	public String toString(){
	    JSONObject retVal=new JSONObject();
	    String retValStr="{}";
	    
	    try {
	    	if(null!=rssTestStatus){
	    	    retVal.put("rssTestStatus", this.rssTestStatus.trim());
	    	}
	    	else{
	    		retVal.put("rssTestStatus", "");
	    	}
	    	if(null!=this.magazineName){
	    		retVal.put("magazineName", this.magazineName.trim());
	    	}
	    	else{
	    		retVal.put("magazineName", "");
	    	}
	    	if(null!=this.description){
	    		retVal.put("description", this.description.trim());
	    	}
	    	else{
	    		retVal.put("description", "");
	    	}	 
	    	if(null!=this.techCategory){
	    		retVal.put("techCategory", this.techCategory.trim());
	    	}
	    	else{
	    		retVal.put("techCategory", "");
	    	}
	    	if(null!=this.magazineSourceLink){
	    		retVal.put("magazineSourceLink", this.magazineSourceLink.trim());
	    	}
	    	else{
	    		retVal.put("magazineSourceLink", "");
	    	}	
	    	if(null!=this.sourceLink){
	    		retVal.put("sourceLink", this.sourceLink.trim());
	    	}
	    	else{
	    		retVal.put("sourceLink", "");
	    	}	
	    	if(null!=this.status){
	    		retVal.put("status", this.status.trim());
	    	}
	    	else{
	    		retVal.put("status", "");
	    	}
	    	if(null!=this.dateAdded){
	    		retVal.put("dateAdded", this.dateAdded);
	    	}
	    	else{
	    		retVal.put("dateAdded", -1L);
	    	}	
	    	if(null!=this.include){
	    		retVal.put("include", this.include.trim());
	    	}
	    	else{
	    		retVal.put("include", "");
	    	}
	    	if(null!=this.exclude){
	    		retVal.put("exclude", this.exclude.trim());
	    	}
	    	else{
	    		retVal.put("exclude", "");
	    	}	    	
	    	retValStr=retVal.toString();
		} 
	    catch (JSONException e) {
		    e.printStackTrace();	
		}
	    return retValStr;
	}
	
	public String getRssTestStatus() {
		return rssTestStatus;
	}
	public void setRssTestStatus(String rssTestStatus) {
		this.rssTestStatus = rssTestStatus;
	}
	public String getMagazineName() {
		return magazineName;
	}
	public void setMagazineName(String magazineName) {
		this.magazineName = magazineName;
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
	public String getSourceLink() {
		return sourceLink;
	}
	public void setSourceLink(String sourceLink) {
		this.sourceLink = sourceLink;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Long dateAdded) {
		this.dateAdded = dateAdded;
	}
	public String getInclude() {
		return include;
	}
	public void setInclude(String include) {
		this.include = include;
	}
	public String getExclude() {
		return exclude;
	}
	public void setExclude(String exclude) {
		this.exclude = exclude;
	}
	public String getMagazineSourceLink() {
		return magazineSourceLink;
	}
	public void setMagazineSourceLink(String magazineSourceLink) {
		this.magazineSourceLink = magazineSourceLink;
	}
	
	

}
