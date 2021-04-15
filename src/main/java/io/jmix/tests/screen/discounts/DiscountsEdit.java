package io.jmix.tests.screen.discounts;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.customer.Discounts;

@UiController("Discounts.edit")
@UiDescriptor("discounts-edit.xml")
@EditedEntityContainer("discountsDc")
public class DiscountsEdit extends StandardEditor<Discounts> {
}
