package com.twingly.search;

import com.twingly.search.exception.BlogStream;
import com.twingly.search.exception.OperationResult;
import com.twingly.search.exception.TwinglyException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
    private static final String USER_AGENT_PROPERTY = "User-Agent";
    private static final String DEFAULT_USER_AGENT = "Twingly Search Java Client/" + Constants.VERSION;
    private final String apiKey;
    private final SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT);
    private String userAgent = DEFAULT_USER_AGENT;
    private JAXBContext jaxbContext;

    /**
     * Instantiates a new Query.
     *
     * @param apiKey the api key
     */
    public Query(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
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
    public String buildRequestQuery(String searchPattern, Language documentLanguage, Date startTime, Date endTime) {

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
            sb.append(AND).append("documentlang=").append(documentLanguage.toStringRepresentation());
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
        String query = buildRequestQuery(searchPattern, documentLanguage, startTime, endTime);
        return makeRequestInternal(query);
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
        String query = buildRequestQuery(searchPattern, documentLanguage, startTime, null);
        return makeRequestInternal(query);
    }

    /**
     * Make request result.
     *
     * @param searchPattern    the search pattern
     * @param documentLanguage the document language
     * @return the result
     */
    public Result makeRequest(String searchPattern, Language documentLanguage) {
        String query = buildRequestQuery(searchPattern, documentLanguage, null, null);
        return makeRequestInternal(query);
    }

    /**
     * Make request result.
     *
     * @param searchPattern the search pattern
     * @return the result
     */
    public Result makeRequest(String searchPattern) {
        String query = buildRequestQuery(searchPattern, null, null, null);
        return makeRequestInternal(query);
    }

    /**
     * Query result.
     *
     * @param query the query
     * @return the result
     */
    public Result query(String query) {
        return makeRequestInternal(query);
    }

    private Result makeRequestInternal(String query) {
        try {
            Unmarshaller jaxbUnmarshaller = getJAXBContext().createUnmarshaller();

            URL url = getUrl(query);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty(USER_AGENT_PROPERTY, userAgent);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                Object result = jaxbUnmarshaller.unmarshal(br);
                if (result instanceof Result) {
                    return (Result) result;
                } else if (result instanceof BlogStream) {
                    throw new TwinglyException((BlogStream) result);
                }
                throw new TwinglyException("Unprocessed exception");
            }
        } catch (JAXBException e) {
            throw new TwinglyException("Unable to process request", e);
        } catch (IOException e) {
            throw new TwinglyException("IO exception", e);
        }
    }

    URL getUrl(String query) {
        try {
            return new URL(query);
        } catch (MalformedURLException e) {
            throw new TwinglyException("Malformed query", e);
        }
    }

    /**
     * Gets jaxb context.
     *
     * @return the jaxb context
     */
    JAXBContext getJAXBContext() {
        if (jaxbContext == null) {
            try {
                jaxbContext = JAXBContext.newInstance(Result.class, Post.class, OperationResult.class, BlogStream.class);
            } catch (JAXBException e) {
                throw new TwinglyException("Cannot initialize JAXBContext for Result", e);
            }
        }
        return jaxbContext;
    }
}
