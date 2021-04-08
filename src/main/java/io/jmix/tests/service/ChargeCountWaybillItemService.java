package io.jmix.tests.service;

import io.jmix.tests.entity.waybill.WayBill;

public interface ChargeCountWaybillItemService {
    String NAME = "st_ChargeCountWaybillItemService";

    double getChargeValue(double[] arr);

    double getTotalCharge(WayBill wayBill);

    double getTotalWeight(WayBill wayBill);
}
