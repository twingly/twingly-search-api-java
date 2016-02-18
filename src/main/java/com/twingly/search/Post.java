package com.twingly.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * A class representing a single blog post
 * 
 * @author marcus@twingly.com
 * @see Query The Query class documentation for an example on how to use this class
 */
public class Post {

	private String url;
	private String blogUrl;
	private String title;
	private String summary;
	private String languageCode;
	private String avatar;
	private Date date;
	private String blogName;
	private Collection<String> links;
	private Collection<String> tags;
	private int authority;

	/**
	 * Make the constructor invisible outside the package
	 */
	Post() {
	}

	/**
	 * Gets the URL of this post
	 * @return The URL of this post
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * Sets the URL of this post
	 * @param url
	 */
	void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * Gets the URL of this blog
	 * @return The URL of this blog
	 */
	public String getBlogUrl() {
		return blogUrl;
	}
	
	/**
	 * Sets the URL of this blog
	 * @param blogUrl
	 */
	void setBlogUrl(String blogUrl) {
		this.blogUrl = blogUrl;
	}

	/**
	 * Gets the links from this post
	 * @return A list of links
	 */
	public Collection<String> getLinks() {
		return links;
	}

	/**
	 * Adds an out link to this post
	 * @param link The link (url)
	 */
	void addLink(String link) {
		if (links == null)
			links = new ArrayList<String>();
		links.add(link);
	}

	/**
	 * Gets the tags from this post
	 * @return A list of tags
	 */
	public Collection<String> getTags() {
		return tags;
	}

	/**
	 * Adds a tag to this post
	 * @param tag The tag
	 */
	void addTag(String tag) {
		if (tags == null)
			tags = new ArrayList<String>();
		tags.add(tag);
	}

	/**
	 * Gets the title of this blog post
	 * @return The title of this blog post
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title of this blog post
	 * @param title The title of this blog post
	 */
	void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the text content of this post
	 * @return The content of this post
	 */
	public String getPostContent() {
		return summary;
	}

	/**
	 * Sets the post content of this post
	 * @param content The content
	 */
	void setPostContent(String content) {
		this.summary = content;
	}

	/**
	 * Gets the language code of this post
	 * @return The language code of this post
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * Sets the language code of this post
	 * @param languageCode The language code of this post
	 */
	void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * Gets the URL of the avatar associated with the post, or null if unknown
	 * @return The URL of the avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * Sets the URL of the avatar associated with the post, or null if unknown
	 * @param avatar The URL of the avatar
	 */
	void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * Gets the time and date of this post
	 * All dates are UTC
	 * @return The publish date of this post
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Sets the time and date of this post
	 * All dates are UTC
	 * @param date The date of this post
	 */
	void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * Gets the name of this blog
	 * @return The name of this blog
	 */
	public String getBlogName() {
		return blogName;
	}
	
	/**
	 * Sets the name of this blog
	 * @param blogName The name of this blog
	 */
	void setBlogName(String blogName) {
		this.blogName = blogName;
	}
	
	/**
	 * Gets the authority associated with this blog
	 * @return The authority
	 */
	public int getAuthority() {
		return authority;
	}
	
	/**
	 * Sets the authority value for this blog
	 * @param authority The authority
	 */
	void setAuthority(int authority) {
		this.authority = authority;
	}
}
