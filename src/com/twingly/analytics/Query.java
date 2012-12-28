package com.twingly.analytics;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * A class used to make one or more requests to Twingly's REST API.
 * To use the API you need an API key. To obtain an API key, send an email to info@twingly.com
 * <br>
 * Example usage:
 * <pre>
 * Query q = new Query(yourApiKey);                // create a request object using your api key
 * q.setDocumentLang("en");                        // only search for posts in english
 * Result result = q.makeRequest("spotify");       // find posts containing the word spotify
 * for (Post p : result.getPosts())
 *     System.out.println(p.getUrl());             // print the url for each post
 * </pre> 
 * Later you can repeat the search for any new posts that have come in since your last request:
 * <pre>
 * q.setStartTime(result.getNewestPost());         // only search for posts newer than the last result
 * result = q.makeRequest("spotify");              // request new posts containing the word spotify
 *  
 * </pre>
 *
 * A Query object can be reused for several requests
 * 
 * @author marcus@twingly.com
 *
 */
public class Query {

	/**
	 * Enum for the possible search types
	 * @author marcus@twingly.com
	 */
	public enum SearchType {
		BLOGS,
		MICROBLOGS
	}

	private final String apiKey;

	private String documentLang;

	private Date startTime;

	private Date endTime;

	private boolean approved;

	private SearchType searchType = SearchType.BLOGS;

	private final XMLReader parser;

	private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * Creates a query object
	 * 
	 * @param apiKey The API key
	 * @throws SAXException If the XML parser couldn't be initialized
	 */
	public Query(String apiKey) throws SAXException {
		this.apiKey = apiKey;
		this.parser = XMLReaderFactory.createXMLReader();
	}

	/**
	 * Sets the language for the query. Only blogposts in the given language will be returned.
	 * The format is a two letter ISO language code, for example 'en' for English, 'sv' for Swedish,
	 * 'de' for German and so on.
	 * @param lang The language code
	 */
	public void setDocumentLang(String lang) {
		documentLang = lang;
	}

	/**
	 * Gets the document language, or the empty string if none is set
	 * @return The document language
	 */
	public String getDocumentLang() {
		return (documentLang == null) ? "" : documentLang;
	}

	/**
	 * Sets the start time for the query. Only blogposts with a date later than this date will be returned.
	 * All dates are UTC.
	 * @param startTime The start time
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * Gets the start time for the query. Only blogposts with a date later than this date will be returned.
	 * All dates are UTC.
	 * @return The start time
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * Sets the end time for the query. Only blogposts with a date earlier than this date will be returned.
	 * All dates are UTC.
	 * @param endTime The start time
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * Gets the end time for the query. Only blogposts with a date earlier than this date will be returned.
	 * All dates are UTC.
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * Sets the approved flag for the query. If it is true, only approved blogs will be returned, otherwise
	 * both approved and unapproved blogs will be returned.
	 * @param approved True if you only want approved blogposts in the search result
	 */
	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	/**
	 * Returns the approved flag for this query as set by setApproved
	 * @see #setApproved(boolean)
	 * @return The approved flag
	 */
	public boolean isApproved() {
		return approved;
	}

	/**
	 * Returns the search type (blogs/microblogs)
	 * @return The search type
	 */
	public SearchType getSearchType() {
		return searchType;
	}

	/**
	 * Sets the search type; if you want search results from blogs or microblogs
	 * @param searchType The search type
	 */
	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}

	private String buildRequestString(String search) {

		StringBuilder sb = new StringBuilder();

		// base URL, XML version, client type and the API key
		sb.append("http://api.twingly.com/analytics/Analytics.ashx?xmloutputversion=1&clienttype=javaapi&key=");
		sb.append(apiKey);

		// the search pattern
		sb.append("&searchpattern=");
		sb.append(urlEncode(search));

		// add start time if supplied
		if (startTime != null) {
			sb.append("&ts=");
			sb.append(urlEncode(df.format(startTime)));
		}

		// add end time if supplied
		if (endTime != null) {
			sb.append("&tsTo=");
			sb.append(urlEncode(df.format(endTime)));
		}

		// add document language if supplied
		if (getDocumentLang().length() != 0) {
			sb.append("&documentlang=");
			sb.append(documentLang);
		}

		// add approved
		sb.append("&approved=");
		sb.append(approved ? "True" : "False");

		// add search type
		sb.append("&product=");
		if (searchType.equals(SearchType.BLOGS))
			sb.append("search");
		else
			sb.append("microblogsearch");

		return sb.toString();
	}

	private String urlEncode(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// This is impossible, UTF-8 is always supported according to the java standard
			return null;
		}
	}

	/**
	 * Makes a request query to the REST API using the parameters you have previously set.
	 * The search string can include additional modifiers such as link:nytimes.com - see
	 * {@link <a href="http://twingly.com/help#search">Twingly Help</a>} for the syntax.
	 * 
	 * @param search The search string
	 * @return The search result
	 * @throws IOException In case of an IO error (error contacting the http server and so on)
	 * @throws SAXException In case of malformed XML result
	 */
	public Result makeRequest(String search) throws IOException, SAXException {
		AnalyticsParser handler = new AnalyticsParser();
		parser.setContentHandler(handler);
		parser.parse(buildRequestString(search));
		return new Result(handler.getPosts(), handler.getMatchesReturned(), handler.getMatchesTotal());
	}
}
