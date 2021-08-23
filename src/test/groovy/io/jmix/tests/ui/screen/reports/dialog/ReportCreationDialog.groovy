package io.jmix.tests.ui.screen.reports.dialog

import com.codeborne.selenide.Condition
import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.OptionsGroup
import io.jmix.masquerade.component.TextField

import static io.jmix.masquerade.Conditions.ENABLED
import static io.jmix.masquerade.Conditions.VISIBLE

class ReportCreationDialog extends Composite<ReportCreationDialog> {

    @Wire
    ComboBox entityField

    @Wire
    ComboBox templateFileTypeField

    @Wire
    OptionsGroup reportTypeGenerateField

    @Wire
    Button nextBtn

    @Wire
    TextField reportNameField

    void selectEntity(String entityName) {
        entityField.shouldBe(VISIBLE)
                .shouldBe(ENABLED)
                .openOptionsPopup()
                .select(entityName)
    }

    void checkReportName(String reportName) {
        reportNameField.shouldHave(Condition.value(reportName))
    }
}
