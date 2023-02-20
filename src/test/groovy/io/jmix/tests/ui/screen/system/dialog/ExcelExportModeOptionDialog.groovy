package io.jmix.tests.ui.screen.system.dialog

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button

class ExcelExportModeOptionDialog extends Composite<ExcelExportModeOptionDialog> {

    @Wire(path = "optionDialog_ExportMode.ALL_ROWS")
    Button allRows

    @Wire(path = "optionDialog_ExportMode.CURRENT_PAGE")
    Button currentPage

    @Wire(path = "optionDialog_ExportMode.SELECTED_ROWS")
    Button selectedRows

    @Wire(path = "optionDialog_cancel")
    Button cancel
}
