package io.jmix.tests.ui.screen.role

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.Table

class RoleModelLookup extends Composite<RoleModelLookup> {

    @Wire
    Table roleModelsTable
    @Wire
    Button lookupSelectAction
    @Wire
    Button lookupCancelAction
}
