package com.twingly.search

import com.twingly.search.domain.Coordinate

import spock.lang.Specification
import spock.lang.Unroll

class CoordinateSpockTest extends Specification {
    def coordinates = new Coordinate()

    @Unroll
    def "should robustly parse coordinates"() {
        when:
        def actual = new Coordinate(latitude, longitude).toString()
        then:
        actual == expected
        where:
        latitude || longitude || expected
        "49.1"   || "10.75"   || "Coordinate{latitude=49.1, longitude=10.75}"
        "FOO"    || "BAR"     || "Coordinate{latitude=null, longitude=null}"
        ""       || ""        || "Coordinate{latitude=null, longitude=null}"
    }
}
