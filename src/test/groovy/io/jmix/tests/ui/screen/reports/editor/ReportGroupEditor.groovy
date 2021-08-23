package io.jmix.tests.ui.screen.reports.editor

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.TextField

import static io.jmix.masquerade.Conditions.EDITABLE
import static io.jmix.masquerade.Conditions.REQUIRED

class ReportGroupEditor extends Composite<ReportGroupEditor> {

    @Wire(path = "title")
    TextField titleInput

    @Wire(path = "code")
    TextField codeInput

    @Wire(path = "windowCommitAndClose")
    Button ok

    void setGroupTitle(String groupTitle) {
        titleInput.shouldBe(REQUIRED)
        titleInput.shouldBe(EDITABLE)
        titleInput.setValue(groupTitle)
    }

    void setSystemCode(String systemCodeTitle) {
        codeInput.shouldBe(EDITABLE)
        codeInput.setValue(systemCodeTitle)
    }

}
