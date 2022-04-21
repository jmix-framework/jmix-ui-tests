package io.jmix.tests.ui.screen.administration.emailtemplates.editor.template.fromreport.tabs

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.CheckBox
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.TextField

class EditorFromReportMainTab extends Composite<EditorFromReportMainTab> {

    @Wire
    CheckBox useReportSubject

    @Wire
    ComboBox emailBody

}
