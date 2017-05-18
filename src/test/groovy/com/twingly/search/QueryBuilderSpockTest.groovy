package com.twingly.search

import com.twingly.search.domain.Language
import com.twingly.search.domain.Location
import com.twingly.search.exception.QueryException
import com.twingly.search.exception.TwinglySearchQueryException
import spock.lang.Specification
import spock.lang.Unroll

import java.text.SimpleDateFormat

class QueryBuilderSpockTest extends Specification {
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SEARCH_QUERY_DATE_FORMAT)

    @Unroll
    def "query location should be '#expectedQueryLocation' for '#location'"() {
        when:
        def query = QueryBuilder.create("q").location(location).build()
        then:
        query.location == expectedQueryLocation
        where:
        location           || expectedQueryLocation
        "ua"               || location
        Location.Ukraine   || Location.Ukraine.isoCode
        "NO_SUCH_LOCATION" || null
        ""                 || null
        "       "          || null
    }

    def "should convert Query to QueryBuilder and back"() {
        given:
        def startTime = simpleDateFormat.parse("2016-03-03 15:30:45")
        def endTime = simpleDateFormat.parse("2016-05-03 15:30:45")
        def searchQuery = "searchQuery"
        def lang = "ua"
        def location = "uk"
        def query = new Query(startTime, endTime, searchQuery, lang, location)
        expect:
        query.toBuilder() == QueryBuilder.fromQuery(query)
    }

    def "should throw TwinglySearchQueryException for empty search pattern"() {
        when:
        QueryBuilder.create("")
        then:
        thrown(TwinglySearchQueryException)
    }

    def "should create valid query object with only search pattern"() {
        when:
        def query = QueryBuilder.create("searchPattern").build()
        then:
        query.searchQuery == "searchPattern"
        query.lang == null
        query.endTime == null
        query.startTime == null
    }

    def "should set documentLanguage from Language enum"() {
        given:
        def language = Language.English
        when:
        def query = QueryBuilder.create("pattern").documentLanguage(language).build()
        then:
        query.lang == Language.English.isoCode
    }

    def "should throw QueryException on invalid search pattern set"() {
        given:
        def validSearchPattern = "searchPattern"
        def invalidSearchPattern = ""
        def queryBuilder = QueryBuilder.create(validSearchPattern)
        when:
        queryBuilder.searchPattern(invalidSearchPattern)
        then:
        thrown(QueryException)
    }

    def "should set search query with search pattern set"() {
        given:
        def validSearchPattern = "searchPattern"
        def queryBuilder = QueryBuilder.create(validSearchPattern)
        when:
        queryBuilder.searchPattern(validSearchPattern)
        then:
        queryBuilder.build().searchQuery == validSearchPattern
    }

    def "should create new Query object"() {
        given:
        def queryBuilder = QueryBuilder.create("pattern").lang("en")
        def firstQuery = queryBuilder.build()
        when:
        def secondQuery = queryBuilder.createNewQuery().build()
        then:
        firstQuery != secondQuery
    }

    def "should set start time if it is before end time"() {
        given:
        def startTime = simpleDateFormat.parse("2016-03-03 15:30:45")
        def endTime = simpleDateFormat.parse("2016-05-03 15:30:45")
        when:
        def query = QueryBuilder.create("pattern").startTime(startTime).endTime(endTime).build()
        then:
        query.endTime == endTime
        query.startTime == startTime
    }

    def "should not set end time if it's before start time"() {
        given:
        def startTime = simpleDateFormat.parse("2016-05-03 15:30:45")
        def endTime = simpleDateFormat.parse("2016-03-03 15:30:45")
        when:
        def query = QueryBuilder.create("pattern").startTime(startTime).endTime(endTime).build()
        then:
        query.endTime == null
        query.startTime == startTime
    }

    def "should not set start time if it's after end time"() {
        given:
        def startTime = simpleDateFormat.parse("2016-05-03 15:30:45")
        def endTime = simpleDateFormat.parse("2016-03-03 15:30:45")
        when:
        def query = QueryBuilder.create("pattern").endTime(endTime).startTime(startTime).build()
        then:
        query.endTime == endTime
        query.startTime == null
    }

    @Unroll
    def "language should be '#expectedLanguage' for '#language'"() {
        when:
        def query = QueryBuilder.create("q").lang(language).build()
        then:
        query.lang == expectedLanguage
        where:
        language           || expectedLanguage
        "uk"               || language
        Language.Ukrainian || Language.Ukrainian.isoCode
        "NO_SUCH_LANGUAGE" || null
        ""                 || null
        "          "       || null
    }

    def "should not set language from null Language enum"() {
        when:
        def query = QueryBuilder.create("pattern").documentLanguage(null).build()
        then:
        query.lang == null
    }

    def "should create valid query object with all fields set"() {
        given:
        def searchPattern = "searchPattern"
        def language = "en"
        def startTime = simpleDateFormat.parse("2016-04-03 15:30:45")
        def endTime = simpleDateFormat.parse("2016-05-03 15:30:45")
        when:
        def query = QueryBuilder.create(searchPattern).startTime(startTime).
                endTime(endTime).lang(language).build()
        then:
        query.searchQuery == "searchPattern"
        query.lang == language
        query.endTime == endTime
        query.startTime == startTime
    }
}
