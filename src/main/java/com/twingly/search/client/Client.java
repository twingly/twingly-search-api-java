package com.twingly.search.client;

import com.twingly.search.Constants;
import com.twingly.search.Query;
import com.twingly.search.domain.Result;
import com.twingly.search.exception.TwinglySearchException;

/**
 * Performs all network operations related to using Twingly Search API
 *
 * @see <a href="https://developer.twingly.com/resources/search-language/">Search Langunage</a>
 * @see Query
 */
public interface Client {
    /**
     * Default User-agent that will be used if no other is given
     */
    String DEFAULT_USER_AGENT = "Twingly Search Java Client/" + Constants.VERSION;
    String BASE_URL = "https://api.twingly.com";
    String SEARCH_API_VERSION = "v3";
    String SEARCH_PATH = String.format("/blog/search/api/%s/search", SEARCH_API_VERSION);
    String API_URL = BASE_URL + SEARCH_PATH;

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
     * @param q query to perform request with
     * @return Result object
     * @throws TwinglySearchException if any exception happens
     * @since 3.0.0
     */
    Result makeRequest(String q);

    /**
     * Enables or disables compression
     *
     * @param value true in order to enable compressions or false in order to disable compressions
     * @since 3.0.0
     */
    void setIsCompressionEnabled(boolean value);

    /**
     * Checks whether compressions is enabled
     *
     * @return true if compressions is enabled - false otherwise
     * @since 3.0.0
     */
    boolean isCompressionEnabled();

    /**
     * Make request with given query
     *
     * @param query query to perform request with
     * @return Result object
     * @throws TwinglySearchException if any exception happens
     */
    Result makeRequest(Query query);
}
