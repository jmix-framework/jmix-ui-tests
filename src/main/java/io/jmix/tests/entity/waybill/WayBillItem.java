package io.jmix.tests.entity.waybill;

import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.tests.entity.spaceport.Dimensions;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@JmixEntity
@Table(name = "WAY_BILL_ITEM")
@Entity(name = "WayBillItem")
public class WayBillItem {
    @InstanceName
    @Column(name = "NAME")
    private String name;

    @Column(name = "WEIGHT")
    private Double weight;

    @Embedded
    @EmbeddedParameters(nullAllowed = false)
    @AttributeOverrides({
            @AttributeOverride(name = "length", column = @Column(name = "DIM_LENGTH")),
            @AttributeOverride(name = "width", column = @Column(name = "DIM_WIDTH")),
            @AttributeOverride(name = "height", column = @Column(name = "DIM_HEIGHT"))
    })
    private Dimensions dim;

    @Column(name = "CHARGE", nullable = false, precision = 19, scale = 2)
    @NotNull
    private BigDecimal charge;

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "NUMBER_", nullable = false)
    @NotNull
    private Integer number;

    @JoinColumn(name = "WAY_BILL_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private WayBill wayBill;

    public WayBill getWayBill() {
        return wayBill;
    }

    public void setWayBill(WayBill wayBill) {
        this.wayBill = wayBill;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public Dimensions getDim() {
        return dim;
    }

    public void setDim(Dimensions dim) {
        this.dim = dim;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}