package io.jmix.tests.ui.screen.application.individual

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.TextField

import static io.jmix.masquerade.Conditions.ENABLED

class IndividualEditor extends Composite<IndividualEditor> {
    @Wire(path = 'gradeField')
    ComboBox grade

    @Wire(path = 'firstNameField')
    TextField firstName

    @Wire(path = 'lastNameField')
    TextField lastName

    @Wire(path = 'emailField')
    TextField email

    @Wire
    Button commitAndCloseBtn

    void saveChanges() {
        commitAndCloseBtn.shouldBe(ENABLED)
                .click()
    }
}
