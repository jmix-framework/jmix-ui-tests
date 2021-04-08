package io.jmix.tests.screen.waybillitem;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.waybill.WayBillItem;

@UiController("WayBillItem.edit")
@UiDescriptor("way-bill-item-edit.xml")
@EditedEntityContainer("wayBillItemDc")
public class WayBillItemEdit extends StandardEditor<WayBillItem> {
}