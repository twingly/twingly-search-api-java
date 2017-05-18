package com.twingly.search;

import com.twingly.search.domain.Language;
import com.twingly.search.domain.Location;
import com.twingly.search.exception.QueryException;
import com.twingly.search.exception.TwinglySearchQueryException;

import java.util.Date;
import java.util.Objects;

/**
 * The type Query builder.
 */
public class QueryBuilder {
    private Date startTime;
    private Date endTime;
    private String searchQuery;
    private String lang;
    private String location;

    private QueryBuilder() {

    }

    private QueryBuilder(Query query) {
        this.searchQuery = query.getSearchQuery();
        this.lang = query.getLang();
        this.location = query.getLocation();
        this.startTime = query.getStartTime();
        this.endTime = query.getEndTime();
    }

    /**
     * Create query builder with given search query
     *
     * @param searchQuery the search query
     * @return the query builder
     */
    public static QueryBuilder create(String searchQuery) {
        return new QueryBuilder().searchQuery(searchQuery);
    }

    /**
     * Create QueryBuilder object with initial values from given query
     *
     * @param query Query object to initialize builder with
     * @return QueryBuilder with fields initialized from given query
     * @since 3.0.0
     */
    public static QueryBuilder fromQuery(Query query) {
        return new QueryBuilder(query);
    }

    /**
     * Set search query.
     *
     * @param searchQuery the search pattern
     * @return the query builder
     * @throws com.twingly.search.exception.TwinglySearchQueryException if searchQuery is empty or null
     */
    public QueryBuilder searchQuery(String searchQuery) {
        if (isEmpty(searchQuery)) {
            throw new TwinglySearchQueryException("Missing search query");
        }
        this.searchQuery = searchQuery;
        return this;
    }

    /**
     * Set search pattern.
     *
     * @param searchPattern the search pattern
     * @return the query builder
     * @deprecated since 3.0.0 use {@link #searchQuery(String)} instead
     */
    @Deprecated
    public QueryBuilder searchPattern(String searchPattern) {
        if (isEmpty(searchPattern)) {
            throw new QueryException("Missing pattern");
        }
        this.searchQuery = searchPattern;
        return this;
    }

    /**
     * Set start time.
     *
     * @param startTime the start time
     * @return the query builder
     * @since 3.0.0
     */
    public QueryBuilder startTime(Date startTime) {
        if (startTime != null) {
            if (endTime != null) {
                if (startTime.before(endTime)) {
                    this.startTime = startTime;
                }
            } else {
                this.startTime = startTime;
            }
        }
        return this;
    }

    /**
     * Set end time.
     *
     * @param endTime the end time
     * @return the query builder
     * @since 3.0.0
     */
    public QueryBuilder endTime(Date endTime) {
        if (endTime != null) {
            if (startTime != null) {
                if (endTime.after(startTime)) {
                    this.endTime = endTime;
                }
            } else {
                this.endTime = endTime;
            }
        }
        return this;
    }

    /**
     * Set language.
     * <p>
     * If lang is null - {@link Query#lang} will be set to null, otherwise {@link Language#getIsoCode()}
     * will be set as {@link Query#lang}
     *
     * @param lang the language
     * @return the query builder
     * @since 3.0.0
     */
    public QueryBuilder lang(Language lang) {
        if (lang != null) {
            this.lang = lang.getIsoCode();
        } else {
            this.lang = null;
        }
        return this;
    }

    /**
     * Set document language.
     *
     * @param language the language
     * @return the query builder
     * @deprecated since 3.0.0 - use {@link #lang(Language)} instead
     */
    @Deprecated
    public QueryBuilder documentLanguage(Language language) {
        return lang(language);
    }

    /**
     * Set language.
     * <p>
     * If lang is not null and empty, {@link Language#fromIsoCode(String)} will be called to check whether given lang
     * is supported. If it is supported - it will be used as {@link Query#lang}, otherwise {@link Query#lang}
     * will be set to null.
     *
     * @param lang the language
     * @return the query builder
     * @since 3.0.0
     */
    public QueryBuilder lang(String lang) {
        if (isNotEmpty(lang) && Language.fromIsoCode(lang) != null) {
            this.lang = lang;
        } else {
            this.lang = null;
        }
        return this;
    }

    /**
     * Set document language.
     *
     * @param documentLanguage the document language
     * @return the query builder
     * @deprecated since 3.0.0 - use {@link #lang(String)} instead
     */
    @Deprecated
    public QueryBuilder documentLanguage(String documentLanguage) {
        return lang(documentLanguage);
    }

    /**
     * Set location.
     * <p>
     * If location is null - {@link Query#location} will be set to null, otherwise {@link Location#getIsoCode()}
     * will be set as {@link Query#location}
     *
     * @param location the language
     * @return the query builder
     * @since 3.0.0
     */
    public QueryBuilder location(Location location) {
        if (location != null) {
            this.location = location.getIsoCode();
        } else {
            this.location = null;
        }
        return this;
    }

    /**
     * Set location.
     * <p>
     * If lang is not null and empty, {@link Location#fromIsoCode(String)} will be called to check whether given location
     * is supported. If it is supported - it will be used as {@link Query#location}, otherwise {@link Query#location}
     * will be set to null.
     *
     * @param location the language
     * @return the query builder
     * @since 3.0.0
     */
    public QueryBuilder location(String location) {
        if (isNotEmpty(location) && Location.fromIsoCode(location) != null) {
            this.location = location;
        } else {
            this.location = null;
        }
        return this;
    }

    /**
     * Create new query.
     *
     * @return the query builder
     * @deprecated since 3.0.0 - use new QueryBuilder each time as current method can lead to empty {@link #searchQuery}
     */
    @Deprecated
    public QueryBuilder createNewQuery() {
        clean();
        return this;
    }

    /**
     * Set current builder values (except {@link #searchQuery}) to default.
     */
    private void clean() {
        startTime = null;
        endTime = null;
        lang = null;
        location = null;
    }

    /**
     * Build query.
     *
     * @return the query
     */
    public Query build() {
        return new Query(startTime, endTime, searchQuery, lang, location);
    }

    private boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    private boolean isEmpty(String value) {
        return value == null || "".equals(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QueryBuilder)) return false;
        QueryBuilder that = (QueryBuilder) o;
        return Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(searchQuery, that.searchQuery) &&
                Objects.equals(lang, that.lang) &&
                Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, searchQuery, lang, location);
    }
}
