package com.twingly.search.exception;

import com.twingly.search.domain.Error;

/**
 * Represents Twingly Search Exception with {@link com.twingly.search.domain.Error} as cause.
 *
 * @see com.twingly.search.exception.TwinglySearchException
 * @see com.twingly.search.domain.Error
 */
public class TwinglySearchErrorException extends TwinglySearchException {
    private final Error error;

    /**
     * Instantiates a new Error exception.
     */
    public TwinglySearchErrorException(Error error) {
        super(String.format("Error code: %s. Error message: %s", error.getCode(), error.getMessage()));
        this.error = error;
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
