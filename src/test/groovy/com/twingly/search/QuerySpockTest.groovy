package com.twingly.search

import com.twingly.search.client.Client
import com.twingly.search.domain.Language
import com.twingly.search.exception.QueryException
import com.twingly.search.exception.TwinglySearchException
import spock.lang.Specification
import spock.lang.Unroll

import java.text.SimpleDateFormat

class QuerySpockTest extends Specification {
    def sdf = new SimpleDateFormat(Constants.DATE_FORMAT)
    def client = Mock(Client)

    @Unroll
    def "Query should throw QueryException if search pattern is #exceptionCause"() {
        given:
        def query = new Query(client, "apiKey")
        when:
        query.buildRequestQuery(searchPattern, null, null, null)
        then:
        thrown(QueryException)
        where:
        searchPattern | exceptionCause
        null          | "null"
        ""            | "empty"
    }

    def "creation of Query object without properly set env API key should through exception"() {
        when:
        new Query(client)
        then:
        thrown(TwinglySearchException)
    }

    def "should create query with environment API key"() {
        given:
        def apiKey = "key"
        System.setProperty(Constants.TWINGLY_API_KEY_PROPERTY, apiKey)
        when:
        def query = new Query(client)
        def queryString = query.buildRequestQuery("q", null, null, null)
        then:
        assert queryString.contains(apiKey)
        System.getProperties().remove(Constants.TWINGLY_API_KEY_PROPERTY) != null
    }

    def "should make request with query"() {
        given:
        def apiKey = "apiKey"
        def queryString = "https://twingly.com/"
        def query = Spy(Query, constructorArgs: [client, apiKey])
        when:
        def result = query.query(queryString)
        then:
        1 * client.makeRequest(_ as String)
    }


    def "should make request with searchPattern, language, startDate and endDate"() {
        given:
        def apiKey = "apiKey"
        def searchPattern = "searchPattern"
        def language = Language.English
        def startDate = sdf.parse("2016-02-18 00:00:00")
        def endDate = sdf.parse("2016-03-18 00:00:00")
        def query = Spy(Query, constructorArgs: [client, apiKey])
        when:
        def result = query.makeRequest(searchPattern, language, startDate, endDate)
        then:
        1 * query.buildRequestQuery(searchPattern, language.getIsoCode(), startDate, endDate)
        1 * client.makeRequest(_ as String)
    }

    def "should make request with searchPattern, language and startDate"() {
        given:
        def apiKey = "apiKey"
        def searchPattern = "searchPattern"
        def language = Language.English
        def startDate = sdf.parse("2016-02-18 00:00:00")
        def query = Spy(Query, constructorArgs: [client, apiKey])
        when:
        def result = query.makeRequest(searchPattern, language, startDate)
        then:
        1 * query.buildRequestQuery(searchPattern, language.getIsoCode(), startDate, null)
        1 * client.makeRequest(_ as String)
    }

    def "should make request with searchPattern and language"() {
        given:
        def apiKey = "apiKey"
        def searchPattern = "searchPattern"
        def language = Language.English
        def query = Spy(Query, constructorArgs: [client, apiKey])
        when:
        def result = query.makeRequest(searchPattern, language)
        then:
        1 * query.buildRequestQuery(searchPattern, language.getIsoCode(), null, null)
        1 * client.makeRequest(_ as String)
    }

    def "should make request with searchPattern"() {
        given:
        def apiKey = "apiKey"
        def searchPattern = "searchPattern"
        def query = Spy(Query, constructorArgs: [client, apiKey])
        when:
        def result = query.makeRequest(searchPattern)
        then:
        1 * query.buildRequestQuery(searchPattern, null, null, null)
        1 * client.makeRequest(_ as String)
    }

    @Unroll
    def "should build valid request query for apiKey=#apiKey, searchPattern=#searchPattern, st=#startDate, stTo=#endDate"() {
        given:
        def query = new Query(client, apiKey)
        def st = startDate != null ? sdf.parse(startDate) : null
        def stTo = endDate != null ? sdf.parse(endDate) : null
        when:
        def actual = query.buildRequestQuery(searchPattern, language?.getIsoCode(), st, stTo)
        then:
        actual == expected
        where:
        apiKey | searchPattern     | language           | startDate             | endDate               || expected
        "test" | "searchPattern"   | Language.Afrikaans | "2016-02-18 00:00:00" | "2016-03-18 00:00:00" || "https://api.twingly.com/analytics/Analytics.ashx?key=test&xmloutputversion=2&searchpattern=searchPattern&ts=2016-02-18+00%3A00%3A00&tsTo=2016-03-18+00%3A00%3A00&documentlang=af"
        "key"  | "searchPattern"   | null               | "2016-02-18 00:00:00" | "2016-03-18 00:00:00" || "https://api.twingly.com/analytics/Analytics.ashx?key=key&xmloutputversion=2&searchpattern=searchPattern&ts=2016-02-18+00%3A00%3A00&tsTo=2016-03-18+00%3A00%3A00"
        "test" | "github"          | null               | null                  | "2016-03-18 00:00:00" || "https://api.twingly.com/analytics/Analytics.ashx?key=test&xmloutputversion=2&searchpattern=github&tsTo=2016-03-18+00%3A00%3A00"
        "test" | "Nothing special" | null               | null                  | null                  || "https://api.twingly.com/analytics/Analytics.ashx?key=test&xmloutputversion=2&searchpattern=Nothing+special"
    }

}
