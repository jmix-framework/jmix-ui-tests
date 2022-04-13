package io.jmix.tests.ui.screen.administration.webdav.dialog

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.TextField

class WebdavCollectionDialog extends Composite<WebdavCollectionDialog> {
    @Wire
    TextField nameField

    @Wire(path = 'windowCommitAndClose')
    Button ok

    @Wire(path = 'windowClose')
    Button cancel
}
