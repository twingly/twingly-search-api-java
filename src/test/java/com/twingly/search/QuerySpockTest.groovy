package com.twingly.search

import spock.lang.Specification

import java.text.SimpleDateFormat

class QuerySpockTest extends Specification {
    def sdf = new SimpleDateFormat(Constants.DATE_FORMAT)
    def apiKey = ""
    Query query

    def "setup"() {
        query = new Query(apiKey)
    }

    def "query should return results"() {
        given:
        def searchPattern = "github"
        def timeStart = sdf.parse("2016-02-18 10:00:00")
        def timeEnd = sdf.parse("2016-02-19 11:00:00")
        def language = Language.English
        when:
        def result = query.makeRequest(searchPattern, language, timeStart, timeEnd)
        then:
        result != null
    }

}
