package com.twingly.search;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import com.twingly.search.Constants;

/**
 * The type Query.
 */
public class Query {
    private static final SimpleDateFormat QUERY_DATE_FORMAT = new SimpleDateFormat(Constants.QUERY_DATE_FORMAT);
    private Date startTime;
    private Date endTime;
    private String searchQuery;
    private String lang;
    private String location;

    /**
     * Package-level constructor for API usage only. API users should use QueryBuilder instead.
     *
     * @see QueryBuilder
     * @deprecated since 1.1.0 - use {@link #Query(Date, Date, String, String, String)} instead
     */
    @Deprecated
    Query() {
        //
    }

    Query(Date startTime, Date endTime, String searchQuery, String lang, String location) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.searchQuery = searchQuery;
        this.lang = lang;
        this.location = location;
    }

    /**
     * Gets language
     *
     * @return language
     * @since 1.1.0
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets language
     *
     * @param lang language
     * @since 1.1.0
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * Convert current query object to #QueryBuilder.
     *
     * @return QueryBuilder object from current Query
     * @since 1.1.0
     */
    public QueryBuilder toBuilder() {
        return QueryBuilder.fromQuery(this);
    }

    /**
     * Gets location
     *
     * @return the location
     * @since 1.1.0
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location
     *
     * @param location the location
     * @since 1.1.0
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets start date.
     *
     * @return the start date
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets start date.
     *
     * @param startTime the start date
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets end date.
     *
     * @param endTime the end date
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets search query.
     *
     * @return the search query
     */
    public String getSearchQuery() {
        return searchQuery;
    }

    /**
     * Sets search query.
     *
     * @param searchQuery the search query
     */
    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    /**
     * Convert Query object to its String representation
     *
     * @return search query in String representation
     */
    public String toStringRepresentation() {
        StringBuilder result = new StringBuilder(searchQuery);
        if (lang != null) {
            result.append(" lang:").append(lang);
        }
        if (startTime != null) {
            result.append(" start-date:").append(QUERY_DATE_FORMAT.format(startTime));
        }
        if (endTime != null) {
            result.append(" end-date:").append(QUERY_DATE_FORMAT.format(endTime));
        }
        if (location != null) {
            result.append(" location:").append(location);
        }
        return result.toString();
    }

    /**
     * Gets search pattern.
     *
     * @return the search pattern
     * @deprecated since 1.1.0 - use {@link #getSearchQuery()} instead
     */
    @Deprecated
    public String getSearchPattern() {
        return getSearchQuery();
    }

    /**
     * Sets search pattern.
     *
     * @param searchPattern the search pattern
     * @deprecated since 1.1.0 - use {@link #setSearchQuery(String)} instead
     */
    @Deprecated
    public void setSearchPattern(String searchPattern) {
        setSearchQuery(searchPattern);
    }


    /**
     * Gets document language.
     *
     * @return the document language
     * @deprecated since 1.1.0 - use {@link #getLang()} instead
     */
    @Deprecated
    public String getDocumentLanguage() {
        return getLang();
    }

    /**
     * Sets document language.
     *
     * @param documentLanguage the document language
     * @deprecated since 1.1.0 - use {@link #setLang(String)} instead
     */
    @Deprecated
    public void setDocumentLanguage(String documentLanguage) {
        setLang(documentLanguage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Query)) return false;
        Query query = (Query) o;
        return Objects.equals(startTime, query.startTime) &&
                Objects.equals(endTime, query.endTime) &&
                Objects.equals(searchQuery, query.searchQuery) &&
                Objects.equals(lang, query.lang) &&
                Objects.equals(location, query.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, searchQuery, lang, location);
    }

    @Override
    public String toString() {
        return toStringRepresentation();
    }
}
