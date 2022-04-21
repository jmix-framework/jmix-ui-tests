package io.jmix.tests.ui.screen.administration.emailtemplates.editor.template.common.tab

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class EditorFromReportAttachmentsTab extends Composite<EditorFromReportAttachmentsTab> implements TableActionsTrait {

    public static final String REPORT_ATTACHMENTS_TABLE_J_TEST_ID = "reportsTable"
    public static final String FILE_ATTACHMENTS_TABLE_J_TEST_ID = "filesTable"

    @Wire(path = "add")
    Button addReportAttach

    @Wire(path = ["reportAttachmentBox","remove"])
    Button removeReportAttach

    @Wire(path = "create")
    Button uploadFileAttach

    @Wire(path = "edit")
    Button editFileAttach

    @Wire(path = ["filesTable_composition","remove"])
    Button removeFileAttach

    @Wire(path = "download")
    Button downloadFileAttach
}
