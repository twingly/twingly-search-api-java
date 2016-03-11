package com.twingly.search;

import java.util.Date;

/**
 * The type Query.
 */
public class Query {
    private Date startTime;
    private Date endTime;
    private String searchPattern;
    private String documentLanguage;

    /**
     * Package-level constructor for API usage only. API users should use QueryBuilder instead.
     *
     * @see QueryBuilder
     */
    Query() {
        //
    }

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets start time.
     *
     * @param startTime the start time
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets end time.
     *
     * @param endTime the end time
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets search pattern.
     *
     * @return the search pattern
     */
    public String getSearchPattern() {
        return searchPattern;
    }

    /**
     * Sets search pattern.
     *
     * @param searchPattern the search pattern
     */
    public void setSearchPattern(String searchPattern) {
        this.searchPattern = searchPattern;
    }

    /**
     * Gets document language.
     *
     * @return the document language
     */
    public String getDocumentLanguage() {
        return documentLanguage;
    }

    /**
     * Sets document language.
     *
     * @param documentLanguage the document language
     */
    public void setDocumentLanguage(String documentLanguage) {
        this.documentLanguage = documentLanguage;
    }
}
