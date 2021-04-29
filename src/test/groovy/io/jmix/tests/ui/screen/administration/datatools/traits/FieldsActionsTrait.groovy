package io.jmix.tests.ui.screen.administration.datatools.traits

import io.jmix.masquerade.component.TextField

import static io.jmix.masquerade.Conditions.EDITABLE
import static io.jmix.masquerade.Conditions.VISIBLE

trait FieldsActionsTrait {

    void fillTextField(TextField field, String value) {
        field.shouldBe(VISIBLE)
                .shouldBe(EDITABLE)
                .setValue(value)
    }
}
