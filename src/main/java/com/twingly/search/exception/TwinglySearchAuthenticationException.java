package com.twingly.search.exception;

import com.twingly.search.domain.Error;

/**
 * Exception happened due to problems with Authentication
 */
public class TwinglySearchAuthenticationException extends TwinglySearchClientException {
    /**
     * Instantiates a new Authentication exception.
     *
     * @param error Error from API response
     */
    public TwinglySearchAuthenticationException(Error error) {
        super(error);
    }
}
