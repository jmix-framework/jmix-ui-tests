package io.jmix.tests.ui.screen.administration.datatools.editors

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.TextField

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.value
import static io.jmix.masquerade.Selectors.byText

class DiscountsEditor extends Composite<DiscountsEditor> {

    @Wire(path = "windowCommitAndClose")
    Button ok

    @Wire(path = "windowClose")
    Button cancel

    @Wire
    ComboBox grade

    @Wire
    TextField value

    void selectGrade(String gradeName) {
        grade.shouldBe(VISIBLE)
                .openOptionsPopup()
                .select(byText(gradeName))
                .shouldHave(value(gradeName))
    }

}
