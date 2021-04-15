package io.jmix.tests.entity.spaceport;

import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
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

    @NotNull
    @NumberFormat(pattern = "#.######")
    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @InstanceName
    @DependsOnProperties({"latitude", "longitude"})
    public String getInstanceName() {
        return String.format("%s %s", latitude, longitude);
    }
}