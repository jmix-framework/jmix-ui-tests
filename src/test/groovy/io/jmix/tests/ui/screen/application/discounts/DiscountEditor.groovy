package io.jmix.tests.ui.screen.application.discounts

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.TextField

class DiscountEditor extends Composite<DiscountEditor> {
    @Wire(path = '+DiscountsLocaleLocalization_En')
    TextField locale

    @Wire(path = '+DiscountsLocalizedEnum')
    ComboBox enumer
}
