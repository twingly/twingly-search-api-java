package com.twingly.search.client;

import com.twingly.search.Query;
import com.twingly.search.domain.Result;
import com.twingly.search.exception.TwinglySearchException;

/**
 * Performs all network operations related to using Twingly Search API
 *
 * @see com.twingly.search.Query
 */
public interface Client {
    /**
     * Get current User Agent that will be used in request
     *
     * @return user agent string
     */
    String getUserAgent();

    /**
     * Set user agent to use in requests
     *
     * @param userAgent user agent to be set
     */
    void setUserAgent(String userAgent);

    /**
     * Make request with given query
     *
     * @param query query to perform request with
     * @return Result object
     * @throws TwinglySearchException if any exception happens
     */
    Result makeRequest(Query query);
}
