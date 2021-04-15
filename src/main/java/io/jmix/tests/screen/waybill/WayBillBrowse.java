package io.jmix.tests.screen.waybill;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.waybill.WayBill;

@UiController("WayBill.browse")
@UiDescriptor("way-bill-browse.xml")
@LookupComponent("wayBillsTable")
public class WayBillBrowse extends StandardLookup<WayBill> {
}
