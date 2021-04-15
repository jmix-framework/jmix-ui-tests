package io.jmix.tests.entity.atmosphere;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JmixEntity
@Table(name = "ATMOSPHERIC_GAS")
@Entity(name = "AtmosphericGas")
public class AtmosphericGas {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "GAS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Gas gas;

    @Column(name = "VOLUME", nullable = false)
    @NotNull
    private Double volume;

    @JoinColumn(name = "ATMOSPHERE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Atmosphere atmosphere;

    public Atmosphere getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(Atmosphere atmosphere) {
        this.atmosphere = atmosphere;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Gas getGas() {
        return gas;
    }

    public void setGas(Gas gas) {
        this.gas = gas;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @InstanceName
    @DependsOnProperties({"atmosphere", "gas"})
    public String getInstanceName() {
        return String.format("%s %s", atmosphere, gas);
    }
}