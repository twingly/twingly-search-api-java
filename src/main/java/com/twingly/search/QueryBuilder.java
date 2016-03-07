package com.twingly.search;

import com.twingly.search.domain.Language;
import com.twingly.search.exception.QueryException;

import java.util.Date;

/**
 * The type Query builder.
 */
public class QueryBuilder {
    private Query query;

    private QueryBuilder() {
        query = new Query();
    }

    /**
     * Create query builder with given search pattern
     *
     * @param searchPattern the search pattern
     * @return the query builder
     */
    public static QueryBuilder create(String searchPattern) {
        return new QueryBuilder().searchPattern(searchPattern);
    }

    /**
     * Set search pattern.
     *
     * @param searchPattern the search pattern
     * @return the query builder
     */
    public QueryBuilder searchPattern(String searchPattern) {
        if (isEmpty(searchPattern)) {
            throw new QueryException("Missing pattern");
        }
        query.setSearchPattern(searchPattern);
        return this;
    }

    /**
     * Set start time.
     *
     * @param startTime the start time
     * @return the query builder
     */
    public QueryBuilder startTime(Date startTime) {
        if (startTime != null) {
            if (query.getEndTime() != null) {
                if (startTime.before(query.getEndTime())) {
                    query.setStartTime(startTime);
                }
            } else {
                query.setStartTime(startTime);
            }
        }
        return this;
    }

    /**
     * Set end time.
     *
     * @param endTime the end time
     * @return the query builder
     */
    public QueryBuilder endTime(Date endTime) {
        if (endTime != null) {
            if (query.getStartTime() != null) {
                if (endTime.after(query.getStartTime())) {
                    query.setEndTime(endTime);
                }
            } else {
                query.setEndTime(endTime);
            }
        }
        return this;
    }

    /**
     * Set document language.
     *
     * @param language the language
     * @return the query builder
     */
    public QueryBuilder documentLanguage(Language language) {
        if (language != null) {
            query.setDocumentLanguage(language.getIsoCode());
        }
        return this;
    }

    /**
     * Set document language.
     *
     * @param documentLanguage the document language
     * @return the query builder
     */
    public QueryBuilder documentLanguage(String documentLanguage) {
        if (isNotEmpty(documentLanguage)) {
            query.setDocumentLanguage(documentLanguage);
        }
        return this;
    }

    /**
     * Create new query.
     *
     * @return the query builder
     */
    public QueryBuilder createNewQuery() {
        query = new Query();
        return this;
    }

    /**
     * Build query.
     *
     * @return the query
     */
    public Query build() {
        return query;
    }

    private boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    private boolean isEmpty(String value) {
        return value == null || "".equals(value);
    }
}
