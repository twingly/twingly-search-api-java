package com.twingly.search.exception;

import com.twingly.search.domain.BlogStream;
import com.twingly.search.domain.Error;

/**
 * Represents possible exceptions related to the search Server.
 */
public class TwinglySearchServerException extends TwinglySearchErrorException {
    /**
     * Instantiates a new Server exception.
     */
    public TwinglySearchServerException(Error error) {
        super(error);
    }

    /**
     * Instantiates a new Twingly search server exception.
     */
    @Deprecated
    public TwinglySearchServerException() {
        super();
    }

    /**
     * Instantiates a new Twingly search server exception.
     *
     * @param blogStream the blog stream
     */
    @Deprecated
    public TwinglySearchServerException(BlogStream blogStream) {
        super(blogStream);
    }

    /**
     * Instantiates a new Twingly search server exception.
     *
     * @param message the message
     * @deprecated since 3.0.0
     */
    @Deprecated
    public TwinglySearchServerException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Twingly search server exception.
     *
     * @param message the message
     * @param cause   the cause
     * @deprecated since 3.0.0
     */
    @Deprecated
    public TwinglySearchServerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Twingly search server exception.
     *
     * @param cause the cause
     * @deprecated since 3.0.0
     */
    @Deprecated
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
     * @deprecated since 3.0.0
     */
    @Deprecated
    protected TwinglySearchServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
