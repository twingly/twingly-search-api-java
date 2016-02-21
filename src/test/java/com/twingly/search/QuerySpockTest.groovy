package com.twingly.search

import spock.lang.Specification
import spock.lang.Unroll

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.bind.Unmarshaller
import javax.xml.transform.stream.StreamSource
import java.text.SimpleDateFormat

class QuerySpockTest extends Specification {
    def sdf = new SimpleDateFormat(Constants.DATE_FORMAT)
    def jaxbContext = Mock(JAXBContext)
    def unmarschaller = Mock(Unmarshaller)
    def jaxbElement = Mock(JAXBElement)

    def "should make request with query"() {
        given:
        def apiKey = "apiKey"
        def queryString = "some valid query"
        def query = Spy(Query, constructorArgs: [apiKey])
        def expectedValue = Mock(Result)
        when:
        def result = query.query(queryString)
        then:
        1 * query.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> unmarschaller
        1 * unmarschaller.unmarshal(_ as StreamSource, Result.class) >> jaxbElement
        1 * jaxbElement.getValue() >> expectedValue
        result == expectedValue
    }


    def "should make request with searchPattern, language, startDate and endDate"() {
        given:
        def apiKey = "apiKey"
        def searchPattern = "searchPattern"
        def language = Language.English
        def startDate = sdf.parse("2016-02-18 00:00:00")
        def endDate = sdf.parse("2016-03-18 00:00:00")
        def query = Spy(Query, constructorArgs: [apiKey])
        def expectedValue = Mock(Result)
        when:
        def result = query.makeRequest(searchPattern, language, startDate, endDate)
        then:
        1 * query.buildRequestQuery(searchPattern, language, startDate, endDate)
        1 * query.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> unmarschaller
        1 * unmarschaller.unmarshal(_ as StreamSource, Result.class) >> jaxbElement
        1 * jaxbElement.getValue() >> expectedValue
        result == expectedValue
    }

    def "should make request with searchPattern, language and startDate"() {
        given:
        def apiKey = "apiKey"
        def searchPattern = "searchPattern"
        def language = Language.English
        def startDate = sdf.parse("2016-02-18 00:00:00")
        def query = Spy(Query, constructorArgs: [apiKey])
        def expectedValue = Mock(Result)
        when:
        def result = query.makeRequest(searchPattern, language, startDate)
        then:
        1 * query.buildRequestQuery(searchPattern, language, startDate, null)
        1 * query.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> unmarschaller
        1 * unmarschaller.unmarshal(_ as StreamSource, Result.class) >> jaxbElement
        1 * jaxbElement.getValue() >> expectedValue
        result == expectedValue
    }

    def "should make request with searchPattern and language"() {
        given:
        def apiKey = "apiKey"
        def searchPattern = "searchPattern"
        def language = Language.English
        def query = Spy(Query, constructorArgs: [apiKey])
        def expectedValue = Mock(Result)
        when:
        def result = query.makeRequest(searchPattern, language)
        then:
        1 * query.buildRequestQuery(searchPattern, language, null, null)
        1 * query.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> unmarschaller
        1 * unmarschaller.unmarshal(_ as StreamSource, Result.class) >> jaxbElement
        1 * jaxbElement.getValue() >> expectedValue
        result == expectedValue
    }

    def "should make request with searchPattern"() {
        given:
        def apiKey = "apiKey"
        def searchPattern = "searchPattern"
        def query = Spy(Query, constructorArgs: [apiKey])
        def expectedValue = Mock(Result)
        when:
        def result = query.makeRequest(searchPattern)
        then:
        1 * query.buildRequestQuery(searchPattern, null, null, null)
        1 * query.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> unmarschaller
        1 * unmarschaller.unmarshal(_ as StreamSource, Result.class) >> jaxbElement
        1 * jaxbElement.getValue() >> expectedValue
        result == expectedValue
    }

    @Unroll
    def "should build valid request query for apiKey=#apiKey, searchPattern=#searchPattern, st=#startDate, stTo=#endDate"() {
        given:
        def query = new Query(apiKey)
        def st = startDate != null ? sdf.parse(startDate) : null
        def stTo = endDate != null ? sdf.parse(endDate) : null
        when:
        def actual = query.buildRequestQuery(searchPattern, language, st, stTo)
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
