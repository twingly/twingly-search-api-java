package com.twingly.search;

import java.util.Date;
import java.util.List;

/**
 * A class representing a search result
 * 
 * @author marcus@twingly.com
 * @see Query The Query class documentation for an example on how to use this class
 */
public class Result {

	private final int matchesReturned;
	
	private final int matchesTotal;

	private final List<Post> posts;

	private Date newestPost;

	private Date oldestPost;

	/**
	 * Constructs a result from a list of posts. The constructor is not visible outside the package.
	 * @param posts A list of Post objects
	 */
	Result(List<Post> posts, int matchesReturned, int matchesTotal) {
		this.posts = posts;
		this.matchesReturned = matchesReturned;
		this.matchesTotal = matchesTotal;
	}

	/**
	 * Returns all posts that matched the query
	 * @return The posts returned from the query
	 */
	public List<Post> getPosts() {
		return posts;
	}

	/**
	 * Gets the total number of matches returned
	 * @return The total number of matches returned or -1 if unknown
	 */
	public int getMatchesReturned() {
		return matchesReturned;
	}

	/**
	 * Gets the total number of matches to the search query
	 * @return The total number of matches or -1 if unknown
	 */
	public int getMatchesTotal() {
		return matchesTotal;
	}

	/**
	 * Gets the date of the newest posts in the result
	 * @return The date of the newest post in the result
	 */
	public Date getNewestPost() {
		if (newestPost == null) {
			Date np = null;
			// find the newest and oldest posts
			for (Post p : posts) {
				Date d = p.getDate();
				if (np == null || np.before(d))
					np = d;
			}
			newestPost = np;
		}
		return newestPost;
	}

	/**
	 * Gets the date of the oldest post in the result
	 * @return The date of the oldest post in the result
	 */
	public Date getOldestPost() {
		if (oldestPost == null) {
			Date op = null;
			// find the newest and oldest posts
			for (Post p : posts) {
				Date d = p.getDate();
				if (op == null || op.after(d))
					op = d;
			}
			oldestPost = op;
		}
		return oldestPost;
	}
}
