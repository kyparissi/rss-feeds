package com.inx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.inx.entity.CSVRssFeed;
import com.inx.entity.RestCallReturnObj;
import com.inx.entity.RssArticle;
import com.inx.entity.RssEsRecord;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

public class ParseRssFeeds {
	
	/*****
	 * 
	 * To run this command in eclipse, add these arguments to you run configuration:
	 * 
        -esserverandport "http://localhost:9200/"
        -esparentindexsuffix "rss_feeds_hierarchy/rss_feeds_parent/"
        -eschildindexsuffix "rss_feeds_hierarchy/rss_feeds_child/"
        -inputfile "/Users/doanth/Downloads/Bliss/InXero.Sample.Content Sources.July.15.2017.csv"	
	 * 
	 */
	
	/***
	 * Here are the same arguments on a single line
	 * 
	 * -esserverandport "http://localhost:9200/" -esparentindexsuffix "rss_feeds_hierarchy/rss_feeds_parent/" -eschildindexsuffix "rss_feeds_hierarchy/rss_feeds_child/" -inputfile "/Users/doanth/Downloads/Bliss/InXero.Sample.Content Sources.July.15.2017.csv"	
	 * 
	 */
	
	//File file=new File("/Users/doanth/Downloads/Bliss/InXero.Sample.Content Sources.July.15.2017.csv");
	//String elasticsearchParentRecordIndexUrl="http://localhost:9200/rss_feeds_hierarchy/rss_feeds_parent";
	//String elasticsearchChildRecordIndexUrl="http://localhost:9200/rss_feeds_hierarchy/rss_feeds_child/"+aUrlHash+"?parent="+parentId;
	
	@Option(name="-esserverandport", usage="Elasticsearch Server name and port, for example: localhost:9200", required=true)
	private String esserverandport;

	@Option(name="-esparentindexsuffix", usage="The parent elasticsearch index suffix, for example: rss_feeds_hierarchy/rss_feeds_parent", required=true)
	private String esparentindexsuffix;

	@Option(name="-eschildindexsuffix", usage="The child elasticsearch index suffix, for example: rss_feeds_hierarchy/rss_feeds_child", required=true)
	private String eschildindexsuffix;
	
	@Option(name="-inputfile", usage="Input file to read from, should be in .csv format", required=true)
	private String inputfile;
	
	
	private CmdLineParser parser;
	private String[] args;
	
	private String testStatusCol;
	private String magazineNameCol;
	private String descriptionCol;
	private String techCategoryCol;
	private String magazineLinkCol;
	private String rssSourceLinkCol;
	private String statusCol;
	private String dateAddedCol;
	private String includeCol;
	private String excludeCol;
	
	public ParseRssFeeds(String[]args){
		this.testStatusCol="Test Status";
		this.magazineNameCol="Magazine Name";
		this.descriptionCol="Brief Description";
		this.techCategoryCol="Technology Category";
		this.magazineLinkCol="Magazine Source Link";
		this.rssSourceLinkCol="RSS Source Link";
		this.statusCol="STATUS";
		this.dateAddedCol="Date Added";
		this.includeCol="Include";
		this.excludeCol="Exclude";
		
		this.args=args;
	}
	
	public static void main(String[]args){
		ParseRssFeeds parse=new ParseRssFeeds(args);
		try {
			parse.doMain();
			parse.process();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Missing or illegal arguments, exiting");
		}
		
	}
	
