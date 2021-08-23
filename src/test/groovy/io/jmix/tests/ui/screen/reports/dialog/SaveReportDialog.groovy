package io.jmix.tests.ui.screen.reports.dialog

import com.codeborne.selenide.Selenide
import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import static io.jmix.masquerade.Conditions.*


class SaveReportDialog extends Composite<SaveReportDialog> {

    @Wire
    Button saveBtn

    void save() {
        saveBtn.shouldBe(VISIBLE)
                .shouldBe(ENABLED)
                .click()
        Selenide.sleep(5000)
    }
}
