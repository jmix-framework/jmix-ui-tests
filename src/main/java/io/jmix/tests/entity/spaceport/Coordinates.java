package io.jmix.tests.entity.spaceport;

import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.NumberFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@JmixEntity(name = "Coordinates")
@Embeddable
public class Coordinates {
    @NumberFormat(pattern = "#.######")
    @Column(name = "LATITUDE", nullable = false)
    @NotNull
    private Double latitude;

    @NumberFormat(pattern = "#.######")
    @Column(name = "LONGTITUDE")
    private Double longtitude;

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}