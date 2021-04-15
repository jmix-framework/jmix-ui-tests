package io.jmix.tests.entity.waybill;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.tests.entity.User;
import io.jmix.tests.entity.customer.Customer;
import io.jmix.tests.entity.spaceport.Carrier;
import io.jmix.tests.entity.spaceport.SpacePort;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "WAY_BILL")
@Entity(name = "WayBill")
public class WayBill {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Composition
    @OneToMany(mappedBy = "wayBill")
    private List<WayBillItem> items;

    @Column(name = "REFERENCE", nullable = false)
    @NotNull
    private String reference;

    @JoinColumn(name = "CREATOR_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User creator;

    @InstanceName
    @JoinColumn(name = "SHIPPER_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Customer shipper;

    @JoinColumn(name = "CONSIGNEE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Customer consignee;

    @JoinColumn(name = "DEPARTURE_PORT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SpacePort departurePort;

    @JoinColumn(name = "DESTINATION_PORT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SpacePort destinationPort;

    @JoinColumn(name = "CARRIER_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Carrier carrier;

    @Column(name = "TOTAL_WEIGHT", nullable = false)
    @NotNull
    private Double totalWeight;

    @Column(name = "TOTAL_CHARGE", nullable = false)
    @NotNull
    private Double totalCharge;

    public List<WayBillItem> getItems() {
        return items;
    }

    public void setItems(List<WayBillItem> items) {
        this.items = items;
    }

    public Double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(Double totalCharge) {
        this.totalCharge = totalCharge;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public SpacePort getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(SpacePort destinationPort) {
        this.destinationPort = destinationPort;
    }

    public SpacePort getDeparturePort() {
        return departurePort;
    }

    public void setDeparturePort(SpacePort departurePort) {
        this.departurePort = departurePort;
    }

    public Customer getConsignee() {
        return consignee;
    }

    public void setConsignee(Customer consignee) {
        this.consignee = consignee;
    }

    public Customer getShipper() {
        return shipper;
    }

    public void setShipper(Customer shipper) {
        this.shipper = shipper;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}