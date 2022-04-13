package io.jmix.tests.ui.screen.administration.webdav.browse

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.FileUploadField
import io.jmix.masquerade.component.TextField
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class WebDAVDocumentBrowse extends Composite<WebDAVDocumentBrowse> implements TableActionsTrait {

    @Wire
    FileUploadField uploadBtn

    @Wire
    Button manageVersionsBtn

    @Wire
    Button removeBtn

    @Wire
    Button lockBtn

    @Wire
    Button createCollectionBtn

    @Wire
    Button renameCollectionBtn

    @Wire
    TextField filePathField

    @Wire
    Button applyFilePathBtn

    @Wire
    Button enableVersioningBtn

    @Wire
    Button disableVersioningBtn

}
