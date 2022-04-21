package io.jmix.tests.ui.screen.administration.emailtemplates.browse

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class EmailTemplateBlockBrowse extends Composite<EmailTemplateBlockBrowse> implements TableActionsTrait {

    public static final String TEMPLATE_BLOCKS_TABLE_J_TEST_ID = "templateBlocksTable"

    @Wire
    Button createBtn

    @Wire
    Button editBtn

    @Wire
    Button removeBtn

    @Wire
    Button groups
}
