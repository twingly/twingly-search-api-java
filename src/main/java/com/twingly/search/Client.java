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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Iurii Sergiichuk on 23.02.2016.
 */
public class Client {
    private static final String USER_AGENT_PROPERTY = "User-Agent";
    private static final String DEFAULT_USER_AGENT = "Twingly Search Java Client/" + Constants.VERSION;
    private String userAgent = DEFAULT_USER_AGENT;
    private JAXBContext jaxbContext;

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Result makeRequest(String query) {
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
