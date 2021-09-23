package io.jmix.tests.ui.screen.application.dymanicCategories

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button

import static io.jmix.masquerade.Conditions.ENABLED
import static io.jmix.masquerade.Conditions.VISIBLE

class DynamicCategoryBrowse extends Composite<DynamicCategoryBrowse> {
    @Wire
    Button createBtn

    DynamicCategoryEditor create() {
        createBtn
                .shouldBe(VISIBLE, ENABLED)
                .click()
        return new DynamicCategoryEditor()
    }
}
