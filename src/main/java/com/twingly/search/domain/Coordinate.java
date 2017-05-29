package com.twingly.search.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * Geographical coordinates from blog post
 *
 * @since 3.0.0
 */
@XmlRootElement(name = "coordinates")
public class Coordinate {
    /**
     * Latitude value
     */
    private String latitude;
    /**
     * Longitude value
     */
    private String longitude;

    public Coordinate() {
    }

    public Coordinate(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Coordinate{");
        sb.append("latitude='").append(latitude).append('\'');
        sb.append(", longitude='").append(longitude).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
