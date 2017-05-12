package com.twingly.search.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a search result
 */
@XmlRootElement(name = "twinglydata")
public class Result {

    /**
     * number of Posts the Query returned.
     */
    @XmlAttribute(name = "numberOfMatchesReturned")
    private int numberOfMatchesReturned;
    /**
     * total number of Posts the Query matched.
     */
    @XmlAttribute(name = "numberOfMatchesTotal")
    private int numberOfMatchesTotal;

    /**
     * number of seconds it took to execute the Query.
     */
    @XmlAttribute(name = "secondsElapsed")
    private double secondsElapsed;

    /**
     * all posts that matched the {Query}
     */
    @XmlElement(name = "post")
    private List<Post> posts;

    /**
     * Defined whether result is complete or not
     */
    @XmlAttribute(name = "incompleteResult")
    private boolean incompleteResult;

    /**
     * Instantiates a new Result.
     */
    public Result() {

    }

    /**
     * Constructs a result from a list of posts. The constructor is not visible outside the package.
     *
     * @param posts                   A list of Post objects
     * @param numberOfMatchesReturned the number of matches returned
     * @param numberOfMatchesTotal    the number of matches total
     * @param secondsElapsed          the seconds elapsed
     */
    Result(List<Post> posts, int numberOfMatchesReturned, int numberOfMatchesTotal, double secondsElapsed) {
        this.posts = posts;
        this.numberOfMatchesReturned = numberOfMatchesReturned;
        this.numberOfMatchesTotal = numberOfMatchesTotal;
        this.secondsElapsed = secondsElapsed;
    }

    /**
     * Checks whether all Posts that matched the query are returned via this result.
     *
     * @return true if this result includes all Posts that matched the query, false if there are more Posts to fetch from the API
     */
    public boolean areAllResultsReturned() {
        return numberOfMatchesTotal == numberOfMatchesReturned;
    }

    /**
     * Returns all posts that matched the query
     *
     * @return The posts returned from the query
     */
    public List<Post> getPosts() {
        if (posts == null) {
            posts = new ArrayList<>();
        }
        return posts;
    }

    /**
     * Sets posts.
     *
     * @param posts the posts
     */
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    /**
     * Gets incomplete result
     *
     * @return the incomplete result
     */
    public boolean isIncompleteResult() {
        return incompleteResult;
    }

    /**
     * Sets incomplete result
     *
     * @param incompleteResult the incomplete result
     */
    public void setIncompleteResult(boolean incompleteResult) {
        this.incompleteResult = incompleteResult;
    }

    /**
     * Gets the total number of matches returned
     *
     * @return The total number of matches returned or -1 if unknown
     */
    public int getNumberOfMatchesReturned() {
        return numberOfMatchesReturned;
    }

    /**
     * Sets number of matches returned.
     *
     * @param numberOfMatchesReturned the number of matches returned
     */
    public void setNumberOfMatchesReturned(int numberOfMatchesReturned) {
        this.numberOfMatchesReturned = numberOfMatchesReturned;
    }

    /**
     * Gets the total number of matches to the search query
     *
     * @return The total number of matches or -1 if unknown
     */
    public int getNumberOfMatchesTotal() {
        return numberOfMatchesTotal;
    }

    /**
     * Sets number of matches total.
     *
     * @param numberOfMatchesTotal the number of matches total
     */
    public void setNumberOfMatchesTotal(int numberOfMatchesTotal) {
        this.numberOfMatchesTotal = numberOfMatchesTotal;
    }

    /**
     * Gets number of seconds it took to execute query
     *
     * @return Number of seconds it took to execute query or -1 if unknown
     */
    public double getSecondsElapsed() {
        return secondsElapsed;
    }

    /**
     * Sets seconds elapsed.
     *
     * @param secondsElapsed the seconds elapsed
     */
    public void setSecondsElapsed(double secondsElapsed) {
        this.secondsElapsed = secondsElapsed;
    }
}
