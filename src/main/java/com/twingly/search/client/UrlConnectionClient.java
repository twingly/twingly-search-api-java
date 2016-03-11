package com.twingly.search.client;

import com.twingly.search.Constants;
import com.twingly.search.Query;
import com.twingly.search.domain.*;
import com.twingly.search.exception.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Iterator;

/**
 * This client uses URLConnection to perform network interactions.
 *
 * @see Client
 * @see com.twingly.search.Query
 * @see com.twingly.search.QueryBuilder
 * @see URLConnection
 */
public class UrlConnectionClient implements Client {
    /**
     * The constant USER_AGENT_PROPERTY.
     */
    private static final String USER_AGENT_PROPERTY = "User-Agent";
    /**
     * Default User-agent that will be used if no other is given
     */
    private static final String DEFAULT_USER_AGENT = "Twingly Search Java Client/" + Constants.VERSION;
    private static final String BASE_URL = "https://api.twingly.com";
    private static final String SEARCH_PATH = "/analytics/Analytics.ashx";
    private static final char AND = '&';
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
    private final String apiKey;
    private String userAgent = DEFAULT_USER_AGENT;
    private JAXBContext jaxbContext;

    /**
     * Constructor that uses given apiKey
     *
     * @param apiKey the api key
     */
    public UrlConnectionClient(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Constructor that tries to instantiate Query object with
     * API key from System property variables
     *
     * @see Constants#TWINGLY_API_KEY_PROPERTY
     */
    public UrlConnectionClient() {
        this.apiKey = System.getProperty(Constants.TWINGLY_API_KEY_PROPERTY);
        if (this.apiKey == null) {
            throw new TwinglySearchException("Api key missing, could not find " + Constants.TWINGLY_API_KEY_PROPERTY + " property");
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * {@inheritDoc}
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Result makeRequest(Query query) {
        String queryString = buildQueryString(query);
        return makeRequest(queryString);
    }

    private String buildQueryString(Query query) {
        StringBuilder sb = new StringBuilder();
        sb.append(BASE_URL);
        sb.append(SEARCH_PATH);
        sb.append("?key=").append(apiKey);
        sb.append(AND).append("xmloutputversion=2");
        sb.append(AND).append("searchpattern=").append(urlEncode(query.getSearchPattern()));
        if (query.getStartTime() != null) {
            sb.append(AND).append("ts=").append(urlEncode(simpleDateFormat.format(query.getStartTime())));
        }
        if (query.getEndTime() != null) {
            sb.append(AND).append("tsTo=").append(urlEncode(simpleDateFormat.format(query.getEndTime())));
        }
        if (query.getDocumentLanguage() != null) {
            sb.append(AND).append("documentlang=").append(query.getDocumentLanguage());
        }
        return sb.toString();
    }

    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // This is impossible, UTF-8 is always supported according to the java standard
            throw new TwinglySearchException("It's quite impossible, but there's no UTF-8 encoding in your JVM", e);
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
                throw new TwinglySearchException("Cannot initialize JAXBContext for Result", e);
            }
        }
        return jaxbContext;
    }

    /**
     * Unmarshal xml for result.
     *
     * @param reader the reader
     * @return the result
     */
    Result unmarshalXmlForResult(Reader reader) {
        try {
            Unmarshaller jaxbUnmarshaller = getJAXBContext().createUnmarshaller();
            Object result = jaxbUnmarshaller.unmarshal(reader);
            if (result instanceof Result) {
                return filterResultsWithEmptyContentTypes((Result) result);
            } else if (result instanceof BlogStream) {
                handleException((BlogStream) result);
            }
            throw new TwinglySearchException("Unprocessed exception");
        } catch (JAXBException e) {
            throw new TwinglySearchException("Unable to process request", e);
        }
    }

    private Result filterResultsWithEmptyContentTypes(Result result) {
        Iterator<Post> iterator = result.getPosts().iterator();
        while (iterator.hasNext()) {
            Post post = iterator.next();
            if (post.getContentType() == null) {
                iterator.remove();
            }
        }
        return result;
    }

    private void handleException(BlogStream blogStream) {
        if (blogStream.getOperationResult() != null && blogStream.getOperationResult().getResultType() == OperationResultType.FAILURE) {
            String message = blogStream.getOperationResult().getMessage();
            if (OperationFailureMessages.API_KEY_DOES_NOT_EXIST.equalsIgnoreCase(message)) {
                throw new TwinglySearchServerAPIKeyDoesNotExistException(blogStream);
            }
            if (OperationFailureMessages.UNAUTHORIZED_API_KEY.equalsIgnoreCase(message)) {
                throw new TwinglySearchServerAPIKeyUnauthorizedException(blogStream);
            }
            if (OperationFailureMessages.SERVICE_UNAVAILABLE.equalsIgnoreCase(message)) {
                throw new TwinglySearchServerServiceUnavailableException(blogStream);
            }
            throw new TwinglySearchServerException(blogStream);
        }
    }


    private Result makeRequest(String query) {
        try {
            URL url = getUrl(query);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty(USER_AGENT_PROPERTY, getUserAgent());
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                return unmarshalXmlForResult(br);
            }
        } catch (IOException e) {
            throw new TwinglySearchException("IO exception", e);
        }
    }

    /**
     * Gets url from query string.
     *
     * @param query the query
     * @return the url
     */
    URL getUrl(String query) {
        try {
            return new URL(query);
        } catch (MalformedURLException e) {
            throw new TwinglySearchException("Malformed query", e);
        }
    }
}
