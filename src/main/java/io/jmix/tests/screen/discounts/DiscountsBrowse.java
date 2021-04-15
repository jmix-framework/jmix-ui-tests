package io.jmix.tests.screen.discounts;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.customer.Discounts;

@UiController("Discounts.browse")
@UiDescriptor("discounts-browse.xml")
@LookupComponent("discountsesTable")
public class DiscountsBrowse extends StandardLookup<Discounts> {
}
