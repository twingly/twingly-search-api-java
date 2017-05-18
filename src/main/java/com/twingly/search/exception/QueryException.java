package com.twingly.search.exception;

import com.twingly.search.domain.BlogStream;

/**
 * The type Query exception.
 */
@Deprecated
public class QueryException extends TwinglySearchException {
    /**
     * Instantiates a new Query exception.
     */
    public QueryException() {
        super();
    }

    /**
     * Instantiates a new Query exception.
     *
     * @param blogStream the blog stream
     */
    public QueryException(BlogStream blogStream) {
        super(blogStream);
    }

    /**
     * Instantiates a new Query exception.
     *
     * @param message the message
     */
    public QueryException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Query exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public QueryException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Query exception.
     *
     * @param cause the cause
     */
    public QueryException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Query exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected QueryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
