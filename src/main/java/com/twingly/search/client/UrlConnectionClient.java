package com.twingly.search.client;

import com.twingly.search.Constants;
import com.twingly.search.Query;
import com.twingly.search.domain.Error;
import com.twingly.search.domain.Post;
import com.twingly.search.domain.Result;
import com.twingly.search.exception.TwinglySearchException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.*;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;

/**
 * This client uses URLConnection to perform network interactions.
 * <p>
 * Compression is enabled for this client by default.
 *
 * @see Client
 * @see URLConnection
 */
public class UrlConnectionClient implements Client {
    /**
     * The constant USER_AGENT_PROPERTY.
     */
    private static final String USER_AGENT_PROPERTY = "User-Agent";
    /**
     * The constant ACCEPT_ENCODING_PROPERTY
     */
    private static final String ACCEPT_ENCODING_PROPERTY = "Accept-Encoding";
    /**
     * The constant Gzip content compression
     */
    private static final String GZIP = "gzip";

    /**
     * The constant for deflate content compression
     */
    private static final String DEFLATE = "deflate";

    /**
     * The constant for the accepted by current client encodings
     */
    private static final String ACCEPTED_ENCODINGS = "gzip;q=1.0,deflate;q=0.6,identity;q=0.3";

    private static final char AND = '&';
    private final String apiKey;
    private String userAgent = DEFAULT_USER_AGENT;
    private JAXBContext jaxbContext;
    private boolean isCompressionsEnabled = true;

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
    public Result makeRequest(String q) {
        String queryString = buildQueryString(q);
        return makeRequestInternal(queryString);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIsCompressionEnabled(boolean value) {
        isCompressionsEnabled = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCompressionEnabled() {
        return isCompressionsEnabled;
    }

    @Override
    public Result makeRequest(Query query) {
        return makeRequest(query.toStringRepresentation());
    }

    private String buildQueryString(String q) {
        StringBuilder sb = new StringBuilder();
        sb.append(API_URL);
        sb.append("?apiKey=").append(apiKey);
        sb.append(AND).append("q=").append(urlEncode(q));
        String result = sb.toString();
        return result;
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
                jaxbContext = JAXBContext.newInstance(Result.class, Post.class, Error.class);
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
                return (Result) result;
            } else if (result instanceof Error) {
                throw TwinglySearchException.fromError((Error) result);
            }
            throw new TwinglySearchException("Unprocessed exception");
        } catch (JAXBException e) {
            throw new TwinglySearchException("Unable to process request", e);
        }
    }


    private Result makeRequestInternal(String query) {
        try {
            URL url = getUrl(query);
            HttpURLConnection connection = prepareConnection(url);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(getConnectionStream(connection)))) {
                return unmarshalXmlForResult(br);
            }
        } catch (IOException e) {
            throw new TwinglySearchException("IO exception", e);
        }
    }

    private HttpURLConnection prepareConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (isCompressionsEnabled) {
            connection.setRequestProperty(ACCEPT_ENCODING_PROPERTY, ACCEPTED_ENCODINGS);
        }
        connection.setRequestProperty(USER_AGENT_PROPERTY, getUserAgent());
        return connection;
    }

    private InputStream getConnectionStream(HttpURLConnection connection) throws IOException {
        int responseSerie = connection.getResponseCode() / 100;
        InputStream connectionStream;
        if (responseSerie == 4) {
            connectionStream = connection.getErrorStream();
        } else {
            connectionStream = connection.getInputStream();
        }
        String contentEncoding = connection.getContentEncoding();
        if (GZIP.equalsIgnoreCase(contentEncoding)) {
            return new GZIPInputStream(connectionStream);
        }
        if (DEFLATE.equalsIgnoreCase(contentEncoding)) {
            return new DeflaterInputStream(connectionStream);
        }
        return connectionStream;
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
