package io.jmix.tests.ui.screen.administration.security.editor

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.*
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
    TextArea descriptionField

    @Wire
    OptionsGroup scopesField

    @Wire
    TabSheet policiesTabSheet

    void openChildRolesTab() {
        policiesTabSheet.getTab("childRolesTab").select()
    }

}
