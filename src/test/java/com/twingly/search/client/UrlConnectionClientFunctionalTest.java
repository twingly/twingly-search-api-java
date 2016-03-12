package com.twingly.search.client;

import com.twingly.search.Query;
import com.twingly.search.QueryBuilder;
import com.twingly.search.domain.Language;
import com.twingly.search.domain.Result;
import com.twingly.search.exception.TwinglySearchServerAPIKeyDoesNotExistException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import software.betamax.Configuration;
import software.betamax.ProxyConfiguration;
import software.betamax.TapeMode;
import software.betamax.junit.Betamax;
import software.betamax.junit.RecorderRule;

import java.io.File;

public class UrlConnectionClientFunctionalTest {
    private static final String API_KEY = "API_KEY";
    private static final String TAPES_STORAGE = "src/test/resources/com/twingly/search/client/tapes";

    File f = new File(TAPES_STORAGE);
    Configuration configuration = ProxyConfiguration.builder().tapeRoot(f).sslEnabled(true).build();
    @Rule
    public RecorderRule recorder = new RecorderRule(configuration);

    @Test
    @Betamax(tape = "search for spotify", mode = TapeMode.READ_WRITE)
    public void searchForSpotify() {
        Client client = new UrlConnectionClient(API_KEY);
        String searchPattern = "spotify page-size:10";
        Query query = QueryBuilder.create(searchPattern).documentLanguage(Language.English).build();
        Result result = client.makeRequest(query);
        Assert.assertNotNull(result);
        Assert.assertEquals(10, result.getNumberOfMatchesReturned());
    }

    @Test(expected = TwinglySearchServerAPIKeyDoesNotExistException.class)
    @Betamax(tape = "search without valid API key", mode = TapeMode.READ_WRITE)
    public void searchWithoutValidApiKey() {
        Client client = new UrlConnectionClient(API_KEY);
        String searchPattern = "some pattern";
        Query query = QueryBuilder.create(searchPattern).build();
        client.makeRequest(query);
    }
}
