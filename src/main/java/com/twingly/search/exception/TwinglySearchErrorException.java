package com.twingly.search.exception;

import com.twingly.search.domain.BlogStream;
import com.twingly.search.domain.Error;

/**
 * Represents Twingly Search Exception with {@link com.twingly.search.domain.Error} as cause.
 *
 * @see com.twingly.search.exception.TwinglySearchException
 * @see com.twingly.search.domain.Error
 */
public class TwinglySearchErrorException extends TwinglySearchException {
    private Error error;

    /**
     * Instantiates a new Error exception.
     *
     * @param error Error from API response
     */
    public TwinglySearchErrorException(Error error) {
        super(String.format("Error code: %s. Error message: %s", error.getCode(), error.getMessage()));
        this.error = error;
    }

    @Deprecated
    public TwinglySearchErrorException() {
    }

    @Deprecated
    public TwinglySearchErrorException(BlogStream blogStream) {
        super(blogStream);
    }

    @Deprecated
    public TwinglySearchErrorException(String message) {
        super(message);
    }

    @Deprecated
    public TwinglySearchErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    @Deprecated
    public TwinglySearchErrorException(Throwable cause) {
        super(cause);
    }

    @Deprecated
    public TwinglySearchErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Gets Error that caused exception.
     *
     * @return the error
     */
    public Error getError() {
        return error;
    }
}
