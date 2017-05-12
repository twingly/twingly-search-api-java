package com.twingly.search.exception;

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

}
