package com.twingly.search.exception;

import com.twingly.search.domain.BlogStream;

/**
 * The type Twingly search server exception.
 */
public class TwinglySearchServerException extends TwinglySearchException {
    /**
     * Instantiates a new Twingly search server exception.
     */
    public TwinglySearchServerException() {
        super();
    }

    /**
     * Instantiates a new Twingly search server exception.
     *
     * @param blogStream the blog stream
     */
    public TwinglySearchServerException(BlogStream blogStream) {
        super(blogStream);
    }

    /**
     * Instantiates a new Twingly search server exception.
     *
     * @param message the message
     */
    public TwinglySearchServerException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Twingly search server exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public TwinglySearchServerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Twingly search server exception.
     *
     * @param cause the cause
     */
    public TwinglySearchServerException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Twingly search server exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected TwinglySearchServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
