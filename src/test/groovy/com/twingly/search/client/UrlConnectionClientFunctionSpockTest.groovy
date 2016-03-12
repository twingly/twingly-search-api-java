package com.twingly.search.client

import com.twingly.search.QueryBuilder
import com.twingly.search.domain.Language
import com.twingly.search.exception.TwinglySearchServerAPIKeyDoesNotExistException
import org.junit.Rule
import software.betamax.Configuration
import software.betamax.ProxyConfiguration
import software.betamax.TapeMode
import software.betamax.junit.Betamax
import software.betamax.junit.RecorderRule
import spock.lang.Specification

class UrlConnectionClientFunctionSpockTest extends Specification {
    private static final String TAPES_STORAGE = "src/test/resources/com/twingly/search/client/tapes";

    File f = new File(TAPES_STORAGE);
    Configuration configuration = ProxyConfiguration.builder().tapeRoot(f).sslEnabled(true).build();
    @Rule
    RecorderRule recorder = new RecorderRule(configuration);

    @Betamax(tape = "search for spotify", mode = TapeMode.READ_WRITE)
    def "search for spotify"() {
        given:
        def apiKey = "API_KEY"
        def client = new UrlConnectionClient(apiKey);
        def searchPattern = "spotify page-size:10";
        def query = QueryBuilder.create(searchPattern).documentLanguage(Language.English).build();
        when:
        def result = client.makeRequest(query);
        then:
        result != null
        result.getNumberOfMatchesReturned() == 10
    }

    @Betamax(tape = "search without valid API key", mode = TapeMode.READ_WRITE)
    def "search without valid api key"() {
        given:
        def apiKey = "API_KEY"
        def client = new UrlConnectionClient(apiKey);
        def searchPattern = "some pattern";
        def query = QueryBuilder.create(searchPattern).build();
        when:
        client.makeRequest(query);
        then:
        thrown(TwinglySearchServerAPIKeyDoesNotExistException)
    }
}
