package com.twingly.search.exception;

import com.twingly.search.domain.BlogStream;

/**
 * The type Twingly search server service unavailable exception.
 */
public class TwinglySearchServerServiceUnavailableException extends TwinglySearchServerException {
    /**
     * Instantiates a new Twingly search server service unavailable exception.
     */
    public TwinglySearchServerServiceUnavailableException() {
        super();
    }

    /**
     * Instantiates a new Twingly search server service unavailable exception.
     *
     * @param blogStream the blog stream
     */
    public TwinglySearchServerServiceUnavailableException(BlogStream blogStream) {
        super(blogStream);
    }

    /**
     * Instantiates a new Twingly search server service unavailable exception.
     *
     * @param message the message
     */
    public TwinglySearchServerServiceUnavailableException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Twingly search server service unavailable exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public TwinglySearchServerServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Twingly search server service unavailable exception.
     *
     * @param cause the cause
     */
    public TwinglySearchServerServiceUnavailableException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Twingly search server service unavailable exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected TwinglySearchServerServiceUnavailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
