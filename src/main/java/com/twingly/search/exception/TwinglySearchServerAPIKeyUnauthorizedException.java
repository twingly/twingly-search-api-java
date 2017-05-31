package com.twingly.search.exception;

import com.twingly.search.domain.BlogStream;

/**
 * The type Twingly search server api key unauthorized exception.
 *
 * @deprecated since 1.1.0
 */
@Deprecated
public class TwinglySearchServerAPIKeyUnauthorizedException extends TwinglySearchException {
    /**
     * Instantiates a new Twingly search server api key unauthorized exception.
     */
    public TwinglySearchServerAPIKeyUnauthorizedException() {
        super();
    }

    /**
     * Instantiates a new Twingly search server api key unauthorized exception.
     *
     * @param blogStream the blog stream
     */
    public TwinglySearchServerAPIKeyUnauthorizedException(BlogStream blogStream) {
        super(blogStream);
    }

    /**
     * Instantiates a new Twingly search server api key unauthorized exception.
     *
     * @param message the message
     */
    public TwinglySearchServerAPIKeyUnauthorizedException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Twingly search server api key unauthorized exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public TwinglySearchServerAPIKeyUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Twingly search server api key unauthorized exception.
     *
     * @param cause the cause
     */
    public TwinglySearchServerAPIKeyUnauthorizedException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Twingly search server api key unauthorized exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected TwinglySearchServerAPIKeyUnauthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
