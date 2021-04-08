package io.jmix.tests.screen.waybillitem;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.waybill.WayBillItem;

@UiController("WayBillItem.browse")
@UiDescriptor("way-bill-item-browse.xml")
@LookupComponent("wayBillItemsTable")
public class WayBillItemBrowse extends StandardLookup<WayBillItem> {
}