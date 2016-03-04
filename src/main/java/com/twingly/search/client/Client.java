package com.twingly.search.client;

import com.twingly.search.domain.Result;
import com.twingly.search.exception.TwinglySearchException;

/**
 * Performs all network operations related to Query usage
 *
 * @see com.twingly.search.Query
 */
public interface Client {
    /**
     * Get current User Agent that will be used in request
     *
     * @return user agent string
     */
    public String getUserAgent();

    /**
     * Set user agent to use in requests
     *
     * @param userAgent user agent to be set
     */
    public void setUserAgent(String userAgent);

    /**
     * Make request with given query parameter
     *
     * @param query query string to perform request with
     * @return Result object
     * @throws TwinglySearchException if any exception happens
     */
    public Result makeRequest(String query);
}
