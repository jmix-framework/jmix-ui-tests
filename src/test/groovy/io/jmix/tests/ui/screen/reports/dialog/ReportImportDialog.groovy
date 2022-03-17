package io.jmix.tests.ui.screen.reports.dialog

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.FileUploadField
import org.testcontainers.shaded.org.apache.commons.io.FileUtils

class ReportImportDialog extends Composite<ReportImportDialog> {

    @Wire
    FileUploadField fileUploadField

    @Wire
    Button commitBtn

}
