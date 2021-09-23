package io.jmix.tests.ui.screen.application.dymanicCategories

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.TextField


class DynamicCategoryEditor extends Composite<DynamicCategoryEditor> {
    @Wire (path = 'categoryField')
    ComboBox category

    @Wire(path = '+DynamiccategoryCategorized')
    TextField categorized
}
