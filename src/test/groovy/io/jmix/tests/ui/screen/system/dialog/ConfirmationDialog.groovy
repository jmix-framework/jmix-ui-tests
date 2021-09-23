package io.jmix.tests.ui.screen.system.dialog

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button

import static io.jmix.masquerade.Conditions.ENABLED
import static io.jmix.masquerade.Conditions.VISIBLE

class ConfirmationDialog extends Composite<ConfirmationDialog> {

    @Wire(path = "optionDialog_yes")
    public Button yes

    @Wire(path = "optionDialog_no")
    public Button no

    void confirmChanges() {
        yes.shouldBe(VISIBLE)
                .shouldBe(ENABLED)
                .click()
    }
}
