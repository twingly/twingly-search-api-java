package com.twingly.search.client;

import com.twingly.search.Result;
import com.twingly.search.exception.TwinglyException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Client that could be used in Query object.
 * This client is based on Apache Commons HttpClient
 *
 * @see HttpClient
 * @see Client
 * @see com.twingly.search.Query
 */
public class ApacheHttpClient extends AbstractClient {
    private HttpClient httpClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public Result makeRequest(String query) {
        try {
            HttpGet request = new HttpGet(query);
            request.setHeader(USER_AGENT_PROPERTY, getUserAgent());
            HttpResponse response = getHttpClient().execute(request);
            try (BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()))) {
                return unmarshalXmlForResult(rd);
            }
        } catch (IOException e) {
            throw new TwinglyException("IO exception", e);
        }
    }

    /**
     * Gets http client.
     *
     * @return the http client
     */
    public HttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = HttpClients.createDefault();
        }
        return httpClient;
    }
}
