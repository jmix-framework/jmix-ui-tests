package io.jmix.tests.entity.spaceport;

import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@JmixEntity(name = "Dimensions")
@Embeddable
public class Dimensions {
    @Column(name = "LENGTH", nullable = false)
    @NotNull
    private Double length;

    @Column(name = "WIDTH", nullable = false)
    @NotNull
    private Double width;

    @Column(name = "HEIGHT", nullable = false)
    @NotNull
    private Double height;

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }
}