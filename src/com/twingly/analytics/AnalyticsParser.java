package com.twingly.analytics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class AnalyticsParser extends DefaultHandler {

	/**
	 * A list of all posts that have been processed
	 */
	private ArrayList<Post> entries;

	/**
	 * The post currently being processed
	 */
	private Post currentEntry;

	/**
	 * StringBuilder for the characters() function
	 */
	private StringBuilder buf;

	private int matchesTotal;
	
	private int matchesReturned;

	private boolean failed = false;

	/**
	 * Date parser
	 */
	private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss'Z'");

	/**
	 * Make the constructor invisible outside of the package
	 */
	AnalyticsParser() {
	}

	/**
	 * Returns all parsed posts
	 * @return A list of all parsed posts
	 */
	public List<Post> getPosts() {
		return entries;
	}

	/**
	 * The number of matches returned
	 * @return The number of matches returned, or -1 if unknown
	 */
	public int getMatchesReturned() {
		return matchesReturned;
	}

	/**
	 * The total number of matches
	 * @return The total number of matches, or -1 if unknown
	 */
	public int getMatchesTotal() {
		return matchesTotal;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		buf.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String s = buf.toString().trim();
		buf = new StringBuilder();
		if (localName.equals("post"))
			entries.add(currentEntry);
		else if (localName.equals("url"))
			currentEntry.setUrl(s);
		else if (localName.equals("title"))
			currentEntry.setTitle(s);
		else if (localName.equals("summary"))
			currentEntry.setPostContent(s);
		else if (localName.equals("authority"))
			currentEntry.setAuthority(Integer.parseInt(s));
		else if (localName.equals("blogName"))
			currentEntry.setBlogName(s);
		else if (localName.equals("blogUrl"))
			currentEntry.setBlogUrl(s);
		else if (localName.equals("languageCode"))
			currentEntry.setLanguageCode(s);
		else if (localName.equals("TermSearch") || localName.equals("twinglydata"))
			entries.trimToSize();
		else if (localName.equals("link"))
			currentEntry.addLink(s);
		else if (localName.equals("tag"))
			currentEntry.addTag(s);
		else if (localName.equals("avatarUrl"))
			currentEntry.setAvatar(s);
		else if (localName.equals("operationResult") && failed)
			throw new SAXException(s);
		else if (localName.equals("date") || localName.equals("published")) {
			try {
				currentEntry.setDate(df.parse(s));
			} catch (ParseException e) {
				throw new SAXException("Couldn't parse date: " + s);
			}
		}
	}

	@Override
	public void startDocument() throws SAXException {
		currentEntry = new Post();
		buf = new StringBuilder();
		entries = new ArrayList<Post>();
		matchesTotal = matchesReturned = -1;
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		if (localName.equals("post")) {
			currentEntry = new Post();
		} else if (localName.equals("TermSearch")) {
			matchesReturned = Integer.parseInt(attributes.getValue("numberOfDocuments"));
		} else if (localName.equals("twinglydata")) {
			matchesReturned = Integer.parseInt(attributes.getValue("numberOfMatchesReturned"));
			matchesTotal = Integer.parseInt(attributes.getValue("numberOfMatchesTotal"));
			entries.ensureCapacity(matchesReturned);
		} else if (localName.equals("operationResult") && attributes.getValue("resultType").equals("failure")) {
			failed = true;
		}
	}
}
