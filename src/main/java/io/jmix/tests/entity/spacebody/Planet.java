package io.jmix.tests.entity.spacebody;

import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.tests.entity.atmosphere.Atmosphere;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "PLANET")
@Entity(name = "Planet")
public class Planet extends AstronomicalBody {
    @NotNull
    @Column(name = "SEMI_MAJOR_AXES", nullable = false)
    private Double semiMajorAxes;

    @Column(name = "ORBITAL_PERIOD", nullable = false)
    @NotNull
    private Double orbitalPeriod;

    @Column(name = "ROTATION_PERIOD")
    private Double rotationPeriod;

    @JoinColumn(name = "ATMOSPHERE_ID")
    @Composition
    @OneToOne(fetch = FetchType.LAZY)
    private Atmosphere atmosphere;

    @Column(name = "RINGS")
    private Boolean rings;

    public Boolean getRings() {
        return rings;
    }

    public void setRings(Boolean rings) {
        this.rings = rings;
    }

    public Atmosphere getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(Atmosphere atmosphere) {
        this.atmosphere = atmosphere;
    }

    public Double getRotationPeriod() {
        return rotationPeriod;
    }

    public void setRotationPeriod(Double rotationPeriod) {
        this.rotationPeriod = rotationPeriod;
    }

    public Double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public void setOrbitalPeriod(Double orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }

    public Double getSemiMajorAxes() {
        return semiMajorAxes;
    }

    public void setSemiMajorAxes(Double semiMajorAxes) {
        this.semiMajorAxes = semiMajorAxes;
    }
}