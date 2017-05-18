package com.twingly.search.exception;

import com.twingly.search.domain.BlogStream;

/**
 * The type Twingly search server api key does not exist exception.
 *
 * @deprecated since 3.0.0
 */
@Deprecated
public class TwinglySearchServerAPIKeyDoesNotExistException extends TwinglySearchServerException {
    /**
     * Instantiates a new Twingly search server api key does not exist exception.
     */
    public TwinglySearchServerAPIKeyDoesNotExistException() {
        super();
    }

    /**
     * Instantiates a new Twingly search server api key does not exist exception.
     *
     * @param blogStream the blog stream
     */
    public TwinglySearchServerAPIKeyDoesNotExistException(BlogStream blogStream) {
        super(blogStream);
    }

    /**
     * Instantiates a new Twingly search server api key does not exist exception.
     *
     * @param message the message
     */
    public TwinglySearchServerAPIKeyDoesNotExistException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Twingly search server api key does not exist exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public TwinglySearchServerAPIKeyDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Twingly search server api key does not exist exception.
     *
     * @param cause the cause
     */
    public TwinglySearchServerAPIKeyDoesNotExistException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Twingly search server api key does not exist exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected TwinglySearchServerAPIKeyDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