	public void process(){
		List<CSVRssFeed> parsedCSVList;
		try {
			parsedCSVList = this.parseTheFile();
			String baseUrl=this.esserverandport;
			for(CSVRssFeed aFeed:parsedCSVList){
				String magazineSourceLink=aFeed.getMagazineSourceLink();
				if(!this.esserverandport.toLowerCase().trim().startsWith("http:")){
					baseUrl="http://"+baseUrl;
				}
				if(!baseUrl.endsWith("/")){
					baseUrl+="/";
				}
				String elasticsearchParentRecordIndexUrl=(baseUrl+this.esparentindexsuffix);
				if(null!=magazineSourceLink && !magazineSourceLink.trim().isEmpty()){

					System.out.println("~!@~!@~!@~!@~!@~!@~!@~!@~!@About to call url: "+magazineSourceLink);
					try{
						RestCallReturnObj aReturnObj=ParseRssFeeds.makeGetCall(magazineSourceLink,"application/xhtml");

						if(aReturnObj.isSuccssful()){
							ResponseBody body=aReturnObj.getResponseBody();
							String resultStr=body.string();

							System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
							//System.out.println(resultStr);
							SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
							try {
								SAXParser saxParser = saxParserFactory.newSAXParser();
								InputSource inputSource = new InputSource( new StringReader(resultStr));
								RssEsRecord aRecordHandler=new RssEsRecord();
								saxParser.parse(inputSource, aRecordHandler);
								
								int parentId=magazineSourceLink.hashCode();
								JSONObject parentJSONObj=new JSONObject();
								try {
									parentJSONObj.put("_id", parentId);
									parentJSONObj.put("magazine_name", aFeed.getMagazineName());
									parentJSONObj.put("test_status", aFeed.getRssTestStatus());
									parentJSONObj.put("description", aFeed.getDescription());
									parentJSONObj.put("tech_category", aFeed.getTechCategory());
									parentJSONObj.put("magazine_url", magazineSourceLink);
									
									String parentJSONStr=parentJSONObj.toString();
																		
									ParseRssFeeds.makePostCall(elasticsearchParentRecordIndexUrl, "put",parentJSONStr);
//									Document doc=Jsoup.connect(elasticsearchParentRecordIndexUrl)
//											          .data("_id",(parentId+""))
//											          .data("magazine_name",aFeed.getMagazineName())
//											          .data("test_status", aFeed.getRssTestStatus())
//											          .data("description", aFeed.getDescription())
//											          .data("tech_category", aFeed.getTechCategory())
//											          .data("magazine_url", magazineSourceLink)
//											          .post();
								} 
								catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								List<RssArticle>articlesList=aRecordHandler.getArticlesList();
								if(null!=articlesList){
									for(RssArticle anArticle:articlesList){
										String aUrl=anArticle.getArticleLink();

										if(null!=aUrl && !aUrl.trim().isEmpty()){
											System.out.println("Calling aUrl="+aUrl);

											try {
												Document doc=Jsoup.connect(aUrl).get();
												int aUrlHash=aUrl.trim().hashCode();
												String parsedStr=doc.text();

												//String elasticsearchChildRecordIndexUrl="http://localhost:9200/rss_feeds_hierarchy/rss_feeds_child/"+aUrlHash+"?parent="+parentId;
												String elasticsearchChildRecordIndexUrl=(baseUrl+this.eschildindexsuffix+aUrlHash+"?parent="+parentId);
												JSONObject childJSONObj=new JSONObject();

												childJSONObj.put("article_raw", parsedStr);
												childJSONObj.put("article_keyword", parsedStr);
												String articleTitle=anArticle.getArticleTitle();
												if(null==articleTitle){
													articleTitle="";
												}
												childJSONObj.put("art_title", articleTitle);
												childJSONObj.put("art_link", aUrl);
												String childJSONObjStr=childJSONObj.toString();
												ParseRssFeeds.makePostCall(elasticsearchChildRecordIndexUrl,"put", childJSONObjStr);
											} catch (JSONException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											catch(HttpStatusException e){
												e.printStackTrace();
												//continue
											}
											catch(MalformedURLException e){
												e.printStackTrace();
												//continue
											}
											catch(IllegalArgumentException e){
												e.printStackTrace();
												//continue
											}
										}
									}
								}
								else{
									System.out.println("articlesList is null : aRecordHandler.getRssBaseLink()="+aRecordHandler.getRssBaseLink());
								}
							} 
							catch (ParserConfigurationException e) {
								e.printStackTrace();
							} 
							catch (SAXException e) {
								e.printStackTrace();
							}
							
							System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
						}
						else{
							System.out.println("!!!!!!call to url: "+magazineSourceLink+" failed");
						}
					}
					catch(UnknownHostException e){
						System.out.println("~!@~!@~!@~!@~!@~!@~!@~!@~!@UnknownHostException caught "+
					                       "for url: magazineSourceLink: "+magazineSourceLink);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	private List<CSVRssFeed> parseTheFile()throws IOException{
		List<CSVRssFeed>csvRecordsList=new ArrayList<CSVRssFeed>();
		//File file=new File("/Users/doanth/Downloads/Bliss/InXero.Sample.Content Sources.July.15.2017.csv");
		File file=new File(this.inputfile);
		if(file.exists()){
			FileReader reader=new FileReader(file);
			BufferedReader buffReader=new BufferedReader(reader);

			CSVParser parser=new CSVParser(buffReader, CSVFormat.EXCEL.withHeader());
			
			//skip the first line
			//CSVRecord aRecord=iter.next();
			boolean hasHitBlogs=false;
			for(CSVRecord record:parser){
				CSVRssFeed aCSVRecord=new CSVRssFeed();
				
				String testStatus=record.get(this.testStatusCol);
				aCSVRecord.setRssTestStatus(testStatus);
				String magazineName=record.get(this.magazineNameCol);
				aCSVRecord.setMagazineName(magazineName);
				String description=record.get(this.descriptionCol);
				aCSVRecord.setDescription(description);
				String techCategory=record.get(this.techCategoryCol);
				aCSVRecord.setTechCategory(techCategory);
				String magazineLink=record.get(this.magazineLinkCol);
				aCSVRecord.setMagazineSourceLink(magazineLink);
				String rssSourceLink=record.get(this.rssSourceLinkCol);
				aCSVRecord.setSourceLink(rssSourceLink);
				String status=record.get(this.statusCol);
				aCSVRecord.setStatus(status);
				String dateAdded=record.get(this.dateAddedCol);
				SimpleDateFormat dateFormat=new SimpleDateFormat("MMMM dd yyyy");
				Date theDate=null;
				if(null!=dateAdded && !dateAdded.isEmpty()){
					try {
						theDate=dateFormat.parse(dateAdded);
					} 
					catch (ParseException e) {
						//do nothing
					}
				}
				Long dateLong=null;
				if(null!=theDate){
					dateLong=theDate.getTime();
				}
				if(null!=dateLong){
					aCSVRecord.setDateAdded(dateLong);
				}
				String include=record.get(this.includeCol);
				aCSVRecord.setInclude(include);
				String exclude=record.get(this.excludeCol);
				aCSVRecord.setExclude(exclude);
				
				if(null!=magazineName && magazineName.trim().equals("BLOGS")){	
					hasHitBlogs=true;
				}
				//We have not yet hit the BLOGS value
				if(!hasHitBlogs){
			        csvRecordsList.add(aCSVRecord);
				}
				else{
					aCSVRecord.setTechCategory("blog "+techCategory);
				}
			}
		}
		return csvRecordsList;
	}
	
	public static RestCallReturnObj makeGetCall(String url, 
			                                    String acceptHeaderValue)throws IOException, UnknownHostException{
		String METHOD_NAME="makeGetCall()";

//		if(log.isDebugEnabled()){
//			log.debug(MatchUtil.class.getName()+"."+METHOD_NAME+": START: url="+url);
//		}
		RestCallReturnObj retVal=new RestCallReturnObj();

		//We now make a jpostal call to expand the address
		OkHttpClient httpClient=new OkHttpClient();
		
		httpClient.setFollowRedirects(false);
		httpClient.setHostnameVerifier(new HostnameVerifier() {
			
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
		});
		
		Builder builder=new Request.Builder();
		
		Request req=builder.url(url)
				.header("Accept", acceptHeaderValue)				
				.build();

		long jpostalRestCallTimeStart=System.currentTimeMillis();
		com.squareup.okhttp.Response resp=httpClient.newCall(req).execute();
		
		ResponseBody respBody=resp.body();

		retVal.setSuccssful(resp.isSuccessful());
		retVal.setCallTook(System.currentTimeMillis()-jpostalRestCallTimeStart);
		retVal.setResponseBody(respBody);
		retVal.setHeaders(resp.headers());
//		if(log.isDebugEnabled()){
//			log.debug(MatchUtil.class.getName()+"."+METHOD_NAME+": END");
//		}
		return retVal;
	}


	
	private static RestCallReturnObj makePostCall(String url, String protocol, String jsonBodyStr)throws IOException{
		String METHOD_NAME=ParseRssFeeds.class.getName()+".makePostCall()";
//		if(log.isDebugEnabled()){
//			log.debug(METHOD_NAME+"START");
//
//		}
		RestCallReturnObj retVal=new RestCallReturnObj();

		RequestBody body=RequestBody.create(com.squareup.okhttp.MediaType.parse("application/json"),jsonBodyStr);

		OkHttpClient elasticSerachHttpClient=new OkHttpClient();
		Builder builder=new Request.Builder();
		Request elasticSearchReq=null;
		if(null!=protocol && protocol.trim().equalsIgnoreCase("put")){
			elasticSearchReq=builder.url(url)
				                    .header("Accept", "application/json")	
				                    .put(body)
				                    .build();
		}
		else{
			elasticSearchReq=builder.url(url)
                                    .header("Accept", "application/json")	
                                    .post(body)
                                    .build(); 			
		}

		//Make the elasticsearch call
		long elasticSearchQueryStartTime=System.currentTimeMillis();
		com.squareup.okhttp.Response theResp=elasticSerachHttpClient.newCall(elasticSearchReq).execute();
		long elasticSearchQueryStartEnd=System.currentTimeMillis();
		ResponseBody theRespBody=theResp.body();

		retVal.setCallTook(elasticSearchQueryStartEnd-elasticSearchQueryStartTime);
		retVal.setHeaders(theResp.headers());
		retVal.setResponseBody(theRespBody);
//		if(log.isDebugEnabled()){
//			log.debug(METHOD_NAME+"END");
//
//		}
		return retVal;
	}
	
	public boolean doMain()throws IOException{
		boolean retVal=true;		

		this.parser=new CmdLineParser(this);

		try{
			this.parser.parseArgument(this.args);			
		}
		catch(CmdLineException e){
			// if there's a problem in the command line,
			// you'll get this exception. this will report
			// an error message.
			System.err.println(e.getMessage());
			System.err.println("java producer [options...] arguments...");
			// print the list of available options
			this.parser.printUsage(System.err);
			System.err.println();
			retVal=false;
		}
		return retVal;
	}
}
