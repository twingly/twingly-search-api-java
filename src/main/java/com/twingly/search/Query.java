package com.twingly.search;

import com.twingly.search.exception.TwinglyException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type Query.
 */
public class Query {
    private static final String BASE_URL = "https://api.twingly.com";
    private static final String SEARCH_PATH = "/analytics/Analytics.ashx";
    private static final char AND = '&';
    private final String apiKey;
    private final SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT);
    private Client client;


    /**
     * Instantiates a new Query.
     *
     * @param apiKey the api key
     */
    public Query(String apiKey) {
        this.apiKey = apiKey;
    }


    /**
     * Build request query string.
     *
     * @param searchPattern    the search pattern
     * @param documentLanguage the document language
     * @param startTime        the start time
     * @param endTime          the end time
     * @return the ready-to-use Query
     */
    public String buildRequestQuery(String searchPattern, String documentLanguage, Date startTime, Date endTime) {

        StringBuilder sb = new StringBuilder();
        sb.append(BASE_URL);
        sb.append(SEARCH_PATH);
        sb.append("?key=").append(apiKey);
        sb.append(AND).append("xmloutputversion=2");

        sb.append(AND).append("searchpattern=").append(urlEncode(searchPattern));

        // add start time if supplied
        if (startTime != null) {
            sb.append(AND).append("ts=").append(urlEncode(df.format(startTime)));
        }

        // add end time if supplied
        if (endTime != null) {
            sb.append(AND).append("tsTo=").append(urlEncode(df.format(endTime)));
        }

        // add document language if supplied
        if (documentLanguage != null) {
            sb.append(AND).append("documentlang=").append(documentLanguage);
        }
        return sb.toString();
    }

    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // This is impossible, UTF-8 is always supported according to the java standard
            throw new TwinglyException("It's quite impossible, but there's no UTF-8 encoding in your JVM", e);
        }
    }

    /**
     * Make request result.
     *
     * @param searchPattern    the search pattern
     * @param documentLanguage the document language
     * @param startTime        the start time
     * @param endTime          the end time
     * @return the result
     */
    public Result makeRequest(String searchPattern, Language documentLanguage, Date startTime, Date endTime) {
        String query = buildRequestQuery(searchPattern, documentLanguage.getIsoCode(), startTime, endTime);
        return getClient().makeRequest(query);
    }

    /**
     * Make request result.
     *
     * @param searchPattern    the search pattern
     * @param documentLanguage the document language
     * @param startTime        the start time
     * @return the result
     */
    public Result makeRequest(String searchPattern, Language documentLanguage, Date startTime) {
        String query = buildRequestQuery(searchPattern, documentLanguage.getIsoCode(), startTime, null);
        return getClient().makeRequest(query);
    }

    /**
     * Make request result.
     *
     * @param searchPattern    the search pattern
     * @param documentLanguage the document language
     * @return the result
     */
    public Result makeRequest(String searchPattern, Language documentLanguage) {
        String query = buildRequestQuery(searchPattern, documentLanguage.getIsoCode(), null, null);
        return getClient().makeRequest(query);
    }

    /**
     * Make request result.
     *
     * @param searchPattern the search pattern
     * @return the result
     */
    public Result makeRequest(String searchPattern) {
        String query = buildRequestQuery(searchPattern, null, null, null);
        return getClient().makeRequest(query);
    }

    /**
     * Query result.
     *
     * @param query the query
     * @return the result
     */
    public Result query(String query) {
        return getClient().makeRequest(query);
    }

    Client getClient() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }
}
