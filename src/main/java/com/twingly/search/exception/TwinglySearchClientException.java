package com.twingly.search.exception;

import com.twingly.search.domain.Error;

/**
 * Represents possible exceptions related to the Client configuration or submitted Query
 */
public class TwinglySearchClientException extends TwinglySearchErrorException {

    /**
     * Instantiates a new Client exception.
     *
     * @param error Error from API response
     */
    public TwinglySearchClientException(Error error) {
        super(error);
    }
}
