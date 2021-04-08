package io.jmix.tests.service.app;

import io.jmix.core.DataManager;
import io.jmix.tests.entity.customer.CustomerGrade;
import io.jmix.tests.entity.waybill.WayBill;
import io.jmix.tests.entity.waybill.WayBillItem;
import io.jmix.tests.service.ChargeCountWaybillItemService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;

@Component(ChargeCountWaybillItemServiceBean.NAME)
public class ChargeCountWaybillItemServiceBean implements ChargeCountWaybillItemService{
    public static final String NAME = "_ChargeCountWaybillItemServiceBean";

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ChargeCountWaybillItemServiceBean.class);
    @Autowired
    private DataManager dataManager;

    @Override
    public double getChargeValue(double[] arr) {
        return (arr[0] + arr[1] + arr[2])*arr[3];
    }

    @Override
    public double getTotalCharge(WayBill wayBill) {
        try{
            double totalCharge = 0;
            double charge = wayBill.getItems().stream().map(WayBillItem::getCharge).mapToDouble(e -> e.doubleValue()).sum();
            CustomerGrade grade = wayBill.getShipper().getGrade();
            double discount = dataManager.loadValue("select e.value from st_Discounts e where e.grade = :grade", BigDecimal.class)
                    .parameter("grade", grade.getId())
                    .one().doubleValue();
            totalCharge = (charge * (100 - discount)) / 100;
            return totalCharge;
        } catch (NullPointerException e){
            log.error("List is empty", e);
            return 0;
        } catch (IllegalStateException e){
            log.error("There is no value from this grade");
            return wayBill.getItems().stream().map(WayBillItem::getCharge).mapToDouble(s -> s.doubleValue()).sum();
        }
    }

    @Override
    public double getTotalWeight(WayBill wayBill) {
        try{
            double totalWeight = 0;
            totalWeight = wayBill.getItems().stream().mapToDouble(WayBillItem::getWeight).sum();
            return totalWeight;
        } catch (NullPointerException e){
            log.error("List is empty", e);
            return 0;
        }
    }
}