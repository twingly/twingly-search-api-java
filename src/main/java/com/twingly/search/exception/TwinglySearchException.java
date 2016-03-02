package com.twingly.search.exception;

import com.twingly.search.domain.BlogStream;

/**
 * Custom runtime Twingly Exception
 */
public class TwinglySearchException extends RuntimeException {
    /**
     * Instantiates a new Twingly exception.
     */
    public TwinglySearchException() {
        super();
    }

    /**
     * Instantiates a new Twingly exception.
     *
     * @param blogStream the blog stream
     */
    public TwinglySearchException(BlogStream blogStream) {
        this(String.format("resultType:%s, message:%s", blogStream.getOperationResult().getResultType(), blogStream.getOperationResult().getMessage()));
    }

    /**
     * Instantiates a new Twingly exception.
     *
     * @param message the message
     */
    public TwinglySearchException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Twingly exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public TwinglySearchException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Twingly exception.
     *
     * @param cause the cause
     */
    public TwinglySearchException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Twingly exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected TwinglySearchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
