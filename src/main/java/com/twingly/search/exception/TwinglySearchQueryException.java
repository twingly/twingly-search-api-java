package com.twingly.search.exception;

import com.twingly.search.domain.Error;

/**
 * Exception happened due to problems with search Query
 */
public class TwinglySearchQueryException extends TwinglySearchClientException {

    public TwinglySearchQueryException(String message) {
        super(new Error("-1", message));
    }

    /**
     * Instantiates a new Query exception.
     *
     * @param error Error from API response
     */
    public TwinglySearchQueryException(Error error) {
        super(error);
    }
}
