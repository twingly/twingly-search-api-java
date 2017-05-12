package com.twingly.search.client

import com.twingly.search.domain.Language
import com.twingly.search.domain.Location
import com.twingly.search.exception.TwinglySearchClientException
import org.junit.Rule
import software.betamax.Configuration
import software.betamax.TapeMode
import software.betamax.junit.Betamax
import software.betamax.junit.RecorderRule
import spock.lang.Specification

class UrlConnectionClientFunctionSpockTest extends Specification {
    private static final String TAPES_STORAGE = "src/test/resources/com/twingly/search/client/tapes"

    File f = new File(TAPES_STORAGE)
    Configuration configuration = Configuration.builder().tapeRoot(f).sslEnabled(true).build()
    @Rule
    RecorderRule recorder = new RecorderRule(configuration)

    @Betamax(tape = "search for spotify", mode = TapeMode.READ_WRITE)
    def "search for spotify in English"() {
        given:
        def apiKey = "693C38C9-FB4E-4DC9-869E-80CFD00ED839"
        def client = new UrlConnectionClient(apiKey)
        def q = String.format("spotify page-size:10 lang:%s", Language.English.isoCode)
        when:
        def result = client.makeRequest(q)
        then:
        result != null
        result.getNumberOfMatchesReturned() == 10
    }

    @Betamax(tape = "search for google", mode = TapeMode.READ_WRITE)
    def "search for google without compression in Ukraine and Belarus"() {
        given:
        def apiKey = "693C38C9-FB4E-4DC9-869E-80CFD00ED839"
        def client = new UrlConnectionClient(apiKey)
        client.setIsCompressionEnabled(false)
        def q = String.format("google page-size:100 location:%s|%s", Location.Ukraine.isoCode, Location.Belarus.isoCode)
        when:
        def result = client.makeRequest(q)
        then:
        result != null
        result.getNumberOfMatchesReturned() == 100
    }

    @Betamax(tape = "search without valid API key", mode = TapeMode.READ_WRITE)
    def "search without valid api key"() {
        given:
        def apiKey = "API_KEY"
        def client = new UrlConnectionClient(apiKey)
        def q = "some pattern"
        when:
        client.makeRequest(q)
        then:
        def ex = thrown(TwinglySearchClientException)
        ex?.error?.code == "40101"
        ex?.error?.message == "Unauthorized"
    }
}
