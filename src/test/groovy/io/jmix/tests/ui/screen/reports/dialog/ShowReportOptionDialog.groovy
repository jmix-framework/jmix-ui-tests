package io.jmix.tests.ui.screen.reports.dialog

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button

class ShowReportOptionDialog extends Composite<ShowReportOptionDialog> {

    @Wire(path = "optionDialog_ExportMode.ALL_ROWS")
    Button allRows

    @Wire(path = "optionDialog_ExportMode.CURRENT_PAGE")
    Button currentPage

    @Wire(path = "optionDialog_cancel")
    Button cancel
}
