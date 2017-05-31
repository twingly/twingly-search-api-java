package com.twingly.search.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.math.BigDecimal;

/**
 * Geographical coordinates from blog post
 *
 * @since 1.1.0
 */
@XmlRootElement(name = "coordinates")
public class Coordinate {
    /**
     * Latitude value
     */
    private BigDecimal latitude;
    /**
     * Longitude value
     */
    private BigDecimal longitude;

    public Coordinate() {
    }

    public Coordinate(String latitude, String longitude) {
        try {
            this.latitude = new BigDecimal(latitude);
            this.longitude = new BigDecimal(longitude);
        } catch (NumberFormatException e) {
            this.latitude = null;
            this.longitude = null;
        }
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Coordinate{");
        sb.append("latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
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
