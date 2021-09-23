package io.jmix.tests.ui.screen.system.dialog

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button

import static io.jmix.masquerade.Conditions.ENABLED
import static io.jmix.masquerade.Conditions.VISIBLE

class OptionDialog extends Composite<OptionDialog> {

    @Wire(path = 'optionDialog_ok')
    Button ok


    @Wire(path = 'optionDialog_cancel')
    Button cancel

    void confirm() {
        ok.shouldBe(VISIBLE)
                .shouldBe(ENABLED)
                .click()
    }
}
