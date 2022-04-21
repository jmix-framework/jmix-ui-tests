package io.jmix.tests.ui.screen.administration.emailtemplates.browse

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class EmailTemplateBlockGroupBrowse extends Composite<EmailTemplateBlockGroupBrowse> implements TableActionsTrait {

    public static final String TEMPLATE_BLOCK_GROUPS_TABLE_J_TEST_ID = "templateBlockGroupsTable"

    @Wire
    Button createBtn

    @Wire
    Button editBtn

    @Wire
    Button removeBtn
}
