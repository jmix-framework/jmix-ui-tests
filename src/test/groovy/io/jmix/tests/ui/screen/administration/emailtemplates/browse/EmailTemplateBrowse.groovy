package io.jmix.tests.ui.screen.administration.emailtemplates.browse

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.PopupButton
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class EmailTemplateBrowse extends Composite<EmailTemplateBrowse> implements TableActionsTrait {

    public static final String EMAIL_TEMPLATES_TABLE_J_TEST_ID = "emailTemplatesTable"

    @Wire
    PopupButton createBtn

    @Wire
    Button editBtn

    @Wire
    Button removeBtn

    @Wire
    Button sendBtn

    @Wire
    Button groupsBtn

    @Wire
    Button blocksBtn
}
