package io.jmix.tests.entity.spacebody;

import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.tests.entity.atmosphere.Atmosphere;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "MOON")
@Entity(name = "Moon")
public class Moon extends AstronomicalBody {

    @JoinColumn(name = "PLANET_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Planet planet;

    @JoinColumn(name = "ATMOSPHERE_ID")
    @Composition
    @OneToOne(fetch = FetchType.LAZY)
    private Atmosphere atmosphere;

    public Atmosphere getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(Atmosphere atmosphere) {
        this.atmosphere = atmosphere;
    }

    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

}