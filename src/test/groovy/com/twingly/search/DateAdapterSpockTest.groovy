package com.twingly.search

import spock.lang.Specification
import spock.lang.Unroll

class DateAdapterSpockTest extends Specification {
    private static PREVIOUS_TIME_ZONE
    def adapter = new DateAdapter()

    def setupSpec() {
        PREVIOUS_TIME_ZONE = TimeZone.getDefault()
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }

    def cleanupSpec() {
        TimeZone.setDefault(PREVIOUS_TIME_ZONE)
    }

    @Unroll
    def "java date:#date should be equal to formatted date:#expected"() {
        when:
        def actual = adapter.marshal(date)
        then:
        actual == expected
        where:
        date                            || expected
        new Date(115, 6, 6, 21, 21, 21) || "2015-07-06T21:21:21Z"
        new Date(0, 6, 6, 21, 21, 21)   || "1900-07-06T21:21:21Z"
        null                            || ""
    }

    @Unroll
    def "formatted date:#date should be equal to java date:#expected"() {
        when:
        def actual = adapter.unmarshal(date)
        then:
        actual == expected
        where:
        date                   || expected
        "2015-07-06T21:21:21Z" || new Date(115, 6, 6, 21, 21, 21)
        "1900-07-06T21:21:21Z" || new Date(0, 6, 6, 21, 21, 21)
        ""                     || null
        "       "              || null
        null                   || null
    }
}
