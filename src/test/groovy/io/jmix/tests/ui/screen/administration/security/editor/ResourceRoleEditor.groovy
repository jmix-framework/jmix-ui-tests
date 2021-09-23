package io.jmix.tests.ui.screen.administration.security.editor

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.OptionsGroup
import io.jmix.masquerade.component.PopupButton
import io.jmix.masquerade.component.TabSheet
import io.jmix.masquerade.component.TextField
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class ResourceRoleEditor extends Composite<ResourceRoleEditor> implements TableActionsTrait {

    @Wire(path = "createResourcePolicyPopupBtn")
    PopupButton createResourceBtn

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
    OptionsGroup scopesField

    @Wire
    TabSheet policiesTabSheet

    void openChildRolesTab() {
        policiesTabSheet.getTab("childRolesTab").select()
    }

}
