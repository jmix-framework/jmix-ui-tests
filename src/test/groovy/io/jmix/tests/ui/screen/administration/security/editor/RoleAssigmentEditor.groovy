package io.jmix.tests.ui.screen.administration.security.editor

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class RoleAssigmentEditor extends Composite<RoleAssigmentEditor> implements TableActionsTrait {
    @Wire
    Button addResourceRoleBtn

    @Wire
    Button addRowLevelRoleBtn

    @Wire
    Button commit
}
