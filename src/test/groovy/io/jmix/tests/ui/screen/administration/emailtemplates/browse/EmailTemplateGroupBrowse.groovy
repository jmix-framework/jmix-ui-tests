package io.jmix.tests.ui.screen.administration.emailtemplates.browse

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class EmailTemplateGroupBrowse extends Composite<EmailTemplateGroupBrowse> implements TableActionsTrait {

    public static final String TEMPLATE_GROUPS_TABLE_J_TEST_ID = "templateGroupsTable"

    @Wire
    Button createBtn

    @Wire
    Button editBtn

    @Wire
    Button removeBtn
}
