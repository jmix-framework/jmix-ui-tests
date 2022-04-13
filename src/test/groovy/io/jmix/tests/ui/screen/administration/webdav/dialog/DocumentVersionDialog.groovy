package io.jmix.tests.ui.screen.administration.webdav.dialog

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.FileUploadField
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class DocumentVersionDialog extends Composite<DocumentVersionDialog> implements TableActionsTrait {

    @Wire(path = "commitAndCloseBtn")
    Button ok

    @Wire
    FileUploadField upload

    @Wire
    Button copyToHeadButton

    @Wire
    Button refresh

    @Wire
    Button closeBtn
}