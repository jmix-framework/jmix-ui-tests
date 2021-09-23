package io.jmix.tests.ui.screen.application.localizedDynamic

import io.jmix.masquerade.Conditions
import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button

class LocalizedDynamicBrowse extends Composite<LocalizedDynamicBrowse> {

    @Wire
    Button createBtn

    LocalizedDynamicEditor create() {
        createBtn
                .shouldBe(Conditions.VISIBLE)
                .click()
        return new LocalizedDynamicEditor()
    }
}
