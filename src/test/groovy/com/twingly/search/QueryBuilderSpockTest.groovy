package com.twingly.search

import com.twingly.search.domain.Language
import com.twingly.search.exception.QueryException
import spock.lang.Specification

import java.text.SimpleDateFormat

class QueryBuilderSpockTest extends Specification {
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);

    def "should through QueryException for empty search pattern"() {
        when:
        QueryBuilder.create("")
        then:
        thrown(QueryException)
    }

    def "should create valid query object with only search pattern"() {
        when:
        def query = QueryBuilder.create("searchPattern").build()
        then:
        query.searchPattern == "searchPattern"
        query.documentLanguage == null
        query.endTime == null
        query.startTime == null
    }

    def "should set documentLanguage from Language enum"() {
        given:
        def language = Language.English
        when:
        def query = QueryBuilder.create("pattern").documentLanguage(language).build()
        then:
        query.documentLanguage == Language.English.isoCode
    }

    def "should through QueryException on invalid search pattern set"() {
        given:
        def validSearchPattern = "searchPattern"
        def invalidSearchPattern = ""
        def queryBuilder = QueryBuilder.create(validSearchPattern)
        when:
        queryBuilder.searchPattern(invalidSearchPattern)
        then:
        thrown(QueryException)
    }

    def "should create new Query object"() {
        given:
        def queryBuilder = QueryBuilder.create("pattern")
        def firstQuery = queryBuilder.build()
        when:
        def secondQuery = queryBuilder.createNewQuery().build()
        then:
        firstQuery != secondQuery
    }

    def "should set start time if it is before end time"() {
        given:
        def startTime = simpleDateFormat.parse("2016-03-03 15:30:45");
        def endTime = simpleDateFormat.parse("2016-05-03 15:30:45");
        when:
        def query = QueryBuilder.create("pattern").startTime(startTime).endTime(endTime).build()
        then:
        query.endTime == endTime
        query.startTime == startTime
    }

    def "should not set end time if it's before start time"() {
        given:
        def startTime = simpleDateFormat.parse("2016-05-03 15:30:45");
        def endTime = simpleDateFormat.parse("2016-03-03 15:30:45");
        when:
        def query = QueryBuilder.create("pattern").startTime(startTime).endTime(endTime).build()
        then:
        query.endTime == null
        query.startTime == startTime
    }

    def "should not set start time if it's after end time"() {
        given:
        def startTime = simpleDateFormat.parse("2016-05-03 15:30:45");
        def endTime = simpleDateFormat.parse("2016-03-03 15:30:45");
        when:
        def query = QueryBuilder.create("pattern").endTime(endTime).startTime(startTime).build()
        then:
        query.endTime == endTime
        query.startTime == null
    }

    def "should not set language from null Language enum"() {
        when:
        def query = QueryBuilder.create("pattern").documentLanguage(null).build()
        then:
        query.documentLanguage == null
    }

    def "should not set empty language"() {
        when:
        def query = QueryBuilder.create("pattern").documentLanguage("").build()
        then:
        query.documentLanguage == null
    }


    def "should create valid query object with all fields set"() {
        given:
        def searchPattern = "searchPattern"
        def language = "en"
        def startTime = simpleDateFormat.parse("2016-04-03 15:30:45");
        def endTime = simpleDateFormat.parse("2016-05-03 15:30:45");
        when:
        def query = QueryBuilder.create(searchPattern).startTime(startTime).
                endTime(endTime).documentLanguage(language).build()
        then:
        query.searchPattern == "searchPattern"
        query.documentLanguage == language
        query.endTime == endTime
        query.startTime == startTime
    }
}
