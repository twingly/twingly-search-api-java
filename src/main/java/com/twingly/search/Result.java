package com.twingly.search;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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

    public Result() {

    }

    /**
     * Constructs a result from a list of posts. The constructor is not visible outside the package.
     *
     * @param posts A list of Post objects
     */
    Result(List<Post> posts, int numberOfMatchesReturned, int numberOfMatchesTotal, double secondsElapsed) {
        this.posts = posts;
        this.numberOfMatchesReturned = numberOfMatchesReturned;
        this.numberOfMatchesTotal = numberOfMatchesTotal;
        this.secondsElapsed = secondsElapsed;
    }

    /**
     * Returns all posts that matched the query
     *
     * @return The posts returned from the query
     */
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    /**
     * Gets the total number of matches returned
     *
     * @return The total number of matches returned or -1 if unknown
     */
    public int getNumberOfMatchesReturned() {
        return numberOfMatchesReturned;
    }

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

    public void setSecondsElapsed(double secondsElapsed) {
        this.secondsElapsed = secondsElapsed;
    }
}
