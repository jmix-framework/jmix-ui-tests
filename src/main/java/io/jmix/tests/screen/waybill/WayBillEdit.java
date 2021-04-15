package io.jmix.tests.screen.waybill;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.waybill.WayBill;

@UiController("WayBill.edit")
@UiDescriptor("way-bill-edit.xml")
@EditedEntityContainer("wayBillDc")
public class WayBillEdit extends StandardEditor<WayBill> {
}
