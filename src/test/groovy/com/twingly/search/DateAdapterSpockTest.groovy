package com.twingly.search

import spock.lang.Specification
import spock.lang.Unroll

class DateAdapterSpockTest extends Specification {
    def adapter = new DateAdapter()

    @Unroll
    def "java date:#date should be equal to formated date:#expected"() {
        when:
        def actual = adapter.marshal(date)
        then:
        actual == expected
        where:
        date                            || expected
        new Date(115, 6, 6, 21, 21, 21) || "2015-07-06 21:21:21"
        new Date(0, 6, 6, 21, 21, 21)   || "1900-07-06 21:21:21"
        null                            || ""
    }

    @Unroll
    def "formated date:#date should be equal to java date:#expected"() {
        when:
        def actual = adapter.unmarshal(date)
        then:
        actual == expected
        where:
        date                  || expected
        "2015-07-06 21:21:21" || new Date(115, 6, 6, 21, 21, 21)
        "1900-07-06 21:21:21" || new Date(0, 6, 6, 21, 21, 21)
        ""                    || null
        "       "             || null
        null                  || null
    }
}
