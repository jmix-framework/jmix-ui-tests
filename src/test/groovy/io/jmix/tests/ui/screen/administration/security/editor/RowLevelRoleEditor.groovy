package io.jmix.tests.ui.screen.administration.security.editor

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.OptionsGroup
import io.jmix.masquerade.component.TabSheet
import io.jmix.masquerade.component.TextField
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class RowLevelRoleEditor extends Composite<RowLevelRoleEditor> implements TableActionsTrait{
    @Wire
    Button create

    @Wire
    Button edit

    @Wire
    Button remove

    @Wire(path = "windowCommitAndClose")
    Button ok

    @Wire(path = "windowClose")
    Button cancel

    @Wire
    TextField nameField

    @Wire
    TextField codeField

    @Wire
    TabSheet policiesTabSheet

    void openChildRolesTab() {
        policiesTabSheet.getTab("childRolesTab").select()
    }
}
