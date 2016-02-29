package com.twingly.search.client;

import com.twingly.search.Result;
import com.twingly.search.exception.TwinglyException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Client that could be used in Query object.
 * This client uses URLConnection to perform network interactions.
 * UrlConnectionClient is used by default in Query object
 *
 * @see Client
 * @see com.twingly.search.Query
 * @see URLConnection
 */
public class UrlConnectionClient extends AbstractClient {
    /**
     * Make request for result.
     *
     * @param query the query
     * @return the result
     */
    public Result makeRequest(String query) {
        try {
            URL url = getUrl(query);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty(USER_AGENT_PROPERTY, getUserAgent());
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                return unmarshalXmlForResult(br);
            }
        } catch (IOException e) {
            throw new TwinglyException("IO exception", e);
        }
    }

    /**
     * Gets url from query string.
     *
     * @param query the query
     * @return the url
     */
    protected URL getUrl(String query) {
        try {
            return new URL(query);
        } catch (MalformedURLException e) {
            throw new TwinglyException("Malformed query", e);
        }
    }
}
